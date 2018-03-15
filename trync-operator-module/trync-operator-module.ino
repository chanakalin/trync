 #include <Wire.h>

const int analogInPin = A0;

//LEDs
const int LED_R = 9;
const int LED_B = 8;
const int LED_Y = 7; 

void setup() {
  Wire.begin(20);                //listen on 20
  Wire.onReceive(receiveEvent); 
}

void loop() {
  delay(100);
}

// function that executes whenever data is received from master
// this function is registered as an event, see setup()
void receiveEvent(int howMany) {
  while (1 < Wire.available()) { // loop through all but the last
    int x = Wire.read();
    //R = 0 bit,B=1 bit, Y=2 bit
    if(x&(1<<0)){
      digitalWrite(LED_R,HIGH);
    }else{
      digitalWrite(LED_R,LOW);
    }
    if(x&(1<<1)){
      digitalWrite(LED_B,HIGH);
    }else{
      digitalWrite(LED_B,LOW);
    }
    if(x&(1<<2)){
      digitalWrite(LED_Y,HIGH);
    }else{
      digitalWrite(LED_Y,LOW);
    }
  }

}
