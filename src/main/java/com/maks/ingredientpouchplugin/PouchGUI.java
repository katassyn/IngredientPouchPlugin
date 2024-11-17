package com.maks.ingredientpouchplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PouchGUI {

    private final IngredientPouchPlugin plugin;
    private final Player player;
    private Inventory inventory;
    private int page = 1;
    private int maxPage;

    private final Map<Integer, List<String>> pages = new HashMap<>();
    private final Map<Integer, String> slotItemMap = new HashMap<>();

    private static final int[] ITEM_SLOTS = {
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34,
            37, 38, 39, 40, 41, 42, 43
    };

    public PouchGUI(IngredientPouchPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        groupItemsByCategory();
        this.maxPage = pages.size();
    }

    private void groupItemsByCategory() {
        Map<String, List<String>> categoryItems = new LinkedHashMap<>();

        for (String itemId : plugin.getItemManager().getItemIds()) {
            String category = plugin.getItemManager().getItemCategory(itemId);
            categoryItems.computeIfAbsent(category, k -> new ArrayList<>()).add(itemId);
        }

        int pageIndex = 1;

        // Sort categories to ensure consistent ordering
        List<String> categories = new ArrayList<>(categoryItems.keySet());
        Collections.sort(categories);

        for (String category : categories) {
            List<String> items = categoryItems.get(category);

            // Sort items for consistency
            Collections.sort(items);

            // Group items into pages
            int fromIndex = 0;
            while (fromIndex < items.size()) {
                int toIndex = Math.min(fromIndex + ITEM_SLOTS.length, items.size());
                List<String> pageItems = items.subList(fromIndex, toIndex);

                pages.put(pageIndex++, new ArrayList<>(pageItems));
                fromIndex = toIndex;
            }
        }
    }

    public void open() {
        openPage(page);
    }

    public void openPage(int pageNumber) {
        this.page = pageNumber;
        inventory = Bukkit.createInventory(null, 54, "Ingredient Pouch - Page " + page);

        loadItems();

        player.openInventory(inventory);
    }

    private void loadItems() {
        List<String> itemsOnPage = pages.get(page);
        if (itemsOnPage == null) return;

        Map<String, Integer> quantities = getPlayerQuantities(player.getUniqueId().toString());

        // Pre-fill inventory with black panes
        ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, filler);
        }

        slotItemMap.clear(); // Clear previous mappings

        // Grupuj przedmioty według nazwy bazowej
        Map<String, List<String>> groupedItems = new LinkedHashMap<>();
        for (String itemId : itemsOnPage) {
            String baseName = getBaseName(itemId);
            groupedItems.computeIfAbsent(baseName, k -> new ArrayList<>()).add(itemId);
        }

        List<String> orderedItems = new ArrayList<>();
        for (List<String> group : groupedItems.values()) {
            orderedItems.addAll(group);
        }

        int slotIndex = 0;
        for (String itemId : orderedItems) {
            if (slotIndex >= ITEM_SLOTS.length) break;
            int slot = ITEM_SLOTS[slotIndex];

            ItemStack item = plugin.getItemManager().getItem(itemId);
            if (item == null) {
                plugin.getLogger().warning("Item with ID '" + itemId + "' not found in ItemManager.");
                continue;
            }

            item = item.clone();
            ItemMeta meta = item.getItemMeta();

            int totalQuantity = quantities.getOrDefault(itemId, 0);

            // Update lore with quantity
            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&eYou have: " + totalQuantity));
            meta.setLore(lore);
            item.setItemMeta(meta);

            inventory.setItem(slot, item);
            slotIndex++;

            // Map slot to itemId
            slotItemMap.put(slot, itemId);
        }

        // Add navigation buttons
        addNavigationButtons();
    }

    private String getBaseName(String itemId) {
        // Remove level suffix from itemId to get base name
        if (itemId.endsWith("_I") || itemId.endsWith("_II") || itemId.endsWith("_III")) {
            return itemId.substring(0, itemId.lastIndexOf('_'));
        }
        return itemId;
    }

    public String getItemIdBySlot(int slot) {
        return slotItemMap.get(slot);
    }

    public int getCurrentPage() {
        return page;
    }

    public int getMaxPage() {
        return maxPage;
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

        if (page > 1) {
            inventory.setItem(45, previousPage);
        }

        if (page < maxPage) {
            inventory.setItem(53, nextPage);
        }
    }

    private Map<String, Integer> getPlayerQuantities(String playerUUID) {
        Map<String, Integer> quantities = new HashMap<>();

        String sql = "SELECT item_id, quantity FROM player_pouch WHERE player_uuid = ?";

        try (PreparedStatement stmt = plugin.getDatabaseManager().getConnection().prepareStatement(sql)) {
            stmt.setString(1, playerUUID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String itemId = rs.getString("item_id");
                int quantity = rs.getInt("quantity");
                quantities.put(itemId, quantity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quantities;
    }
}
