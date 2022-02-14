package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int PORT = 23446;
    private static JSONDatabase database = new JSONDatabase();
    private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws IOException {
        System.out.println("Server started!");
        try(ServerSocket server = new ServerSocket(PORT, 50,InetAddress.getByName("127.0.0.1"))) {
            while(true){
                Socket client = server.accept();
                ClientHandler clientHandler = new ClientHandler(client,server,database);
                executor.submit(clientHandler);
            }
        }
    }

}
