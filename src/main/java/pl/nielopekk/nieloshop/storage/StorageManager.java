package pl.nielopekk.nieloshop.storage;

import org.bukkit.entity.Player;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.storage.impl.JsonStorage;
import pl.nielopekk.nieloshop.storage.impl.SqliteStorage;

import java.util.UUID;

/**
 * Manages data storage for player data
 * Supports SQLite and JSON storage backends
 */
public class StorageManager {

    private final NieloShop plugin;
    private IStorage storage;

    public StorageManager(NieloShop plugin) {
        this.plugin = plugin;
        initStorage();
    }

    /**
     * Initializes storage based on configuration
     */
    private void initStorage() {
        String type = plugin.getConfigManager().getStorageType().toUpperCase();

        switch (type) {
            case "JSON":
                storage = new JsonStorage(plugin);
                plugin.getLogger().info("Using JSON storage");
                break;
            case "SQLITE":
            default:
                storage = new SqliteStorage(plugin);
                plugin.getLogger().info("Using SQLite storage");
                break;
        }

        storage.init();
    }

    /**
     * Loads player data from storage
     * @param uuid Player UUID
     * @return PlayerData object
     */
    public PlayerData loadPlayerData(UUID uuid) {
        return storage.loadPlayerData(uuid);
    }

    /**
     * Saves player data to storage
     * @param uuid Player UUID
     * @param data PlayerData to save
     */
    public void savePlayerData(UUID uuid, PlayerData data) {
        storage.savePlayerData(uuid, data);
    }

    /**
     * Loads all player data from storage
     */
    public void load() {
        storage.loadAll();
    }

    /**
     * Saves all player data to storage
     */
    public void save() {
        storage.saveAll();
    }

    /**
     * Closes storage connection
     */
    public void close() {
        storage.close();
    }

    /**
     * Gets or creates player data
     * @param player Player
     * @return PlayerData object
     */
    public PlayerData getOrCreatePlayerData(Player player) {
        PlayerData data = loadPlayerData(player.getUniqueId());
        if (data == null) {
            data = new PlayerData(player.getUniqueId());
            data.setWalletBalance(plugin.getConfigManager().getStartingBalance());
            savePlayerData(player.getUniqueId(), data);
        }
        return data;
    }
}
