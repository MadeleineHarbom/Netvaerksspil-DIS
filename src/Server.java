package src;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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

        ServerSocket welcomeSocket = new ServerSocket(6666);
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));



        while (true) {
            // der oprettes en connectionSocket til hver klient der vil have forbindelse
            Socket connectionSocket = welcomeSocket.accept();

            // input for klient
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            // output til klient
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            System.out.println("Test");
        }

    }
}
