package pl.nielopekk.nieloshop.models;

import org.bukkit.inventory.ItemStack;

/**
 * Represents an item in a shop
 * Contains item data, prices, and slot information
 */
public class ShopItem {

    private final ItemStack itemStack;
    private final double buyPrice;
    private final double sellPrice;
    private final int slot;

    public ShopItem(ItemStack itemStack, double buyPrice, double sellPrice, int slot) {
        this.itemStack = itemStack.clone();
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.slot = slot;
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public int getSlot() {
        return slot;
    }

    /**
     * Checks if this item can be sold
     * @return true if sell price is greater than 0
     */
    public boolean isSellable() {
        return sellPrice > 0;
    }

    /**
     * Checks if this item can be bought
     * @return true if buy price is greater than 0
     */
    public boolean isBuyable() {
        return buyPrice > 0;
    }
}
