package pl.nielopekk.nieloshop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.storage.PlayerData;

/**
 * Handles player join events
 * Loads player data and initializes new players
 */
public class PlayerJoinListener implements Listener {

    private final NieloShop plugin;

    public PlayerJoinListener(NieloShop plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerData data = plugin.getStorageManager().getOrCreatePlayerData(player);
        data.setLastLogin(System.currentTimeMillis());
        data.setLastMovement(System.currentTimeMillis());
        plugin.getStorageManager().savePlayerData(player.getUniqueId(), data);
    }
}
