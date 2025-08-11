package com.maks.ingredientpouchplugin.api;

import com.maks.ingredientpouchplugin.IngredientPouchPlugin;
import com.maks.ingredientpouchplugin.PouchGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Implementation of the PouchAPI interface
 */
public class PouchAPIImpl implements PouchAPI {
    private final IngredientPouchPlugin plugin;
    private final int debuggingFlag = 0; // Set to 0 when everything is working properly

    public PouchAPIImpl(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public int getItemQuantity(String playerUUID, String itemId) {
        if (debuggingFlag == 1) {
            plugin.getLogger().info("[API-DEBUG] Getting quantity of " + itemId + " for player " + playerUUID);
        }
        
        // Call the method in PouchListener
        int quantity = plugin.getPouchListener().getCurrentQuantity(playerUUID, itemId);
        
        if (debuggingFlag == 1) {
            plugin.getLogger().info("[API-DEBUG] Quantity: " + quantity);
        }
        
        return quantity;
    }

    @Override
    public boolean updateItemQuantity(String playerUUID, String itemId, int amount) {
        if (debuggingFlag == 1) {
            plugin.getLogger().info("[API-DEBUG] Updating quantity of " + itemId + 
                               " for player " + playerUUID + " by " + amount);
        }
        
        // If withdrawing, check if player has enough
        if (amount < 0) {
            int currentQuantity = getItemQuantity(playerUUID, itemId);
            if (currentQuantity < Math.abs(amount)) {
                if (debuggingFlag == 1) {
                    plugin.getLogger().info("[API-DEBUG] Not enough items. Has: " + 
                                       currentQuantity + ", needs: " + Math.abs(amount));
                }
                return false;
            }
        }
        
        // Update asynchronously as in the original code
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            // Call the method in PouchListener
            plugin.getPouchListener().updatePlayerQuantity(playerUUID, itemId, amount);
            
            // Update GUI if player has it open
            Bukkit.getScheduler().runTask(plugin, () -> {
                Player player = Bukkit.getPlayer(java.util.UUID.fromString(playerUUID));
                if (player != null) {
                    updatePouchGUI(player, itemId);
                }
            });
        });
        
        return true;
    }

    @Override
    public ItemStack getItem(String itemId) {
        if (debuggingFlag == 1) {
            plugin.getLogger().info("[API-DEBUG] Getting item with ID: " + itemId);
        }
        
        // Get the item from ItemManager
        return plugin.getItemManager().getItem(itemId);
    }

    @Override
    public boolean hasPouchOpen(Player player) {
        // Check if the player has a GUI open in the map
        return plugin.getPouchGuis().containsKey(player.getUniqueId());
    }

    @Override
    public void updatePouchGUI(Player player, String itemId) {
        PouchGUI gui = plugin.getPouchGuis().get(player.getUniqueId());
        if (gui != null) {
            if (debuggingFlag == 1) {
                plugin.getLogger().info("[API-DEBUG] Updating GUI for player " + 
                                   player.getName() + ", item: " + itemId);
            }
            
            gui.updateSingleItem(itemId);
            player.updateInventory();
        }
    }
}