#define 	TAG	"TRYNCA"
#define 	TRYNCA_SERIAL		CONFIG_TRYNCA_SERIAL

#define 	NVS_STORAGE_PAIR	"pairstorage"
#define 	NVS_WIFI_SSID		"wifissid"
#define		NVS_WIFI_PASSWD		"wifipasswd"
#define		NVS_MQTT_BROKER		"mqttbroker"
#define		NVS_MQTT_USER		"mqttuser"
#define		NVS_MQTT_PASSWD		"mqttpasswd"

#define		TRYNCA_MODE_IDLE	0
#define 	TRYNCA_MODE_ONLINE	1
#define		TRYNCA_MODE_OFFLINE	2

#define		MODULE_BME280		0
#define		MODULE_GUVA_S12SD	1
#define		MODULE_BH1750		2
#define		MODULE_SSD1306		3
#define		MODULE_OPERATOR		4



//vars
char* wifi_ssid;
char* wifi_passwd;
char* mqtt_broker;
char* mqtt_user;
char* mqtt_passwd;
uint8_t tryncaMode = TRYNCA_MODE_IDLE;
TaskHandle_t modeSwitchWaitTask = NULL;
TaskHandle_t tryncPahoMQTTTask = NULL;
uint8_t modulesAvailable = 0;
uint16_t dataFeedingInterval=120;//in seconds


//pairing options
char pair_wifi_ssid[25];
char pair_wifi_passwd[25];

//string concat
char * str_concat(char * one, char * two) {
	size_t sizeA = strlen(one);
	size_t sizeB = strlen(two);
	size_t size = sizeof(char) * (sizeA + sizeB + 1); //Size of our new memory block large enough to contain both a and b.
													  //Leave additional space for null terminator
	char * result = malloc(size);                 //Actually allocate new memory
	memcpy(result, one, sizeA);                //Copy a into the first half of c
	memcpy(result + sizeA, two, sizeB);            //Copy b into the second half
	result[sizeA + sizeB] = '\0';     //Assign null terminator to last character
	return result;
}


//pairing
void pairing_init();
void pairWithGateway();
static void pairing_uart_event_task(void *pvParameters);
void readPairedParameters();
void pairedParamReadErrorNotify();

//status
void status_init();
void status_green(uint8_t lightup);
void status_red(uint8_t lightup);
void status_buzzer(uint8_t activate);

void bme280_task(void *ignore);
void task_paho(void *ignore);
