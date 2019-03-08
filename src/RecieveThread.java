package src;

import java.io.BufferedReader;
import java.io.IOException;

public class RecieveThread extends Thread {

    private BufferedReader sender;
    String s;




    public RecieveThread (BufferedReader bf) {
        this.sender = bf;
    }

    public void run() {
        while (true) {
            try {
                s = sender.readLine();
                Main.decodeAndExecute(s);
            }
            catch (IOException e) {
                System.out.println("IO exception");
            }

        }
    }

    public String getMsg() {
        return s;
    }



}
