package src;

import java.io.BufferedReader;
import java.io.IOException;

public class RecieveThread extends Thread {

    private BufferedReader sender;




    public RecieveThread (BufferedReader bf) {
        this.sender = bf;
    }

    public void run() {
        while (true) {
            try {
                String s = sender.readLine();
                System.out.println("RecieveThread: "  + s);
            }
            catch (IOException e) {
                System.out.println("IO exception");
            }

        }
    }



}
