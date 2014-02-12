package org.udoo.carmadillodue;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.OutputStream;

import org.udoo.carmadillo.networking.UDPReceiveCommandServer;


public class CarmadilloMain {
	
	static OutputStream out;
	
	public CarmadilloMain(){
		super();
	}

	void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(230400,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,
                		SerialPort.PARITY_NONE);
                
                out = serialPort.getOutputStream();                

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }
	
    static public void serialWrite ( byte[] joystickIn )
    {
    	/*	
    	 * 	joystickIn[0] -> direction
    	 * 	joystickIn[1] -> speed
    	 *  joystickIn[2] -> pan
    	 *  joystickIn[3] -> tilt
    	 *  joystickIn[4] -> command
    	 */
    		
		if(joystickIn[0] < 0) joystickIn[0]     =  (byte)0;
		if(joystickIn[0] > 180) joystickIn[0]   =  (byte)180;
		if(joystickIn[1] < 0) joystickIn[1]  	=  (byte)0;
		if(joystickIn[1] > 180) joystickIn[1] 	=  (byte)180;
		if(joystickIn[2] < 5) joystickIn[2]     =  (byte)5;
		if(joystickIn[2] > 180) joystickIn[2]   =  (byte)180;
		if(joystickIn[3] < 10) joystickIn[3] 	=  (byte)10;
		if(joystickIn[3] > 125) joystickIn[3] 	=  (byte)125;
        	
		System.out.println("dir: " + joystickIn[0] 
				+ " - speed: " + joystickIn[1] 
				+ " - pan: " + joystickIn[2] 
				+ " - tilt: " + joystickIn[3]
				+ " - command: " + joystickIn[4]);
		
		try
        {        
			out.write(joystickIn);               
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }            
    }
 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("connecting to serial port...");
		try
        {
			(new CarmadilloMain()).connect("/dev/ttyS0");
			System.out.println("connected");
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		new UDPReceiveCommandServer().start();
		System.out.println("UDPserver start");
	}

}
