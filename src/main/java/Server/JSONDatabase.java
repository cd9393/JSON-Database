package Server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JSONDatabase {
    private JsonObject database;
    private final String DB_FILE_PATH = "src/server/data/db.json";
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();
    private final Gson gson = new Gson();

    public JSONDatabase() {
        readDBFile();
    }

    public JsonElement get(JsonElement key) {
        readDBFile();
        if(key.isJsonPrimitive() && database.has(key.getAsString())) {
            return database.get(key.getAsString());
        } else if (key.isJsonArray()) {
            return findElement(key.getAsJsonArray(), false);
        }
        return null;
    }


    public String set(JsonElement key, JsonElement value) {
        if (database.isJsonNull()) {
            database = new JsonObject();
            database.add(key.getAsString(),value);
        } else {
            if (key.isJsonPrimitive()) {
                database.add(key.getAsString(),value);
            }
            else if (key.isJsonArray()){
                JsonArray keys = key.getAsJsonArray();
                String toAdd = keys.remove(keys.size() - 1).getAsString();
                findElement(keys, true).getAsJsonObject().add(toAdd, value);
            }
        }
        writeDBFile();
        return "OK";
    }

    public String delete(JsonElement key) {
        if (key.isJsonPrimitive() && database.has(key.getAsString())){
            database.remove(key.getAsString());
            writeDBFile();
        }
        else if (key.isJsonArray()){
            JsonArray keys = key.getAsJsonArray();
            String toDelete = keys.remove(keys.size() - 1).getAsString();
            findElement(keys, true).getAsJsonObject().remove(toDelete);
            writeDBFile();
            return "OK";
        }
        return "ERROR";
    }


    private void readDBFile() {
        Path path = new File(DB_FILE_PATH).toPath();
        readLock.lock();
        try(Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            database = gson.fromJson(reader,JsonObject.class);
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

    private JsonElement findElement(JsonArray keys, boolean createIfDoesntExist) {
        JsonElement result = database;
        if(createIfDoesntExist) {
            for(JsonElement key : keys) {
                if(!result.getAsJsonObject().has(key.getAsString())) {
                    result.getAsJsonObject().add(key.getAsString(),new JsonObject());
                }
                result = result.getAsJsonObject().get(key.getAsString());
            }
        } else {
            for(JsonElement key : keys) {
                if(!key.isJsonPrimitive() || !result.getAsJsonObject().has(key.getAsString())) {

                }
                result = result.getAsJsonObject().get(key.getAsString());
            }
        }
        return result;
    }
}
