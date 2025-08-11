//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.maks.ingredientpouchplugin;

import com.maks.ingredientpouchplugin.api.PouchAPI;
import com.maks.ingredientpouchplugin.api.PouchAPIImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.plugin.java.JavaPlugin;

public class IngredientPouchPlugin extends JavaPlugin {
    private DatabaseManager databaseManager;
    private ItemManager itemManager;
    private Map<UUID, PouchGUI> pouchGuis;
    private PouchListener pouchListener;
    private ChatListener chatListener;
    private NotificationManager notificationManager; // New field
    private PouchAPI api; // New API field

    public IngredientPouchPlugin() {
    }

    public void onEnable() {
        this.saveDefaultConfig();
        this.databaseManager = new DatabaseManager(this);
        this.databaseManager.init();
        this.itemManager = new ItemManager(this);
        this.pouchGuis = new HashMap();
        this.pouchListener = new PouchListener(this);
        this.chatListener = new ChatListener(this);
        this.notificationManager = new NotificationManager(this); // Initialize notification manager

        // Register commands
        this.getCommand("ingredient_pouch").setExecutor(new IngredientPouchCommand(this));
        this.getCommand("drop").setExecutor(new DropNotificationCommand(this));

        // Register listeners
        new ItemPickupListener(this);
        new PlayerConnectionListener(this);

        // Initialize the API
        this.api = new PouchAPIImpl(this);

        getLogger().info("IngredientPouch API initialized and ready for other plugins to use.");
    }

    public void onDisable() {
        this.databaseManager.close();
    }

    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }

    public ItemManager getItemManager() {
        return this.itemManager;
    }

    public Map<UUID, PouchGUI> getPouchGuis() {
        return this.pouchGuis;
    }

    public PouchListener getPouchListener() {
        return this.pouchListener;
    }

    public ChatListener getChatListener() {
        return this.chatListener;
    }
    
    public NotificationManager getNotificationManager() {
        return this.notificationManager;
    }

    /**
     * Get the API for interacting with the IngredientPouch plugin
     * @return The API interface
     */
    public PouchAPI getAPI() {
        return this.api;
    }
}
