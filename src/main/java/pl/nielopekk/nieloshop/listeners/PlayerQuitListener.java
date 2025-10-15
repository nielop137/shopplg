package pl.nielopekk.nieloshop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.storage.PlayerData;

/**
 * Handles player quit events
 * Saves player data before disconnect
 */
public class PlayerQuitListener implements Listener {

    private final NieloShop plugin;

    public PlayerQuitListener(NieloShop plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        PlayerData data = plugin.getStorageManager().loadPlayerData(player.getUniqueId());
        if (data != null) {
            long sessionTime = System.currentTimeMillis() - data.getLastLogin();
            data.addPlaytime(sessionTime);
            plugin.getStorageManager().savePlayerData(player.getUniqueId(), data);
        }
    }
}
