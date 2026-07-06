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
    private final Map<Integer, String> categorySlotMap = new HashMap<>();
    private final List<String> orderedCategories = new ArrayList<>();
    private final Map<String, Integer> categoryStartPage = new HashMap<>();
    private boolean inCategorySelection = false;
    private static final int[] ITEM_SLOTS = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
    private static final int[] CATEGORY_SLOTS = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};

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

        // Define specific category order: 1-CURRENCY, 2-EXPO, 3-MONSTER_FRAGMENTS, 4-Q, 5-KOPALNIA, 6-MINE, 7-FARMING, 8-BEES, 9-LOWISKO, 10-THREAD_WEAVING, 11-SCIENTIST, 12-DEMON_TOWER
        List<String> orderedCategories = new ArrayList<>();
        String[] categoryOrder = {"CURRENCY", "EXPO", "MONSTER_FRAGMENTS", "Q", "KOPALNIA", "MINE", "FARMING", "BEES", "LOWISKO", "THREAD_WEAVING", "SCIENTIST", "DEMON_TOWER"};


        // Add categories in specified order
        for (String category : categoryOrder) {
            if (categoryItems.containsKey(category)) {
                this.orderedCategories.add(category);
            }
        }

        // Add any remaining categories in the order they were defined
        List<String> remainingCategories = new ArrayList<>(categoryItems.keySet());
        remainingCategories.removeAll(this.orderedCategories);
        this.orderedCategories.addAll(remainingCategories);

        for (String category : this.orderedCategories) {
            // Save starting page for each category
            this.categoryStartPage.put(category, pageIndex);
            List<String> items = categoryItems.get(category);

            if (debuggingFlag == 1) {
                //this.plugin.getLogger().info("[DEBUG] Processing category: " + category);
                //this.plugin.getLogger().info("[DEBUG] Items before sorting: " + String.join(", ", items));
            }

            if (debuggingFlag == 1) {
              //  this.plugin.getLogger().info("[DEBUG] Items after sorting: " + String.join(", ", items));
            }

            for (int fromIndex = 0; fromIndex < items.size(); fromIndex += ITEM_SLOTS.length) {
                int toIndex = Math.min(fromIndex + ITEM_SLOTS.length, items.size());
                List<String> pageItems = items.subList(fromIndex, toIndex);
                this.pages.put(pageIndex++, new ArrayList<>(pageItems));
            }
        }
    }

    public void open() {
        this.openCategorySelection();
    }

    public void openCategorySelection() {
        this.inCategorySelection = true;
        this.inventory = Bukkit.createInventory(null, 54, "Ingredient Pouch - Categories");
        this.loadCategorySelection();
        this.player.openInventory(this.inventory);
    }

    private void loadCategorySelection() {
        // Set background
        ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        for (int i = 0; i < this.inventory.getSize(); i++) {
            this.inventory.setItem(i, filler);
        }

        this.categorySlotMap.clear();
        int slotIndex = 0;

        for (String category : this.orderedCategories) {
            if (slotIndex >= CATEGORY_SLOTS.length) {
                break;
            }

            int slot = CATEGORY_SLOTS[slotIndex];
            ItemStack categoryItem = getCategoryIcon(category);
            this.inventory.setItem(slot, categoryItem);
            this.categorySlotMap.put(slot, category);
            slotIndex++;
        }
    }

    private ItemStack getCategoryIcon(String category) {
        Material material;
        String displayName;
        String description;

        switch (category) {
            case "CURRENCY":
                material = Material.GOLD_INGOT;
                displayName = "&e&lCurrency";
                description = "&7Currencies and valuable items";
                break;
            case "EXPO":
                material = Material.DRAGON_HEAD;
                displayName = "&d&lExperience Areas";
                description = "&7Mini boss fragments";
                break;
            case "MONSTER_FRAGMENTS":
                material = Material.FLINT;
                displayName = "&c&lMonster Fragments";
                description = "&7Soul and heart fragments";
                break;
            case "Q":
                material = Material.NETHERITE_SCRAP;
                displayName = "&6&lBoss Materials";
                description = "&7Legendary boss drops";
                break;
            case "KOPALNIA":
                material = Material.COBBLESTONE;
                displayName = "&8&lMine";
                description = "&7Mining materials";
                break;
            case "MINE":
                material = Material.DIAMOND_ORE;
                displayName = "&b&lGems";
                description = "&7Precious gems and minerals";
                break;
            case "FARMING":
                material = Material.PUMPKIN_SEEDS;
                displayName = "&a&lFarming";
                description = "&7Agricultural materials";
                break;
            case "BEES":
                material = Material.HONEYCOMB;
                displayName = "&6&lBees";
                description = "&7Honey and bee products";
                break;
            case "LOWISKO":
                material = Material.FISHING_ROD;
                displayName = "&3&lFishing";
                description = "&7Fishing materials";
                break;
            case "THREAD_WEAVING":
                material = Material.STRING;
                displayName = "&5&lThread Weaving";
                description = "&7Thread essences";
                break;
            case "SCIENTIST":
                material = Material.BREWING_STAND;
                displayName = "&9&lScientist";
                description = "&7Research materials";
                break;
            case "DEMON_TOWER":
                material = Material.NETHER_BRICK;
                displayName = "&c&lDemon Tower";
                description = "&7Demon Tower drops";
                break;
            default:
                material = Material.CHEST;
                displayName = "&f&l" + category;
                description = "&7" + category + " items";
                break;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', description));
        lore.add("");
        lore.add(ChatColor.YELLOW + "Click to open category");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public void openCategory(String category) {
        this.inCategorySelection = false;
        Integer startPage = this.categoryStartPage.get(category);
        if (startPage != null) {
            this.openPage(startPage);
        } else {
            this.openPage(1);
        }
    }

    public void openPage(int pageNumber) {
        this.inCategorySelection = false;
        this.page = pageNumber;
        this.inventory = Bukkit.createInventory(null, 54, "Ingredient Pouch - Page " + this.page);
        this.loadItems();
        this.player.openInventory(this.inventory);
    }

    public boolean isInCategorySelection() {
        return this.inCategorySelection;
    }

    public String getCategoryBySlot(int slot) {
        return this.categorySlotMap.get(slot);
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
                    //this.plugin.getLogger().warning("Item with ID '" + itemId + "' not found in ItemManager.");
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
               // this.plugin.getLogger().warning("Item with ID '" + itemId + "' not found in ItemManager.");
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

        ItemStack backToCategories = new ItemStack(Material.BARRIER);
        ItemMeta backMeta = backToCategories.getItemMeta();
        backMeta.setDisplayName(ChatColor.RED + "Back to Categories");
        backToCategories.setItemMeta(backMeta);

        if (this.page > 1) {
            this.inventory.setItem(45, previousPage);
        }

        // Back to categories button in the middle
        this.inventory.setItem(49, backToCategories);

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

}
