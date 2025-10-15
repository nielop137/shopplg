package pl.nielopekk.nieloshop.storage.impl;

import org.bukkit.Bukkit;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.storage.IStorage;
import pl.nielopekk.nieloshop.storage.PlayerData;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * SQLite storage implementation
 * Stores player data in a local SQLite database file
 */
public class SqliteStorage implements IStorage {

    private final NieloShop plugin;
    private Connection connection;
    private final Map<UUID, PlayerData> cache;

    public SqliteStorage(NieloShop plugin) {
        this.plugin = plugin;
        this.cache = new HashMap<>();
    }

    @Override
    public void init() {
        try {
            File dataFolder = plugin.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }

            File dbFile = new File(dataFolder, "data.db");
            String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();

            connection = DriverManager.getConnection(url);
            createTables();

            plugin.getLogger().info("SQLite database initialized successfully!");
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to initialize SQLite database!");
            e.printStackTrace();
        }
    }

    /**
     * Creates database tables if they don't exist
     */
    private void createTables() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS player_data (" +
                "uuid TEXT PRIMARY KEY," +
                "wallet_balance REAL NOT NULL DEFAULT 0," +
                "time_points INTEGER NOT NULL DEFAULT 0," +
                "total_playtime INTEGER NOT NULL DEFAULT 0," +
                "last_login INTEGER NOT NULL DEFAULT 0" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public PlayerData loadPlayerData(UUID uuid) {
        if (cache.containsKey(uuid)) {
            return cache.get(uuid);
        }

        String sql = "SELECT * FROM player_data WHERE uuid = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, uuid.toString());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                PlayerData data = new PlayerData(
                        uuid,
                        rs.getDouble("wallet_balance"),
                        rs.getInt("time_points"),
                        rs.getLong("total_playtime"),
                        rs.getLong("last_login")
                );
                cache.put(uuid, data);
                return data;
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to load player data for " + uuid);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void savePlayerData(UUID uuid, PlayerData data) {
        cache.put(uuid, data);

        String sql = "INSERT OR REPLACE INTO player_data (uuid, wallet_balance, time_points, total_playtime, last_login) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, uuid.toString());
            pstmt.setDouble(2, data.getWalletBalance());
            pstmt.setInt(3, data.getTimePoints());
            pstmt.setLong(4, data.getTotalPlaytime());
            pstmt.setLong(5, data.getLastLogin());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to save player data for " + uuid);
            e.printStackTrace();
        }
    }

    @Override
    public void loadAll() {
        String sql = "SELECT * FROM player_data";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int count = 0;
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                PlayerData data = new PlayerData(
                        uuid,
                        rs.getDouble("wallet_balance"),
                        rs.getInt("time_points"),
                        rs.getLong("total_playtime"),
                        rs.getLong("last_login")
                );
                cache.put(uuid, data);
                count++;
            }

            plugin.getLogger().info("Loaded " + count + " player data entries from database");
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to load all player data!");
            e.printStackTrace();
        }
    }

    @Override
    public void saveAll() {
        int count = 0;
        for (Map.Entry<UUID, PlayerData> entry : cache.entrySet()) {
            savePlayerData(entry.getKey(), entry.getValue());
            count++;
        }
        plugin.getLogger().info("Saved " + count + " player data entries to database");
    }

    @Override
    public void close() {
        saveAll();
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                plugin.getLogger().info("SQLite database connection closed");
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to close database connection!");
            e.printStackTrace();
        }
    }
}
