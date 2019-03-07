package game2019;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

public class SendThread extends Thread {
	String message  = ""; 
	Player me;
	DataOutputStream messageOut;
	String before;
	
	// tilføjes senere og til constructer
	//Player me;
	
	public SendThread(Player me, DataOutputStream messageOut) {
		this.me = me;
		this.messageOut = messageOut;
	}
	
	public void run() {
		while(true) {
			before = me.getPosition();
			if (!before.equals(me.getPosition())) {
			try { 
				System.out.println(me.getPosition());
				message = me.getPosition();
				messageOut.writeBytes(message + '\n');
				messageOut.flush();
				System.out.println("besked sendt");
				Thread.sleep(100);
			} catch (IOException e) {
				System.out.println("Error occured in ThreadSender");
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
	}


}

