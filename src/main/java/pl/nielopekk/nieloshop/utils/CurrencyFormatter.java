package pl.nielopekk.nieloshop.utils;

import pl.nielopekk.nieloshop.NieloShop;

import java.text.DecimalFormat;

/**
 * Utility class for formatting currency values
 * Supports short format (1k, 2.3M, 5B) and custom symbols
 */
public class CurrencyFormatter {

    private final NieloShop plugin;

    public CurrencyFormatter(NieloShop plugin) {
        this.plugin = plugin;
    }

    /**
     * Formats a currency value according to configuration
     * @param amount Amount to format
     * @return Formatted string
     */
    public String format(double amount) {
        if (!plugin.getConfigManager().useShortFormat()) {
            return String.format("%.2f", amount);
        }

        int decimalPlaces = plugin.getConfigManager().getDecimalPlaces();
        String pattern = "#." + "#".repeat(decimalPlaces);
        DecimalFormat df = new DecimalFormat(pattern);

        if (amount >= 1_000_000_000_000.0) {
            return df.format(amount / 1_000_000_000_000.0) + plugin.getConfigManager().getTrillionSymbol();
        } else if (amount >= 1_000_000_000.0) {
            return df.format(amount / 1_000_000_000.0) + plugin.getConfigManager().getBillionSymbol();
        } else if (amount >= 1_000_000.0) {
            return df.format(amount / 1_000_000.0) + plugin.getConfigManager().getMillionSymbol();
        } else if (amount >= 1_000.0) {
            return df.format(amount / 1_000.0) + plugin.getConfigManager().getThousandSymbol();
        } else {
            return df.format(amount);
        }
    }

    /**
     * Formats a currency value with currency name
     * @param amount Amount to format
     * @param currencyName Name of currency
     * @return Formatted string with currency name
     */
    public String formatWithName(double amount, String currencyName) {
        return format(amount) + " " + currencyName;
    }

    /**
     * Static method for quick formatting
     * @param plugin Plugin instance
     * @param amount Amount to format
     * @return Formatted string
     */
    public static String quickFormat(NieloShop plugin, double amount) {
        return new CurrencyFormatter(plugin).format(amount);
    }
}
