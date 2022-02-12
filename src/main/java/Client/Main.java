package Client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final String SERVER_ADDRESS= "127.0.0.1";
    private static final int PORT = 34522;
    @Parameter(names = "-t")
    String command;

    @Parameter(names = "-k")
    String key;

    @Parameter(names = "-v")
    String value;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(args);
        String request = main.createJSON();
        main.runClient(request);

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
        return command + "," + key + "," + value;
    }

    private String createJSON() {
        Map<String,String> commands = new HashMap<>();
        commands.put("type",command);
        commands.put("key",key);
        if(!"null".equals(value)){
            commands.put("value",value);
        }
        Gson gson = new Gson();
        return gson.toJson(commands);
    }
}
