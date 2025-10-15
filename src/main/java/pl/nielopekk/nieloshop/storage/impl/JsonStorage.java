package pl.nielopekk.nieloshop.storage.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.storage.IStorage;
import pl.nielopekk.nieloshop.storage.PlayerData;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JSON storage implementation
 * Stores player data in JSON files
 */
public class JsonStorage implements IStorage {

    private final NieloShop plugin;
    private final Map<UUID, PlayerData> cache;
    private final Gson gson;
    private File dataFile;

    public JsonStorage(NieloShop plugin) {
        this.plugin = plugin;
        this.cache = new HashMap<>();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void init() {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        dataFile = new File(dataFolder, "playerdata.json");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                try (FileWriter writer = new FileWriter(dataFile)) {
                    writer.write("{}");
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Failed to create playerdata.json!");
                e.printStackTrace();
            }
        }

        plugin.getLogger().info("JSON storage initialized successfully!");
    }

    @Override
    public PlayerData loadPlayerData(UUID uuid) {
        if (cache.containsKey(uuid)) {
            return cache.get(uuid);
        }

        loadAll();
        return cache.get(uuid);
    }

    @Override
    public void savePlayerData(UUID uuid, PlayerData data) {
        cache.put(uuid, data);
    }

    @Override
    public void loadAll() {
        try (FileReader reader = new FileReader(dataFile)) {
            Type type = new TypeToken<Map<String, PlayerDataJson>>() {}.getType();
            Map<String, PlayerDataJson> jsonData = gson.fromJson(reader, type);

            if (jsonData != null) {
                cache.clear();
                for (Map.Entry<String, PlayerDataJson> entry : jsonData.entrySet()) {
                    UUID uuid = UUID.fromString(entry.getKey());
                    PlayerDataJson json = entry.getValue();
                    PlayerData data = new PlayerData(
                            uuid,
                            json.walletBalance,
                            json.timePoints,
                            json.totalPlaytime,
                            json.lastLogin
                    );
                    cache.put(uuid, data);
                }
                plugin.getLogger().info("Loaded " + cache.size() + " player data entries from JSON");
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to load player data from JSON!");
            e.printStackTrace();
        }
    }

    @Override
    public void saveAll() {
        Map<String, PlayerDataJson> jsonData = new HashMap<>();

        for (Map.Entry<UUID, PlayerData> entry : cache.entrySet()) {
            PlayerData data = entry.getValue();
            PlayerDataJson json = new PlayerDataJson();
            json.walletBalance = data.getWalletBalance();
            json.timePoints = data.getTimePoints();
            json.totalPlaytime = data.getTotalPlaytime();
            json.lastLogin = data.getLastLogin();
            jsonData.put(entry.getKey().toString(), json);
        }

        try (FileWriter writer = new FileWriter(dataFile)) {
            gson.toJson(jsonData, writer);
            plugin.getLogger().info("Saved " + cache.size() + " player data entries to JSON");
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save player data to JSON!");
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        saveAll();
        plugin.getLogger().info("JSON storage closed");
    }

    /**
     * JSON data structure for serialization
     */
    private static class PlayerDataJson {
        double walletBalance;
        int timePoints;
        long totalPlaytime;
        long lastLogin;
    }
}
