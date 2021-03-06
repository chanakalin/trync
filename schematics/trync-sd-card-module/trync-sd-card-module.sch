EESchema Schematic File Version 2
LIBS:power
LIBS:device
LIBS:switches
LIBS:relays
LIBS:motors
LIBS:transistors
LIBS:conn
LIBS:linear
LIBS:regul
LIBS:74xx
LIBS:cmos4000
LIBS:adc-dac
LIBS:memory
LIBS:xilinx
LIBS:microcontrollers
LIBS:dsp
LIBS:microchip
LIBS:analog_switches
LIBS:motorola
LIBS:texas
LIBS:intel
LIBS:audio
LIBS:interface
LIBS:digital-audio
LIBS:philips
LIBS:display
LIBS:cypress
LIBS:siliconi
LIBS:opto
LIBS:atmel
LIBS:contrib
LIBS:valves
LIBS:trync-sd-card-module-cache
EELAYER 25 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 1
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L Conn_01x06 J1
U 1 1 5A8F030B
P 3300 2600
F 0 "J1" H 3300 2900 50  0000 C CNN
F 1 "CON" H 3300 2200 50  0000 C CNN
F 2 "Pin_Headers:Pin_Header_Straight_1x06_Pitch2.54mm" H 3300 2600 50  0001 C CNN
F 3 "" H 3300 2600 50  0001 C CNN
	1    3300 2600
	-1   0    0    1   
$EndComp
$Comp
L Micro_SD_Card J2
U 1 1 5A8F036A
P 7150 2600
F 0 "J2" H 6500 3200 50  0000 C CNN
F 1 "Micro_SD_Card" H 7800 3200 50  0000 R CNN
F 2 "Kicad-MicroSD:Conn_uSDcard" H 8300 2900 50  0001 C CNN
F 3 "" H 7150 2600 50  0001 C CNN
	1    7150 2600
	1    0    0    -1  
$EndComp
$Comp
L R R1
U 1 1 5A8F03D7
P 6100 2350
F 0 "R1" V 6180 2350 50  0000 C CNN
F 1 "10K" V 6100 2350 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0204_L3.6mm_D1.6mm_P7.62mm_Horizontal" V 6030 2350 50  0001 C CNN
F 3 "" H 6100 2350 50  0001 C CNN
	1    6100 2350
	1    0    0    -1  
$EndComp
$Comp
L VCC #PWR01
U 1 1 5A8F05C5
P 3500 2000
F 0 "#PWR01" H 3500 1850 50  0001 C CNN
F 1 "VCC" H 3500 2150 50  0000 C CNN
F 2 "" H 3500 2000 50  0001 C CNN
F 3 "" H 3500 2000 50  0001 C CNN
	1    3500 2000
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR02
U 1 1 5A8F05DF
P 3500 3050
F 0 "#PWR02" H 3500 2800 50  0001 C CNN
F 1 "GND" H 3500 2900 50  0000 C CNN
F 2 "" H 3500 3050 50  0001 C CNN
F 3 "" H 3500 3050 50  0001 C CNN
	1    3500 3050
	1    0    0    -1  
$EndComp
Text GLabel 3800 2400 2    60   Input ~ 0
CS
Text GLabel 3800 2700 2    60   Input ~ 0
MOSI
Text GLabel 3800 2600 2    60   Input ~ 0
MISO
Text GLabel 3800 2500 2    60   Input ~ 0
SCK
Wire Wire Line
	3500 2300 3500 2000
Wire Wire Line
	3800 2400 3500 2400
Wire Wire Line
	3500 2500 3800 2500
Wire Wire Line
	3800 2600 3500 2600
Wire Wire Line
	3500 2700 3800 2700
Wire Wire Line
	3500 2800 3500 3050
Text GLabel 5800 2400 0    60   Input ~ 0
CS
Text GLabel 5800 2700 0    60   Input ~ 0
SCK
Text GLabel 5800 2900 0    60   Input ~ 0
MISO
Text GLabel 5800 2500 0    60   Input ~ 0
MOSI
$Comp
L VCC #PWR03
U 1 1 5A8F0700
P 6000 1750
F 0 "#PWR03" H 6000 1600 50  0001 C CNN
F 1 "VCC" H 6000 1900 50  0000 C CNN
F 2 "" H 6000 1750 50  0001 C CNN
F 3 "" H 6000 1750 50  0001 C CNN
	1    6000 1750
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR04
U 1 1 5A8F0714
P 6100 3300
F 0 "#PWR04" H 6100 3050 50  0001 C CNN
F 1 "GND" H 6100 3150 50  0000 C CNN
F 2 "" H 6100 3300 50  0001 C CNN
F 3 "" H 6100 3300 50  0001 C CNN
	1    6100 3300
	1    0    0    -1  
$EndComp
Wire Wire Line
	6100 2200 6100 1900
Wire Wire Line
	6100 1900 6000 1900
Wire Wire Line
	6000 1750 6000 2600
Wire Wire Line
	5800 2500 6250 2500
Connection ~ 6100 2500
Wire Wire Line
	6000 2600 6250 2600
Connection ~ 6000 1900
Wire Wire Line
	6250 2400 5800 2400
Wire Wire Line
	6250 2700 5800 2700
Wire Wire Line
	6250 2900 5800 2900
Wire Wire Line
	6250 2800 6100 2800
Wire Wire Line
	6100 2800 6100 3300
Wire Wire Line
	7950 3300 7950 3200
Wire Wire Line
	6200 3300 7950 3300
Wire Wire Line
	6200 3300 6200 3150
Wire Wire Line
	6200 3150 6100 3150
Connection ~ 6100 3150
$EndSCHEMATC
