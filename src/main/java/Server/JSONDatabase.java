package Server;
import java.util.HashMap;
import java.util.Map;

public class JSONDatabase {
   private Map<String, String> database;

    public JSONDatabase() {
        this.database = new HashMap<>();
    }

    public String get(String key) {
        return database.getOrDefault(key, "ERROR");
    }

    public String set(String key, String value) {
        database.put(key,value);
        return "OK";
    }

    public String delete(String key) {
        if (database.containsKey(key)) {
            database.remove(key);
            return "OK";
        }
        return "ERROR";
    }
}
