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
	static ArrayList<ServerThread> mahThreads = new ArrayList<>();
	static ArrayList<Player> players = new ArrayList<>(); //lave eventuelt til hashmap
	static boolean gameon = false;

    private  String[] board = {    // 20x20
            "wwwwwwwwwwwwwwwwwwww",
            "w        ww        w",
            "w w  w  www w  w  ww",
            "w w  w   ww w  w  ww",
            "w  w               w",
            "w w w w w w w  w  ww",
            "w w     www w  w  ww",
            "w w     w w w  w  ww",
            "w   w w  w  w  w   w",
            "w     w  w  w  w   w",
            "w ww ww        w  ww",
            "w  w w    w    w  ww",
            "w        ww w  w  ww",
            "w         w w  w  ww",
            "w        w     w  ww",
            "w  w              ww",
            "w  w www  w w  ww ww",
            "w w      ww w     ww",
            "w   w   ww  w      w",
            "wwwwwwwwwwwwwwwwwwww"
    };

    //Copy pasta
    public static void main(String[] args) throws Exception {
    	
    	int portNumber = 7777;
    	ServerSocket serverSocket = new ServerSocket(portNumber);

    	ServerThread[] queue = new ServerThread[10000];
    	int counter = 0;
    	int size = 0;


    	while (!gameon) { //When size == readycounter
    		//accepterer en client når den forsøger at forbinde, og starter en serverSocketTraad
			Socket sock = serverSocket.accept();
    		ServerThread st = new ServerThread(sock);
    		st.start();
    		mahThreads.add(st);
    	}

    	while (gameon) {
    	    if (queue[counter] != null) {
    	        //handle thread
                counter++;
            }
        }

		//TODO Login og spilstart (disign) + Liste med players og IP
		//TODO Queue
        //TODO Check loveligt moves her

    	//when dequeue
		//for each ST
		//push





       }
	public static void broadcast(String s) throws IOException {
		for (ServerThread st : mahThreads) {
			st.sendMsg(s);
		}
	}

	public static boolean checkMove(int x, int y, String dir) {
        return true;
    }



	public static void requestGameStart() {
    	for (ServerThread st : mahThreads) {
    		if (!st.ready) {
    			return;
			}
		}
		startGame();
	}

	public static void startGame() {
		System.out.println("Game started");
		gameon = true;
	}

    public Player getPlayerAt(int x, int y) {
        for (Player p : players) {
            if (p.getXpos()==x && p.getYpos()==y) {
                return p;
            }
        }
        return null;
    }



    
}
