package src;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;

public class Main extends Application {

	static String ip = "localhost";//"192.168.1.109";
	static int port = 7777;
	static Socket clientSocket;
	static DataOutputStream outToServer;

	public static final int size = 20; 
	public static final int scene_height = size * 20 + 100;
	public static final int scene_width = size * 20 + 200;
	public static String playername;

	public static Image image_floor;
	public static Image image_wall;
	public static Image hero_right,hero_left,hero_up,hero_down;

	public static Player me;
	public static List<Player> players = new ArrayList<Player>();

	private static Label[][] fields;
	private TextArea scoreList;
	private static InfoScreen infoScreen;
	private static boolean gameon;
	
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
	//TODO det andre spiller kan ikke bevaege sig!
	//TODO update GUI saa att cunsom characters vises direkte, ikke Orville

	
	// -------------------------------------------
	// | Maze: (0,0)              | Score: (1,0) |
	// |-----------------------------------------|
	// | boardGrid (0,1)          | scorelist    |
	// |                          | (1,1)        |
	// -------------------------------------------
	@Override
	public void start(Stage primaryStage) {
		infoScreen = new InfoScreen("Indtast oplysninger");
		infoScreen.showAndWait();
        try {
            connectToServer();
        }
        catch (Exception e) {
            System.out.println("cant connect to server");
        }
        System.out.println(playername);

        

		//readyCheck();

        while (!gameon) {
        	
        }

		try {
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(0, 10, 0, 10));

			Text mazeLabel = new Text("Maze:");
			mazeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	
			Text scoreLabel = new Text("Score:");
			scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

			scoreList = new TextArea();
			
			GridPane boardGrid = new GridPane();

			image_wall  = new Image(getClass().getResourceAsStream("Image/wall4.png"),size,size,false,false);
			image_floor = new Image(getClass().getResourceAsStream("Image/floor1.png"),size,size,false,false);

			hero_right  = new Image(getClass().getResourceAsStream("Image/heroRight.png"),size,size,false,false);
			hero_left   = new Image(getClass().getResourceAsStream("Image/heroLeft.png"),size,size,false,false);
			hero_up     = new Image(getClass().getResourceAsStream("Image/heroUp.png"),size,size,false,false);
			hero_down   = new Image(getClass().getResourceAsStream("Image/heroDown.png"),size,size,false,false);

			fields = new Label[20][20];
			for (int j=0; j<20; j++) {
				for (int i=0; i<20; i++) {
					switch (board[j].charAt(i)) {
					case 'w':
						fields[i][j] = new Label("", new ImageView(image_wall));
						break;
					case ' ':					
						fields[i][j] = new Label("", new ImageView(image_floor));
						break;
					default: throw new Exception("Illegal field value: "+board[j].charAt(i) );
					}
					boardGrid.add(fields[i][j], i, j);
				}
			}
			scoreList.setEditable(false);
			
			
			grid.add(mazeLabel,  0, 0); 
			grid.add(scoreLabel, 1, 0); 
			grid.add(boardGrid,  0, 1);
			grid.add(scoreList,  1, 1);
						
			Scene scene = new Scene(grid,scene_width,scene_height);
			primaryStage.setScene(scene);
			primaryStage.show();

			scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				switch (event.getCode()) {
				case UP:    playerMoved(0,-1,"up");    break;
				case DOWN:  playerMoved(0,+1,"down");  break;
				case LEFT:  playerMoved(-1,0,"left");  break;
				case RIGHT: playerMoved(+1,0,"right"); break;
				//send to server?
				default: break;
				}
			});
			
            // Setting up start positions for standard players located in connecttoserver

			setupPlayers();


			//fields[9][4].setGraphic(new ImageView(hero_up));

			
			//fields[14][15].setGraphic(new ImageView(hero_up));

			scoreList.setText(getScoreList());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendName() {
		try {
            outToServer.writeBytes("name " + playername + '\n');
            System.out.println("Client: Name sent");
            outToServer.flush(); //Hmmm
        }
        catch (Exception e) {
            System.out.println("main failed to send name");
        }
	}

	public void playerMoved(int delta_x, int delta_y, String direction) {
		me.direction = direction;
		int x = me.getXpos(),y = me.getYpos();

		if (board[y+delta_y].charAt(x+delta_x)=='w') {
			me.addPoints(-1);
		} 
		else {
			Player p = getPlayerAt(x+delta_x,y+delta_y);
			if (p!=null) {
              me.addPoints(10);
              p.addPoints(-10);
			} else {
				me.addPoints(1);
			
				fields[x][y].setGraphic(new ImageView(image_floor));
				x+=delta_x;
				y+=delta_y;

				if (direction.equals("right")) {
					fields[x][y].setGraphic(new ImageView(hero_right));
				};
				if (direction.equals("left")) {
					fields[x][y].setGraphic(new ImageView(hero_left));
				};
				if (direction.equals("up")) {
					fields[x][y].setGraphic(new ImageView(hero_up));
				};
				if (direction.equals("down")) {
					fields[x][y].setGraphic(new ImageView(hero_down));
				};

				me.setXpos(x);
				me.setYpos(y);
			}
		}
		scoreList.setText(getScoreList());
		//Mades BS
		try {
			outToServer.writeBytes(me.getPosition() + '\n');
		}
		catch (IOException io) {
			System.out.println("IO Exception in main trying to write move to server");
		}


	}

	public String getScoreList() {
		StringBuffer b = new StringBuffer(100);
		b.append(me + "\r\n");
		for (Player p : players) {
			b.append(p+"\r\n");
		}
		return b.toString();
	}

	public Player getPlayerAt(int x, int y) {
		for (Player p : players) {
			if (p.getXpos()==x && p.getYpos()==y) {
				return p;
			}
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		//connectToServer();
		launch(args);
	}

	public static void connectToServer() throws Exception{

		
		clientSocket = new Socket(ip, port); //(serverIP, port serveren lytter paa)
		outToServer = new DataOutputStream(clientSocket.getOutputStream());

		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		//SendThread sender = new SendThread(me, outToServer);
		RecieveThread reciever = new RecieveThread(inFromServer);

		//sender.start();
		reciever.start();


	}

	public static void readyCheck() {
        Alert ready = new Alert(Alert.AlertType.INFORMATION);
        ready.setContentText("Are you reeeeaaaady?");
        ready.showAndWait();
        try {
            outToServer.writeBytes("ready" + '\n');
        }
        catch (Exception e) {
            System.out.println("IO exception in readycheck in main"); //bug when multiple
        }

    }

	public static void decodeAndExecute(String s) {
		if (s.startsWith("charinit")) {
			String[] stringarray = s.split(" ");
	        for (String str : stringarray) {
	            System.out.println(str);
	        }
			String name = stringarray[1];
			String dir = stringarray[4];
			int x;
			int y;
			try {
				x = Integer.parseInt(stringarray[2]);
				y = Integer.parseInt(stringarray[3]);
			}
			catch (Exception e) {
				System.out.println("Parse exception Main character creation");
				x = 0; // for compiler
				y = 0; // for compiler
			}
            Player p = new Player(name, x, y, dir);
			if (Main.playername.equalsIgnoreCase(name) && me == null)  {
			    me = p;
                System.out.println("Hero created");
            }
            else {
                players.add(p);
                System.out.println("character created");
            }


		}

		else if (s.startsWith("move")){
			//TODO HER KOMMER VI ALDRIG IND I
			String[] stringarraymove = s.split(" ");

			System.out.println("A player moved");

			for (String str : stringarraymove) {
				System.out.println(str);
			}
			String name = stringarraymove[1];
			String dir = stringarraymove[4];
			int x;
			int y;
			try {
				x = Integer.parseInt(stringarraymove[2]);
				y = Integer.parseInt(stringarraymove[3]);
			}
			catch (Exception e) {
				System.out.println("Parse exception Main character creation");
				x = 0; // for compiler
				y = 0; // for compiler
			}
			if (name.equalsIgnoreCase(playername)) {
				System.out.println("I moved");
				me.setDirection(dir);
				me.setXpos(x);
				me.setYpos(y);
			}
			else {
				System.out.println("Someone else moved");
				for (Player o : players) {
					System.out.println(o.getName());
					if (o.getName().equalsIgnoreCase(name)) {
						o.setDirection(dir);
						o.setXpos(x);
						o.setYpos(y);
					}
				}
			}
			setupPlayers();
		}
		else if(s.startsWith("start")) {
			infoScreen.close();
			gameon = true;
		}

	}

	public void createPlayer(String s) {
		String[] sa = s.split(" ");
		String name = sa[0];
		int x;
		int y;
		try {
			x = Integer.parseInt(sa[1]);
			y = Integer.parseInt(sa[2]);
		}
		catch (Exception e) {
			System.out.println("Parse issues in Main");
			x = 0;//til compler
			y= 0; //Til compiler
		}
		String dir = sa[3];
		if (!(name.equals(me.getName()))) {
			Player p = new Player(name, x, y, dir);
			players.add(p);

		}
	}

	public static void setupPlayers() {
		if (me == null) {
			System.out.println("Rage!!! Hero not found");
		}
		if (me.getDirection().equals("right")) {
			fields[me.getXpos()][me.getYpos()].setGraphic(new ImageView(hero_right));
		};
		if (me.getDirection().equals("left")) {
			fields[me.getXpos()][me.getYpos()].setGraphic(new ImageView(hero_left));
		};
		if (me.getDirection().equals("up")) {
			fields[me.getXpos()][me.getYpos()].setGraphic(new ImageView(hero_up));
		};
		if (me.getDirection().equals("down")) {
			fields[me.getXpos()][me.getYpos()].setGraphic(new ImageView(hero_down));
		};

		System.out.println("Antal spiller: " + players.size());
		for (Player p : players) {
			System.out.println(p.getName());
			if (p.getDirection().equals("right")) {
				fields[p.getXpos()][p.getYpos()].setGraphic(new ImageView(hero_right));
			};
			if (p.getDirection().equals("left")) {
				fields[p.getXpos()][p.getYpos()].setGraphic(new ImageView(hero_left));
			};
			if (p.getDirection().equals("up")) {
				fields[p.getXpos()][p.getYpos()].setGraphic(new ImageView(hero_up));
			};
			if (p.getDirection().equals("down")) {
				fields[p.getXpos()][p.getYpos()].setGraphic(new ImageView(hero_down));
			};
		}
	}


}

