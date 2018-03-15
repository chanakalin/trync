/**#define STATUS_GREEN	GPIO_NUM_23
#define STATUS_RED		GPIO_NUM_22
**/
#define STATUS_GREEN	GPIO_NUM_22
#define STATUS_RED		GPIO_NUM_23
#define STATUS_BUZZER	GPIO_NUM_21

/**
 * lightup = 0 ; turn off green status
 * lightup >= 1 ; turn on green status
 */

void status_init() {
	gpio_pad_select_gpio (STATUS_GREEN);
	gpio_set_direction(STATUS_GREEN, GPIO_MODE_OUTPUT);
	gpio_set_level(STATUS_GREEN, 0);
	gpio_pad_select_gpio (STATUS_RED);
	gpio_set_direction(STATUS_RED, GPIO_MODE_OUTPUT);
	gpio_set_level(STATUS_RED, 0);
	gpio_pad_select_gpio (STATUS_BUZZER);
	gpio_set_direction(STATUS_BUZZER, GPIO_MODE_OUTPUT);
	gpio_set_level(STATUS_BUZZER, 0);
}

void status_green(uint8_t lightup) {
	if (lightup) {
		gpio_set_level(STATUS_GREEN, 1);
	} else {
		gpio_set_level(STATUS_GREEN, 0);
	}
}

void status_red(uint8_t lightup) {
	if (lightup) {
		gpio_set_level(STATUS_RED, 1);
	} else {
		gpio_set_level(STATUS_RED, 0);
	}
}


void status_buzzer(uint8_t activate) {
	if (activate) {
		gpio_set_level(STATUS_BUZZER, 1);
	} else {
		gpio_set_level(STATUS_BUZZER, 0);
	}
}
