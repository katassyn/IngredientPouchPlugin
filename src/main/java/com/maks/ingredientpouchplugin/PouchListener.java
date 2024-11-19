//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.maks.ingredientpouchplugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PouchListener implements Listener {
    private final IngredientPouchPlugin plugin;
    private final Map<UUID, String> pendingWithdrawals = new HashMap();

    public PouchListener(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        if (event.getView().getTitle().startsWith("Ingredient Pouch")) {
            event.setCancelled(true);
            PouchGUI gui = (PouchGUI)this.plugin.getPouchGuis().get(player.getUniqueId());
            if (gui == null) {
                return;
            }

            if (clickedInventory == event.getView().getTopInventory()) {
                int clickedSlot = event.getSlot();
                if (clickedSlot == 45) {
                    if (gui.getCurrentPage() > 1) {
                        gui.openPage(gui.getCurrentPage() - 1);
                    }

                    return;
                }

                if (clickedSlot == 53) {
                    if (gui.getCurrentPage() < gui.getMaxPage()) {
                        gui.openPage(gui.getCurrentPage() + 1);
                    }

                    return;
                }

                String itemId = gui.getItemIdBySlot(clickedSlot);
                if (itemId == null) {
                    return;
                }

                if (event.isLeftClick() && !event.isShiftClick()) {
                    this.depositItem(player, itemId);
                } else if (event.isRightClick()) {
                    if (event.isShiftClick()) {
                        this.promptWithdrawAmount(player, itemId);
                        player.closeInventory();
                    } else {
                        this.withdrawItem(player, itemId, 1, false);
                    }
                }
            } else if (clickedInventory == player.getInventory() && event.isShiftClick()) {
                event.setCancelled(true);
            }
        }

    }

    private void promptWithdrawAmount(Player player, String itemId) {
        player.sendMessage(ChatColor.GREEN + "Please enter the amount you wish to withdraw. Type 'cancel' to abort.");
        this.pendingWithdrawals.put(player.getUniqueId(), itemId);
        this.plugin.getChatListener().addPlayer(player.getUniqueId());
    }

    public void processChatInput(Player player, String message) {
        UUID playerId = player.getUniqueId();
        if (this.pendingWithdrawals.containsKey(playerId)) {
            String itemId = (String)this.pendingWithdrawals.get(playerId);
            if (message.equalsIgnoreCase("cancel")) {
                player.sendMessage(ChatColor.RED + "Withdrawal canceled.");
                this.pendingWithdrawals.remove(playerId);
            } else {
                int withdrawAmount;
                try {
                    withdrawAmount = Integer.parseInt(message);
                } catch (NumberFormatException var7) {
                    player.sendMessage(ChatColor.RED + "Invalid number. Please enter a valid integer or type 'cancel' to abort.");
                    return;
                }

                this.pendingWithdrawals.remove(playerId);
                this.withdrawItem(player, itemId, withdrawAmount, true);
            }
        }
    }

    private void withdrawItem(Player player, String itemId, int withdrawAmount, boolean showMessage) {
        if (withdrawAmount <= 0) {
            player.sendMessage(ChatColor.RED + "You must withdraw at least 1 item.");
        } else {
            int currentQuantity = this.getCurrentQuantity(player.getUniqueId().toString(), itemId);
            if (currentQuantity < withdrawAmount) {
                player.sendMessage(ChatColor.RED + "You don't have enough items to withdraw.");
            } else {
                ItemStack originalItem = this.plugin.getItemManager().getItem(itemId);
                if (originalItem == null) {
                    player.sendMessage(ChatColor.RED + "Error: Item not found.");
                    this.plugin.getLogger().warning("Item with ID '" + itemId + "' not found in ItemManager.");
                } else {
                    this.plugin.getLogger().info("Original item lore before withdrawal: " + originalItem.getItemMeta().getLore());
                    int maxStackSize = originalItem.getMaxStackSize();
                    List<ItemStack> itemsToWithdraw = new ArrayList();

                    int stackAmount;
                    for(int amountLeft = withdrawAmount; amountLeft > 0; amountLeft -= stackAmount) {
                        stackAmount = Math.min(maxStackSize, amountLeft);
                        ItemStack itemStack = originalItem.clone();
                        itemStack.setAmount(stackAmount);
                        this.plugin.getLogger().info("Withdrawing item with lore: " + itemStack.getItemMeta().getLore());
                        itemsToWithdraw.add(itemStack);
                    }

                    Map<Integer, ItemStack> leftover = player.getInventory().addItem((ItemStack[])itemsToWithdraw.toArray(new ItemStack[0]));
                    if (!leftover.isEmpty()) {
                        player.sendMessage(ChatColor.RED + "You don't have enough inventory space.");
                    } else {
                        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                            this.updatePlayerQuantity(player.getUniqueId().toString(), itemId, -withdrawAmount);
                            Bukkit.getScheduler().runTask(this.plugin, () -> {
                                PouchGUI gui = (PouchGUI)this.plugin.getPouchGuis().get(player.getUniqueId());
                                if (gui != null) {
                                    gui.updateSingleItem(itemId);
                                }

                            });
                        });
                        if (showMessage) {
                            player.sendMessage(ChatColor.GREEN + "You have withdrawn " + withdrawAmount + " " + originalItem.getItemMeta().getDisplayName() + ".");
                        }

                    }
                }
            }
        }
    }

    private void depositItem(Player player, String itemId) {
        ItemStack itemToDeposit = this.plugin.getItemManager().getItem(itemId);
        if (itemToDeposit == null) {
            player.sendMessage(ChatColor.RED + "Error: Item not found.");
            this.plugin.getLogger().warning("Item with ID '" + itemId + "' not found in ItemManager.");
        } else {
            int amountInInventory = this.countItemsInInventory(player, itemToDeposit);
            if (amountInInventory == 0) {
                player.sendMessage(ChatColor.RED + "You don't have any " + itemToDeposit.getItemMeta().getDisplayName() + " to deposit.");
            } else {
                this.removeItemsFromInventory(player, itemToDeposit, amountInInventory);
                Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                    this.updatePlayerQuantity(player.getUniqueId().toString(), itemId, amountInInventory);
                    Bukkit.getScheduler().runTask(this.plugin, () -> {
                        PouchGUI gui = (PouchGUI)this.plugin.getPouchGuis().get(player.getUniqueId());
                        if (gui != null) {
                            gui.updateSingleItem(itemId);
                        }

                    });
                });
                player.sendMessage(ChatColor.GREEN + "You have deposited " + amountInInventory + " " + itemToDeposit.getItemMeta().getDisplayName() + ".");
            }
        }
    }

    private int getCurrentQuantity(String playerUUID, String itemId) {
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

    private void updatePlayerQuantity(String playerUUID, String itemId, int amount) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            String sql = "INSERT INTO player_pouch (player_uuid, item_id, quantity) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = GREATEST(quantity + ?, 0)";

            try {
                PreparedStatement stmt = this.plugin.getDatabaseManager().getConnection().prepareStatement(sql);

                try {
                    stmt.setString(1, playerUUID);
                    stmt.setString(2, itemId);
                    stmt.setInt(3, amount > 0 ? amount : 0);
                    stmt.setInt(4, amount);
                    stmt.executeUpdate();
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

        });
    }

    private int countItemsInInventory(Player player, ItemStack itemToMatch) {
        int count = 0;
        ItemStack[] var4 = player.getInventory().getContents();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            ItemStack invItem = var4[var6];
            if (this.areItemsSimilar(invItem, itemToMatch)) {
                count += invItem.getAmount();
            }
        }

        return count;
    }

    private void removeItemsFromInventory(Player player, ItemStack itemToMatch, int amount) {
        int remaining = amount;

        for(int i = 0; i < player.getInventory().getSize(); ++i) {
            ItemStack invItem = player.getInventory().getItem(i);
            if (this.areItemsSimilar(invItem, itemToMatch)) {
                int invAmount = invItem.getAmount();
                if (invAmount <= remaining) {
                    player.getInventory().setItem(i, (ItemStack)null);
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

    private boolean areItemsSimilar(ItemStack item1, ItemStack item2) {
        if (item1 != null && item2 != null) {
            if (item1.getType() != item2.getType()) {
                return false;
            } else {
                ItemMeta meta1 = item1.getItemMeta();
                ItemMeta meta2 = item2.getItemMeta();
                if (meta1 != null && meta2 != null) {
                    String name1 = meta1.hasDisplayName() ? ChatColor.stripColor(meta1.getDisplayName()) : "";
                    String name2 = meta2.hasDisplayName() ? ChatColor.stripColor(meta2.getDisplayName()) : "";
                    if (!name1.equals(name2)) {
                        return false;
                    } else {
                        List<String> lore1 = meta1.hasLore() ? meta1.getLore() : new ArrayList();
                        List<String> lore2 = meta2.hasLore() ? meta2.getLore() : new ArrayList();
                        if (!((List)lore2).isEmpty()) {
                            String lastLine = (String)((List)lore2).get(((List)lore2).size() - 1);
                            if (ChatColor.stripColor(lastLine).startsWith("You have:")) {
                                ((List)lore2).remove(((List)lore2).size() - 1);
                            }
                        }

                        List<String> strippedLore1 = new ArrayList();
                        Iterator var10 = ((List)lore1).iterator();

                        while(var10.hasNext()) {
                            String line = (String)var10.next();
                            strippedLore1.add(ChatColor.stripColor(line));
                        }

                        List<String> strippedLore2 = new ArrayList();
                        Iterator var15 = ((List)lore2).iterator();

                        while(var15.hasNext()) {
                            String line = (String)var15.next();
                            strippedLore2.add(ChatColor.stripColor(line));
                        }

                        if (!strippedLore1.equals(strippedLore2)) {
                            return false;
                        } else {
                            return meta1.getEnchants().equals(meta2.getEnchants());
                        }
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}
