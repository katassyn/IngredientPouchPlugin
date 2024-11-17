package com.maks.ingredientpouchplugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IngredientPouchPlugin extends JavaPlugin {

    private DatabaseManager databaseManager;
    private ItemManager itemManager;
    private Map<UUID, PouchGUI> pouchGuis;

    @Override
    public void onEnable() {
        // Save default config if it doesn't exist
        saveDefaultConfig();

        // Initialize database connection
        databaseManager = new DatabaseManager(this);
        databaseManager.init();

        // Initialize item manager
        itemManager = new ItemManager(this);

        // Initialize pouch GUIs map
        pouchGuis = new HashMap<>();

        // Register command
        getCommand("ingredient_pouch").setExecutor(new IngredientPouchCommand(this));

        // Register the GUI listener
        new PouchListener(this);
    }

    @Override
    public void onDisable() {
        // Close database connection
        databaseManager.close();
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public Map<UUID, PouchGUI> getPouchGuis() {
        return pouchGuis;
    }
}
