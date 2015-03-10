import lejos.nxt.*;

public class MasterTest {

	public static void main(String[] args) {
		String slaveName = "NXT";
		String slaveMethod ="runme";
		
		BTCom.connect(slaveName);
		BTCom.sendData(slaveMethod);
		
		Motor.A.setSpeed(20);
		Motor.B.setSpeed(20);
		Motor.A.rotate(190, true);

		Motor.B.rotate(190);


		BTCom.stopConnection();
	}
}
