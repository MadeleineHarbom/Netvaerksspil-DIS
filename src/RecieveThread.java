package src;

import java.io.BufferedReader;
import java.io.IOException;

public class RecieveThread extends Thread {

    private BufferedReader sender;
    private String sendername;



    public RecieveThread (BufferedReader bf) {
        this.sender = bf;
    }

    public void run() {
        while (true) {
            try {
                String s = sender.readLine();
                System.out.println("FROM " + this.sendername +": "  + s);
            }
            catch (IOException e) {
                System.out.println("IO exception");
            }

        }
    }



}
