#include <string.h>

#include <esp_event.h>
#include <esp_event_loop.h>
#include <esp_system.h>
#include <esp_wifi.h>
#include <esp_err.h>
#include <esp_log.h>
#include <freertos/FreeRTOS.h>
#include <lwip/sockets.h>
#include <nvs_flash.h>
#include "driver/uart.h"
#include <cJSON.h>
#include <sdkconfig.h>

#include <trynca.h>
#include <trynca-i2c.h>
#include <bme280.h>
#include <ssd1306.h>
#include <status-indicators.h>
#include <pairing-gateway.h>
#include <mqtt_paho_trynca.h>




//wifi connection event handler
static esp_err_t event_handler(void *ctx, system_event_t *event) {
	switch (event->event_id) {
	case SYSTEM_EVENT_STA_START:
		esp_wifi_connect();
		break;

	case SYSTEM_EVENT_STA_GOT_IP:
		ESP_LOGI(TAG, "Connected to to SSID '%s'", wifi_ssid)
		;
		status_green(1);
		status_red(0);
		vTaskDelay(2000 / portTICK_PERIOD_MS);
		xTaskCreatePinnedToCore(&task_paho, "task_paho", 8048, NULL, 5, NULL,
				0);
		break;

	case SYSTEM_EVENT_STA_DISCONNECTED:
		//esp_mqtt_stop();
		ESP_LOGW(TAG, "Disconnected from SSID '%s'", wifi_ssid)
		;
		status_green(0);
		status_red(1);
		break;

	default:
		break;
	}

	return ESP_OK;
}

void app_main(void) {
	//SET MQTT Topic
	tryncaMQTTBaseTopic =str_concat("/TRYNCA/",CONFIG_TRYNCA_SERIAL);


	//initialize NVS
	esp_err_t err = nvs_flash_init();
	if (err == ESP_ERR_NVS_NO_FREE_PAGES) {
		// NVS partition was truncated and needs to be erased
		// Retry nvs_flash_init
		ESP_ERROR_CHECK(nvs_flash_erase());
		err = nvs_flash_init();
	}
	ESP_ERROR_CHECK(err);

	//initialize status leds and buzzer
	status_init();
	pairing_init();
	i2c_init();
	//initilize modules
	err = bme280_init();
	if(err==ESP_OK){
		modulesAvailable |= (1<<MODULE_BME280);
		ESP_LOGI(TAG,"Module BM280 available");
	}else{
		modulesAvailable &= ~(1<<MODULE_BME280);
		ESP_LOGW(TAG,"Module BM280 not available");
	}


	ESP_LOGI(TAG, "Pair level = %d (%d)", gpio_get_level (PAIR_BUTTON),
			isPairingModeSelected());

	if (isPairingModeSelected()) {	        //pair with gateway
		status_green(1);
		status_red(1);
		pairWithGateway();
	} else {	        //start TRYNCA
		status_green(0);
		status_red(1);
		//read paired parameters
		readPairedParameters();
		status_green(1);
		status_red(0);
	}

	//connect to wifi in station mmode
	//init tcpip adapter
	tcpip_adapter_init();
	ESP_ERROR_CHECK(esp_event_loop_init(event_handler, NULL));
	wifi_init_config_t wifi_init_cfg = WIFI_INIT_CONFIG_DEFAULT()
	;

	ESP_ERROR_CHECK(esp_wifi_init(&wifi_init_cfg));
	ESP_ERROR_CHECK(esp_wifi_set_storage(WIFI_STORAGE_RAM));
	//wifi_config_t wifi_config = { .sta = { .ssid = (uint8_t)wifi_ssid, .password =
	//		(uint8_t)wifi_passwd ,}, };
	wifi_config_t wifi_config;
	strcpy((char *) wifi_config.sta.ssid, (char *) wifi_ssid);
	strcpy((char *) wifi_config.sta.password, (char *) wifi_passwd);
	ESP_ERROR_CHECK(esp_wifi_set_mode(WIFI_MODE_STA));
	ESP_ERROR_CHECK(esp_wifi_set_config(ESP_IF_WIFI_STA, &wifi_config));
	err = esp_wifi_start();
	if (err != ESP_OK) {
		ESP_ERROR_CHECK(err);
		ESP_LOGE(TAG, "Failed to connect to SSID '%s'", wifi_ssid);
		status_green(0);
		status_red(1);
	} else {	        //fine and go
		ESP_LOGI(TAG, "Connecting to to SSID '%s'", wifi_ssid);
		status_green(0);
		status_red(1);
	}

	//wait for 60 sec
	vTaskDelay(60000 / portTICK_PERIOD_MS);
	if (tryncaMode == TRYNCA_MODE_IDLE) {	   //if still idle reboot
		ESP_LOGW(TAG, "Rebooting");
	}

	while (1) {
		vTaskDelay(1000 / portTICK_PERIOD_MS);
	}
}
