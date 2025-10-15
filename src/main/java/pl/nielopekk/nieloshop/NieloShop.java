package pl.nielopekk.nieloshop;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import pl.nielopekk.nieloshop.commands.*;
import pl.nielopekk.nieloshop.listeners.*;
import pl.nielopekk.nieloshop.managers.*;
import pl.nielopekk.nieloshop.storage.StorageManager;
import pl.nielopekk.nieloshop.utils.PlaceholderExpansion;

/**
 * NieloShop - Advanced shop plugin for Minecraft
 * Author: Nielopekk (discord: nielopekk)
 * Version: 1.0.0
 * Supports: Spigot/Paper 1.16.5 - 1.21+
 *
 * Main plugin class responsible for initialization and dependency management
 */
public class NieloShop extends JavaPlugin {

    private static NieloShop instance;
    private Economy economy;

    // Managers
    private ConfigManager configManager;
    private StorageManager storageManager;
    private WalletManager walletManager;
    private TimePointsManager timePointsManager;
    private ShopManager shopManager;
    private ItemAddManager itemAddManager;

    @Override
    public void onEnable() {
        instance = this;

        // ASCII Art Logo
        getLogger().info("  _   _ _      _      ____  _                 ");
        getLogger().info(" | \\ | (_) ___| | ___/ ___|| |__   ___  _ __  ");
        getLogger().info(" |  \\| | |/ _ \\ |/ _ \\___ \\| '_ \\ / _ \\| '_ \\ ");
        getLogger().info(" | |\\  | |  __/ | (_) |__) | | | | (_) | |_) |");
        getLogger().info(" |_| \\_|_|\\___|_|\\___/____/|_| |_|\\___/| .__/ ");
        getLogger().info("                                        |_|    ");
        getLogger().info("Version: " + getDescription().getVersion());
        getLogger().info("Author: Nielopekk (discord: nielopekk)");
        getLogger().info("");

        // Setup Vault economy
        if (!setupEconomy()) {
            getLogger().severe("Vault not found! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Initialize managers
        configManager = new ConfigManager(this);
        storageManager = new StorageManager(this);
        walletManager = new WalletManager(this);
        timePointsManager = new TimePointsManager(this);
        shopManager = new ShopManager(this);
        itemAddManager = new ItemAddManager(this);

        // Load data
        storageManager.load();
        shopManager.loadShops();

        // Register commands
        registerCommands();

        // Register listeners
        registerListeners();

        // Setup PlaceholderAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderExpansion(this).register();
            getLogger().info("PlaceholderAPI integration enabled!");
        } else {
            getLogger().warning("PlaceholderAPI not found! Placeholders will not work.");
        }

        // Start time points task
        if (configManager.isTimePointsEnabled()) {
            timePointsManager.startTask();
            getLogger().info("Time points system enabled!");
        }

        getLogger().info("NieloShop enabled successfully!");
    }

    @Override
    public void onDisable() {
        // Save data
        if (storageManager != null) {
            storageManager.save();
        }

        // Stop tasks
        if (timePointsManager != null) {
            timePointsManager.stopTask();
        }

        // Close database connection
        if (storageManager != null) {
            storageManager.close();
        }

        getLogger().info("NieloShop disabled successfully!");
    }

    /**
     * Sets up Vault economy integration
     * @return true if successful, false otherwise
     */
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    /**
     * Registers all plugin commands
     */
    private void registerCommands() {
        getCommand("nieloshop").setExecutor(new NieloShopCommand(this));
        getCommand("shop").setExecutor(new ShopCommand(this));
        getCommand("portfel").setExecutor(new WalletCommand(this));
        getCommand("czas").setExecutor(new TimeCommand(this));
    }

    /**
     * Registers all event listeners
     */
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        getServer().getPluginManager().registerEvents(new ItemAddListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatInputListener(this), this);
    }

    // Getters
    public static NieloShop getInstance() {
        return instance;
    }

    public Economy getEconomy() {
        return economy;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public WalletManager getWalletManager() {
        return walletManager;
    }

    public TimePointsManager getTimePointsManager() {
        return timePointsManager;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }

    public ItemAddManager getItemAddManager() {
        return itemAddManager;
    }
}
