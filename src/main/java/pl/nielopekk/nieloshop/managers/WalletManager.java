package pl.nielopekk.nieloshop.managers;

import org.bukkit.entity.Player;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.storage.PlayerData;

import java.util.UUID;

/**
 * Manages virtual wallet system for players
 * Handles wallet balance operations and queries
 */
public class WalletManager {

    private final NieloShop plugin;

    public WalletManager(NieloShop plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets player's wallet balance
     * @param player Player
     * @return Wallet balance
     */
    public double getBalance(Player player) {
        PlayerData data = plugin.getStorageManager().getOrCreatePlayerData(player);
        return data.getWalletBalance();
    }

    /**
     * Sets player's wallet balance
     * @param player Player
     * @param amount New balance
     */
    public void setBalance(Player player, double amount) {
        PlayerData data = plugin.getStorageManager().getOrCreatePlayerData(player);
        data.setWalletBalance(Math.max(0, amount));
        plugin.getStorageManager().savePlayerData(player.getUniqueId(), data);
    }

    /**
     * Adds amount to player's wallet
     * @param player Player
     * @param amount Amount to add
     */
    public void addBalance(Player player, double amount) {
        if (amount <= 0) return;
        PlayerData data = plugin.getStorageManager().getOrCreatePlayerData(player);
        data.addWalletBalance(amount);
        plugin.getStorageManager().savePlayerData(player.getUniqueId(), data);
    }

    /**
     * Removes amount from player's wallet
     * @param player Player
     * @param amount Amount to remove
     * @return true if successful, false if insufficient balance
     */
    public boolean removeBalance(Player player, double amount) {
        if (amount <= 0) return false;
        PlayerData data = plugin.getStorageManager().getOrCreatePlayerData(player);

        if (data.getWalletBalance() < amount) {
            return false;
        }

        data.removeWalletBalance(amount);
        plugin.getStorageManager().savePlayerData(player.getUniqueId(), data);
        return true;
    }

    /**
     * Checks if player has sufficient wallet balance
     * @param player Player
     * @param amount Amount to check
     * @return true if player has enough balance
     */
    public boolean hasBalance(Player player, double amount) {
        return getBalance(player) >= amount;
    }
}
