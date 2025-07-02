package com.maks.ingredientpouchplugin.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Public API for the IngredientPouch plugin
 * Allows other plugins to interact with the pouch system
 */
public interface PouchAPI {
    /**
     * Get the quantity of a specific item in a player's pouch
     * 
     * @param playerUUID The UUID of the player as a string
     * @param itemId The ID of the item to check
     * @return The quantity of the item in the player's pouch
     */
    int getItemQuantity(String playerUUID, String itemId);
    
    /**
     * Update the quantity of an item in a player's pouch
     * Positive amount adds items, negative amount removes items
     * 
     * @param playerUUID The UUID of the player as a string
     * @param itemId The ID of the item to update
     * @param amount The amount to add (positive) or remove (negative)
     * @return true if successful, false if trying to remove more than available
     */
    boolean updateItemQuantity(String playerUUID, String itemId, int amount);
    
    /**
     * Get an ItemStack representing the item with the given ID
     * 
     * @param itemId The ID of the item to get
     * @return The ItemStack, or null if the item doesn't exist
     */
    ItemStack getItem(String itemId);
    
    /**
     * Check if a player has their pouch GUI open
     * 
     * @param player The player to check
     * @return true if the player has their pouch GUI open
     */
    boolean hasPouchOpen(Player player);
    
    /**
     * Update the pouch GUI for a player if it's open
     * 
     * @param player The player whose GUI to update
     * @param itemId The ID of the item that was changed
     */
    void updatePouchGUI(Player player, String itemId);
}