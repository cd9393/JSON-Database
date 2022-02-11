package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
private static final int PORT = 34522;
private static JSONDatabase database = new JSONDatabase();

    public static void main(String[] args) throws IOException {
        System.out.println("Server started!");
        try(ServerSocket server = new ServerSocket(PORT, 50,InetAddress.getByName("127.0.0.1"))) {
                while(true){
                    try(
                            Socket socket = server.accept(); // accepting a new client
                            DataInputStream input = new DataInputStream(socket.getInputStream());
                            DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                    ) {
                        String receivedMessage = input.readUTF();
                        String[] messageArray = receivedMessage.split(",");

                        if ("exit".equals(messageArray[0])) {
                            output.writeUTF("OK");
                            break;
                        } else {
                            CommandHandler handler = new CommandHandler(messageArray,database);
                            String response = handler.run();
                            System.out.println(response);
                            output.writeUTF(response);
                        }
                    }
                }
        }
    }

}
