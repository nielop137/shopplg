package pl.nielopekk.nieloshop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.gui.ItemAddGUI;
import pl.nielopekk.nieloshop.models.ItemAddSession;
import pl.nielopekk.nieloshop.utils.MessageUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles chat input for price configuration
 * Used when adding items to shop
 */
public class ChatInputListener implements Listener {

    private final NieloShop plugin;
    private final Map<Player, InputStep> activeInputs;

    public ChatInputListener(NieloShop plugin) {
        this.plugin = plugin;
        this.activeInputs = new HashMap<>();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!activeInputs.containsKey(player)) {
            return;
        }

        event.setCancelled(true);

        String message = event.getMessage();
        InputStep step = activeInputs.get(player);

        try {
            double price = Double.parseDouble(message);

            if (price < 0) {
                player.sendMessage(MessageUtil.color("&cPrice cannot be negative! Try again:"));
                return;
            }

            ItemAddSession session = plugin.getItemAddManager().getSession(player);
            if (session == null) {
                activeInputs.remove(player);
                return;
            }

            switch (step) {
                case BUY_PRICE:
                    session.setBuyPrice(price);
                    player.sendMessage(MessageUtil.color("&aBuy price set to: &e" + price));
                    player.sendMessage(MessageUtil.color("&aType sell price in chat (or 0 to skip):"));
                    activeInputs.put(player, InputStep.SELL_PRICE);
                    break;

                case SELL_PRICE:
                    session.setSellPrice(price);
                    player.sendMessage(MessageUtil.color("&aSell price set to: &e" + price));
                    activeInputs.remove(player);

                    plugin.getServer().getScheduler().runTask(plugin, () -> {
                        ItemAddGUI gui = new ItemAddGUI(plugin, player, session);
                        gui.open();
                    });
                    break;
            }

        } catch (NumberFormatException e) {
            player.sendMessage(MessageUtil.color("&cInvalid number! Please enter a valid price:"));
        }
    }

    /**
     * Starts price input process for a player
     * @param player Player
     */
    public void startPriceInput(Player player) {
        activeInputs.put(player, InputStep.BUY_PRICE);
    }

    /**
     * Cancels price input for a player
     * @param player Player
     */
    public void cancelInput(Player player) {
        activeInputs.remove(player);
    }

    /**
     * Checks if player has active price input
     * @param player Player
     * @return true if active
     */
    public boolean hasActiveInput(Player player) {
        return activeInputs.containsKey(player);
    }

    private enum InputStep {
        BUY_PRICE,
        SELL_PRICE
    }
}
