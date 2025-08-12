package com.maks.mycraftingplugin2.integration;

import com.maks.ingredientpouchplugin.IngredientPouchPlugin;
import com.maks.ingredientpouchplugin.api.PouchAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Helper class to integrate IngredientPouch with MyCraftingPlugin2
 * Handles checking and removing items from both inventory and pouch
 */
public class PouchIntegrationHelper {
    
    private static PouchAPI pouchAPI;
    private static boolean apiAvailable = false;
    private static final int debugFlag = 0; // Set to 0 to disable debug
    
    static {
        initializeAPI();
    }
    
    /**
     * Initialize the IngredientPouch API
     */
    private static void initializeAPI() {
        if (Bukkit.getPluginManager().getPlugin("IngredientPouchPlugin") != null) {
            try {
                IngredientPouchPlugin plugin = (IngredientPouchPlugin) Bukkit.getPluginManager().getPlugin("IngredientPouchPlugin");
                pouchAPI = plugin.getAPI();
                apiAvailable = true;
                
                if (debugFlag == 1) {
                    Bukkit.getLogger().info("[PouchIntegration] Successfully connected to IngredientPouch API");
                }
            } catch (Exception e) {
                apiAvailable = false;
                Bukkit.getLogger().warning("[PouchIntegration] Failed to connect to IngredientPouch API: " + e.getMessage());
            }
        } else {
            apiAvailable = false;
            if (debugFlag == 1) {
                Bukkit.getLogger().info("[PouchIntegration] IngredientPouch plugin not found");
            }
        }
    }
    
    /**
     * Check if API is available
     */
    public static boolean isAPIAvailable() {
        return apiAvailable;
    }
    
    /**
     * Get total amount of an item (inventory + pouch)
     * Handles stacked items (x100, x1000) properly
     */
    public static int getTotalItemAmount(Player player, ItemStack requiredItem) {
        int totalAmount = 0;
        
        // First check inventory for regular and stacked items
        int invAmount = getInventoryAmount(player, requiredItem);
        totalAmount += invAmount;
        
        if (debugFlag == 1) {
            Bukkit.getLogger().info("[PouchIntegration] Inventory amount for " + getItemName(requiredItem) + ": " + invAmount);
        }
        
        // Then check pouch if API is available
        if (apiAvailable) {
            int pouchAmount = getPouchAmount(player, requiredItem);
            totalAmount += pouchAmount;
            
            if (debugFlag == 1) {
                Bukkit.getLogger().info("[PouchIntegration] Pouch amount for " + getItemName(requiredItem) + ": " + pouchAmount);
            }
        }
        
        if (debugFlag == 1) {
            Bukkit.getLogger().info("[PouchIntegration] Total amount: " + totalAmount);
        }
        
        return totalAmount;
    }
    
    /**
     * Get amount from player's inventory
     */
    private static int getInventoryAmount(Player player, ItemStack requiredItem) {
        int total = 0;
        
        // Get the base item name without stack size
        String baseItemName = getBaseItemName(requiredItem);
        
        for (ItemStack invItem : player.getInventory().getContents()) {
            if (invItem != null && isMatchingItem(invItem, requiredItem, baseItemName)) {
                // Extract the actual amount from the item
                int itemAmount = getActualItemAmount(invItem);
                total += itemAmount;
                
                if (debugFlag == 1 && itemAmount > 0) {
                    Bukkit.getLogger().info("[PouchIntegration] Found " + itemAmount + " in inventory slot");
                }
            }
        }
        
        return total;
    }
    
    /**
     * Get amount from player's pouch
     */
    private static int getPouchAmount(Player player, ItemStack requiredItem) {
        if (!apiAvailable) return 0;
        
        String itemId = findPouchItemId(requiredItem);
        if (itemId == null) {
            if (debugFlag == 1) {
                Bukkit.getLogger().info("[PouchIntegration] Could not find pouch item ID for: " + getItemName(requiredItem));
            }
            return 0;
        }
        
        int quantity = pouchAPI.getItemQuantity(player.getUniqueId().toString(), itemId);
        
        if (debugFlag == 1) {
            Bukkit.getLogger().info("[PouchIntegration] Pouch quantity for " + itemId + ": " + quantity);
        }
        
        return quantity;
    }
    
    /**
     * Remove items from inventory and/or pouch
     * Returns true if successful, false if not enough items
     */
    public static boolean removeItems(Player player, ItemStack requiredItem) {
        int amountNeeded = getActualItemAmount(requiredItem);
        
        if (debugFlag == 1) {
            Bukkit.getLogger().info("[PouchIntegration] Need to remove " + amountNeeded + " of " + getItemName(requiredItem));
        }
        
        // First check if player has enough total
        if (getTotalItemAmount(player, requiredItem) < amountNeeded) {
            return false;
        }
        
        // Remove from inventory first
        int removedFromInventory = removeFromInventory(player, requiredItem, amountNeeded);
        amountNeeded -= removedFromInventory;
        
        if (debugFlag == 1) {
            Bukkit.getLogger().info("[PouchIntegration] Removed " + removedFromInventory + " from inventory, still need: " + amountNeeded);
        }
        
        // If still need more, remove from pouch
        if (amountNeeded > 0 && apiAvailable) {
            boolean success = removeFromPouch(player, requiredItem, amountNeeded);
            if (!success) {
                // Rollback inventory changes
                giveItems(player, requiredItem, removedFromInventory);
                return false;
            }
        }
        
        return amountNeeded <= 0;
    }
    
    /**
     * Remove items from inventory
     * Returns amount actually removed
     */
    private static int removeFromInventory(Player player, ItemStack requiredItem, int amountToRemove) {
        int totalRemoved = 0;
        String baseItemName = getBaseItemName(requiredItem);
        ItemStack[] contents = player.getInventory().getContents();
        
        for (int i = 0; i < contents.length; i++) {
            if (totalRemoved >= amountToRemove) break;
            
            ItemStack invItem = contents[i];
            if (invItem != null && isMatchingItem(invItem, requiredItem, baseItemName)) {
                int itemAmount = getActualItemAmount(invItem);
                
                if (itemAmount <= (amountToRemove - totalRemoved)) {
                    // Remove entire stack
                    contents[i] = null;
                    totalRemoved += itemAmount;
                } else {
                    // Remove partial amount
                    int toRemove = amountToRemove - totalRemoved;
                    int newAmount = itemAmount - toRemove;
                    
                    // Update the item with new amount
                    ItemStack newItem = createStackedItem(invItem, newAmount);
                    contents[i] = newItem;
                    totalRemoved += toRemove;
                }
            }
        }
        
        player.getInventory().setContents(contents);
        return totalRemoved;
    }
    
    /**
     * Remove items from pouch
     */
    private static boolean removeFromPouch(Player player, ItemStack requiredItem, int amountToRemove) {
        if (!apiAvailable) return false;
        
        String itemId = findPouchItemId(requiredItem);
        if (itemId == null) return false;
        
        // Negative amount removes from pouch
        boolean success = pouchAPI.updateItemQuantity(player.getUniqueId().toString(), itemId, -amountToRemove);
        
        if (debugFlag == 1) {
            Bukkit.getLogger().info("[PouchIntegration] Removed " + amountToRemove + " from pouch: " + success);
        }
        
        // Update GUI if player has pouch open
        if (success && pouchAPI.hasPouchOpen(player)) {
            pouchAPI.updatePouchGUI(player, itemId);
        }
        
        return success;
    }
    
    /**
     * Give items to player (used for rollback)
     */
    private static void giveItems(Player player, ItemStack item, int amount) {
        if (amount <= 0) return;
        
        ItemStack toGive = createStackedItem(item, amount);
        Map<Integer, ItemStack> leftover = player.getInventory().addItem(toGive);
        
        // Drop any items that didn't fit
        for (ItemStack leftoverItem : leftover.values()) {
            player.getWorld().dropItemNaturally(player.getLocation(), leftoverItem);
        }
    }
    
    /**
     * Find the pouch item ID for a given item
     */
    private static String findPouchItemId(ItemStack item) {
        if (!apiAvailable) return null;
        
        String itemName = getBaseItemName(item);
        
        // Use the centralized mappings
        String pouchId = PouchItemMappings.getPouchItemId(itemName);
        
        if (pouchId != null) {
            if (debugFlag == 1) {
                Bukkit.getLogger().info("[PouchIntegration] Found mapping: " + itemName + " -> " + pouchId);
            }
            return pouchId;
        }
        
        // Try to auto-generate ID from name if no mapping found
        if (debugFlag == 1) {
            Bukkit.getLogger().info("[PouchIntegration] No mapping found for: " + itemName + ", trying auto-generate");
        }
        return generateItemId(itemName);
    }
    
    /**
     * Generate item ID from display name
     */
    private static String generateItemId(String displayName) {
        // This is a simple generator - adjust based on your naming conventions
        String id = displayName.toLowerCase()
            .replaceAll("\\[ i \\]", "_I")
            .replaceAll("\\[ ii \\]", "_II")
            .replaceAll("\\[ iii \\]", "_III")
            .replaceAll("[^a-zA-Z0-9_]", "_")
            .replaceAll("_+", "_")
            .replaceAll("^_|_$", "");
        
        if (debugFlag == 1) {
            Bukkit.getLogger().info("[PouchIntegration] Generated ID: " + id + " from name: " + displayName);
        }
        
        return id;
    }
    
    /**
     * Get the actual amount of an item (handles x100, x1000 in name)
     */
    private static int getActualItemAmount(ItemStack item) {
        if (item == null) return 0;
        
        String displayName = getItemName(item);
        
        // Check for x1000, x100, etc. in the name
        if (displayName.contains(" x")) {
            String[] parts = displayName.split(" x");
            if (parts.length >= 2) {
                try {
                    String numberPart = parts[parts.length - 1].replaceAll("[^0-9]", "");
                    int multiplier = Integer.parseInt(numberPart);
                    // Multiply by the actual stack size
                    return item.getAmount() * multiplier;
                } catch (NumberFormatException e) {
                    // Not a valid number, treat as x1
                }
            }
        }
        
        // Default: actual item amount
        return item.getAmount();
    }
    
    /**
     * Get base item name without stack size modifier
     */
    private static String getBaseItemName(ItemStack item) {
        String name = getItemName(item);
        
        // Remove x100, x1000, etc.
        if (name.contains(" x")) {
            String[] parts = name.split(" x");
            if (parts.length >= 2) {
                // Check if last part is a number
                try {
                    Integer.parseInt(parts[parts.length - 1].replaceAll("[^0-9]", ""));
                    // It's a number, so remove it
                    return String.join(" x", Arrays.copyOf(parts, parts.length - 1));
                } catch (NumberFormatException e) {
                    // Not a number, keep full name
                }
            }
        }
        
        return name;
    }
    
    /**
     * Check if two items match (ignoring stack size in name)
     */
    private static boolean isMatchingItem(ItemStack item1, ItemStack item2, String baseItemName) {
        if (item1 == null || item2 == null) return false;

        String name1 = getBaseItemName(item1);
        String name2 = baseItemName != null ? baseItemName : getBaseItemName(item2);

        // For items in the MINE category, match solely by display name
        if (PouchItemMappings.isMineCategoryItem(name1) || PouchItemMappings.isMineCategoryItem(name2)) {
            return name1.equals(name2);
        }

        if (item1.getType() != item2.getType()) return false;

        return name1.equals(name2);
    }
    
    /**
     * Create a stacked item with specific amount in name
     */
    private static ItemStack createStackedItem(ItemStack baseItem, int totalAmount) {
        ItemStack newItem = baseItem.clone();
        
        if (totalAmount > 1) {
            ItemMeta meta = newItem.getItemMeta();
            if (meta != null) {
                String baseName = getBaseItemName(baseItem);
                
                // Determine stack size and count
                int stackSize = 1;
                int stackCount = totalAmount;
                
                if (totalAmount >= 1000 && totalAmount % 1000 == 0) {
                    meta.setDisplayName(baseName + " x1000");
                    stackCount = totalAmount / 1000;
                } else if (totalAmount >= 100 && totalAmount % 100 == 0) {
                    meta.setDisplayName(baseName + " x100");
                    stackCount = totalAmount / 100;
                } else {
                    meta.setDisplayName(baseName);
                    stackCount = totalAmount;
                }
                
                newItem.setItemMeta(meta);
                newItem.setAmount(Math.min(stackCount, 64));
            }
        }
        
        return newItem;
    }
    
    /**
     * Get item display name
     */
    private static String getItemName(ItemStack item) {
        if (item == null) return "";
        
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasDisplayName()) {
            return ChatColor.stripColor(meta.getDisplayName());
        }
        
        return item.getType().toString();
    }
    
    /**
     * Send debug info to player
     */
    public static void sendDebugInfo(Player player, ItemStack item) {
        if (debugFlag != 1) return;
        
        player.sendMessage(ChatColor.YELLOW + "=== Item Debug Info ===");
        player.sendMessage("Item: " + getItemName(item));
        player.sendMessage("Base name: " + getBaseItemName(item));
        player.sendMessage("Actual amount: " + getActualItemAmount(item));
        player.sendMessage("Inventory: " + getInventoryAmount(player, item));
        if (apiAvailable) {
            player.sendMessage("Pouch: " + getPouchAmount(player, item));
        }
        player.sendMessage("Total: " + getTotalItemAmount(player, item));
    }
}