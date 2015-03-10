import lejos.nxt.*;


public class SlaveMain {

	public static void main(String[] args) {
		
		BTCom.connect();
		String runme = null;
		runme = BTCom.recieveData();
		LCD.clear();
		System.out.println("\n\n" + runme);

		Motor.A.setSpeed(20);
		Motor.B.setSpeed(20);
		Motor.A.rotate(-190, true);
		Motor.B.rotate(-190);
		BTCom.stopConnection();
	}

}
