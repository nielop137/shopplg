package pl.nielopekk.nieloshop.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * Utility class for serializing and deserializing ItemStacks
 * Preserves full NBT data for compatibility with custom items
 */
public class ItemSerializer {

    /**
     * Serializes an ItemStack to Base64 string
     * Preserves all NBT data including custom items from plugins like MMOItems, Oraxen
     * @param item ItemStack to serialize
     * @return Base64 encoded string
     */
    public static String serialize(ItemStack item) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(item);
            dataOutput.close();
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deserializes an ItemStack from Base64 string
     * Restores all NBT data
     * @param data Base64 encoded string
     * @return ItemStack or null if deserialization fails
     */
    public static ItemStack deserialize(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack item = (ItemStack) dataInput.readObject();
            dataInput.close();
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
