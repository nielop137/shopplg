package pl.nielopekk.nieloshop.models;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Represents an active item addition session
 * Stores temporary data while admin is adding an item to shop
 */
public class ItemAddSession {

    private final UUID playerUuid;
    private final ShopType shopType;
    private ItemStack itemStack;
    private double buyPrice;
    private double sellPrice;
    private int slot;

    public ItemAddSession(UUID playerUuid, ShopType shopType) {
        this.playerUuid = playerUuid;
        this.shopType = shopType;
        this.slot = -1;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public ShopType getShopType() {
        return shopType;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    /**
     * Checks if session has all required data
     * @return true if complete
     */
    public boolean isComplete() {
        return itemStack != null && (buyPrice > 0 || sellPrice > 0);
    }

    /**
     * Converts session to ShopItem
     * @return ShopItem instance
     */
    public ShopItem toShopItem() {
        return new ShopItem(itemStack, buyPrice, sellPrice, slot);
    }
}
