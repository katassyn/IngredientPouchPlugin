# IngredientPouchPlugin

A Minecraft plugin that adds a virtual pouch system for storing crafting ingredients and items. Designed for RPG servers, it provides an efficient way to manage large quantities of crafting materials and special items.

## Features

- **Virtual Storage System**: Store items without taking up physical inventory space
- **Category Organization**: Items automatically sorted by categories (EXPO, MONSTER_FRAGMENTS, Q, KOPALNIA, MINE, FARMING, LOWISKO, CURRENCY)
- **Paginated GUI**: Easy-to-navigate interface with previous/next page buttons
- **Database Integration**: Secure MySQL storage with HikariCP connection pooling
- **Item Management**: Support for custom items with special properties
- **Efficient Resource Usage**: Optimized for performance with connection pooling
- **User-Friendly Commands**: Simple command interface for accessing the pouch

## Requirements

- Paper/Spigot 1.20.1+
- Java 8 or higher
- MySQL Database

## Installation

1. Download the latest release from the releases page
2. Place the .jar file in your server's `plugins` folder
3. Start the server to generate the config file
4. Edit the configuration in `plugins/IngredientPouchPlugin/config.yml`
5. Restart the server

## Configuration

```yaml
database:
  host: localhost
  port: 3306
  name: your_database_name
  user: your_username
  password: your_password
```

## Commands

- `/ingredient_pouch` - Opens the ingredient pouch GUI

## Permissions

- `ingredient_pouch.use` - Allows use of the ingredient pouch command
- `ingredient_pouch.admin` - Allows use of admin commands (coming soon)

## Usage

1. Players can access their pouch using `/ingredient_pouch`
2. Left-click items in inventory to deposit them into the pouch
3. Right-click items in the pouch to withdraw one at a time
4. Shift + Right-click to specify a custom amount to withdraw
5. Navigate through pages using the arrow buttons

## Database Structure

The plugin uses a MySQL database with the following table structure:

```sql
CREATE TABLE player_pouch (
    player_uuid VARCHAR(36),
    item_id VARCHAR(255),
    quantity INT,
    PRIMARY KEY(player_uuid, item_id)
);
```

## Development

### Building from Source

1. Clone the repository:
```bash
git clone https://github.com/yourusername/IngredientPouchPlugin.git
```

2. Build using Maven:
```bash
cd IngredientPouchPlugin
mvn clean package
```

### Dependencies

- PaperMC API
- HikariCP

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## API Usage

You can integrate with the plugin using its API:

```java
IngredientPouchPlugin plugin = (IngredientPouchPlugin) Bukkit.getPluginManager().getPlugin("IngredientPouchPlugin");
ItemManager itemManager = plugin.getItemManager();

// Get item from pouch
ItemStack item = itemManager.getItem("item_id");

// Check item category
String category = itemManager.getItemCategory("item_id");
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For support, please open an issue in the GitHub repository or contact us through [your contact method].
