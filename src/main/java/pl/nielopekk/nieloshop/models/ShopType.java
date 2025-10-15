package pl.nielopekk.nieloshop.models;

/**
 * Represents different types of shops
 */
public enum ShopType {
    VAULT("vault-shop", "vault"),
    TIME("time-shop", "time"),
    WALLET("wallet-shop", "wallet");

    private final String configKey;
    private final String command;

    ShopType(String configKey, String command) {
        this.configKey = configKey;
        this.command = command;
    }

    public String getConfigKey() {
        return configKey;
    }

    public String getCommand() {
        return command;
    }

    /**
     * Gets ShopType from command string
     * @param command Command string
     * @return ShopType or null if not found
     */
    public static ShopType fromCommand(String command) {
        for (ShopType type : values()) {
            if (type.command.equalsIgnoreCase(command)) {
                return type;
            }
        }
        return null;
    }
}
