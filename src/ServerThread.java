package src;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	
	Socket socket = null;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	
	public void run() {
		try {
			//Tager imod kommunikation fra klienten
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//Skal forsøge at sende en besked til klienten, skal måske være i serverklassen.
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(System.in));
			
			//Den "pipe" der er til klienten.
			DataOutputStream outFromServer = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Skal også startes en sende og en modtage tråd, måske noget af ovenstående skal findes i disse tråde, ellers kan de tilføjes som parametre
	}

}
