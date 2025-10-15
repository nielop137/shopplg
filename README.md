# NieloShop

**Author:** Nielopekk (discord: nielopekk)
**Version:** 1.0.0
**Minecraft:** Spigot/Paper 1.16.5 - 1.21+

Advanced multi-currency shop plugin for Minecraft with intuitive admin interface and modern features.

---

## Features

### 🏪 Three Shop Types
- **Vault Shop** - Uses economy money (Vault)
- **Time Shop** - Uses time points earned by playing
- **Wallet Shop** - Uses virtual currency independent from Vault

### ⏱️ Time Points System
- Players earn points for time spent online
- Configurable points per interval
- AFK detection (stops awarding when player is inactive)
- Tracks total playtime

### 💼 Virtual Wallet
- Independent from Vault economy
- Admin commands to manage player wallets
- PlaceholderAPI support: `%nieloshop_wallet%`

### 🛠️ Easy Item Management
- Add items to shops via intuitive GUI
- No manual YAML editing required
- Full NBT preservation (works with custom items from MMOItems, Oraxen, etc.)
- Click item in inventory → Set prices → Confirm

### 🎨 Modern GUI
- Animated shop interfaces
- Purchase confirmation
- Stack buying options (+1, +16, +64, multiple stacks)
- Sell items from inventory (Right-click)
- Pagination for large shops

### 💾 Flexible Storage
- SQLite (default)
- JSON (optional)

### 🔧 Configuration
- Fully configurable messages (Polish/English)
- Currency formatting (1k, 2.3M, 5B)
- Customizable GUI titles and colors
- Adjustable time points settings

---

## Requirements

- **Required:** Vault
- **Optional:** PlaceholderAPI (for placeholders)

---

## Commands

### Admin Commands
```
/nieloshop reload          - Reload configuration
/nieloshop save            - Save all data
/nieloshop add <shop>      - Add item to shop (vault/time/wallet)
```

### Shop Commands
```
/shop vault                - Open Vault shop
/shop time                 - Open Time shop
/shop wallet               - Open Wallet shop
```

### Wallet Commands
```
/portfel                   - Check your wallet balance
/portfel nadaj <player> <amount>   - Give wallet currency
/portfel zabierz <player> <amount> - Take wallet currency
/portfel ustaw <player> <amount>   - Set wallet balance
```

### Time Commands
```
/czas                      - View playtime and time points
```

---

## Permissions

```yaml
nieloshop.use              - Open shops (default: true)
nieloshop.admin            - Admin commands (default: op)
nieloshop.portfel.use      - Check own wallet (default: true)
nieloshop.portfel.give     - Give wallet currency (default: op)
nieloshop.portfel.take     - Take wallet currency (default: op)
nieloshop.portfel.set      - Set wallet balance (default: op)
nieloshop.reload           - Reload configuration (default: op)
```

---

## PlaceholderAPI Placeholders

```
%nieloshop_wallet%         - Player's wallet balance
%nieloshop_time_points%    - Player's time points
%nieloshop_playtime%       - Player's total playtime
```

---

## How to Add Items to Shop

1. Run command: `/nieloshop add vault` (or time/wallet)
2. Click any item in your inventory
3. Type buy price in chat (or 0 to skip)
4. Type sell price in chat (or 0 to skip)
5. Confirm in GUI

The item will be automatically saved with full NBT data, preserving custom properties from plugins like MMOItems, Oraxen, ItemsAdder, etc.

---

## Configuration Files

### config.yml
Main plugin configuration including:
- Storage type (SQLite/JSON)
- Time points settings
- Wallet settings
- Currency formatting
- GUI settings

### messages.yml
All plugin messages with color code support (`&`).
Fully translatable (Polish/English included).

### shops.yml
Automatically managed - items are saved here when added via `/nieloshop add`.
Manual editing not recommended.

---

## Building

```bash
mvn clean package
```

The compiled JAR will be in `target/NieloShop-1.0.0.jar`

---

## Installation

1. Download or compile the plugin
2. Install Vault and an economy plugin (e.g., EssentialsX)
3. (Optional) Install PlaceholderAPI
4. Place JAR in `plugins/` folder
5. Restart server
6. Configure `config.yml` and `messages.yml` as needed
7. Add items to shops using `/nieloshop add`

---

## Storage

### SQLite (Default)
- File: `plugins/NieloShop/data.db`
- Best for most servers
- No external setup required

### JSON
- File: `plugins/NieloShop/playerdata.json`
- Good for debugging
- Set `storage-type: JSON` in config.yml

---

## AFK Detection

The plugin detects when players are AFK:
- Monitors player movement
- Configurable threshold (default: 60 seconds)
- Stops awarding time points when AFK

---

## Currency Formatting

The plugin supports short currency format:
- 1,000 → 1k
- 1,500,000 → 1.5M
- 2,300,000,000 → 2.3B

Symbols are fully configurable in config.yml.

---

## Support

For issues or questions:
- Discord: **nielopekk**

---

## License

This plugin was created for private use. All rights reserved to the author.

---

## Technical Details

### Architecture
```
pl.nielopekk.nieloshop/
├── commands/          - Command handlers
├── gui/               - Inventory GUIs
├── listeners/         - Event handlers
├── managers/          - Business logic
├── models/            - Data models
├── storage/           - Data persistence
│   └── impl/         - Storage implementations
└── utils/             - Utility classes
```

### Item Serialization
Items are serialized using Bukkit's serialization system, preserving:
- Full NBT data
- Custom model data
- Lore and display names
- Enchantments
- Item flags
- Plugin-specific data (MMOItems, Oraxen, etc.)

### Database Schema (SQLite)
```sql
CREATE TABLE player_data (
    uuid TEXT PRIMARY KEY,
    wallet_balance REAL NOT NULL DEFAULT 0,
    time_points INTEGER NOT NULL DEFAULT 0,
    total_playtime INTEGER NOT NULL DEFAULT 0,
    last_login INTEGER NOT NULL DEFAULT 0
);
```

---

## Changelog

### Version 1.0.0
- Initial release
- Three shop types (Vault, Time, Wallet)
- Time points system with AFK detection
- Virtual wallet system
- GUI-based item management
- Full NBT item support
- PlaceholderAPI integration
- SQLite and JSON storage
- Configurable currency formatting
- Polish and English messages
