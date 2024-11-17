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

