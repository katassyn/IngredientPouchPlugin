package com.maks.ingredientpouchplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Locale;

public class ItemPickupListener implements Listener {

    private final IngredientPouchPlugin plugin;

    private static final String ANDERIUM_ITEM_ID = "andermant";
    private static final String LEGACY_ANDERMANT_DISPLAY = "andermant";
    private static final long LEGACY_CONVERSION_INTERVAL_TICKS = 10L;

    public ItemPickupListener(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::convertLegacyAndermantForOnlinePlayers, 0L, LEGACY_CONVERSION_INTERVAL_TICKS);
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
                processInventoryForPouch(player, itemId);
            });
        }
    }

    private void convertLegacyAndermantForOnlinePlayers() {
        ItemStack canonicalItem = plugin.getItemManager().getItem(ANDERIUM_ITEM_ID);
        if (canonicalItem == null) {
            return;
        }

        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            convertLegacyAndermant(onlinePlayer, canonicalItem);
        }
    }

    private void convertLegacyAndermant(Player player, ItemStack canonicalItem) {
        PlayerInventory inventory = player.getInventory();
        ItemStack displayItem = canonicalItem.clone();
        int totalAmount = 0;

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack invItem = inventory.getItem(slot);
            if (invItem == null) {
                continue;
            }

            ItemMeta meta = invItem.getItemMeta();
            if (meta == null || !meta.hasDisplayName()) {
                continue;
            }

            String strippedName = ChatColor.stripColor(meta.getDisplayName()).trim();
            String normalizedName = strippedName.toLowerCase(Locale.ROOT);
            if (!isLegacyAndermantName(normalizedName)) {
                continue;
            }

            int multiplier = extractStackMultiplier(normalizedName);
            int amountToAdd = Math.max(multiplier, 1) * invItem.getAmount();
            totalAmount += amountToAdd;
            inventory.setItem(slot, null);
        }

        if (totalAmount > 0) {
            addItemToPouch(player, ANDERIUM_ITEM_ID, totalAmount);
            sendNotificationIfNeeded(player, displayItem, ANDERIUM_ITEM_ID, totalAmount);
        }
    }

    private boolean isLegacyAndermantName(String normalizedName) {
        if (normalizedName == null || normalizedName.isEmpty()) {
            return false;
        }

        if (normalizedName.equals(LEGACY_ANDERMANT_DISPLAY)) {
            return true;
        }

        return normalizedName.startsWith(LEGACY_ANDERMANT_DISPLAY + " x");
    }

    private int extractStackMultiplier(String normalizedName) {
        int index = normalizedName.lastIndexOf(" x");
        if (index < 0) {
            return 1;
        }

        String numericPart = normalizedName.substring(index + 2).replaceAll("[^0-9]", "");
        if (numericPart.isEmpty()) {
            return 1;
        }

        try {
            return Math.max(Integer.parseInt(numericPart), 1);
        } catch (NumberFormatException ignored) {
            return 1;
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

        // If notifications are off, do not send anything
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
     */
    private void processInventoryForPouch(Player player, String itemId) {
        // Get the original item from the item manager to ensure we are matching correctly
        ItemStack originalItem = plugin.getItemManager().getItem(itemId);
        if (originalItem == null) {
            //plugin.getLogger().warning("Item with ID '" + itemId + "' not found in ItemManager.");
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
            addItemToPouch(player, itemId, totalAmount);
            sendNotificationIfNeeded(player, originalItem, itemId, totalAmount);
        }
    }
}
