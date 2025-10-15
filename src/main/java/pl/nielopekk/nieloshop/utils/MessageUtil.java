package pl.nielopekk.nieloshop.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import pl.nielopekk.nieloshop.NieloShop;

/**
 * Utility class for sending formatted messages
 * Handles color codes and placeholders
 */
public class MessageUtil {

    /**
     * Translates color codes in a string
     * Supports & color codes
     * @param message Message to translate
     * @return Translated message
     */
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Sends a message to a command sender
     * @param sender Command sender
     * @param message Message to send
     */
    public static void send(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }

    /**
     * Sends a message with prefix to a command sender
     * @param plugin Plugin instance
     * @param sender Command sender
     * @param message Message to send
     */
    public static void sendWithPrefix(NieloShop plugin, CommandSender sender, String message) {
        String prefix = plugin.getConfigManager().getPrefix();
        sender.sendMessage(color(prefix + message));
    }

    /**
     * Sends a message from messages.yml
     * @param plugin Plugin instance
     * @param sender Command sender
     * @param path Message path in messages.yml
     */
    public static void sendMessage(NieloShop plugin, CommandSender sender, String path) {
        String message = plugin.getConfigManager().getMessage(path);
        sendWithPrefix(plugin, sender, message);
    }

    /**
     * Sends a message from messages.yml with placeholders
     * @param plugin Plugin instance
     * @param sender Command sender
     * @param path Message path in messages.yml
     * @param replacements Placeholder replacements (key, value pairs)
     */
    public static void sendMessage(NieloShop plugin, CommandSender sender, String path, String... replacements) {
        String message = plugin.getConfigManager().getMessage(path);

        for (int i = 0; i < replacements.length - 1; i += 2) {
            message = message.replace("{" + replacements[i] + "}", replacements[i + 1]);
        }

        sendWithPrefix(plugin, sender, message);
    }
}
