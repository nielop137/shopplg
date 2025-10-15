package pl.nielopekk.nieloshop.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.models.ItemAddSession;
import pl.nielopekk.nieloshop.utils.MessageUtil;

import java.util.Arrays;

/**
 * GUI for configuring item to add to shop
 * Allows setting prices and confirming addition
 */
public class ItemAddGUI {

    private final NieloShop plugin;
    private final Player player;
    private final ItemAddSession session;
    private final Inventory inventory;

    private static final int ITEM_SLOT = 13;
    private static final int CONFIRM_SLOT = 29;
    private static final int CANCEL_SLOT = 33;

    public ItemAddGUI(NieloShop plugin, Player player, ItemAddSession session) {
        this.plugin = plugin;
        this.player = player;
        this.session = session;

        this.inventory = Bukkit.createInventory(null, 54, MessageUtil.color("&eAdd Item to Shop"));

        setupGUI();
    }

    /**
     * Sets up the GUI items
     */
    private void setupGUI() {
        ItemStack displayItem = session.getItemStack();
        if (displayItem != null) {
            ItemMeta meta = displayItem.getItemMeta();
            if (meta != null) {
                meta.setLore(Arrays.asList(
                        "",
                        MessageUtil.color("&7Buy price: &e" + session.getBuyPrice()),
                        MessageUtil.color("&7Sell price: &e" + session.getSellPrice()),
                        "",
                        MessageUtil.color("&7Type prices in chat")
                ));
                displayItem.setItemMeta(meta);
            }
            inventory.setItem(ITEM_SLOT, displayItem);
        }

        ItemStack confirm = new ItemStack(Material.LIME_WOOL);
        ItemMeta confirmMeta = confirm.getItemMeta();
        if (confirmMeta != null) {
            confirmMeta.setDisplayName(MessageUtil.color(plugin.getConfigManager().getMessage("gui-confirm")));
            confirmMeta.setLore(Arrays.asList(
                    MessageUtil.color("&7Click to add item to shop")
            ));
            confirm.setItemMeta(confirmMeta);
        }
        inventory.setItem(CONFIRM_SLOT, confirm);

        ItemStack cancel = new ItemStack(Material.RED_WOOL);
        ItemMeta cancelMeta = cancel.getItemMeta();
        if (cancelMeta != null) {
            cancelMeta.setDisplayName(MessageUtil.color(plugin.getConfigManager().getMessage("gui-cancel")));
            cancelMeta.setLore(Arrays.asList(
                    MessageUtil.color("&7Click to cancel")
            ));
            cancel.setItemMeta(cancelMeta);
        }
        inventory.setItem(CANCEL_SLOT, cancel);

        for (int i = 0; i < 54; i++) {
            if (i != ITEM_SLOT && i != CONFIRM_SLOT && i != CANCEL_SLOT && inventory.getItem(i) == null) {
                ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta glassMeta = glass.getItemMeta();
                if (glassMeta != null) {
                    glassMeta.setDisplayName(" ");
                    glass.setItemMeta(glassMeta);
                }
                inventory.setItem(i, glass);
            }
        }
    }

    /**
     * Opens the GUI for the player
     */
    public void open() {
        player.openInventory(inventory);
    }

    public Player getPlayer() {
        return player;
    }

    public ItemAddSession getSession() {
        return session;
    }
}
