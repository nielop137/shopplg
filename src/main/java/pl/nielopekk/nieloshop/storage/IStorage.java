package pl.nielopekk.nieloshop.storage;

import java.util.UUID;

/**
 * Interface for storage implementations
 * Allows switching between different storage backends (SQLite, JSON)
 */
public interface IStorage {

    /**
     * Initializes the storage system
     */
    void init();

    /**
     * Loads player data from storage
     * @param uuid Player UUID
     * @return PlayerData or null if not found
     */
    PlayerData loadPlayerData(UUID uuid);

    /**
     * Saves player data to storage
     * @param uuid Player UUID
     * @param data PlayerData to save
     */
    void savePlayerData(UUID uuid, PlayerData data);

    /**
     * Loads all player data from storage
     */
    void loadAll();

    /**
     * Saves all player data to storage
     */
    void saveAll();

    /**
     * Closes storage connection
     */
    void close();
}
