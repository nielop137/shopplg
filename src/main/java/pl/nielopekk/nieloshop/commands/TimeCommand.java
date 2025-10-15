package pl.nielopekk.nieloshop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.nielopekk.nieloshop.NieloShop;
import pl.nielopekk.nieloshop.utils.MessageUtil;

/**
 * Time command for checking playtime and time points
 * Usage: /czas
 */
public class TimeCommand implements CommandExecutor {

    private final NieloShop plugin;

    public TimeCommand(NieloShop plugin) {
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

        int points = plugin.getTimePointsManager().getPoints(player);
        long playtime = plugin.getTimePointsManager().getPlaytime(player);
        String formattedTime = plugin.getTimePointsManager().formatPlaytime(playtime);

        MessageUtil.sendMessage(plugin, sender, "time-points-info",
                "time", formattedTime,
                "points", String.valueOf(points));

        return true;
    }
}
