package pl.nielopekk.nieloshop.managers;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.models.ShopItem;
import pl.nielopekk.nieloshop.models.ShopType;
import pl.nielopekk.nieloshop.utils.ItemSerializer;

import java.util.*;

/**
 * Manages shop items and operations
 * Handles loading, saving, and accessing shop items
 */
public class ShopManager {

    private final NieloShop plugin;
    private final Map<ShopType, List<ShopItem>> shops;

    public ShopManager(NieloShop plugin) {
        this.plugin = plugin;
        this.shops = new EnumMap<>(ShopType.class);
        for (ShopType type : ShopType.values()) {
            shops.put(type, new ArrayList<>());
        }
    }

    /**
     * Loads all shops from configuration
     */
    public void loadShops() {
        ConfigurationSection config = plugin.getConfigManager().getShopsConfig();

        for (ShopType type : ShopType.values()) {
            List<ShopItem> items = new ArrayList<>();
            String key = type.getConfigKey();

            ConfigurationSection section = config.getConfigurationSection(key);
            if (section != null) {
                for (String itemKey : section.getKeys(false)) {
                    ConfigurationSection itemSection = section.getConfigurationSection(itemKey);
                    if (itemSection != null) {
                        try {
                            String serialized = itemSection.getString("item");
                            ItemStack itemStack = ItemSerializer.deserialize(serialized);

                            if (itemStack == null || itemStack.getType() == Material.AIR) {
                                continue;
                            }

                            double buyPrice = itemSection.getDouble("buy-price", 0);
                            double sellPrice = itemSection.getDouble("sell-price", 0);
                            int slot = itemSection.getInt("slot", -1);

                            ShopItem shopItem = new ShopItem(itemStack, buyPrice, sellPrice, slot);
                            items.add(shopItem);
                        } catch (Exception e) {
                            plugin.getLogger().warning("Failed to load item " + itemKey + " from " + key);
                            e.printStackTrace();
                        }
                    }
                }
            }

            shops.put(type, items);
            plugin.getLogger().info("Loaded " + items.size() + " items for " + type.name() + " shop");
        }
    }

    /**
     * Saves all shops to configuration
     */
    public void saveShops() {
        ConfigurationSection config = plugin.getConfigManager().getShopsConfig();

        for (ShopType type : ShopType.values()) {
            String key = type.getConfigKey();
            config.set(key, null);

            List<ShopItem> items = shops.get(type);
            for (int i = 0; i < items.size(); i++) {
                ShopItem item = items.get(i);
                String itemKey = key + "." + i;

                config.set(itemKey + ".item", ItemSerializer.serialize(item.getItemStack()));
                config.set(itemKey + ".buy-price", item.getBuyPrice());
                config.set(itemKey + ".sell-price", item.getSellPrice());
                config.set(itemKey + ".slot", item.getSlot());
            }
        }

        plugin.getConfigManager().saveShops();
    }

    /**
     * Gets items for a specific shop type
     * @param type Shop type
     * @return List of shop items
     */
    public List<ShopItem> getShopItems(ShopType type) {
        return new ArrayList<>(shops.get(type));
    }

    /**
     * Adds an item to a shop
     * @param type Shop type
     * @param item Shop item to add
     */
    public void addItem(ShopType type, ShopItem item) {
        shops.get(type).add(item);
        saveShops();
    }

    /**
     * Removes an item from a shop
     * @param type Shop type
     * @param index Item index
     * @return true if successful
     */
    public boolean removeItem(ShopType type, int index) {
        List<ShopItem> items = shops.get(type);
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            saveShops();
            return true;
        }
        return false;
    }

    /**
     * Clears all items from a shop
     * @param type Shop type
     */
    public void clearShop(ShopType type) {
        shops.get(type).clear();
        saveShops();
    }
}
