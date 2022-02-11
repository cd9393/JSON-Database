package Server;

public class JSONDatabase {
    String[] database;

    public JSONDatabase() {
        this.database = new String[1000];
        init();
    }

    public void init() {
        for (int i = 0; i < this.database.length; i++) {
            this.database[i] = "";
        }
    }

    public String get(int index) {
        return "".equals(this.database[index])? "ERROR" : this.database[index];
    }

    public String set(int index, String value) {
        this.database[index] = value;
        return "OK";
    }

    public String delete(int index) {
        this.database[index] = "";
        return "OK";
    }
}
