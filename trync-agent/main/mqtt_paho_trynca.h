#include <esp_log.h>
#include <freertos/FreeRTOS.h>
#include <freertos/task.h>
#include <stdio.h>

#include "MQTTClient.h"
#include "sdkconfig.h"

char * tryncaMQTTBaseTopic;

MQTTClient client;

static char tag[] = "mqtt_paho";
static unsigned char sendBuf[1000];
static unsigned char readBuf[1000];
Network network;
//update trynca
static void tryncaUpdateFunction(MessageData *md) {
	ESP_LOGI(tag, "Subscription received!: %.*s", md->topicName->lenstring.len,
			md->topicName->lenstring.data);
	ESP_LOGI(tag, "Message : payloadlen=%d", md->message->payloadlen);

}

void messageArrived(MessageData* data)
{
	ESP_LOGW(tag,"Message arrived on topic \n");
}

void task_paho(void *ignore) {
	ESP_LOGI(tag, "Starting ...");
	int rc;
	NetworkInit(&network);
	ESP_LOGI(tag, "NetworkConnect  ...");
	NetworkConnect(&network, mqtt_broker, 1883);
	//NetworkConnectTLS();
	ESP_LOGI(tag, "MQTTClientInit  ...");
	MQTTClientInit(&client, &network, 1000,            // command_timeout_ms
			sendBuf,         //sendbuf,
			sizeof(sendBuf), //sendbuf_size,
			readBuf,         //readbuf,
			sizeof(readBuf)  //readbuf_size
			);

	MQTTString clientId = MQTTString_initializer;
	clientId.cstring = CONFIG_TRYNCA_SERIAL;

	MQTTPacket_connectData data = MQTTPacket_connectData_initializer;
	data.clientID = clientId;
	data.willFlag = 0;
	data.MQTTVersion = 3;
	data.keepAliveInterval = 0;
	data.cleansession = 1;
	data.username.cstring = mqtt_user;
	data.password.cstring = mqtt_passwd;

	ESP_LOGI(tag, "MQTTConnect  ...");
	rc = MQTTConnect(&client, &data);
	if (rc != SUCCESS) {
		ESP_LOGE(tag, "MQTTConnected: %d", rc);
	}

	//publich birth
	cJSON *birtObject = cJSON_CreateObject();
	//serial
	cJSON_AddItemToObject(birtObject, "serial",
			cJSON_CreateString(CONFIG_TRYNCA_SERIAL));
	//modules
	cJSON *moduleObject = cJSON_CreateObject();
	//bme280
	if (modulesAvailable & (1 << MODULE_BME280)) {
		cJSON_AddItemToObject(moduleObject, "BM280", cJSON_CreateTrue());
	} else {
		cJSON_AddItemToObject(moduleObject, "BM280", cJSON_CreateFalse());
	}
	//BH1750
	if (modulesAvailable & (1 << MODULE_BH1750)) {
		cJSON_AddItemToObject(moduleObject, "BH1750", cJSON_CreateTrue());
	} else {
		cJSON_AddItemToObject(moduleObject, "BH1750", cJSON_CreateFalse());
	}
	//GUVA-S12SD
	if (modulesAvailable & (1 << MODULE_GUVA_S12SD)) {
		cJSON_AddItemToObject(moduleObject, "S12SD", cJSON_CreateTrue());
	} else {
		cJSON_AddItemToObject(moduleObject, "S12SD", cJSON_CreateFalse());
	}
	//SSD1306
	if (modulesAvailable & (1 << MODULE_SSD1306)) {
		cJSON_AddItemToObject(moduleObject, "SSD1306", cJSON_CreateTrue());
	} else {
		cJSON_AddItemToObject(moduleObject, "SSD1306", cJSON_CreateFalse());
	}
	//OPERATOR
	if (modulesAvailable & (1 << MODULE_OPERATOR)) {
		cJSON_AddItemToObject(moduleObject, "OPERATOR", cJSON_CreateTrue());
	} else {
		cJSON_AddItemToObject(moduleObject, "OPERATOR", cJSON_CreateFalse());
	}
	//add to birth object
	cJSON_AddItemToObject(birtObject, "modules", moduleObject);
	//parse to string
	char *jsonstring = cJSON_Print(birtObject);
	ESP_LOGI(TAG, "Sending birth \n%s", jsonstring);

	MQTTMessage message;
	char payload[256];
	message.qos = 1;
	message.retained = 0;
	message.payload = payload;
	sprintf(payload, jsonstring);
	message.payloadlen = strlen(payload);
	if (MQTTPublish(&client, str_concat(tryncaMQTTBaseTopic, "/BIRTH"),
			&message) != ESP_OK) {
		ESP_LOGE(tag, "Birth publish error");
	}

	if ((rc = MQTTSubscribe(&client, "/TRYNCA/#", 2, messageArrived))
			!= 0) {
		ESP_LOGE(TAG,"Return code from MQTT subscribe is %d\n", rc);
	}

	//subscribe to update channel
	rc = MQTTSubscribe(&client, "/#", QOS1, tryncaUpdateFunction);

	if (rc != SUCCESS) {
		ESP_LOGE(tag, "MQTTSubscribe: failed %d\n Switching to offline", rc);
		tryncaMode = TRYNCA_MODE_OFFLINE;
		status_green(0);
		status_red(1);
	} else {
		ESP_LOGI(tag, "MQTTSubscribe: successfull %d", rc);
		tryncaMode = TRYNCA_MODE_ONLINE;
		status_green(1);
		status_red(0);
	}
	while (1) {
		MQTTYield(&client, 1000);
	}
	vTaskDelete(NULL);
}
