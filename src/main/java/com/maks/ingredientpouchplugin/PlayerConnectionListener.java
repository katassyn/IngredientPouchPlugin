package com.maks.ingredientpouchplugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Handles player join and quit events to manage notification preferences
 */
public class PlayerConnectionListener implements Listener {
    private final IngredientPouchPlugin plugin;
    
    public PlayerConnectionListener(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Load player's notification preference when they join
        plugin.getNotificationManager().loadPlayerPreference(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Remove player from cache when they leave
        plugin.getNotificationManager().removePlayer(event.getPlayer());
    }
}