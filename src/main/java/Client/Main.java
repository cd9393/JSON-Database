package Client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    private static final String SERVER_ADDRESS= "127.0.0.1";
    private static final int PORT = 34522;
    @Parameter(names = "-t")
    String command;

    @Parameter(names = "-i")
    int index;

    @Parameter(names = "-m")
    String text;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(args);
        String request = main.toString();
        main.runClient(request.replace("null",""));

    }

    private void runClient(String request) {
        try(
                Socket socket = new Socket(SERVER_ADDRESS,PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");
            output.writeUTF(request);
            System.out.println("Sent: " + request);
            String receivedMsg = input.readUTF();
            System.out.println("Received: " + receivedMsg);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return command + "," + index + "," + text;
    }
}
