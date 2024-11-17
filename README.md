# IngredientPouchPlugin

A Minecraft plugin that provides a dedicated storage system for specific items through the `/ingredient_pouch` command. Players can efficiently manage crafting ingredients using a customizable GUI and MySQL-backed data storage.

---

## Features

### **/ingredient_pouch Command**
- Opens a GUI where players can store and manage specific items.
- **Add items**: Left-click (LMB) to add allowed items to the pouch.
- **Withdraw items**: Right-click (RMB) to withdraw items from the pouch.
- **Withdraw specific amounts**: Shift + RMB opens a separate GUI for selecting the amount to withdraw (currently buggy).

---

### **Item Management**
- Supported items are currently hardcoded but will be configurable via `item_list.yml` in future updates.
- Stored items are saved in a MySQL database for each player, ensuring persistence.

---

## Configuration

### **config.yml**
- Configure the MySQL database connection:
  ```yaml
  database:
    host: host
    port: port
    name: database name
    username: username
    password: password

## Contributing

We welcome contributions to the **IngredientPouchPlugin**! Below are some guidelines for adding features or fixing issues:

### Where to Add Changes
- **Commands (`/ingredient_pouch`)**: Add or modify command behavior in the `src/main/java/com/yourpackage/commands` directory.
- **Item Configuration**:
  - Add new items to `item_list.yml` (planned feature).
  - Ensure the item format matches the example:
    ```yaml
    allowed_items:
      - DIAMOND
      - GOLD_INGOT
      - IRON_INGOT
    ```
- **Database Integration**:
  - Update MySQL-related features in `src/main/java/com/yourpackage/database`.

### Reporting Bugs
- If you encounter a bug, please open an issue in this repository with:
  - Steps to reproduce the bug.
  - Relevant logs or screenshots.
  - Expected vs actual behavior.

### Planned Features
- Dynamic item list from `item_list.yml`.
- Bug fixes for Shift + RMB GUI.
- Enhanced messaging and localization support.

Feel free to suggest additional features!
