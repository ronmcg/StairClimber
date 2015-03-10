import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class BTCom {
		   
	private static DataInputStream dis;
	private static DataOutputStream dos;
	private static BTConnection btc;
	private static boolean isConnected;
	private static String name;
		   
	private BTCom(){
		isConnected = false;
	}
		   
	public static boolean getIsConnected(){
		return isConnected;
	}
		   
	/**
	* Send a string
	* @param output
	*/
	public static String sendData(String output){
		try{
			dos.writeUTF(output);
			dos.flush();
			return "1";
		   } catch (IOException ioe){
		    LCD.clear(0);
		    LCD.drawString("failed to send", 0, 0);
		    isConnected = false;
		    stopConnection();
		    reConnect();
		    return "0";
		   }
	}
		   
	/**
	 * Try to receive a string
	 * @return
	 */
	public static String recieveData(){
		boolean received = false;
		String s = "";
		while(!received){
			try {
				s = dis.readUTF();
				if(!s.equals("")){
					received = true;
				}
				} 
			catch (IOException e) {
				LCD.clear(0);
			    LCD.drawString("failed to receive", 0, 0);
			    isConnected = false;
			    stopConnection();
			    reConnect();
			    }
		}
		return s;
	}
		   
		   /**
		    * Stop the connection
		    */
		   public static void stopConnection(){
		      try {
		         dos.close();
		         dis.close();
		         btc.close();
		      } catch (IOException e) {
		         LCD.clear(0);
		         LCD.drawString("failed to close", 0, 0);
		      }
		   }
		   
		   /**
		    * Reconnect as master or slave depending on the initial
		    * connecting was setup.
		    */
		   private static void reConnect(){
		      if(name != null){
		         connect(name);
		      } else {
		         connect();
		      }
		   }

		   /**
		    * Connect to other NXT with following "name". 
		    * The other NXT must be waiting for incoming connection,
		    * and the be pre-paired.
		    * @param aName
		    */
		   public static void connect(String aName){
		      name = aName;
		      LCD.drawString("Connecting...", 0, 0);
		      LCD.refresh();
		      RemoteDevice btrd = Bluetooth.getKnownDevice(name);
		      if (btrd == null) {
		         LCD.clear();
		         LCD.drawString("No such device", 0, 0);
		         LCD.refresh();
		         try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		      }
		      btc = Bluetooth.connect(btrd);
		      if (btc == null) {
		         LCD.clear();
		         LCD.drawString("Connect fail", 0, 0);
		         LCD.refresh();
		         try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		      } else {
		      LCD.clear();
		      LCD.drawString("Connected", 0, 0);
		      LCD.refresh();
		      dis = btc.openDataInputStream();
		      dos = btc.openDataOutputStream();
		      }
		   }

		   /**
		    * Wait for other NXT to connect.
		    */
		   public static void connect(){
		      LCD.drawString("Waiting...", 0, 0);
		      btc = Bluetooth.waitForConnection();
		      LCD.drawString("Connected  ",0,0);
		      isConnected = true;
		      dis = btc.openDataInputStream();
		      dos = btc.openDataOutputStream();
		   }
}
