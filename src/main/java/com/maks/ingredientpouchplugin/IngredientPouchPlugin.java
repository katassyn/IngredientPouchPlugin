//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.maks.ingredientpouchplugin;

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

    public IngredientPouchPlugin() {
    }

    public void onEnable() {
        this.saveDefaultConfig();
        this.databaseManager = new DatabaseManager(this);
        this.databaseManager.init();
        this.itemManager = new ItemManager(this);
        this.pouchGuis = new HashMap();
        this.getCommand("ingredient_pouch").setExecutor(new IngredientPouchCommand(this));
        this.pouchListener = new PouchListener(this);
        this.chatListener = new ChatListener(this);
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
}
