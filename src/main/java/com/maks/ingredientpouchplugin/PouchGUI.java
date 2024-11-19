//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.maks.ingredientpouchplugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    private final Map<Integer, List<String>> pages = new HashMap();
    private final Map<Integer, String> slotItemMap = new HashMap();
    private static final int[] ITEM_SLOTS = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    public PouchGUI(IngredientPouchPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.groupItemsByCategory();
        this.maxPage = this.pages.size();
    }

    private void groupItemsByCategory() {
        Map<String, List<String>> categoryItems = new LinkedHashMap();
        Iterator var2 = this.plugin.getItemManager().getItemIds().iterator();

        while(var2.hasNext()) {
            String itemId = (String)var2.next();
            String category = this.plugin.getItemManager().getItemCategory(itemId);
            ((List)categoryItems.computeIfAbsent(category, (k) -> {
                return new ArrayList();
            })).add(itemId);
        }

        int pageIndex = 1;
        List<String> categories = new ArrayList(categoryItems.keySet());
        Collections.sort(categories);
        Iterator var12 = categories.iterator();

        while(var12.hasNext()) {
            String category = (String)var12.next();
            List<String> items = (List)categoryItems.get(category);
            Collections.sort(items);

            int toIndex;
            for(int fromIndex = 0; fromIndex < items.size(); fromIndex = toIndex) {
                toIndex = Math.min(fromIndex + ITEM_SLOTS.length, items.size());
                List<String> pageItems = items.subList(fromIndex, toIndex);
                this.pages.put(pageIndex++, new ArrayList(pageItems));
            }
        }

    }

    public void open() {
        this.openPage(this.page);
    }

    public void openPage(int pageNumber) {
        this.page = pageNumber;
        this.inventory = Bukkit.createInventory((InventoryHolder)null, 54, "Ingredient Pouch - Page " + this.page);
        this.loadItems();
        this.player.openInventory(this.inventory);
    }

    public void loadItems() {
        List<String> itemsOnPage = (List)this.pages.get(this.page);
        if (itemsOnPage != null) {
            Map<String, Integer> quantities = this.getPlayerQuantities(this.player.getUniqueId().toString());
            ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta fillerMeta = filler.getItemMeta();
            fillerMeta.setDisplayName(" ");
            filler.setItemMeta(fillerMeta);

            int slotIndex;
            for(slotIndex = 0; slotIndex < this.inventory.getSize(); ++slotIndex) {
                this.inventory.setItem(slotIndex, filler);
            }

            this.slotItemMap.clear();
            slotIndex = 0;
            Iterator var6 = itemsOnPage.iterator();

            while(var6.hasNext()) {
                String itemId = (String)var6.next();
                if (slotIndex >= ITEM_SLOTS.length) {
                    break;
                }

                int slot = ITEM_SLOTS[slotIndex];
                ItemStack originalItem = this.plugin.getItemManager().getItem(itemId);
                if (originalItem == null) {
                    this.plugin.getLogger().warning("Item with ID '" + itemId + "' not found in ItemManager.");
                } else {
                    ItemStack displayItem = originalItem.clone();
                    ItemMeta meta = displayItem.getItemMeta();
                    int totalQuantity = (Integer)quantities.getOrDefault(itemId, 0);
                    List<String> lore = meta.hasLore() ? new ArrayList(meta.getLore()) : new ArrayList();
                    String lastLine;
                    if (!lore.isEmpty()) {
                        lastLine = (String)lore.get(lore.size() - 1);
                        if (ChatColor.stripColor(lastLine).startsWith("You have:")) {
                            lore.remove(lore.size() - 1);
                        }
                    }

                    lastLine = ChatColor.YELLOW + "You have: " + totalQuantity;
                    lore.add(lastLine);
                    meta.setLore(lore);
                    displayItem.setItemMeta(meta);
                    this.inventory.setItem(slot, displayItem);
                    ++slotIndex;
                    this.slotItemMap.put(slot, itemId);
                }
            }

            this.addNavigationButtons();
        }
    }

    public void updateSingleItem(String itemId) {
        Integer slot = null;
        Iterator var3 = this.slotItemMap.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<Integer, String> entry = (Map.Entry)var3.next();
            if (((String)entry.getValue()).equals(itemId)) {
                slot = (Integer)entry.getKey();
                break;
            }
        }

        if (slot != null) {
            ItemStack originalItem = this.plugin.getItemManager().getItem(itemId);
            if (originalItem == null) {
                this.plugin.getLogger().warning("Item with ID '" + itemId + "' not found in ItemManager.");
            } else {
                ItemStack displayItem = originalItem.clone();
                ItemMeta meta = displayItem.getItemMeta();
                int totalQuantity = this.getPlayerQuantity(this.player.getUniqueId().toString(), itemId);
                List<String> lore = meta.hasLore() ? new ArrayList(meta.getLore()) : new ArrayList();
                String lastLine;
                if (!lore.isEmpty()) {
                    lastLine = (String)lore.get(lore.size() - 1);
                    if (ChatColor.stripColor(lastLine).startsWith("You have:")) {
                        lore.remove(lore.size() - 1);
                    }
                }

                lastLine = ChatColor.YELLOW + "You have: " + totalQuantity;
                lore.add(lastLine);
                meta.setLore(lore);
                displayItem.setItemMeta(meta);
                this.inventory.setItem(slot, displayItem);
                if (this.player.getOpenInventory().getTopInventory().equals(this.inventory)) {
                    this.player.updateInventory();
                }

            }
        }
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public String getItemIdBySlot(int slot) {
        return (String)this.slotItemMap.get(slot);
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
        Map<String, Integer> quantities = new HashMap();
        String sql = "SELECT item_id, quantity FROM player_pouch WHERE player_uuid = ?";

        try {
            PreparedStatement stmt = this.plugin.getDatabaseManager().getConnection().prepareStatement(sql);

            try {
                stmt.setString(1, playerUUID);
                ResultSet rs = stmt.executeQuery();

                while(rs.next()) {
                    String itemId = rs.getString("item_id");
                    int quantity = rs.getInt("quantity");
                    quantities.put(itemId, quantity);
                }
            } catch (Throwable var9) {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (Throwable var8) {
                        var9.addSuppressed(var8);
                    }
                }

                throw var9;
            }

            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException var10) {
            SQLException e = var10;
            e.printStackTrace();
        }

        return quantities;
    }

    private int getPlayerQuantity(String playerUUID, String itemId) {
        String sql = "SELECT quantity FROM player_pouch WHERE player_uuid = ? AND item_id = ?";

        try {
            PreparedStatement stmt = this.plugin.getDatabaseManager().getConnection().prepareStatement(sql);

            int var6;
            label54: {
                try {
                    stmt.setString(1, playerUUID);
                    stmt.setString(2, itemId);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        var6 = rs.getInt("quantity");
                        break label54;
                    }
                } catch (Throwable var8) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Throwable var7) {
                            var8.addSuppressed(var7);
                        }
                    }

                    throw var8;
                }

                if (stmt != null) {
                    stmt.close();
                }

                return 0;
            }

            if (stmt != null) {
                stmt.close();
            }

            return var6;
        } catch (SQLException var9) {
            SQLException e = var9;
            e.printStackTrace();
            return 0;
        }
    }
}
