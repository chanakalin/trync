#define 	PAIR_BUTTON		GPIO_NUM_4
#define 	UART_GPIO_TXD	(GPIO_NUM_17)
#define		UART_GPIO_RXD	(GPIO_NUM_16)
#define 	BUF_SIZE 		(1024)
static QueueHandle_t uart0_queue;
uint8_t configDataReceived = 0;

void pairing_init() {
	gpio_set_direction(PAIR_BUTTON, GPIO_MODE_INPUT);
	gpio_set_pull_mode(4, GPIO_PULLUP_ONLY);
	vTaskDelay(1000 / portTICK_PERIOD_MS); //wait for a while
}

/*pairing with a gateway
 * if pressed gpio level = 0 and return 1 else return 0
 */
uint8_t isPairingModeSelected() {
	if (gpio_get_level (PAIR_BUTTON)) {
		return 0;
	} else {
		return 1;
	}
}

/**
 * Pairing with gateway
 *
 * Listen to pattern match event (start from char 'T') from Gateway
 *
 * Write "PAIR" to gateway every 10 seconds until pairing get completed
 */
void pairWithGateway() {
	uart_config_t uart_config = { .baud_rate = 38400, .data_bits =
			UART_DATA_8_BITS, .parity = UART_PARITY_DISABLE, .stop_bits =
			UART_STOP_BITS_1, .flow_ctrl = UART_HW_FLOWCTRL_DISABLE };
	uart_param_config(UART_NUM_1, &uart_config);
	// Set UART pins using UART0 default pins i.e. no changes
	uart_set_pin(UART_NUM_1, UART_GPIO_TXD, UART_GPIO_RXD, UART_PIN_NO_CHANGE,
			UART_PIN_NO_CHANGE);
	uart_driver_install(UART_NUM_1, BUF_SIZE * 2, BUF_SIZE * 2, 10,
			&uart0_queue, 0);

	// Set uart pattern detection function
	//uart_enable_pattern_det_intr(UART_NUM_1, 'T', 1, 10000, 10, 10);

	//flush
	uart_flush (UART_NUM_1);

	// Create a task to handle uart event from ISR
	xTaskCreate(pairing_uart_event_task, "pairing_uart_event_task", 2048, NULL,
			12, NULL);

	char pairText[1] = "P";
	while (configDataReceived <= 0) {
		uart_write_bytes(UART_NUM_1, pairText, 1);
		ESP_LOGI(TAG, "Sending '%s'", pairText);
		uart_wait_tx_done(UART_NUM_1, 1000 / portTICK_PERIOD_MS);
		vTaskDelay(3000 / portTICK_PERIOD_MS);
	}
}

/**
 * Task for UART events
 */
static void pairing_uart_event_task(void *pvParameters) {
	uart_event_t event;
	size_t buffered_size;
	uint8_t *dtmp = (uint8_t *) malloc(BUF_SIZE);

	uint8_t *datay = (uint8_t *) malloc(BUF_SIZE);
	while (configDataReceived <= 0) {
		int len = uart_read_bytes(UART_NUM_1, datay, BUF_SIZE,
				100 / portTICK_RATE_MS);
		if (len > 0) {
			ESP_LOGI(TAG, "uart read : %d", len);
			configDataReceived = 1;
		}
	}
	ESP_LOGI(TAG, "uart read : %s", (const char *) datay);

	//get ready NVS
	nvs_handle nvs_handle;
	esp_err_t err = nvs_open(NVS_STORAGE_PAIR, NVS_READWRITE, &nvs_handle);
	if (err != ESP_OK) {
		ESP_LOGE(TAG, "Error (%d) opening NVS handle!\n", err);
	} else {	//save values to NVS
		//const char *xy = strstr((const char *) datay,(const char *)"TRYNC");
		//sample = TRYNC|kura-gw.trync.org1|kura-gw.trync.org|172.16.1.1|trynca-user|trynca-passwd|
		const char *xy = (const char *) datay;
		const char *del = "|";
		char *token;
		uint8_t tokenCount = 0;
		token = strtok(xy, del);
		while (token != NULL) {
			switch (tokenCount) {
			case 1:	//wifissid
				err = nvs_set_str(nvs_handle, NVS_WIFI_SSID, token);
				if (err == ESP_OK) {
					ESP_LOGI(TAG, "Saved WIFI SSID =  %s", token);
				} else {
					ESP_LOGE(TAG, "Saving error WIFI SSID =  %s (err=%d)",
							token, err);
				}
				break;
			case 2:	//wifipasswd
				err = nvs_set_str(nvs_handle, NVS_WIFI_PASSWD, token);
				if (err == ESP_OK) {
					ESP_LOGI(TAG, "Saved WIFI Passwd =  %s", token);
				} else {
					ESP_LOGE(TAG, "Saving error WIFI Passwd =  %s (err=%d)",
							token, err);
				}
				break;
			case 3:	//mqtt broker
				err = nvs_set_str(nvs_handle, NVS_MQTT_BROKER, token);
				if (err == ESP_OK) {
					ESP_LOGI(TAG, "Saved MQTT Broker =  %s", token);
				} else {
					ESP_LOGE(TAG, "Saving error MQTT Broker =  %s (err=%d)",
							token, err);
				}
				break;
			case 4:	//mqtt user
				err = nvs_set_str(nvs_handle, NVS_MQTT_USER, token);
				if (err == ESP_OK) {
					ESP_LOGI(TAG, "Saved MQTT User =  %s", token);
				} else {
					ESP_LOGE(TAG, "Saving error MQTT User =  %s (err=%d)",
							token, err);
				}
				break;
			case 5:	//mqtt password
				err = nvs_set_str(nvs_handle, NVS_MQTT_PASSWD, token);
				if (err == ESP_OK) {
					ESP_LOGI(TAG, "Saved MQTT Passwd =  %s", token);
				} else {
					ESP_LOGE(TAG, "Saving error MQTT Passwd =  %s (err=%d)",
							token, err);
				}
				break;
			}
			token = strtok(NULL, del);
			tokenCount++;
		}
	}

	//reboot after button released
	while (isPairingModeSelected()) {
		status_green(1);
		vTaskDelay(100 / portTICK_PERIOD_MS);
		status_green(0);
		vTaskDelay(100 / portTICK_PERIOD_MS);
	}
	//reboot
	esp_restart();

	//free(dtmp);
	//dtmp = NULL;
	//vTaskDelete (NULL);
}

/**
 * Read paired paramaters
 */
void readPairedParameters() {
	nvs_handle nvs_handle;
	esp_err_t err = nvs_open(NVS_STORAGE_PAIR, NVS_READONLY, &nvs_handle);
	if (err != ESP_OK) {
		ESP_LOGE(TAG, "Error (%d) opening NVS handle!\n", err);
	} else {
		size_t required_size;
		//WIFI SSID
		nvs_get_str(nvs_handle, NVS_WIFI_SSID, NULL, &required_size);
		wifi_ssid = malloc(required_size);
		err = nvs_get_str(nvs_handle, NVS_WIFI_SSID, wifi_ssid, &required_size);
		switch (err) {
		case ESP_OK:
			ESP_LOGI(TAG, " WIFI SSID = %s\n", wifi_ssid);
			break;
		case ESP_ERR_NVS_NOT_FOUND:
			ESP_LOGE(TAG, "WIFI SSID is not initialized yet!\n");
			pairedParamReadErrorNotify();
			break;
		default:
			ESP_LOGE(TAG, "Error (%d) reading!\n", err);
			pairedParamReadErrorNotify();
		}
		//WIFI PASSWD
		nvs_get_str(nvs_handle, NVS_WIFI_PASSWD, NULL, &required_size);
		wifi_passwd = malloc(required_size);
		err = nvs_get_str(nvs_handle, NVS_WIFI_PASSWD, wifi_passwd,
				&required_size);
		switch (err) {
		case ESP_OK:
			ESP_LOGI(TAG, " WIFI PASSWD = %s\n", wifi_passwd);
			break;
		case ESP_ERR_NVS_NOT_FOUND:
			ESP_LOGE(TAG, "WIFI PASSWD is not initialized yet!\n");
			pairedParamReadErrorNotify();
			break;
		default:
			ESP_LOGE(TAG, "Error (%d) reading!\n", err);
			pairedParamReadErrorNotify();
		}
		//MQTT broker
		nvs_get_str(nvs_handle, NVS_MQTT_BROKER, NULL, &required_size);
		mqtt_broker = malloc(required_size);
		err = nvs_get_str(nvs_handle, NVS_MQTT_BROKER, mqtt_broker,
				&required_size);
		switch (err) {
		case ESP_OK:
			ESP_LOGI(TAG, " MQTT Broker = %s\n", mqtt_broker);
			break;
		case ESP_ERR_NVS_NOT_FOUND:
			ESP_LOGE(TAG, "MQTT Broker is not initialized yet!\n");
			pairedParamReadErrorNotify();
			break;
		default:
			ESP_LOGE(TAG, "Error (%d) reading!\n", err);
			pairedParamReadErrorNotify();
		}
		//MQTT user
		nvs_get_str(nvs_handle, NVS_MQTT_USER, NULL, &required_size);
		mqtt_user = malloc(required_size);
		err = nvs_get_str(nvs_handle, NVS_MQTT_USER, mqtt_user, &required_size);
		switch (err) {
		case ESP_OK:
			ESP_LOGI(TAG, " MQTT User = %s\n", mqtt_user);
			break;
		case ESP_ERR_NVS_NOT_FOUND:
			ESP_LOGE(TAG, "MQTT User is not initialized yet!\n");
			pairedParamReadErrorNotify();
			break;
		default:
			ESP_LOGE(TAG, "Error (%d) reading!\n", err);
			pairedParamReadErrorNotify();
		}
		//MQTT passwd
		nvs_get_str(nvs_handle, NVS_MQTT_PASSWD, NULL, &required_size);
		mqtt_passwd = malloc(required_size);
		err = nvs_get_str(nvs_handle, NVS_MQTT_PASSWD, mqtt_passwd, &required_size);
		switch (err) {
		case ESP_OK:
			ESP_LOGI(TAG, " MQTT Passwd = %s\n", mqtt_passwd);
			break;
		case ESP_ERR_NVS_NOT_FOUND:
			ESP_LOGE(TAG, "MQTT Passwd is not initialized yet!\n");
			pairedParamReadErrorNotify();
			break;
		default:
			ESP_LOGE(TAG, "Error (%d) reading!\n", err);
			pairedParamReadErrorNotify();
		}
	}
}

void pairedParamReadErrorNotify() {
	status_green(0);
	uint8_t i = 0;
	while (1) {
		for (i = 0; i < 3; i++) {
			status_red(1);
			vTaskDelay(200 / portTICK_PERIOD_MS);
			status_red(0);
			vTaskDelay(200 / portTICK_PERIOD_MS);
		}
		status_red(1);
		vTaskDelay(1000 / portTICK_PERIOD_MS);
		status_red(0);
		vTaskDelay(3000 / portTICK_PERIOD_MS);
	}
}
