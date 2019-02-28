package src;

import java.io.DataOutputStream;

public class SendThread extends Thread {
	DataOutputStream messageOut;
	Player me;
	
	public SendThread(DataOutputStream messageOut, Player me) {
		this.messageOut = messageOut;
		this.me = me;
	}
	
	public void run() {
		
	}


}

