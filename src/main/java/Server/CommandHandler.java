package Server;


public class CommandHandler {
    private final JSONDatabase database;
    private String command;
    private int index;
    private String text;

    public CommandHandler(String[] commandReceived, JSONDatabase database) {
        this.command = commandReceived[0];
        this.index = Integer.parseInt(commandReceived[1]);
        if(commandReceived.length > 2) {
            this.text = commandReceived[2];
        }
        this.database = database;
    }

    public String run() {
    String response = "ERROR";
        if(checkIndex(index)) {
            switch (command) {
                case "get":
                    response = database.get(index - 1);
                    break;
                case "set":
                    response = database.set(index - 1, text);
                    break;
                case "delete":
                    response = database.delete(index - 1);
                    break;
                case "exit":
                    response =  "exit";
                    break;
                default:
                    break;
            }
        }
        return response;
    }


    private boolean checkIndex(int index) {
        if (index < 1 || index > 1000) {
            return false;
        }
        return true;
    }

}
