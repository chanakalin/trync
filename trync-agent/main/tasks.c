#include <esp_log.h>
#include <freertos/FreeRTOS.h>
#include <freertos/task.h>
#include <stdio.h>
#include "bme280.h"

void bme280_task(void *ignore) {
	while (1) {
		bme280_reading_data readData = bme280_read_values();
				ESP_LOGI(BME280_TAG, "Temp = %.1f0   Humidity = %.0f",
						readData.temperature, readData.humidity);

		vTaskDelay(1000 / portTICK_RATE_MS);
	}
}
