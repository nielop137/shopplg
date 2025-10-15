package pl.nielopekk.nieloshop.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.gui.ItemAddGUI;
import pl.nielopekk.nieloshop.gui.ShopGUI;
import pl.nielopekk.nieloshop.models.ItemAddSession;
import pl.nielopekk.nieloshop.models.ShopItem;
import pl.nielopekk.nieloshop.models.ShopType;
import pl.nielopekk.nieloshop.utils.MessageUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles GUI click events for shops and item addition
 */
public class ItemAddListener implements Listener {

    private final NieloShop plugin;
    private final Map<Player, PriceInputState> priceInputs;

    public ItemAddListener(NieloShop plugin) {
        this.plugin = plugin;
        this.priceInputs = new HashMap<>();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();

        if (title.contains("Add Item to Shop")) {
            handleItemAddGUIClick(event, player);
        } else if (title.contains("Shop")) {
            handleShopGUIClick(event, player);
        } else {
            if (plugin.getItemAddManager().hasSession(player)) {
                handleItemSelection(event, player);
            }
        }
    }

    /**
     * Handles clicks in item add GUI
     */
    private void handleItemAddGUIClick(InventoryClickEvent event, Player player) {
        event.setCancelled(true);

        int slot = event.getRawSlot();
        ItemAddSession session = plugin.getItemAddManager().getSession(player);

        if (session == null) {
            return;
        }

        if (slot == 29) {
            if (session.isComplete()) {
                plugin.getItemAddManager().completeSession(player);
                player.closeInventory();
                MessageUtil.sendMessage(plugin, player, "item-added", "shop", session.getShopType().name());
            } else {
                MessageUtil.send(player, "&cPlease set buy or sell price first!");
            }
        } else if (slot == 33) {
            plugin.getItemAddManager().cancelSession(player);
            player.closeInventory();
            MessageUtil.sendMessage(plugin, player, "add-mode-cancelled");
        }
    }

    /**
     * Handles clicks in shop GUI
     */
    private void handleShopGUIClick(InventoryClickEvent event, Player player) {
        event.setCancelled(true);

        String title = event.getView().getTitle();
        ShopType shopType = getShopTypeFromTitle(title);

        if (shopType == null) {
            return;
        }

        int slot = event.getRawSlot();

        if (slot == 48) {
            ShopGUI gui = getShopGUIFromInventory(player, shopType);
            if (gui != null && gui.getPage() > 0) {
                new ShopGUI(plugin, player, shopType, gui.getPage() - 1).open();
            }
        } else if (slot == 50) {
            ShopGUI gui = getShopGUIFromInventory(player, shopType);
            if (gui != null) {
                new ShopGUI(plugin, player, shopType, gui.getPage() + 1).open();
            }
        } else if (slot == 49) {
            player.closeInventory();
        } else if (slot >= 0 && slot < 45) {
            handleShopItemClick(event, player, shopType, slot);
        }
    }

    /**
     * Handles clicking shop items (buy/sell)
     */
    private void handleShopItemClick(InventoryClickEvent event, Player player, ShopType shopType, int slot) {
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        List<ShopItem> items = plugin.getShopManager().getShopItems(shopType);
        if (slot >= items.size()) {
            return;
        }

        ShopItem shopItem = items.get(slot);

        if (event.isLeftClick()) {
            handleBuy(player, shopItem, shopType);
        } else if (event.isRightClick()) {
            handleSell(player, shopItem, shopType);
        }
    }

    /**
     * Handles buying an item
     */
    private void handleBuy(Player player, ShopItem shopItem, ShopType shopType) {
        if (!shopItem.isBuyable()) {
            MessageUtil.send(player, "&cThis item cannot be bought!");
            return;
        }

        double price = shopItem.getBuyPrice();
        boolean canAfford = false;

        switch (shopType) {
            case VAULT:
                canAfford = plugin.getEconomy().has(player, price);
                if (canAfford) {
                    plugin.getEconomy().withdrawPlayer(player, price);
                }
                break;
            case TIME:
                canAfford = plugin.getTimePointsManager().hasPoints(player, (int) price);
                if (canAfford) {
                    plugin.getTimePointsManager().removePoints(player, (int) price);
                }
                break;
            case WALLET:
                canAfford = plugin.getWalletManager().hasBalance(player, price);
                if (canAfford) {
                    plugin.getWalletManager().removeBalance(player, price);
                }
                break;
        }

        if (!canAfford) {
            MessageUtil.sendMessage(plugin, player, "insufficient-funds",
                    "currency", getCurrencyName(shopType));
            return;
        }

        player.getInventory().addItem(shopItem.getItemStack());
        MessageUtil.sendMessage(plugin, player, "purchase-success",
                "amount", "1",
                "item", shopItem.getItemStack().getType().name(),
                "price", String.valueOf(price),
                "currency", getCurrencyName(shopType));
    }

    /**
     * Handles selling an item
     */
    private void handleSell(Player player, ShopItem shopItem, ShopType shopType) {
        if (!shopItem.isSellable()) {
            MessageUtil.send(player, "&cThis item cannot be sold!");
            return;
        }

        ItemStack itemToSell = shopItem.getItemStack();
        if (!player.getInventory().containsAtLeast(itemToSell, 1)) {
            MessageUtil.sendMessage(plugin, player, "no-items-to-sell");
            return;
        }

        player.getInventory().removeItem(itemToSell);
        double price = shopItem.getSellPrice();

        switch (shopType) {
            case VAULT:
                plugin.getEconomy().depositPlayer(player, price);
                break;
            case TIME:
                plugin.getTimePointsManager().addPoints(player, (int) price);
                break;
            case WALLET:
                plugin.getWalletManager().addBalance(player, price);
                break;
        }

        MessageUtil.sendMessage(plugin, player, "sell-success",
                "amount", "1",
                "item", itemToSell.getType().name(),
                "price", String.valueOf(price),
                "currency", getCurrencyName(shopType));
    }

    /**
     * Handles item selection for adding to shop
     */
    private void handleItemSelection(InventoryClickEvent event, Player player) {
        ItemStack item = event.getCurrentItem();

        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        event.setCancelled(true);
        plugin.getItemAddManager().setItem(player, item);
        player.closeInventory();

        player.sendMessage(MessageUtil.color("&aItem selected! Type buy price in chat (or 0 to skip):"));
        priceInputs.put(player, new PriceInputState(PriceInputType.BUY_PRICE));
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            String title = event.getView().getTitle();

            if (!title.contains("Add Item") && !title.contains("Shop")) {
                if (plugin.getItemAddManager().hasSession(player)) {
                    ItemAddSession session = plugin.getItemAddManager().getSession(player);
                    if (session != null && session.getItemStack() == null) {
                        plugin.getItemAddManager().cancelSession(player);
                    }
                }
            }
        }
    }

    private ShopType getShopTypeFromTitle(String title) {
        if (title.contains("Vault")) return ShopType.VAULT;
        if (title.contains("Time")) return ShopType.TIME;
        if (title.contains("Wallet")) return ShopType.WALLET;
        return null;
    }

    private ShopGUI getShopGUIFromInventory(Player player, ShopType shopType) {
        return new ShopGUI(plugin, player, shopType, 0);
    }

    private String getCurrencyName(ShopType shopType) {
        switch (shopType) {
            case VAULT:
                return plugin.getConfigManager().getMessage("currency-vault");
            case TIME:
                return plugin.getConfigManager().getMessage("currency-time");
            case WALLET:
                return plugin.getConfigManager().getMessage("currency-wallet");
            default:
                return "currency";
        }
    }

    private enum PriceInputType {
        BUY_PRICE,
        SELL_PRICE
    }

    private static class PriceInputState {
        PriceInputType type;

        PriceInputState(PriceInputType type) {
            this.type = type;
        }
    }
}
