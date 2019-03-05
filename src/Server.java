package src;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {

    //Copy pasta
    public static void main(String[] args) throws Exception {
    	
    	int portNumber = 7777;
    	ServerSocket serverSocket = new ServerSocket(portNumber);

		ArrayList<ServerThread> mahThreads = new ArrayList<>();

    	while (mahThreads.size() != 2) {
    		//accepterer en client når den forsøger at forbinde, og starter en serverSocketTråd

    		ServerThread st = new ServerThread(serverSocket.accept());
    		st.start();
    		mahThreads.add(st);
    	}
    	//TODO Serveren skal skrive til alle clients
		//TODO Serveren skal en client kunne skrivew til alle clients (inklusive sig selv)
		//TODO Login og spilstart (disign) + Liste med players og IP
		//TODO Queue

    	//when dequeue
		//for each ST
		//push
    	
    	
        //kode fra Rabeea

//        SendThread sender;
//        RecieveThread reciever;
//
//
//
//        Player p1 = new Player("Made", 1, 1, "UP");
//        Player p2 = new Player("Karl", 1, 1, "DOWN");

//        Map people = new HashMap<String, Player>();
//        people.put("10.24.6.117", p2);
//        people.put("10.24.68.3", p1);

        //Map players = new HashMap<String, Player>(;
        
        
       }

    
}
