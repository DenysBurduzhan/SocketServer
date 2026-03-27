package Client;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageWriter implements Runnable{
    private PrintWriter out;
    private BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    public MessageWriter(PrintWriter out) {
        this.out = out;
    }

    public void sendMessage(String message) {
        if (message != null && !message.isBlank()) {
            messageQueue.offer(message);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = messageQueue.take();
                out.println(msg);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
