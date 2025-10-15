package pl.nielopekk.nieloshop.managers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.models.ItemAddSession;
import pl.nielopekk.nieloshop.models.ShopType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages item addition sessions for admins
 * Handles the process of adding items to shops
 */
public class ItemAddManager {

    private final NieloShop plugin;
    private final Map<UUID, ItemAddSession> sessions;

    public ItemAddManager(NieloShop plugin) {
        this.plugin = plugin;
        this.sessions = new HashMap<>();
    }

    /**
     * Starts an item add session for a player
     * @param player Player starting the session
     * @param shopType Shop type to add item to
     */
    public void startSession(Player player, ShopType shopType) {
        ItemAddSession session = new ItemAddSession(player.getUniqueId(), shopType);
        sessions.put(player.getUniqueId(), session);
    }

    /**
     * Gets active session for a player
     * @param player Player
     * @return ItemAddSession or null if no active session
     */
    public ItemAddSession getSession(Player player) {
        return sessions.get(player.getUniqueId());
    }

    /**
     * Checks if player has an active session
     * @param player Player
     * @return true if active session exists
     */
    public boolean hasSession(Player player) {
        return sessions.containsKey(player.getUniqueId());
    }

    /**
     * Sets the item for a session
     * @param player Player
     * @param item ItemStack to add
     */
    public void setItem(Player player, ItemStack item) {
        ItemAddSession session = sessions.get(player.getUniqueId());
        if (session != null) {
            session.setItemStack(item);
        }
    }

    /**
     * Cancels and removes a player's session
     * @param player Player
     */
    public void cancelSession(Player player) {
        sessions.remove(player.getUniqueId());
    }

    /**
     * Completes a session and adds the item to the shop
     * @param player Player
     */
    public void completeSession(Player player) {
        ItemAddSession session = sessions.get(player.getUniqueId());
        if (session != null && session.isComplete()) {
            plugin.getShopManager().addItem(
                    session.getShopType(),
                    session.toShopItem()
            );
            sessions.remove(player.getUniqueId());
        }
    }
}
