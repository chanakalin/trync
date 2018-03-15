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
LIBS:trync-operator-cache
EELAYER 25 0
EELAYER END
$Descr User 9843 5512
encoding utf-8
Sheet 1 1
Title "Trync Operator Module"
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L Conn_01x04 J1
U 1 1 5A7D8F27
P 850 1250
F 0 "J1" H 850 1450 50  0000 C CNN
F 1 "agent" H 850 950 50  0000 C CNN
F 2 "Pin_Headers:Pin_Header_Straight_1x04_Pitch2.54mm" H 850 1250 50  0001 C CNN
F 3 "" H 850 1250 50  0001 C CNN
	1    850  1250
	-1   0    0    1   
$EndComp
$Comp
L VCC #PWR01
U 1 1 5A7D9079
P 1200 750
F 0 "#PWR01" H 1200 600 50  0001 C CNN
F 1 "VCC" H 1200 900 50  0000 C CNN
F 2 "" H 1200 750 50  0001 C CNN
F 3 "" H 1200 750 50  0001 C CNN
	1    1200 750 
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR02
U 1 1 5A7D908F
P 1200 1600
F 0 "#PWR02" H 1200 1350 50  0001 C CNN
F 1 "GND" H 1200 1450 50  0000 C CNN
F 2 "" H 1200 1600 50  0001 C CNN
F 3 "" H 1200 1600 50  0001 C CNN
	1    1200 1600
	1    0    0    -1  
$EndComp
Text GLabel 1400 1250 2    60   Input ~ 0
SCL
Text GLabel 1400 1150 2    60   Input ~ 0
SDA
$Comp
L Conn_01x05 J2
U 1 1 5A7D9148
P 850 2550
F 0 "J2" H 850 2850 50  0000 C CNN
F 1 "UART" H 850 2250 50  0000 C CNN
F 2 "Pin_Headers:Pin_Header_Straight_1x05_Pitch2.54mm" H 850 2550 50  0001 C CNN
F 3 "" H 850 2550 50  0001 C CNN
	1    850  2550
	-1   0    0    1   
$EndComp
$Comp
L GND #PWR03
U 1 1 5A7D922F
P 1200 2850
F 0 "#PWR03" H 1200 2600 50  0001 C CNN
F 1 "GND" H 1200 2700 50  0000 C CNN
F 2 "" H 1200 2850 50  0001 C CNN
F 3 "" H 1200 2850 50  0001 C CNN
	1    1200 2850
	1    0    0    -1  
$EndComp
$Comp
L VCC #PWR04
U 1 1 5A7D9256
P 1200 2150
F 0 "#PWR04" H 1200 2000 50  0001 C CNN
F 1 "VCC" H 1200 2300 50  0000 C CNN
F 2 "" H 1200 2150 50  0001 C CNN
F 3 "" H 1200 2150 50  0001 C CNN
	1    1200 2150
	1    0    0    -1  
$EndComp
Text GLabel 1850 2750 2    60   Input ~ 0
RESET
Text GLabel 1500 2550 2    60   Input ~ 0
RX
Text GLabel 1500 2450 2    60   Input ~ 0
TX
$Comp
L ATMEGA328-AU U1
U 1 1 5A7D942F
P 3850 2200
F 0 "U1" H 3100 3450 50  0000 L BNN
F 1 "ATMEGA328-AU" H 4250 800 50  0000 L BNN
F 2 "Housings_QFP:TQFP-32_7x7mm_Pitch0.8mm" H 3850 2200 50  0001 C CIN
F 3 "" H 3850 2200 50  0001 C CNN
	1    3850 2200
	1    0    0    -1  
$EndComp
$Comp
L VCC #PWR05
U 1 1 5A7D95F9
P 2850 750
F 0 "#PWR05" H 2850 600 50  0001 C CNN
F 1 "VCC" H 2850 900 50  0000 C CNN
F 2 "" H 2850 750 50  0001 C CNN
F 3 "" H 2850 750 50  0001 C CNN
	1    2850 750 
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR06
U 1 1 5A7D960D
P 2800 3400
F 0 "#PWR06" H 2800 3150 50  0001 C CNN
F 1 "GND" H 2800 3250 50  0000 C CNN
F 2 "" H 2800 3400 50  0001 C CNN
F 3 "" H 2800 3400 50  0001 C CNN
	1    2800 3400
	1    0    0    -1  
$EndComp
Text GLabel 5200 2450 2    60   Input ~ 0
SCL
Text GLabel 5200 2350 2    60   Input ~ 0
SDA
Text GLabel 5200 2550 2    60   Input ~ 0
RESET
$Comp
L C C1
U 1 1 5A7D9850
P 1500 2750
F 0 "C1" H 1525 2850 50  0000 L CNN
F 1 "100n" H 1525 2650 50  0000 L CNN
F 2 "Capacitors_THT:C_Disc_D4.3mm_W1.9mm_P5.00mm" H 1538 2600 50  0001 C CNN
F 3 "" H 1500 2750 50  0001 C CNN
	1    1500 2750
	0    1    1    0   
$EndComp
Text GLabel 5200 2700 2    60   Input ~ 0
RX
Text GLabel 5200 2800 2    60   Input ~ 0
TX
$Comp
L C C2
U 1 1 5A7DA130
P 6200 1700
F 0 "C2" H 6225 1800 50  0000 L CNN
F 1 "0.22n" H 6225 1600 50  0000 L CNN
F 2 "Capacitors_THT:C_Disc_D3.8mm_W2.6mm_P2.50mm" H 6238 1550 50  0001 C CNN
F 3 "" H 6200 1700 50  0001 C CNN
	1    6200 1700
	0    1    1    0   
$EndComp
$Comp
L C C3
U 1 1 5A7DA169
P 6200 2000
F 0 "C3" H 6225 2100 50  0000 L CNN
F 1 "0.22n" H 6225 1900 50  0000 L CNN
F 2 "Capacitors_THT:C_Disc_D3.8mm_W2.6mm_P2.50mm" H 6238 1850 50  0001 C CNN
F 3 "" H 6200 2000 50  0001 C CNN
	1    6200 2000
	0    1    1    0   
$EndComp
$Comp
L Crystal Y1
U 1 1 5A7DA1AE
P 5850 1850
F 0 "Y1" H 5850 2000 50  0000 C CNN
F 1 "16MHz" H 5850 1700 50  0000 C CNN
F 2 "Crystals:Crystal_HC52-8mm_Vertical" H 5850 1850 50  0001 C CNN
F 3 "" H 5850 1850 50  0001 C CNN
	1    5850 1850
	0    1    1    0   
$EndComp
$Comp
L GND #PWR07
U 1 1 5A7DA52A
P 6350 2200
F 0 "#PWR07" H 6350 1950 50  0001 C CNN
F 1 "GND" H 6350 2050 50  0000 C CNN
F 2 "" H 6350 2200 50  0001 C CNN
F 3 "" H 6350 2200 50  0001 C CNN
	1    6350 2200
	1    0    0    -1  
$EndComp
$Comp
L 2SC1815 Q1
U 1 1 5A7DABCC
P 7250 1650
F 0 "Q1" H 7450 1725 50  0000 L CNN
F 1 "2SC1815" H 7450 1650 50  0000 L CNN
F 2 "TO_SOT_Packages_THT:TO-92_Molded_Narrow" H 7450 1575 50  0001 L CIN
F 3 "" H 7250 1650 50  0001 L CNN
	1    7250 1650
	1    0    0    -1  
$EndComp
$Comp
L 2SC1815 Q2
U 1 1 5A7DAF32
P 7500 2200
F 0 "Q2" H 7700 2275 50  0000 L CNN
F 1 "2SC1815" H 7700 2200 50  0000 L CNN
F 2 "TO_SOT_Packages_THT:TO-92_Molded_Narrow" H 7700 2125 50  0001 L CIN
F 3 "" H 7500 2200 50  0001 L CNN
	1    7500 2200
	1    0    0    -1  
$EndComp
$Comp
L 2SC1815 Q3
U 1 1 5A7DAF81
P 7750 2700
F 0 "Q3" H 7950 2775 50  0000 L CNN
F 1 "2SC1815" H 7950 2700 50  0000 L CNN
F 2 "TO_SOT_Packages_THT:TO-92_Molded_Narrow" H 7950 2625 50  0001 L CIN
F 3 "" H 7750 2700 50  0001 L CNN
	1    7750 2700
	1    0    0    -1  
$EndComp
$Comp
L R R1
U 1 1 5A7DAFC7
P 2100 950
F 0 "R1" V 2180 950 50  0000 C CNN
F 1 "10K" V 2100 950 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 2030 950 50  0001 C CNN
F 3 "" H 2100 950 50  0001 C CNN
	1    2100 950 
	1    0    0    -1  
$EndComp
Text GLabel 2200 1250 2    60   Input ~ 0
RESET
$Comp
L SW_Push SW1
U 1 1 5A7DB13D
P 2100 1500
F 0 "SW1" H 2150 1600 50  0000 L CNN
F 1 "RESET" H 2100 1440 50  0000 C CNN
F 2 "Buttons_Switches_THT:SW_PUSH_6mm" H 2100 1700 50  0001 C CNN
F 3 "" H 2100 1700 50  0001 C CNN
	1    2100 1500
	0    -1   -1   0   
$EndComp
$Comp
L GND #PWR08
U 1 1 5A7DB322
P 2100 1850
F 0 "#PWR08" H 2100 1600 50  0001 C CNN
F 1 "GND" H 2100 1700 50  0000 C CNN
F 2 "" H 2100 1850 50  0001 C CNN
F 3 "" H 2100 1850 50  0001 C CNN
	1    2100 1850
	1    0    0    -1  
$EndComp
$Comp
L VCC #PWR09
U 1 1 5A7DB351
P 2100 750
F 0 "#PWR09" H 2100 600 50  0001 C CNN
F 1 "VCC" H 2100 900 50  0000 C CNN
F 2 "" H 2100 750 50  0001 C CNN
F 3 "" H 2100 750 50  0001 C CNN
	1    2100 750 
	1    0    0    -1  
$EndComp
$Comp
L R R5
U 1 1 5A7DB57D
P 6800 1650
F 0 "R5" V 6900 1650 50  0000 C CNN
F 1 "4K7" V 6800 1650 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 6730 1650 50  0001 C CNN
F 3 "" H 6800 1650 50  0001 C CNN
	1    6800 1650
	0    1    1    0   
$EndComp
$Comp
L R R6
U 1 1 5A7DB64A
P 6800 2200
F 0 "R6" V 6880 2200 50  0000 C CNN
F 1 "4K7" V 6800 2200 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 6730 2200 50  0001 C CNN
F 3 "" H 6800 2200 50  0001 C CNN
	1    6800 2200
	0    1    1    0   
$EndComp
$Comp
L R R7
U 1 1 5A7DB7E6
P 6800 2700
F 0 "R7" V 6880 2700 50  0000 C CNN
F 1 "4K7" V 6800 2700 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 6730 2700 50  0001 C CNN
F 3 "" H 6800 2700 50  0001 C CNN
	1    6800 2700
	0    1    1    0   
$EndComp
$Comp
L Conn_01x02 J3
U 1 1 5A7DC191
P 7350 1050
F 0 "J3" H 7350 1150 50  0000 C CNN
F 1 "AC" H 7350 850 50  0000 C CNN
F 2 "Pin_Headers:Pin_Header_Angled_1x02_Pitch2.54mm" H 7350 1050 50  0001 C CNN
F 3 "" H 7350 1050 50  0001 C CNN
	1    7350 1050
	0    -1   -1   0   
$EndComp
$Comp
L Conn_01x02 J4
U 1 1 5A7DC2D6
P 7950 1050
F 0 "J4" H 7950 1150 50  0000 C CNN
F 1 "HEATER" H 7950 850 50  0000 C CNN
F 2 "Pin_Headers:Pin_Header_Angled_1x02_Pitch2.54mm" H 7950 1050 50  0001 C CNN
F 3 "" H 7950 1050 50  0001 C CNN
	1    7950 1050
	0    -1   -1   0   
$EndComp
$Comp
L Conn_01x02 J5
U 1 1 5A7DC3BB
P 8550 1050
F 0 "J5" H 8550 1150 50  0000 C CNN
F 1 "DEHUMIDFIER" H 8550 850 50  0000 C CNN
F 2 "Pin_Headers:Pin_Header_Angled_1x02_Pitch2.54mm" H 8550 1050 50  0001 C CNN
F 3 "" H 8550 1050 50  0001 C CNN
	1    8550 1050
	0    -1   -1   0   
$EndComp
$Comp
L GND #PWR010
U 1 1 5A7DC525
P 7350 1950
F 0 "#PWR010" H 7350 1700 50  0001 C CNN
F 1 "GND" H 7350 1800 50  0000 C CNN
F 2 "" H 7350 1950 50  0001 C CNN
F 3 "" H 7350 1950 50  0001 C CNN
	1    7350 1950
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR011
U 1 1 5A7DC566
P 7600 2500
F 0 "#PWR011" H 7600 2250 50  0001 C CNN
F 1 "GND" H 7600 2350 50  0000 C CNN
F 2 "" H 7600 2500 50  0001 C CNN
F 3 "" H 7600 2500 50  0001 C CNN
	1    7600 2500
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR012
U 1 1 5A7DC5BA
P 7850 3000
F 0 "#PWR012" H 7850 2750 50  0001 C CNN
F 1 "GND" H 7850 2850 50  0000 C CNN
F 2 "" H 7850 3000 50  0001 C CNN
F 3 "" H 7850 3000 50  0001 C CNN
	1    7850 3000
	1    0    0    -1  
$EndComp
$Comp
L R R2
U 1 1 5A7DD9B2
P 5750 2800
F 0 "R2" V 5830 2800 50  0000 C CNN
F 1 "330R" V 5750 2800 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 5680 2800 50  0001 C CNN
F 3 "" H 5750 2800 50  0001 C CNN
	1    5750 2800
	-1   0    0    1   
$EndComp
$Comp
L R R3
U 1 1 5A7DDA6F
P 5950 2800
F 0 "R3" V 6030 2800 50  0000 C CNN
F 1 "330R" V 5950 2800 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 5880 2800 50  0001 C CNN
F 3 "" H 5950 2800 50  0001 C CNN
	1    5950 2800
	-1   0    0    1   
$EndComp
$Comp
L R R4
U 1 1 5A7DDACD
P 6150 2800
F 0 "R4" V 6230 2800 50  0000 C CNN
F 1 "330R" V 6150 2800 50  0000 C CNN
F 2 "Resistors_SMD:R_0805_HandSoldering" V 6080 2800 50  0001 C CNN
F 3 "" H 6150 2800 50  0001 C CNN
	1    6150 2800
	-1   0    0    1   
$EndComp
$Comp
L LED D3
U 1 1 5A7DDB94
P 6400 3600
F 0 "D3" H 6400 3700 50  0000 C CNN
F 1 "LED_G" H 6400 3500 50  0000 C CNN
F 2 "LEDs:LED_0805_HandSoldering" H 6400 3600 50  0001 C CNN
F 3 "" H 6400 3600 50  0001 C CNN
	1    6400 3600
	-1   0    0    1   
$EndComp
$Comp
L LED D1
U 1 1 5A7DDD92
P 6400 2950
F 0 "D1" H 6400 3050 50  0000 C CNN
F 1 "LED_B" H 6400 2850 50  0000 C CNN
F 2 "LEDs:LED_0805_HandSoldering" H 6400 2950 50  0001 C CNN
F 3 "" H 6400 2950 50  0001 C CNN
	1    6400 2950
	-1   0    0    1   
$EndComp
$Comp
L LED D2
U 1 1 5A7DDE0D
P 6400 3300
F 0 "D2" H 6400 3400 50  0000 C CNN
F 1 "LED_R" H 6400 3200 50  0000 C CNN
F 2 "LEDs:LED_0805_HandSoldering" H 6400 3300 50  0001 C CNN
F 3 "" H 6400 3300 50  0001 C CNN
	1    6400 3300
	-1   0    0    1   
$EndComp
$Comp
L GND #PWR013
U 1 1 5A7DE4DC
P 6900 3600
F 0 "#PWR013" H 6900 3350 50  0001 C CNN
F 1 "GND" H 6900 3450 50  0000 C CNN
F 2 "" H 6900 3600 50  0001 C CNN
F 3 "" H 6900 3600 50  0001 C CNN
	1    6900 3600
	1    0    0    -1  
$EndComp
Wire Wire Line
	1050 1050 1200 1050
Wire Wire Line
	1200 1050 1200 750 
Wire Wire Line
	1050 1350 1200 1350
Wire Wire Line
	1200 1350 1200 1600
Wire Wire Line
	1050 1250 1400 1250
Wire Wire Line
	1400 1150 1050 1150
Wire Wire Line
	1050 2350 1200 2350
Wire Wire Line
	1200 2350 1200 2150
Wire Wire Line
	1500 2450 1050 2450
Wire Wire Line
	1500 2550 1050 2550
Wire Wire Line
	1050 2650 1200 2650
Wire Wire Line
	1200 2650 1200 2850
Wire Wire Line
	2800 3400 2800 3200
Wire Wire Line
	2800 3200 2950 3200
Wire Wire Line
	2950 3300 2800 3300
Connection ~ 2800 3300
Wire Wire Line
	2950 3400 2800 3400
Connection ~ 2800 3400
Wire Wire Line
	2950 1100 2850 1100
Wire Wire Line
	2850 750  2850 1400
Wire Wire Line
	2850 1200 2950 1200
Connection ~ 2850 1100
Wire Wire Line
	2850 1400 2950 1400
Connection ~ 2850 1200
Wire Wire Line
	1650 2750 1850 2750
Wire Wire Line
	1350 2750 1050 2750
Wire Wire Line
	5200 2550 4850 2550
Wire Wire Line
	5200 2450 4850 2450
Wire Wire Line
	5200 2350 4850 2350
Wire Wire Line
	5200 2700 4850 2700
Wire Wire Line
	4850 2800 5200 2800
Wire Wire Line
	4850 1700 6050 1700
Connection ~ 5850 1700
Wire Wire Line
	5550 2000 6050 2000
Wire Wire Line
	5550 2000 5550 1800
Wire Wire Line
	5550 1800 4850 1800
Connection ~ 5850 2000
Wire Wire Line
	6350 1700 6350 2200
Connection ~ 6350 2000
Wire Wire Line
	2100 800  2100 750 
Wire Wire Line
	2100 1300 2100 1100
Wire Wire Line
	2200 1250 2100 1250
Connection ~ 2100 1250
Wire Wire Line
	2100 1700 2100 1850
Wire Wire Line
	6950 1650 7050 1650
Wire Wire Line
	7300 2200 6950 2200
Wire Wire Line
	7550 2700 6950 2700
Wire Wire Line
	7350 1250 7350 1450
Wire Wire Line
	7950 1250 7950 2000
Wire Wire Line
	7950 2000 7600 2000
Wire Wire Line
	8550 1250 8550 2500
Wire Wire Line
	8550 2500 7850 2500
Wire Wire Line
	7350 1950 7350 1850
Wire Wire Line
	7450 1250 7450 1850
Wire Wire Line
	7450 1850 7350 1850
Connection ~ 7350 1850
Wire Wire Line
	8050 1250 8050 2400
Wire Wire Line
	8050 2400 7600 2400
Wire Wire Line
	7600 2400 7600 2500
Wire Wire Line
	7850 2900 7850 3000
Wire Wire Line
	7850 2900 8650 2900
Wire Wire Line
	8650 2900 8650 1250
Connection ~ 7850 2900
Connection ~ 7600 2400
Wire Wire Line
	6650 2700 6450 2700
Wire Wire Line
	6450 2700 6450 1300
Wire Wire Line
	6450 1300 4850 1300
Wire Wire Line
	4850 1200 6550 1200
Wire Wire Line
	6550 1200 6550 2200
Wire Wire Line
	6550 2200 6650 2200
Wire Wire Line
	6650 1650 6650 1100
Wire Wire Line
	6650 1100 4850 1100
Wire Wire Line
	4850 1950 5500 1950
Wire Wire Line
	5500 1950 5500 2100
Wire Wire Line
	5500 2100 6050 2100
Wire Wire Line
	6050 2100 6050 2400
Wire Wire Line
	6050 2400 6150 2400
Wire Wire Line
	6150 2400 6150 2650
Wire Wire Line
	4850 2050 5450 2050
Wire Wire Line
	5450 2050 5450 2150
Wire Wire Line
	5450 2150 5950 2150
Wire Wire Line
	5950 2150 5950 2650
Wire Wire Line
	4850 2150 5400 2150
Wire Wire Line
	5400 2150 5400 2250
Wire Wire Line
	5400 2250 5750 2250
Wire Wire Line
	5750 2250 5750 2650
Wire Wire Line
	6150 2950 6250 2950
Wire Wire Line
	6250 3300 5950 3300
Wire Wire Line
	5950 3300 5950 2950
Wire Wire Line
	6250 3600 5750 3600
Wire Wire Line
	5750 3600 5750 2950
Wire Wire Line
	6550 3600 6900 3600
Wire Wire Line
	6550 2950 6550 3600
Connection ~ 6550 3300
Connection ~ 6550 3600
$EndSCHEMATC