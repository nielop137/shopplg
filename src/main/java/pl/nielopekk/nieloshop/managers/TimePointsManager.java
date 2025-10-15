package pl.nielopekk.nieloshop.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.storage.PlayerData;

/**
 * Manages time points system
 * Awards points to online players based on playtime
 * Includes AFK detection
 */
public class TimePointsManager {

    private final NieloShop plugin;
    private BukkitTask task;

    public TimePointsManager(NieloShop plugin) {
        this.plugin = plugin;
    }

    /**
     * Starts the time points task
     */
    public void startTask() {
        int interval = plugin.getConfigManager().getIntervalSeconds() * 20;

        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                awardPoints(player);
            }
        }, interval, interval);
    }

    /**
     * Stops the time points task
     */
    public void stopTask() {
        if (task != null) {
            task.cancel();
        }
    }

    /**
     * Awards time points to a player
     * Checks AFK status before awarding
     * @param player Player to award points to
     */
    private void awardPoints(Player player) {
        PlayerData data = plugin.getStorageManager().getOrCreatePlayerData(player);

        if (plugin.getConfigManager().isStopOnAfk()) {
            long afkThreshold = plugin.getConfigManager().getAfkThresholdSeconds() * 1000L;
            if (data.isAfk(afkThreshold)) {
                return;
            }
        }

        int points = plugin.getConfigManager().getPointsPerInterval();
        data.addTimePoints(points);

        long playtime = plugin.getConfigManager().getIntervalSeconds() * 1000L;
        data.addPlaytime(playtime);

        plugin.getStorageManager().savePlayerData(player.getUniqueId(), data);
    }

    /**
     * Gets player's time points
     * @param player Player
     * @return Time points
     */
    public int getPoints(Player player) {
        PlayerData data = plugin.getStorageManager().getOrCreatePlayerData(player);
        return data.getTimePoints();
    }

    /**
     * Sets player's time points
     * @param player Player
     * @param points New points value
     */
    public void setPoints(Player player, int points) {
        PlayerData data = plugin.getStorageManager().getOrCreatePlayerData(player);
        data.setTimePoints(Math.max(0, points));
        plugin.getStorageManager().savePlayerData(player.getUniqueId(), data);
    }

    /**
     * Adds time points to player
     * @param player Player
     * @param points Points to add
     */
    public void addPoints(Player player, int points) {
        if (points <= 0) return;
        PlayerData data = plugin.getStorageManager().getOrCreatePlayerData(player);
        data.addTimePoints(points);
        plugin.getStorageManager().savePlayerData(player.getUniqueId(), data);
    }

    /**
     * Removes time points from player
     * @param player Player
     * @param points Points to remove
     * @return true if successful, false if insufficient points
     */
    public boolean removePoints(Player player, int points) {
        if (points <= 0) return false;
        PlayerData data = plugin.getStorageManager().getOrCreatePlayerData(player);

        if (data.getTimePoints() < points) {
            return false;
        }

        data.removeTimePoints(points);
        plugin.getStorageManager().savePlayerData(player.getUniqueId(), data);
        return true;
    }

    /**
     * Checks if player has sufficient time points
     * @param player Player
     * @param points Points to check
     * @return true if player has enough points
     */
    public boolean hasPoints(Player player, int points) {
        return getPoints(player) >= points;
    }

    /**
     * Gets player's total playtime in milliseconds
     * @param player Player
     * @return Playtime in milliseconds
     */
    public long getPlaytime(Player player) {
        PlayerData data = plugin.getStorageManager().getOrCreatePlayerData(player);
        return data.getTotalPlaytime();
    }

    /**
     * Formats playtime into readable string
     * @param milliseconds Playtime in milliseconds
     * @return Formatted string (e.g., "5h 30m 15s")
     */
    public String formatPlaytime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        seconds %= 60;
        minutes %= 60;
        hours %= 24;

        StringBuilder sb = new StringBuilder();
        if (days > 0) sb.append(days).append("d ");
        if (hours > 0) sb.append(hours).append("h ");
        if (minutes > 0) sb.append(minutes).append("m ");
        if (seconds > 0) sb.append(seconds).append("s");

        return sb.toString().trim();
    }
}
