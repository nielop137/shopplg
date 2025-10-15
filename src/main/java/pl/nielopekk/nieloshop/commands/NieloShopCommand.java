package pl.nielopekk.nieloshop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.models.ShopType;
import pl.nielopekk.nieloshop.utils.MessageUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main admin command for NieloShop
 * Usage: /nieloshop [reload|save|add <shop>]
 */
public class NieloShopCommand implements CommandExecutor, TabCompleter {

    private final NieloShop plugin;

    public NieloShopCommand(NieloShop plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("nieloshop.admin")) {
            MessageUtil.sendMessage(plugin, sender, "no-permission");
            return true;
        }

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                handleReload(sender);
                break;

            case "save":
                handleSave(sender);
                break;

            case "add":
                handleAdd(sender, args);
                break;

            default:
                sendHelp(sender);
                break;
        }

        return true;
    }

    /**
     * Handles reload subcommand
     */
    private void handleReload(CommandSender sender) {
        if (!sender.hasPermission("nieloshop.reload")) {
            MessageUtil.sendMessage(plugin, sender, "no-permission");
            return;
        }

        plugin.getConfigManager().reload();
        plugin.getShopManager().loadShops();
        MessageUtil.sendMessage(plugin, sender, "reload-success");
    }

    /**
     * Handles save subcommand
     */
    private void handleSave(CommandSender sender) {
        plugin.getStorageManager().save();
        plugin.getShopManager().saveShops();
        MessageUtil.sendMessage(plugin, sender, "save-success");
    }

    /**
     * Handles add subcommand
     */
    private void handleAdd(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(plugin, sender, "player-only");
            return;
        }

        if (args.length < 2) {
            MessageUtil.send(sender, "&cUsage: /nieloshop add <vault|time|wallet>");
            return;
        }

        Player player = (Player) sender;
        ShopType shopType = ShopType.fromCommand(args[1]);

        if (shopType == null) {
            MessageUtil.send(sender, "&cInvalid shop type! Use: vault, time, or wallet");
            return;
        }

        plugin.getItemAddManager().startSession(player, shopType);
        MessageUtil.sendMessage(plugin, sender, "add-mode-activated", "shop", shopType.name());
        MessageUtil.sendMessage(plugin, sender, "click-item");
    }

    /**
     * Sends help message
     */
    private void sendHelp(CommandSender sender) {
        MessageUtil.send(sender, "&6&l=== NieloShop Admin Commands ===");
        MessageUtil.send(sender, "&e/nieloshop reload &7- Reload configuration");
        MessageUtil.send(sender, "&e/nieloshop save &7- Save all data");
        MessageUtil.send(sender, "&e/nieloshop add <shop> &7- Add item to shop");
        MessageUtil.send(sender, "&7Shops: vault, time, wallet");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(Arrays.asList("reload", "save", "add"));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
            completions.addAll(Arrays.asList("vault", "time", "wallet"));
        }

        return completions;
    }
}
