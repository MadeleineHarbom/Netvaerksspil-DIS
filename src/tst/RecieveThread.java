package src.tst;

import java.io.BufferedReader;
import java.io.IOException;

public class RecieveThread extends Thread {

    //BufferedReader incoming;
/*
    public RecieveThread(BufferedReader bf) {
        this.incoming = bf;
    }*/

    private BufferedReader sender;
    private String sendername;



    public RecieveThread (BufferedReader bf, String countername) {
        this.sender = bf;
        this.sendername = countername;
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
