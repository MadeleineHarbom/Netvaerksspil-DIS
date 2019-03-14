package src;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionToClientThread extends Thread {
	
	Socket socket = null;
	ArrayList<Player> players;
	SendThread sender;
	RecieveThread reciever;
	DataOutputStream push2client;
	static boolean ready = false;
	static String message;


	public ConnectionToClientThread(Socket socket, ArrayList<Player> players) {
		this.socket = socket;
	}

	public ConnectionToClientThread(Socket socket) {
		this.socket = socket;
		try {

		push2client = new DataOutputStream(socket.getOutputStream());
		sendMsg("gifv_name");
			System.out.println("C2C: Name requested");
		}
		catch (Exception e) {

		}

	}

	private void enqueue(String message) {
	    Server.enqueue(message);
    }
	
	
	public void run() {
		try {

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));



			while (true) {
				message = inFromClient.readLine();
				//Server.broadcast(message);
				decode(message);
			}

			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		//Skal også startes en sende og en modtage tråd, måske noget af ovenstående skal findes i disse tråde, ellers kan de tilføjes som parametre
	}

	public void sendMsg(String s) throws IOException {
		this.push2client.writeBytes(s + "\n"); //sender til clienten?
	}

	public void decode(String message) throws IOException{
		System.out.println("I am the C2C decoder. This is my string: " + message);
		if (message.toLowerCase().startsWith("ready")) {
			ready(message);

		}
		else if (message.toLowerCase().startsWith("name")){
			makeChar(message);
		}
		else if (message.startsWith("move")) {
		    Server.enqueue(message);
			//move(message);

		}
		else {
			System.out.println("Else in C2C" + message);
			//Laeg kaldet i koe
		}
	}

	public static String getString() {
		return message;
	}

	public static void move(String message) {
        System.out.println("Move in C2C");
        String[] stringarray = message.split(" ");
        String name = stringarray[1];

        int x;
        int y;
        String dir = stringarray[4];
        Player pplayer = Server.findPlayer(name);
        try {
            x = Integer.parseInt(stringarray[2]);
            y = Integer.parseInt(stringarray[3]);
        }
        catch (Exception e) {
            System.out.println("C2CT failed to parse coords");
            x = 0;
            y = 0;
        }
        if (pplayer != null) {
            System.out.println("C2C calling check move");
            Server.checkMove(x, y, pplayer);
        }
        else {
            System.out.println("Couldnt find player");
        }
    }

    public static void makeChar(String message) {
        System.out.println("From C2C after name reveived: " + message);
        String[] sa = message.split(" ");
        Server.names.add(sa[1]); //Send to server
        Server.createRandomizedCharacter(sa[1]);
    }

    public static void ready(String message) throws IOException {
        Server.gameon = true;
        System.out.println("Client told me to go");
        Server.broadcast("start");
        ready = true;
    }


}
