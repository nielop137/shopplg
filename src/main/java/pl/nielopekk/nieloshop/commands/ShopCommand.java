package pl.nielopekk.nieloshop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.gui.ShopGUI;
import pl.nielopekk.nieloshop.models.ShopType;
import pl.nielopekk.nieloshop.utils.MessageUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shop command for opening shop GUIs
 * Usage: /shop <vault|time|wallet>
 */
public class ShopCommand implements CommandExecutor, TabCompleter {

    private final NieloShop plugin;

    public ShopCommand(NieloShop plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(plugin, sender, "player-only");
            return true;
        }

        if (!sender.hasPermission("nieloshop.use")) {
            MessageUtil.sendMessage(plugin, sender, "no-permission");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            MessageUtil.send(sender, "&cUsage: /shop <vault|time|wallet>");
            return true;
        }

        ShopType shopType = ShopType.fromCommand(args[0]);

        if (shopType == null) {
            MessageUtil.send(sender, "&cInvalid shop type! Use: vault, time, or wallet");
            return true;
        }

        ShopGUI gui = new ShopGUI(plugin, player, shopType, 0);
        gui.open();

        switch (shopType) {
            case VAULT:
                MessageUtil.sendMessage(plugin, sender, "shop-vault-opened");
                break;
            case TIME:
                MessageUtil.sendMessage(plugin, sender, "shop-time-opened");
                break;
            case WALLET:
                MessageUtil.sendMessage(plugin, sender, "shop-wallet-opened");
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(Arrays.asList("vault", "time", "wallet"));
        }

        return completions;
    }
}
