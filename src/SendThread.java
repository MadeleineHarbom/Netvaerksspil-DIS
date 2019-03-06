package src;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

public class SendThread extends Thread {
	String message  = ""; 
	BufferedReader inputMessage;
	DataOutputStream messageOut;
	
	// tilføjes senere og til constructer
	//Player me;
	
	public SendThread(BufferedReader inputMessage, DataOutputStream messageOut) {
		this.inputMessage = inputMessage;
		this.messageOut = messageOut;
	
	}
	
	public void run() {
		while(true) {
			try {
				message = inputMessage.readLine();
				messageOut.writeBytes(message + '\n');
			} catch (IOException e) {
				System.out.println("Error occured in ThreadSender");
				e.printStackTrace();
			}
		}
	}


}

