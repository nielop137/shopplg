package pl.nielopekk.nieloshop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.storage.PlayerData;

/**
 * Handles player movement events
 * Updates last movement time for AFK detection
 */
public class PlayerMoveListener implements Listener {

    private final NieloShop plugin;

    public PlayerMoveListener(NieloShop plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom().getBlockX() == event.getTo().getBlockX() &&
                event.getFrom().getBlockY() == event.getTo().getBlockY() &&
                event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
            return;
        }

        Player player = event.getPlayer();
        PlayerData data = plugin.getStorageManager().loadPlayerData(player.getUniqueId());

        if (data != null) {
            data.setLastMovement(System.currentTimeMillis());
            plugin.getStorageManager().savePlayerData(player.getUniqueId(), data);
        }
    }
}
