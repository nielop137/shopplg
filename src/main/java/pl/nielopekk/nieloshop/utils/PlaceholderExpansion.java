package pl.nielopekk.nieloshop.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nielopekk.nieloshop.NieloShop;

/**
 * PlaceholderAPI expansion for NieloShop
 * Provides placeholders for wallet balance and time points
 *
 * Placeholders:
 * - %nieloshop_wallet% - Player's wallet balance
 * - %nieloshop_time_points% - Player's time points
 * - %nieloshop_playtime% - Player's total playtime
 */
public class PlaceholderExpansion extends PlaceholderExpansion {

    private final NieloShop plugin;

    public PlaceholderExpansion(NieloShop plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "nieloshop";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Nielopekk";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        CurrencyFormatter formatter = new CurrencyFormatter(plugin);

        switch (params.toLowerCase()) {
            case "wallet":
                double balance = plugin.getWalletManager().getBalance(player);
                return formatter.format(balance);

            case "time_points":
                int points = plugin.getTimePointsManager().getPoints(player);
                return String.valueOf(points);

            case "playtime":
                long playtime = plugin.getTimePointsManager().getPlaytime(player);
                return plugin.getTimePointsManager().formatPlaytime(playtime);

            default:
                return null;
        }
    }
}
