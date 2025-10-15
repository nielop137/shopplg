package pl.nielopekk.nieloshop.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.models.ShopItem;
import pl.nielopekk.nieloshop.models.ShopType;
import pl.nielopekk.nieloshop.utils.CurrencyFormatter;
import pl.nielopekk.nieloshop.utils.MessageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Main shop GUI
 * Displays shop items with pagination
 */
public class ShopGUI {

    private final NieloShop plugin;
    private final Player player;
    private final ShopType shopType;
    private final int page;
    private final Inventory inventory;

    private static final int ITEMS_PER_PAGE = 45;
    private static final int PREV_PAGE_SLOT = 48;
    private static final int NEXT_PAGE_SLOT = 50;
    private static final int CLOSE_SLOT = 49;

    public ShopGUI(NieloShop plugin, Player player, ShopType shopType, int page) {
        this.plugin = plugin;
        this.player = player;
        this.shopType = shopType;
        this.page = page;

        String title = getTitle();
        this.inventory = Bukkit.createInventory(null, 54, MessageUtil.color(title));

        setupItems();
    }

    /**
     * Gets GUI title based on shop type
     */
    private String getTitle() {
        switch (shopType) {
            case VAULT:
                return plugin.getConfigManager().getVaultShopTitle();
            case TIME:
                return plugin.getConfigManager().getTimeShopTitle();
            case WALLET:
                return plugin.getConfigManager().getWalletShopTitle();
            default:
                return "&6Shop";
        }
    }

    /**
     * Sets up items in the GUI
     */
    private void setupItems() {
        List<ShopItem> allItems = plugin.getShopManager().getShopItems(shopType);

        if (allItems.isEmpty()) {
            ItemStack emptyItem = new ItemStack(Material.BARRIER);
            ItemMeta meta = emptyItem.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(MessageUtil.color("&cShop is empty"));
                emptyItem.setItemMeta(meta);
            }
            inventory.setItem(22, emptyItem);
            return;
        }

        int startIndex = page * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, allItems.size());

        CurrencyFormatter formatter = new CurrencyFormatter(plugin);

        for (int i = startIndex; i < endIndex; i++) {
            ShopItem shopItem = allItems.get(i);
            ItemStack displayItem = shopItem.getItemStack();
            ItemMeta meta = displayItem.getItemMeta();

            if (meta != null) {
                List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
                lore.add("");

                if (shopItem.isBuyable()) {
                    String buyPrice = formatter.format(shopItem.getBuyPrice());
                    lore.add(MessageUtil.color("&aBuy price: &e" + buyPrice));
                    lore.add(MessageUtil.color("&7Left-click to buy"));
                }

                if (shopItem.isSellable()) {
                    String sellPrice = formatter.format(shopItem.getSellPrice());
                    lore.add(MessageUtil.color("&eSell price: &e" + sellPrice));
                    lore.add(MessageUtil.color("&7Right-click to sell"));
                }

                meta.setLore(lore);
                displayItem.setItemMeta(meta);
            }

            int slot = shopItem.getSlot();
            if (slot >= 0 && slot < ITEMS_PER_PAGE) {
                inventory.setItem(slot, displayItem);
            } else {
                inventory.setItem(i - startIndex, displayItem);
            }
        }

        setupNavigationButtons(allItems.size());
    }

    /**
     * Sets up navigation buttons (previous page, next page, close)
     */
    private void setupNavigationButtons(int totalItems) {
        int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);

        if (page > 0) {
            ItemStack prevPage = new ItemStack(Material.ARROW);
            ItemMeta meta = prevPage.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(MessageUtil.color(plugin.getConfigManager().getMessage("gui-previous-page")));
                prevPage.setItemMeta(meta);
            }
            inventory.setItem(PREV_PAGE_SLOT, prevPage);
        }

        if (page < totalPages - 1) {
            ItemStack nextPage = new ItemStack(Material.ARROW);
            ItemMeta meta = nextPage.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(MessageUtil.color(plugin.getConfigManager().getMessage("gui-next-page")));
                nextPage.setItemMeta(meta);
            }
            inventory.setItem(NEXT_PAGE_SLOT, nextPage);
        }

        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta meta = close.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(MessageUtil.color(plugin.getConfigManager().getMessage("gui-close")));
            close.setItemMeta(meta);
        }
        inventory.setItem(CLOSE_SLOT, close);
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

    public ShopType getShopType() {
        return shopType;
    }

    public int getPage() {
        return page;
    }
}
