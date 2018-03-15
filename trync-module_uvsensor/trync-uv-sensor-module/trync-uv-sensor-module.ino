#include <Wire.h>

const int analogInPin = A0;

int sensorValue = 0;     
int outputValue = 0;  

void setup() {
  Wire.begin(10);                // join i2c bus with address 10
  Wire.onReceive(receiveEvent); // register event
}

void loop() {
  delay(100);
}

// function that executes whenever data is received from master
// this function is registered as an event, see setup()
void receiveEvent(int howMany) {
  while (1 < Wire.available()) { // loop through all but the last
    int x = Wire.read();
    if(x==0x20){
      sensorValue = analogRead(analogInPin);
      outputValue = map(sensorValue, 0, 1023, 0, 10);
      Wire.write(outputValue) ;
    }
  }

}
