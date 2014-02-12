package org.udoo.carmadillo.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.udoo.carmadillodue.CarmadilloMain;



public class UDPReceiveCommandServer extends Thread {

	private boolean running = true;

	//UDP variables
	int port = 9002;
	byte[] incomingBytes = new byte[5];
	DatagramSocket datagramSocket;
	DatagramPacket datagramPacket;

	public UDPReceiveCommandServer() {
        try {
			datagramSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startRunning(){
    	running = true;
	}
	 
	public void stopRunning(){
		running = false;
	}
	
	@Override
	public void run() {
		while (running) {	
			try {

			datagramPacket = new DatagramPacket(incomingBytes, incomingBytes.length);
			
			datagramSocket.receive(datagramPacket);
		
			CarmadilloMain.serialWrite(incomingBytes);
			//			System.out.println("incomingBytes: dir: " + incomingBytes[0] + " - speed: " + incomingBytes[1] + " - pan: " + incomingBytes[2] + " - tilt: " + incomingBytes[3]
//			+ " - command: " + incomingBytes[4]);
			
    		Thread.sleep(20);
    		
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
