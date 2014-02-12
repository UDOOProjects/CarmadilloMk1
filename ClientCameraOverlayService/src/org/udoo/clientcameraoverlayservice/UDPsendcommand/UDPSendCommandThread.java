package org.udoo.clientcameraoverlayservice.UDPsendcommand;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.util.Log;

public class UDPSendCommandThread extends Thread {

	private int direction 		= 63;		// 63: value sent when joystick are in the center
	private int speed 			= 63;
	private int pan 			= 63;
	private int tilt 			= 63;
	private int command 		= 0;		// no command
	private int commandCount	= 0;
	
	public int NO_COMMAND 							= 0;
	public static int CENTER_CAM_COMMAND 			= 1;
//	public static int PLAY_SOUND_MOTOR_COMMAND		= 2;
//	public static int PLAY_SOUND_CLACSON_COMMAND	= 3;
//	public static int PLAY_SOUND_BRAKE_COMMAND		= 4;
//	public static int LIGHT_ON_COMMAND				= 5;
//	public static int LIGHT_OFF_COMMAND				= 6;
	
	private boolean running = true;

	//UDP variables
	int port = 9002;
	byte[] outgoingBytes = new byte[5];
	DatagramSocket datagramSocket;
	DatagramPacket datagramPacket;
	InetAddress carAddr;


	public UDPSendCommandThread(String ipIn)
	{
    	try {
			carAddr = InetAddress.getByName(ipIn);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			datagramSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.d("UDP Constructor: ", "UDP thread got this IP: " + ipIn);
	}
	

	public void startRunning(){
    	running = true;
	}
	
	public void stopRunning(){
		running = false;
	}

	@Override
	public void run() {
		try {	
			while (running) {	
				
				if (direction == 0) direction   = 1;
				if (speed == 0) 	speed 		= 1;
				if (pan == 0) 		pan   		= 1;
				if (tilt == 0) 		tilt 		= 1;
				
				outgoingBytes[0] = (byte) direction;
				outgoingBytes[1] = (byte) speed;				
				outgoingBytes[2] = (byte) pan;
				outgoingBytes[3] = (byte) tilt;
				outgoingBytes[4] = (byte) command;

				datagramPacket = new DatagramPacket(outgoingBytes, outgoingBytes.length, carAddr, port);

				datagramSocket.send(datagramPacket);
				
				if(command != 0) {
					if (command == CENTER_CAM_COMMAND){
						// Send a command 5 times before reset variable for prevent UDP package loss
						if (commandCount < 5){ 
							commandCount++ ;
						} else {
							setCommand(NO_COMMAND);
							commandCount = 0;
						}
					}
				}

	    		Thread.sleep(15);

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setSpeed(double inSpeed){
		speed = (int)inSpeed;
	}

	public void setDirection(double inDirection){
		direction = (int)inDirection;
	}
	
	public void setPan(double inPan){
		pan = (int)inPan;
	}

	public void setTilt(double inTilt){
		tilt = (int)inTilt;
	}
	
	public void setCommand(int inCommmand){
		command = inCommmand;
	}

}