package Server;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JSONDatabase {
    private Map<String, String> database;
    private final String DB_FILE_PATH = "src/server/data/db.json";
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();
    private final Gson gson = new Gson();

    public JSONDatabase() {
        this.database = new HashMap<>();
    }

    public String get(String key) {
        readDBFile();
        return database.getOrDefault(key, "ERROR");
    }

    public String set(String key, String value) {
        database.put(key,value);
        writeDBFile();
        return "OK";
    }

    public String delete(String key) {
        if (database.containsKey(key)) {
            database.remove(key);
            writeDBFile();
            return "OK";
        }
        return "ERROR";
    }

    public void setDatabase(Map<String, String> database) {
        this.database = database;
    }

    private void readDBFile() {
        Path path = new File(DB_FILE_PATH).toPath();
        readLock.lock();
        try(Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            setDatabase(gson.fromJson(reader,Map.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        readLock.unlock();
    }

    private void writeDBFile() {
        writeLock.lock();
        try (FileOutputStream fos = new FileOutputStream(DB_FILE_PATH);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {
            gson.toJson(this.database,isr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeLock.unlock();
    }
}
