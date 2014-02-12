#include <stdio.h>
#include <Servo.h>

#define DIR_A 12
#define BRAKE_A 9
#define SPEED_A 3

#define DIR_B 13
#define BRAKE_B 8
#define SPEED_B 11

Servo panServo;
Servo tiltServo;

byte pan = (byte)90;   // init servo values
byte tilt = (byte)40;

boolean received = false;
int initSerialGarbage = 0;

int msg1garbage = 55;
int msg2garbage = 29;
int msg4garbage = 124;

void setup()
{
    // set communiation speed
    Serial.begin(230400);   
    pinMode(DIR_A, OUTPUT);
    pinMode(BRAKE_A, OUTPUT);
    pinMode(SPEED_A, OUTPUT);
    pinMode(DIR_B, OUTPUT);
    pinMode(BRAKE_B, OUTPUT);
    pinMode(SPEED_B, OUTPUT);
    digitalWrite(BRAKE_A, HIGH);
    analogWrite(SPEED_A, 0);
    printf("\r\Carmadillo serial start\r\n");
    panServo.attach(5);
    tiltServo.attach(6);
    delay(50);
    panServo.write(pan);
    tiltServo.write(tilt); 
}

#define RCVSIZE 5

void loop()
{
    byte incomingByte = 0;
    uint8_t msg[RCVSIZE];
    uint32_t nbread = 0; 
  
    while (Serial.available() > 0) {
        // read the incoming byte:
        incomingByte = Serial.read();
        msg[nbread] = incomingByte;
        nbread++;
       // Serial.println(incomingByte);
        received = true;
        initSerialGarbage ++;
    } 
    
   
   if (received && initSerialGarbage > 5) {
     /*Serial.print("msg0: ");Serial.println(msg[0]);
     Serial.print("msg1: ");Serial.println(msg[1]);
     Serial.print("msg2: ");Serial.println(msg[2]);
     Serial.print("msg3: ");Serial.println(msg[3]);
     Serial.print("msg4: ");Serial.println(msg[4]); */
     if (msg[0] > 30 && msg[0] < 90){  // direction
        //digitalWrite(BRAKE_B, HIGH);
        analogWrite(SPEED_B, 0);
     } else if (msg[0] <= 30 && msg[0] > 0){
        digitalWrite(DIR_B, HIGH);
        digitalWrite(BRAKE_B, LOW);
        analogWrite(SPEED_B, 255);
     } else if (msg[0] >= 90){
        digitalWrite(DIR_B, LOW);
        digitalWrite(BRAKE_B, LOW);
        analogWrite(SPEED_B, 255);
     }
     if (msg[1] == 0 || msg[1] == 63){  // speed
        digitalWrite(BRAKE_A, HIGH);
        analogWrite(SPEED_A, 0);
     } else if (msg[1] < 63 && msg[1] > 0 && msg[1] != msg1garbage){
        digitalWrite(DIR_A, LOW);
        digitalWrite(BRAKE_A, LOW);
        analogWrite(SPEED_A, 127 - msg[1]);
     } else if (msg[1] > 63 && msg[1] < 128){
        digitalWrite(DIR_A, HIGH);
        digitalWrite(BRAKE_A, LOW);
        analogWrite(SPEED_A, msg[1]); 
     }
        
     if(msg[2] == 0 || msg[2] == 63){
     }if(msg[2] < 30 && msg[2] > 0 && msg[2] != msg2garbage){
         pan += 3;            // inverted axes
         if (pan > 170) pan = 170;
     } else if(msg[2] > 90){
         pan -= 3;
         if (pan < 5) pan = 5;
     }
     
     if(msg[3] == 0 || msg[3] == 63){
     }else if(msg[3] < 30 && msg[3] > 0){
         tilt += 3;  
         if (tilt > 125) tilt = 125;
     } else if(msg[3] > 90){
         tilt -= 3;
         if (tilt < 10) tilt = 10;
     } 
     
     if(msg[4] != 0 && msg[4] != msg4garbage) {
       if(msg[4] == 1){
         pan = 90;
         tilt = 40;
       }
     }
     
     panServo.write(pan);
     tiltServo.write(tilt); 
     received = false;
   } 
   delay(20);
}
