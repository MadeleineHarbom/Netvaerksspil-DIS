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



        Socket clientSocket = new Socket(ip, port); //(serverIP, port serveren lytter paa)
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        //sender = new SendThread(outToServer, inFromUser);
        //reciever = new RecieveThread(inFromServer, "SERVER");

        //sender.start();
        //reciever.start();





    }
}
