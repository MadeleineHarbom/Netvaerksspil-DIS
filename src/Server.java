package src;


import javax.imageio.event.IIOWriteProgressListener;
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
	static ServerThread[] queue = new ServerThread[10000];
	static int counter = 0;

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
				String[] s = queue[counter].getString().split(" ");
				Player mover = null;
				for (Player p : players) {
					if (p.getName().equals(s[0])) {
						mover = p;
					}
				}
				int x;
				int y;
				try {
					x = Integer.parseInt(s[1]);
					y = Integer.parseInt(s[2]);
				}
				catch (Exception e) {
					System.out.println("Parse issues");
					x =0;
					y=0;
				}
				checkMove(x, y, mover);

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

	public static String checkMove(int x, int y, Player mover) {
    	//TODO Spagetti
		Player p = getPlayerAt(x,y);
		if (p!=null) {
			p.addPoints(-10);
			mover.addPoints(10);
			try {
				broadcast(p.getAllInfo());
				broadcast(mover.getAllInfo());
			}
			catch (IOException e) {
				System.out.println("IO Excetion whne broadvcasting updated characters");
			}
			//me.addPoints(10);s
			//p.addPoints(-10);
		}
		else {
			mover.addPoints(1);
			try {
				broadcast(mover.getAllInfo());
			}
			catch (IOException e) {
				System.out.println("IO Excetion whne broadvcasting updated characters");
			}

		}
		return "Return value";
    }



	public static void requestGameStart() throws IOException{
    	for (ServerThread st : mahThreads) {
    		if (!st.ready) {
    			return;
			}
		}
		startGame();
	}

	public static void startGame() throws IOException{
		System.out.println("Game started");
		//TODO pushe alle characters til brugerne
		for (Player p : players) {
			broadcast(p.toString());
		}
		gameon = true;
	}

    public static Player getPlayerAt(int x, int y) {
        for (Player p : players) {
            if (p.getXpos()==x && p.getYpos()==y) {
                return p;
            }
        }
        return null;
    }

    public void createPlayer(String s) {
    	String[] info = s.split(" "); //Virker? Skal splitte paa space
		try {
			Player p = new Player(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]), info[3]);
			players.add(p);
		}
		catch (Exception e) {
			System.out.println("Character creation failed");
		}
	}







    
}
