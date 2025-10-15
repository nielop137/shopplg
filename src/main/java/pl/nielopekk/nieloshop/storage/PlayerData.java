package pl.nielopekk.nieloshop.storage;

import java.util.UUID;

/**
 * Represents player data stored in the database
 * Contains wallet balance, time points, and playtime information
 */
public class PlayerData {

    private final UUID uuid;
    private double walletBalance;
    private int timePoints;
    private long totalPlaytime;
    private long lastLogin;
    private long lastMovement;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.walletBalance = 0;
        this.timePoints = 0;
        this.totalPlaytime = 0;
        this.lastLogin = System.currentTimeMillis();
        this.lastMovement = System.currentTimeMillis();
    }

    public PlayerData(UUID uuid, double walletBalance, int timePoints, long totalPlaytime, long lastLogin) {
        this.uuid = uuid;
        this.walletBalance = walletBalance;
        this.timePoints = timePoints;
        this.totalPlaytime = totalPlaytime;
        this.lastLogin = lastLogin;
        this.lastMovement = System.currentTimeMillis();
    }

    // Getters
    public UUID getUuid() {
        return uuid;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public int getTimePoints() {
        return timePoints;
    }

    public long getTotalPlaytime() {
        return totalPlaytime;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public long getLastMovement() {
        return lastMovement;
    }

    // Setters
    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public void setTimePoints(int timePoints) {
        this.timePoints = timePoints;
    }

    public void setTotalPlaytime(long totalPlaytime) {
        this.totalPlaytime = totalPlaytime;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setLastMovement(long lastMovement) {
        this.lastMovement = lastMovement;
    }

    // Utility methods
    public void addWalletBalance(double amount) {
        this.walletBalance += amount;
    }

    public void removeWalletBalance(double amount) {
        this.walletBalance -= amount;
    }

    public void addTimePoints(int points) {
        this.timePoints += points;
    }

    public void removeTimePoints(int points) {
        this.timePoints -= points;
    }

    public void addPlaytime(long milliseconds) {
        this.totalPlaytime += milliseconds;
    }

    /**
     * Checks if player has been AFK for the specified duration
     * @param thresholdMillis AFK threshold in milliseconds
     * @return true if player is AFK
     */
    public boolean isAfk(long thresholdMillis) {
        return (System.currentTimeMillis() - lastMovement) >= thresholdMillis;
    }
}
