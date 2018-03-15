#include <esp_log.h>
#include <freertos/FreeRTOS.h>
#include <stdio.h>
#include <driver/i2c.h>

#include <trynca-i2c.h>
#include <si1145.h>


//init SI1145
esp_err_t si1145_init() {
	int ret;
	uint8_t partid, status;
	ret = i2c_write_byte(SI1145_ADDRESS, SI1145_REGISTER_PART_ID);
	if (ret != ESP_OK) {
		return ret;
	}
	ret = i2c_read_byte(SI1145_ADDRESS, &partid);
	ESP_LOGI(SI1145_TAG, "part id = %x", partid);
	if(partid==0b01000101){//page 31 datasheet


	}

	return ret;
}
