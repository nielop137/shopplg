package pl.nielopekk.nieloshop.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.utils.CurrencyFormatter;
import pl.nielopekk.nieloshop.utils.MessageUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Wallet command for managing virtual wallet
 * Usage: /portfel [nadaj|zabierz|ustaw] [player] [amount]
 */
public class WalletCommand implements CommandExecutor, TabCompleter {

    private final NieloShop plugin;

    public WalletCommand(NieloShop plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("nieloshop.portfel.use")) {
            MessageUtil.sendMessage(plugin, sender, "no-permission");
            return true;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                MessageUtil.sendMessage(plugin, sender, "player-only");
                return true;
            }

            Player player = (Player) sender;
            double balance = plugin.getWalletManager().getBalance(player);
            CurrencyFormatter formatter = new CurrencyFormatter(plugin);

            MessageUtil.sendMessage(plugin, sender, "wallet-balance",
                    "balance", formatter.format(balance));
            return true;
        }

        String action = args[0].toLowerCase();

        switch (action) {
            case "nadaj":
            case "give":
                handleGive(sender, args);
                break;

            case "zabierz":
            case "take":
                handleTake(sender, args);
                break;

            case "ustaw":
            case "set":
                handleSet(sender, args);
                break;

            default:
                MessageUtil.send(sender, "&cUsage: /portfel [nadaj|zabierz|ustaw] <player> <amount>");
                break;
        }

        return true;
    }

    /**
     * Handles give subcommand
     */
    private void handleGive(CommandSender sender, String[] args) {
        if (!sender.hasPermission("nieloshop.portfel.give")) {
            MessageUtil.sendMessage(plugin, sender, "no-permission");
            return;
        }

        if (args.length < 3) {
            MessageUtil.send(sender, "&cUsage: /portfel nadaj <player> <amount>");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            MessageUtil.sendMessage(plugin, sender, "player-not-found");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[2]);
            if (amount <= 0) {
                MessageUtil.sendMessage(plugin, sender, "invalid-amount");
                return;
            }
        } catch (NumberFormatException e) {
            MessageUtil.sendMessage(plugin, sender, "invalid-amount");
            return;
        }

        plugin.getWalletManager().addBalance(target, amount);
        double newBalance = plugin.getWalletManager().getBalance(target);

        CurrencyFormatter formatter = new CurrencyFormatter(plugin);
        MessageUtil.sendMessage(plugin, sender, "wallet-given",
                "amount", formatter.format(amount),
                "player", target.getName(),
                "balance", formatter.format(newBalance));
    }

    /**
     * Handles take subcommand
     */
    private void handleTake(CommandSender sender, String[] args) {
        if (!sender.hasPermission("nieloshop.portfel.take")) {
            MessageUtil.sendMessage(plugin, sender, "no-permission");
            return;
        }

        if (args.length < 3) {
            MessageUtil.send(sender, "&cUsage: /portfel zabierz <player> <amount>");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            MessageUtil.sendMessage(plugin, sender, "player-not-found");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[2]);
            if (amount <= 0) {
                MessageUtil.sendMessage(plugin, sender, "invalid-amount");
                return;
            }
        } catch (NumberFormatException e) {
            MessageUtil.sendMessage(plugin, sender, "invalid-amount");
            return;
        }

        plugin.getWalletManager().removeBalance(target, amount);
        double newBalance = plugin.getWalletManager().getBalance(target);

        CurrencyFormatter formatter = new CurrencyFormatter(plugin);
        MessageUtil.sendMessage(plugin, sender, "wallet-taken",
                "amount", formatter.format(amount),
                "player", target.getName(),
                "balance", formatter.format(newBalance));
    }

    /**
     * Handles set subcommand
     */
    private void handleSet(CommandSender sender, String[] args) {
        if (!sender.hasPermission("nieloshop.portfel.set")) {
            MessageUtil.sendMessage(plugin, sender, "no-permission");
            return;
        }

        if (args.length < 3) {
            MessageUtil.send(sender, "&cUsage: /portfel ustaw <player> <amount>");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            MessageUtil.sendMessage(plugin, sender, "player-not-found");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[2]);
            if (amount < 0) {
                MessageUtil.sendMessage(plugin, sender, "invalid-amount");
                return;
            }
        } catch (NumberFormatException e) {
            MessageUtil.sendMessage(plugin, sender, "invalid-amount");
            return;
        }

        plugin.getWalletManager().setBalance(target, amount);

        CurrencyFormatter formatter = new CurrencyFormatter(plugin);
        MessageUtil.sendMessage(plugin, sender, "wallet-set",
                "player", target.getName(),
                "balance", formatter.format(amount));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(Arrays.asList("nadaj", "zabierz", "ustaw"));
        } else if (args.length == 2) {
            return null;
        }

        return completions;
    }
}
