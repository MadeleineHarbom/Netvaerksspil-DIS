package src;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Server {
	static ArrayList<ConnectionToClientThread> mahThreads = new ArrayList<>();
	static ArrayList<Player> players = new ArrayList<>(); //lave eventuelt til hashmap
	static boolean gameon = false;
	static ConnectionToClientThread[] queue = new ConnectionToClientThread[10000];
	static int counter = 0;

    static ArrayList<String> names = new ArrayList<>();

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


    	while (!gameon) { //When size == readycounter
    		//accepterer en client når den forsøger at forbinde, og starter en serverSocketTraad
			Socket sock = serverSocket.accept();

    		ConnectionToClientThread st = new ConnectionToClientThread(sock);
    		st.start();
    		mahThreads.add(st);
    		//get the name. How? Skal Server ha en getMessage metode?
    	}

    	for(Player p : players) {
    		broadcast("charinit " + p.getDirection());
		}

    	while (gameon) {
    	    if (queue[counter] != null) {
				String[] s = queue[counter].getString().split(" ");
				//kan jeg dette3 uden at starte traaden?
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


		//TODO Queue




       }
	public static void broadcast(String s) throws IOException {
		for (ConnectionToClientThread st : mahThreads) {
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
    	for (ConnectionToClientThread st : mahThreads) {
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

    public static void createPlayer(String s, int x, int y, String dir) {

		try {
			Player p = new Player(s, x, y, dir);
			players.add(p);
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







    
}
