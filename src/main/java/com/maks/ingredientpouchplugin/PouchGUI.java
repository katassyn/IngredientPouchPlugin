package com.maks.ingredientpouchplugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PouchGUI {
    private final IngredientPouchPlugin plugin;
    private final Player player;
    private Inventory inventory;
    private int page = 1;
    private int maxPage;
    private final Map<Integer, List<String>> pages = new HashMap<>();
    private final Map<Integer, String> slotItemMap = new HashMap<>();
    private static final int[] ITEM_SLOTS = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    public PouchGUI(IngredientPouchPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.groupItemsByCategory();
        this.maxPage = this.pages.size();
    }

    private void groupItemsByCategory() {
        final int debuggingFlag = 0; // Set to 0 when everything is working properly

        Map<String, List<String>> categoryItems = new LinkedHashMap<>();

        // Use LinkedHashMap to preserve insertion order
        List<String> orderedItemIds = new ArrayList<>(this.plugin.getItemManager().getItemIds());

        // Group items by category while preserving their original order
        for (String itemId : orderedItemIds) {
            String category = this.plugin.getItemManager().getItemCategory(itemId);
            categoryItems.computeIfAbsent(category, k -> new ArrayList<>()).add(itemId);
        }

        int pageIndex = 1;

        // Define specific category order: 1-CURRENCY, 2-EXPO, 3-MONSTER_FRAGMENTS, 4-Q, 5-KOPALNIA, 6-LOWISKO
        List<String> orderedCategories = new ArrayList<>();
        String[] categoryOrder = {"CURRENCY", "EXPO", "MONSTER_FRAGMENTS", "Q", "KOPALNIA", "LOWISKO"};

        // Add categories in specified order
        for (String category : categoryOrder) {
            if (categoryItems.containsKey(category)) {
                orderedCategories.add(category);
            }
        }

        // Add any remaining categories alphabetically
        List<String> remainingCategories = new ArrayList<>(categoryItems.keySet());
        remainingCategories.removeAll(orderedCategories);
        Collections.sort(remainingCategories);
        orderedCategories.addAll(remainingCategories);

        for (String category : orderedCategories) {
            List<String> items = categoryItems.get(category);

            if (debuggingFlag == 1) {
                this.plugin.getLogger().info("[DEBUG] Processing category: " + category);
                this.plugin.getLogger().info("[DEBUG] Items before sorting: " + String.join(", ", items));
            }

            // Sort items to keep same base items together, then by tier
            Collections.sort(items, (item1, item2) -> {
                // Get base ID by removing tier suffix
                String baseId1 = getBaseId(item1);
                String baseId2 = getBaseId(item2);

                if (debuggingFlag == 1) {
                    this.plugin.getLogger().info("[DEBUG] Comparing: " + item1 + " and " + item2);
                    this.plugin.getLogger().info("[DEBUG] Base IDs: " + baseId1 + " and " + baseId2);
                }

                // First compare by base ID (alphabetically)
                int baseComparison = baseId1.compareTo(baseId2);
                if (baseComparison != 0) {
                    return baseComparison;
                }

                // If base IDs are the same, sort by tier level (I < II < III)
                int tier1 = getTierLevel(item1);
                int tier2 = getTierLevel(item2);

                if (debuggingFlag == 1) {
                    this.plugin.getLogger().info("[DEBUG] Same base ID, tiers: " + tier1 + " and " + tier2);
                }

                return Integer.compare(tier1, tier2);
            });

            if (debuggingFlag == 1) {
                this.plugin.getLogger().info("[DEBUG] Items after sorting: " + String.join(", ", items));
            }

            for (int fromIndex = 0; fromIndex < items.size(); fromIndex += ITEM_SLOTS.length) {
                int toIndex = Math.min(fromIndex + ITEM_SLOTS.length, items.size());
                List<String> pageItems = items.subList(fromIndex, toIndex);
                this.pages.put(pageIndex++, new ArrayList<>(pageItems));
            }
        }
    }

    public void open() {
        this.openPage(this.page);
    }

    public void openPage(int pageNumber) {
        this.page = pageNumber;
        this.inventory = Bukkit.createInventory(null, 54, "Ingredient Pouch - Page " + this.page);
        this.loadItems();
        this.player.openInventory(this.inventory);
    }

    public void loadItems() {
        List<String> itemsOnPage = this.pages.get(this.page);
        if (itemsOnPage != null) {
            Map<String, Integer> quantities = this.getPlayerQuantities(this.player.getUniqueId().toString());

            // Set background
            ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta fillerMeta = filler.getItemMeta();
            fillerMeta.setDisplayName(" ");
            filler.setItemMeta(fillerMeta);

            for (int i = 0; i < this.inventory.getSize(); i++) {
                this.inventory.setItem(i, filler);
            }

            this.slotItemMap.clear();
            int slotIndex = 0;

            for (String itemId : itemsOnPage) {
                if (slotIndex >= ITEM_SLOTS.length) {
                    break;
                }

                int slot = ITEM_SLOTS[slotIndex];
                ItemStack originalItem = this.plugin.getItemManager().getItem(itemId);

                if (originalItem == null) {
                    this.plugin.getLogger().warning("Item with ID '" + itemId + "' not found in ItemManager.");
                    continue;
                }

                ItemStack displayItem = originalItem.clone();
                ItemMeta meta = displayItem.getItemMeta();
                int totalQuantity = quantities.getOrDefault(itemId, 0);

                List<String> lore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();

                if (!lore.isEmpty()) {
                    String lastLine = lore.get(lore.size() - 1);
                    if (ChatColor.stripColor(lastLine).startsWith("You have:")) {
                        lore.remove(lore.size() - 1);
                    }
                }

                lore.add(ChatColor.YELLOW + "You have: " + totalQuantity);
                meta.setLore(lore);
                displayItem.setItemMeta(meta);

                this.inventory.setItem(slot, displayItem);
                slotIndex++;
                this.slotItemMap.put(slot, itemId);
            }

            this.addNavigationButtons();
        }
    }

    public void updateSingleItem(String itemId) {
        Integer slot = null;
        for (Map.Entry<Integer, String> entry : this.slotItemMap.entrySet()) {
            if (entry.getValue().equals(itemId)) {
                slot = entry.getKey();
                break;
            }
        }

        if (slot != null) {
            ItemStack originalItem = this.plugin.getItemManager().getItem(itemId);
            if (originalItem == null) {
                this.plugin.getLogger().warning("Item with ID '" + itemId + "' not found in ItemManager.");
                return;
            }

            ItemStack displayItem = originalItem.clone();
            ItemMeta meta = displayItem.getItemMeta();
            int totalQuantity = this.getPlayerQuantity(this.player.getUniqueId().toString(), itemId);

            List<String> lore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();

            if (!lore.isEmpty()) {
                String lastLine = lore.get(lore.size() - 1);
                if (ChatColor.stripColor(lastLine).startsWith("You have:")) {
                    lore.remove(lore.size() - 1);
                }
            }

            lore.add(ChatColor.YELLOW + "You have: " + totalQuantity);
            meta.setLore(lore);
            displayItem.setItemMeta(meta);

            this.inventory.setItem(slot, displayItem);

            if (this.player.getOpenInventory().getTopInventory().equals(this.inventory)) {
                this.player.updateInventory();
            }
        }
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public String getItemIdBySlot(int slot) {
        return this.slotItemMap.get(slot);
    }

    public int getCurrentPage() {
        return this.page;
    }

    public int getMaxPage() {
        return this.maxPage;
    }

    private void addNavigationButtons() {
        ItemStack previousPage = new ItemStack(Material.ARROW);
        ItemMeta prevMeta = previousPage.getItemMeta();
        prevMeta.setDisplayName(ChatColor.GREEN + "Previous Page");
        previousPage.setItemMeta(prevMeta);

        ItemStack nextPage = new ItemStack(Material.ARROW);
        ItemMeta nextMeta = nextPage.getItemMeta();
        nextMeta.setDisplayName(ChatColor.GREEN + "Next Page");
        nextPage.setItemMeta(nextMeta);

        if (this.page > 1) {
            this.inventory.setItem(45, previousPage);
        }

        if (this.page < this.maxPage) {
            this.inventory.setItem(53, nextPage);
        }
    }

    private Map<String, Integer> getPlayerQuantities(String playerUUID) {
        Map<String, Integer> quantities = new HashMap<>();
        String sql = "SELECT item_id, quantity FROM player_pouch WHERE player_uuid = ?";

        try (Connection conn = this.plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playerUUID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String itemId = rs.getString("item_id");
                    int quantity = rs.getInt("quantity");
                    quantities.put(itemId, quantity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quantities;
    }

    private int getPlayerQuantity(String playerUUID, String itemId) {
        String sql = "SELECT quantity FROM player_pouch WHERE player_uuid = ? AND item_id = ?";

        try (Connection conn = this.plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playerUUID);
            stmt.setString(2, itemId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantity");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Extracts the tier information from an item name.
     * Looks for patterns like [I], [II], [III] in the item name.
     * 
     * @param itemName The display name of the item
     * @return The tier string, or empty string if no tier is found
     */
    private String extractTier(String itemName) {
        // Look for [I], [II], [III] pattern in the name
        if (itemName == null) {
            return "";
        }

        // Use regex to find the tier in square brackets
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\[(.*?)\\]");
        java.util.regex.Matcher matcher = pattern.matcher(itemName);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }

        return "";
    }

    /**
     * Converts Roman numeral to integer.
     * Handles I, II, III for tier conversion.
     * 
     * @param roman The Roman numeral string
     * @return The integer value, or 0 if invalid
     */
    private int romanToInt(String roman) {
        if (roman == null || roman.isEmpty()) {
            return 0;
        }

        roman = roman.trim();

        // Simple conversion for our limited use case
        switch (roman) {
            case "I":
                return 1;
            case "II":
                return 2;
            case "III":
                return 3;
            default:
                // Try to parse as integer if it's not a Roman numeral
                try {
                    return Integer.parseInt(roman);
                } catch (NumberFormatException e) {
                    return 0;
                }
        }
    }

    // Helper method to extract the base ID (without tier suffix)
    private String getBaseId(String itemId) {
        if (itemId.endsWith("_I") || itemId.endsWith("_II") || itemId.endsWith("_III")) {
            int lastUnderscoreIndex = itemId.lastIndexOf('_');
            if (lastUnderscoreIndex != -1) {
                return itemId.substring(0, lastUnderscoreIndex);
            }
        }
        return itemId;
    }

    // Helper method to get the tier level (I=1, II=2, III=3)
    private int getTierLevel(String itemId) {
        if (itemId.endsWith("_I")) return 1;
        if (itemId.endsWith("_II")) return 2;
        if (itemId.endsWith("_III")) return 3;
        return 0; // No tier or unable to determine
    }
}
