package src;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    //Copy pasta
    public static void main(String[] args) throws Exception {
        //kode fra Rabeea

        SendThread sender;
        RecieveThread reciever;



        Player p1 = new Player("Made", 1, 1, "UP");
        Player p2 = new Player("Karl", 1, 1, "DOWN");

        Map people = new HashMap<String, Player>();
        people.put("10.24.6.117", p2);
        people.put("10.24.68.3", p1);

        //Map players = new HashMap<String, Player>(;
        
        
        if (args.length != 1) {
            System.err.println("Usage: Netværksspil Server <port number>");
            System.exit(1);
        }

            int portNumber = Integer.parseInt(args[0]);
            boolean listening = true;
            
            try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
                while (listening) {
    	            new ServerThread(serverSocket.accept()).start();
    	        }
    	    } catch (IOException e) {
                System.err.println("Could not listen on port " + portNumber);
                System.exit(-1);
            }
       }

    
}
