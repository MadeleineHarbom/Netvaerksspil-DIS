package src;


import javafx.application.Platform;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Server {
	static ArrayList<ConnectionToClientThread> mahThreads = new ArrayList<>();
	static ArrayList<Player> players = new ArrayList<>(); //lave eventuelt til hashmap
	static boolean gameon = false;
	static String[] queue = new String[10000];
	static int counter = 0;
	static int queuesize = 0;

    static ArrayList<String> names = new ArrayList<>();


    //TODO queue
    private static  String[] board = {    // 20x20
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
    	
    	acceptThread aT = new acceptThread(serverSocket);
    	aT.start();
    	while (!gameon) {
			System.out.println("Press ready!!!!");
		}
		//Broadcast


		System.out.println("Loop change");
		System.out.println("Loop change");
		System.out.println("Loop change");

    	while (true) {
    	    if (queuesize > counter) {
				System.out.println("Entering gameloop");
				gameLoop(); //enqueue...  static issues
            }
            else {
				Thread.sleep(10);
				continue;
			}
        }

       }




	public static void broadcast(String s) throws IOException {
		for (ConnectionToClientThread st : mahThreads) {
			st.sendMsg(s);
		}
	}

	public static Player findPlayer (String name) {
		System.out.println("Server.findPlayer");
    	for (Player p : players) {
			System.out.println("Name :" + name + "... other name " + p.getName());
    		if (name.equalsIgnoreCase(p.getName())) {
    			return p;
			}
		}
		return null;
	}

	public static String checkMove(int x, int y, Player mover) {
    	//TODO Spagetti
		System.out.println("I am the checker of moves");
		Player p = getPlayerAt(x,y);
		if (p!=null) {
			p.addPoints(-10);
			mover.addPoints(10);
			try {



				System.out.println("Mover " + mover.getAllInfo());
				broadcast("move " + mover.getAllInfo());
			}
			catch (IOException e) {
				System.out.println("IO Excetion whne broadvcasting updated characters");
			}

		}
		else {
			mover.addPoints(1);
			mover.setXpos(x);
			mover.setYpos(y);
			try {
				System.out.println("Mover " + mover.getAllInfo());
				broadcast("move " + mover.getAllInfo());
			}
			catch (IOException e) {
				System.out.println("IO Excetion whne broadvcasting updated characters");
			}

		}
		return "Return value";
    }



	public static void requestGameStart() throws IOException{
    	for (ConnectionToClientThread st : mahThreads) {
    		if (!st.ready) {
    			return;
			}
		}
		startGame();
	}

	public static void startGame() throws IOException{
		System.out.println("Game started");

		for (Player p : players) {
			broadcast("charinit " + p.getPosition());
            System.out.println("Player pushed");
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

    public static void createPlayer(String s, int x, int y, String dir) {

		try {
			Player p = new Player(s, x, y, dir);
			players.add(p);
			for(Player ps : players) {
	    		broadcast("charinit " + ps.getPosition());
	            System.out.println("char init braodcasted");
			}
		}
		catch (Exception e) {
			System.out.println("Character creation failed");
		}
	}

	public static void createRandomizedCharacter(String s) {
        int x;
        int y;
        boolean searching = true;
		Random r = new Random();
        while (searching) {
            x = r.nextInt((19 - 0) + 1);
            y = r.nextInt((19 - 0) + 1);
            if (board[y].charAt(x)=='w') {
                continue;
            }
            else {
                Player p = getPlayerAt(x,y);
                if (p == null) {
                    String dir = "";
                    int d = r.nextInt((3 - 0) + 1);
                    if (d == 0) {
                        dir = "up";
                    }
                    else if (d==1) {
                        dir = "down";
                    }
                    else if (d==2) {
                        dir = "right";
                    }
                    else {
                        dir = "left";
                    }
                    createPlayer(s, x, y, dir);

                    searching = false;
                }

            }
        }


    }

    public static void enqueue(String message) {

        queue[queuesize] =  message;
        queuesize++;
		System.out.println("Enqueueing! Queuesize: " +  queuesize);
		System.out.println("Counter: " + counter);
    }

    public static String dequeue() {
        System.out.println("dequeueing");
        String thread = queue[counter];
        counter++;
        return thread;

    }

    public static void gameLoop() {
        String[] s = dequeue().split(" ");
        //kan jeg dette3 uden at starte traaden?
		if (s.length == 0) {
			System.out.println("I didnt get any information");
		}
		for (String sa : s) {
			System.out.println(sa);
		}
        Player mover = null;
        for (Player p : players) {
            if (p.getName().equals(s[1])) { //skal nok vaere 1+
                mover = p;
            }
        }
        int x;
        int y;
        try {
            x = Integer.parseInt(s[2]);
            y = Integer.parseInt(s[3]);
        }
        catch (Exception e) {
            System.out.println("Parse issues");
            x =0;
            y=0;
        }
        if (mover != null) {
			checkMove(x, y, mover);
		}

    }







    
}
