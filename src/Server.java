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

        Map people = new HashMap<String, String>();
        people.put("Karl", "10.24.6.117");
        people.put("Made", "10.24.68.3");

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
