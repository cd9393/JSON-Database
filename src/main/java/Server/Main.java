package Server;

import Messages.Request;
import Messages.Response;
import com.google.gson.Gson;

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
                    Gson gson = new Gson();
                    Request request = gson.fromJson(receivedMessage,Request.class);
                    System.out.println(request);

                    if ("exit".equals(request.getType())) {
                        Response response = new Response("OK");
                        output.writeUTF(gson.toJson(response));
                        break;
                    } else {
                        CommandHandler handler = new CommandHandler(request,database);
                        String response = handler.run();
                        System.out.println(response);
                        output.writeUTF(response);
                    }
                }
            }
        }
    }

}
