package src;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    //THIS CLASS IS TO BE DELETED

    public static void main(String[] args) throws Exception, IOException {
        String ip = "localhost";
        int port = 6666;


        SendThread sender;
        RecieveThread reciever;



        Socket clientSocket = new Socket(ip, portNumber); //(serverIP, port serveren lytter paa)
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        
        //Læser et input fra klienten for at teste 
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(System.in));
        
        //Læser serverens besked
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        //sender = new SendThread(outToServer, inFromUser);
        //reciever = new RecieveThread(inFromServer, "SERVER");

        //sender.start();
        //reciever.start();





    }
}
