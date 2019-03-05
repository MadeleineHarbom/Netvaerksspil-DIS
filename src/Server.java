package src;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

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
	static ArrayList<ServerThread> mahThreads = new ArrayList<>();

    //Copy pasta
    public static void main(String[] args) throws Exception {
    	
    	int portNumber = 7777;
    	ServerSocket serverSocket = new ServerSocket(portNumber);


    	while (mahThreads.size() != 3) {
    		//accepterer en client når den forsøger at forbinde, og starter en serverSocketTråd
			Socket sock = serverSocket.accept();
    		ServerThread st = new ServerThread(sock);
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
    	
    	

        
        
       }
	public static void broadcast(String s) throws IOException {
		for (ServerThread st : mahThreads) {
			st.sendMsg(s);
		}
	}

    
}
