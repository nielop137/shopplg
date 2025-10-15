package pl.nielopekk.nieloshop.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.nielopekk.nieloshop.NieloShop;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Manages plugin configuration files
 * Handles loading, reloading, and accessing configuration values
 */
public class ConfigManager {

    private final NieloShop plugin;
    private FileConfiguration config;
    private FileConfiguration messages;
    private FileConfiguration shops;

    public ConfigManager(NieloShop plugin) {
        this.plugin = plugin;
        loadConfigs();
    }

    /**
     * Loads all configuration files
     * Creates default files if they don't exist
     */
    public void loadConfigs() {
        // Save default configs
        saveDefaultConfig("config.yml");
        saveDefaultConfig("messages.yml");
        saveDefaultConfig("shops.yml");

        // Load configs
        config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        messages = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "messages.yml"));
        shops = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "shops.yml"));
    }

    /**
     * Saves default configuration file from resources
     */
    private void saveDefaultConfig(String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            plugin.getDataFolder().mkdirs();
            try (InputStream in = plugin.getResource(fileName)) {
                if (in != null) {
                    Files.copy(in, file.toPath());
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Could not save " + fileName + "!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Reloads all configuration files
     */
    public void reload() {
        loadConfigs();
    }

    /**
     * Saves shops configuration to file
     */
    public void saveShops() {
        try {
            shops.save(new File(plugin.getDataFolder(), "shops.yml"));
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save shops.yml!");
            e.printStackTrace();
        }
    }

    // Config getters
    public String getStorageType() {
        return config.getString("storage-type", "SQLITE");
    }

    public boolean isTimePointsEnabled() {
        return config.getBoolean("time-points.enabled", true);
    }

    public int getPointsPerInterval() {
        return config.getInt("time-points.points-per-interval", 1);
    }

    public int getIntervalSeconds() {
        return config.getInt("time-points.interval-seconds", 60);
    }

    public int getAfkThresholdSeconds() {
        return config.getInt("time-points.afk-threshold-seconds", 60);
    }

    public boolean isStopOnAfk() {
        return config.getBoolean("time-points.stop-on-afk", true);
    }

    public boolean isWalletEnabled() {
        return config.getBoolean("wallet.enabled", true);
    }

    public double getStartingBalance() {
        return config.getDouble("wallet.starting-balance", 0);
    }

    public boolean useShortFormat() {
        return config.getBoolean("currency-format.use-short-format", true);
    }

    public String getThousandSymbol() {
        return config.getString("currency-format.thousand", "k");
    }

    public String getMillionSymbol() {
        return config.getString("currency-format.million", "M");
    }

    public String getBillionSymbol() {
        return config.getString("currency-format.billion", "B");
    }

    public String getTrillionSymbol() {
        return config.getString("currency-format.trillion", "T");
    }

    public int getDecimalPlaces() {
        return config.getInt("currency-format.decimal-places", 1);
    }

    public String getVaultShopTitle() {
        return config.getString("gui.vault-shop-title", "&6&lVault Shop");
    }

    public String getTimeShopTitle() {
        return config.getString("gui.time-shop-title", "&b&lTime Shop");
    }

    public String getWalletShopTitle() {
        return config.getString("gui.wallet-shop-title", "&a&lWallet Shop");
    }

    public boolean isAnimationsEnabled() {
        return config.getBoolean("gui.enable-animations", true);
    }

    public boolean isConfirmationEnabled() {
        return config.getBoolean("gui.confirmation-enabled", true);
    }

    public String getConfirmationTitle() {
        return config.getString("gui.confirmation-title", "&e&lConfirm Purchase");
    }

    public boolean isDebug() {
        return config.getBoolean("debug", false);
    }

    // Messages getters
    public String getMessage(String path) {
        return messages.getString(path, "Message not found: " + path);
    }

    public String getPrefix() {
        return getMessage("prefix");
    }

    // Shops config getter
    public FileConfiguration getShopsConfig() {
        return shops;
    }
}
