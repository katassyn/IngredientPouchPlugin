package com.maks.ingredientpouchplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemPickupListener implements Listener {

    private final IngredientPouchPlugin plugin;

    public ItemPickupListener(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        // Only process player pickups
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        ItemStack pickedItem = event.getItem().getItemStack();

        // Check if the item can be stored in the pouch
        String itemId = findMatchingItemId(pickedItem);
        if (itemId != null) {
            // Don't cancel the event - let other plugins process it first
            // Schedule a task to run after the event completes
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                // Process the player's inventory to find and move matching items to the pouch
                processInventoryForPouch(player, itemId, pickedItem);
            });
        }
    }

    private String findMatchingItemId(ItemStack pickedItem) {
        // Compare the picked item with all items in the ItemManager
        for (String itemId : plugin.getItemManager().getItemIds()) {
            ItemStack pouchItem = plugin.getItemManager().getItem(itemId);
            if (plugin.getPouchListener().areItemsSimilar(pickedItem, pouchItem)) {
                return itemId;
            }
        }
        return null;
    }

    private void addItemToPouch(Player player, String itemId, int amount) {
        // Update the database
        plugin.getPouchListener().updatePlayerQuantity(
            player.getUniqueId().toString(), 
            itemId, 
            amount
        );

        // Update GUI if open
        PouchGUI gui = plugin.getPouchGuis().get(player.getUniqueId());
        if (gui != null) {
            gui.updateSingleItem(itemId);
        }
    }

    private void sendNotificationIfNeeded(Player player, ItemStack item, String itemId, int amount) {
        // Get player's notification mode
        NotificationMode mode = plugin.getNotificationManager().getNotificationMode(player);
        
        // If notifications are off, don't send anything
        if (mode == NotificationMode.OFF) {
            return;
        }
        
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        
        // If mode is ALL, always send notification
        if (mode == NotificationMode.ALL) {
            player.sendMessage(ChatColor.GREEN + "Added to pouch: " + 
                meta.getDisplayName() + ChatColor.GREEN + " x" + amount);
            return;
        }
        
        // If mode is BEST, only send for specific items
        if (mode == NotificationMode.BEST && meta.hasLore()) {
            List<String> lore = meta.getLore();
            for (String line : lore) {
                String strippedLine = ChatColor.stripColor(line).toLowerCase();
                if (strippedLine.contains("crafting and quest resource") || 
                    strippedLine.contains("legendary crafting material") || 
                    strippedLine.contains("unique currency") ||
                    strippedLine.contains("currency") ||
                    strippedLine.contains("unlock") ||
                    strippedLine.contains("upgrade")) {

                    // Send notification
                    player.sendMessage(ChatColor.GREEN + "Added to pouch: " + 
                        meta.getDisplayName() + ChatColor.GREEN + " x" + amount);
                    break;
                }
            }
        }
    }

    /**
     * Process the player's inventory to find and move matching items to the pouch
     * @param player The player whose inventory to process
     * @param itemId The ID of the item to look for
     * @param template The template item to match against
     */
    private void processInventoryForPouch(Player player, String itemId, ItemStack template) {
        // Get the original item from the item manager to ensure we're matching correctly
        ItemStack originalItem = plugin.getItemManager().getItem(itemId);
        if (originalItem == null) {
            plugin.getLogger().warning("Item with ID '" + itemId + "' not found in ItemManager.");
            return;
        }

        // Count how many matching items are in the player's inventory
        int totalAmount = 0;
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack invItem = player.getInventory().getItem(i);
            if (plugin.getPouchListener().areItemsSimilar(invItem, originalItem)) {
                totalAmount += invItem.getAmount();
                // Remove the item from inventory
                player.getInventory().setItem(i, null);
            }
        }

        // If we found matching items, add them to the pouch
        if (totalAmount > 0) {
            // Add to pouch
            addItemToPouch(player, itemId, totalAmount);

            // Send notification if needed
            sendNotificationIfNeeded(player, originalItem, itemId, totalAmount);
        }
    }
}
