package src;

import java.net.ServerSocket;
import java.net.Socket;

public class acceptThread extends Thread {
	ServerSocket serverSocket;
	
	public acceptThread(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public void run() {
		try {

			Socket sock = serverSocket.accept();
			ConnectionToClientThread st = new ConnectionToClientThread(sock);
			st.start();
			Server.mahThreads.add(st);
			//get the name. How? Skal Server ha en getMessage metode?
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
