package com.maks.ingredientpouchplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages player notification preferences for item pickups
 */
public class NotificationManager {
    private final IngredientPouchPlugin plugin;
    private final Map<UUID, NotificationMode> playerModes = new HashMap<>();
    
    public NotificationManager(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
        createTable();
    }
    
    /**
     * Create the notification preferences table if it doesn't exist
     */
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS player_notifications ("
                + "player_uuid VARCHAR(36) PRIMARY KEY,"
                + "notification_mode INT DEFAULT 1);"; // Default to BEST mode
                
        try (Connection conn = plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            plugin.getLogger().info("Notification preferences table ensured.");
        } catch (SQLException e) {
            plugin.getLogger().severe("Could not create notification preferences table!");
            e.printStackTrace();
        }
    }
    
    /**
     * Get notification mode for a player
     * @param player The player
     * @return The player's notification mode
     */
    public NotificationMode getNotificationMode(Player player) {
        UUID uuid = player.getUniqueId();
        
        // Check cache first
        if (playerModes.containsKey(uuid)) {
            return playerModes.get(uuid);
        }
        
        // Load from database
        loadPlayerPreference(player);
        return playerModes.getOrDefault(uuid, NotificationMode.BEST);
    }
    
    /**
     * Set notification mode for a player
     * @param player The player
     * @param mode The notification mode
     */
    public void setNotificationMode(Player player, NotificationMode mode) {
        playerModes.put(player.getUniqueId(), mode);
    }
    
    /**
     * Load player preference from database
     * @param player The player
     */
    public void loadPlayerPreference(Player player) {
        String sql = "SELECT notification_mode FROM player_notifications WHERE player_uuid = ?";
        
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (Connection conn = plugin.getDatabaseManager().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setString(1, player.getUniqueId().toString());
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int modeValue = rs.getInt("notification_mode");
                        NotificationMode mode = NotificationMode.fromValue(modeValue);
                        
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            playerModes.put(player.getUniqueId(), mode);
                        });
                    } else {
                        // No preference found, use default
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            playerModes.put(player.getUniqueId(), NotificationMode.BEST);
                        });
                    }
                }
            } catch (SQLException e) {
                plugin.getLogger().warning("Failed to load notification preference for " + player.getName());
                e.printStackTrace();
            }
        });
    }
    
    /**
     * Save player preference to database
     * @param player The player
     */
    public void savePlayerPreference(Player player) {
        NotificationMode mode = playerModes.getOrDefault(player.getUniqueId(), NotificationMode.BEST);
        String sql = "INSERT INTO player_notifications (player_uuid, notification_mode) VALUES (?, ?) "
                   + "ON DUPLICATE KEY UPDATE notification_mode = ?";
        
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (Connection conn = plugin.getDatabaseManager().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setString(1, player.getUniqueId().toString());
                stmt.setInt(2, mode.getValue());
                stmt.setInt(3, mode.getValue());
                
                stmt.executeUpdate();
            } catch (SQLException e) {
                plugin.getLogger().warning("Failed to save notification preference for " + player.getName());
                e.printStackTrace();
            }
        });
    }
    
    /**
     * Remove player from cache when they disconnect
     * @param player The player
     */
    public void removePlayer(Player player) {
        playerModes.remove(player.getUniqueId());
    }
}