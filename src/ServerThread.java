package src;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
	
	Socket socket = null;
	ArrayList<Player> players;
	SendThread sender;
	RecieveThread reciever;
	DataOutputStream pusher;
	boolean ready = false;
	static String message;


	public ServerThread(Socket socket, ArrayList<Player> players) {
		this.socket = socket;
	}

	public ServerThread(Socket socket) {
		this.socket = socket;
		try {

		pusher = new DataOutputStream(socket.getOutputStream());
		}
		catch (Exception e) {

		}
	}
	
	
	public void run() {
		try {

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while (true) {
				message = inFromClient.readLine();
				Server.broadcast(message);
				decode(message);
			}

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Skal også startes en sende og en modtage tråd, måske noget af ovenstående skal findes i disse tråde, ellers kan de tilføjes som parametre
	}

	public void sendMsg(String s) throws IOException {
		this.pusher.writeBytes(s + "\n");
	}

	public void decode(String message) throws IOException{
		if (message.toLowerCase().equals("ready")) {
			this.ready = true;
			Server.requestGameStart();
		}
		else {
			System.out.println(message);
			//Laeg kaldet i koe
		}
	}

	public static String getString() {
		return message;
	}

}
