package Server;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientHandler implements Runnable{
    private String name;
    private Server server;
    private BufferedReader in;
    private BufferedWriter out;
    private Socket clientSocket = null;

    public ClientHandler(Socket socket, Server server){
        try{
            this.server = server;
            this.clientSocket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void closeConnection() throws IOException {
        ClientManager.removeClient(this);
        ClientManager.sendMessageToAll(String.valueOf(ClientManager.getClients().size()));
    }

    public void sendMessage(String message) throws IOException {
        out.write(message);
        out.newLine();
        out.flush();
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        while (true){
            try {
                out.write("Enter yor name:");
                out.newLine();
                out.flush();

                String inputName = in.readLine();

                if(!ClientManager.isNameTaken(inputName)){
                    this.name = inputName;
                    break;
                }else{
                    out.write("Name already taken, try again");
                    out.newLine();
                    out.flush();
                    ClientManager.sendMessageToAll(name + " joined the chat");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        try{
                String message;
            while ((message = in.readLine()) != null){
                if (message.startsWith("/pm")) {

                    String[] parts = message.split(" ", 3);

                    if (parts.length < 3) {
                        sendMessage("Usage: /pm <name> <message>");
                        continue;
                    }

                    String targetName = parts[1];
                    String privateMessage = parts[2];

                    ClientManager.sendPrivateMessage(name, targetName, privateMessage);
                    continue;

                }
                if(message.equalsIgnoreCase("END")){
                    break;
                }
                System.out.println(message);
                ClientManager.sendMessageToAll(name +": " + message);
            }
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                closeConnection();
                clientSocket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
