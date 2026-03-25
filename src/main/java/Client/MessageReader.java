package Client;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageReader implements Runnable{
    private BufferedReader in;

    public MessageReader(BufferedReader in){
        this.in = in;
    }


    @Override
    public void run() {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                System.out.println(msg);
            }
        } catch (IOException e) {
            System.out.println("Disconnected from server");
        }
    }
}
