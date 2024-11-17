package com.maks.ingredientpouchplugin;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.meta.Repairable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PouchListener implements Listener {

    private final IngredientPouchPlugin plugin;
    private final Map<UUID, String> withdrawItemMap = new HashMap<>();
    private final Map<UUID, Inventory> anvilInventories = new HashMap<>();

    public PouchListener(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        String title = event.getView().getTitle();
        Player player = (Player) event.getWhoClicked();
        PouchGUI gui = plugin.getPouchGuis().get(player.getUniqueId());

        if (title.startsWith("Ingredient Pouch")) {
            event.setCancelled(true);

            if (event.getClickedInventory() == null || event.getClickedInventory().getType() == InventoryType.PLAYER) {
                return;
            }

            int clickedSlot = event.getSlot();

            // Obsługa przycisków nawigacji
            if (clickedSlot == 45) {
                if (gui != null && gui.getCurrentPage() > 1) {
                    gui.openPage(gui.getCurrentPage() - 1);
                }
                return;
            }

            if (clickedSlot == 53) {
                if (gui != null && gui.getCurrentPage() < gui.getMaxPage()) {
                    gui.openPage(gui.getCurrentPage() + 1);
                }
                return;
            }

            String itemId = gui.getItemIdBySlot(clickedSlot);

            if (itemId == null) {
                return; // Kliknięto na puste miejsce lub tło
            }

            // Teraz możemy kontynuować obsługę depozytu lub wypłaty
            if (event.isLeftClick()) {
                // Depozyt przedmiotu
                depositItem(player, itemId);
            } else if (event.isRightClick()) {
                if (event.isShiftClick()) {
                    // Otwórz GUI kowadła do wpisania ilości
                    openAnvilGUI(player, itemId);
                } else {
                    // Wycofaj 1 przedmiot bez odświeżania GUI
                    withdrawItem(player, itemId, 1, false);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        // Jeśli gracz zamknął GUI kowadła, usuwamy go z mapy
        if (anvilInventories.containsKey(event.getPlayer().getUniqueId())) {
            anvilInventories.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        Inventory inv = event.getInventory();
        Player player = (Player) event.getViewers().get(0);

        if (anvilInventories.containsKey(player.getUniqueId()) && inv.equals(anvilInventories.get(player.getUniqueId()))) {
            event.setResult(new ItemStack(Material.PAPER));
        }
    }

    @EventHandler
    public void onAnvilClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getType() == InventoryType.ANVIL) {
            Player player = (Player) event.getWhoClicked();

            if (anvilInventories.containsKey(player.getUniqueId())) {
                event.setCancelled(true);

                if (event.getSlot() == 2) { // Slot wyniku
                    String itemId = withdrawItemMap.get(player.getUniqueId());

                    ItemStack inputItem = event.getClickedInventory().getItem(0);
                    if (inputItem == null || !inputItem.hasItemMeta()) {
                        player.sendMessage(ChatColor.RED + "Please enter a number.");
                        return;
                    }

                    String amountStr = inputItem.getItemMeta().getDisplayName();
                    int withdrawAmount;
                    try {
                        withdrawAmount = Integer.parseInt(amountStr);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Invalid number.");
                        return;
                    }

                    withdrawItem(player, itemId, withdrawAmount, true);
                    player.closeInventory();
                }
            }
        }
    }

    private void openAnvilGUI(Player player, String itemId) {
        Inventory anvil = Bukkit.createInventory(player, InventoryType.ANVIL, "Enter amount to withdraw");

        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta meta = paper.getItemMeta();
        meta.setDisplayName("Enter amount here");
        paper.setItemMeta(meta);

        anvil.setItem(0, paper);

        withdrawItemMap.put(player.getUniqueId(), itemId);
        anvilInventories.put(player.getUniqueId(), anvil);

        player.openInventory(anvil);
    }

    private void withdrawItem(Player player, String itemId, int withdrawAmount, boolean showMessage) {
        if (withdrawAmount <= 0) {
            player.sendMessage(ChatColor.RED + "You must withdraw at least 1 item.");
            return;
        }

        int currentQuantity = getCurrentQuantity(player.getUniqueId().toString(), itemId);

        if (currentQuantity < withdrawAmount) {
            player.sendMessage(ChatColor.RED + "You don't have enough items to withdraw.");
            return;
        }

        ItemStack originalItem = plugin.getItemManager().getItem(itemId);
        if (originalItem == null) {
            player.sendMessage(ChatColor.RED + "Error: Item not found.");
            plugin.getLogger().warning("Item with ID '" + itemId + "' not found in ItemManager.");
            return;
        }

        int maxStackSize = originalItem.getMaxStackSize();
        List<ItemStack> itemsToWithdraw = new ArrayList<>();

        int amountLeft = withdrawAmount;
        while (amountLeft > 0) {
            int stackAmount = Math.min(maxStackSize, amountLeft);
            ItemStack itemStack = originalItem.clone();
            itemStack.setAmount(stackAmount);
            itemsToWithdraw.add(itemStack);
            amountLeft -= stackAmount;
        }

        // Try to add items to player's inventory
        Map<Integer, ItemStack> leftover = player.getInventory().addItem(itemsToWithdraw.toArray(new ItemStack[0]));

        if (!leftover.isEmpty()) {
            player.sendMessage(ChatColor.RED + "You don't have enough inventory space.");
            return;
        }

        // Update database
        updatePlayerQuantity(player.getUniqueId().toString(), itemId, -withdrawAmount);

        if (showMessage) {
            player.sendMessage(ChatColor.GREEN + "You have withdrawn " + withdrawAmount + " " + originalItem.getItemMeta().getDisplayName() + ".");
        }
    }

    private void depositItem(Player player, String itemId) {
        ItemStack itemToDeposit = plugin.getItemManager().getItem(itemId);

        if (itemToDeposit == null) {
            player.sendMessage(ChatColor.RED + "Error: Item not found.");
            plugin.getLogger().warning("Item with ID '" + itemId + "' not found in ItemManager.");
            return;
        }

        int amountInInventory = countItemsInInventory(player, itemToDeposit);

        if (amountInInventory == 0) {
            player.sendMessage(ChatColor.RED + "You don't have any " + itemToDeposit.getItemMeta().getDisplayName() + " to deposit.");
            return;
        }

        // Remove items from player's inventory
        removeItemsFromInventory(player, itemToDeposit, amountInInventory);

        // Update database
        updatePlayerQuantity(player.getUniqueId().toString(), itemId, amountInInventory);

        player.sendMessage(ChatColor.GREEN + "You have deposited " + amountInInventory + " " + itemToDeposit.getItemMeta().getDisplayName() + ".");
    }

    private void updatePlayerQuantity(String playerUUID, String itemId, int amount) {
        String sql = "INSERT INTO player_pouch (player_uuid, item_id, quantity) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantity = quantity + ?";

        try (PreparedStatement stmt = plugin.getDatabaseManager().getConnection().prepareStatement(sql)) {
            stmt.setString(1, playerUUID);
            stmt.setString(2, itemId);
            stmt.setInt(3, amount > 0 ? amount : 0);
            stmt.setInt(4, amount);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getCurrentQuantity(String playerUUID, String itemId) {
        String sql = "SELECT quantity FROM player_pouch WHERE player_uuid = ? AND item_id = ?";

        try (PreparedStatement stmt = plugin.getDatabaseManager().getConnection().prepareStatement(sql)) {
            stmt.setString(1, playerUUID);
            stmt.setString(2, itemId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private boolean areItemsSimilar(ItemStack item1, ItemStack item2) {
        if (item1 == null || item2 == null) {
            return false;
        }

        if (item1.getType() != item2.getType()) {
            return false;
        }

        ItemMeta meta1 = item1.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();

        if (meta1 == null || meta2 == null) {
            return false;
        }

        // Porównaj nazwę wyświetlaną i NBT (jeśli używasz NBT)
        if (meta1.hasDisplayName() && meta2.hasDisplayName()) {
            return meta1.getDisplayName().equals(meta2.getDisplayName());
        }

        return false;
    }

    private int countItemsInInventory(Player player, ItemStack item) {
        int count = 0;

        for (ItemStack invItem : player.getInventory().getContents()) {
            if (areItemsSimilar(invItem, item)) {
                count += invItem.getAmount();
            }
        }

        return count;
    }

    private void removeItemsFromInventory(Player player, ItemStack item, int amount) {
        int remaining = amount;

        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack invItem = player.getInventory().getItem(i);

            if (areItemsSimilar(invItem, item)) {
                int invAmount = invItem.getAmount();

                if (invAmount <= remaining) {
                    player.getInventory().setItem(i, null);
                    remaining -= invAmount;
                } else {
                    invItem.setAmount(invAmount - remaining);
                    remaining = 0;
                }

                if (remaining == 0) {
                    break;
                }
            }
        }
    }
}
