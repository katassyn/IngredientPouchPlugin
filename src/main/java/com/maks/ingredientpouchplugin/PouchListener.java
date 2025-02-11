package com.maks.ingredientpouchplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PouchListener implements Listener {

    private final IngredientPouchPlugin plugin;
    private final Map<UUID, String> pendingWithdrawals = new HashMap<>();

    public PouchListener(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Sprawdzamy, czy gracz rzeczywiście klika w GUI "Ingredient Pouch"
        if (event.getView().getTitle().startsWith("Ingredient Pouch")) {
            Player player = (Player) event.getWhoClicked();
            // Anuluj standardowe przenoszenie przedmiotów
            event.setCancelled(true);

            Inventory clickedInventory = event.getClickedInventory();
            // Dla bezpieczeństwa – jeżeli w ogóle nie kliknięto w nic (np. poza GUI)
            if (clickedInventory == null) return;

            // Pobieramy aktualne GUI sakwy z mapy
            PouchGUI gui = this.plugin.getPouchGuis().get(player.getUniqueId());
            if (gui == null) {
                return;
            }

            // --- [A] NAPRAWA SHIFT+PPM: --
            // Jeśli gracz kliknął SHIFT+PPM w górnym GUI, usuwamy "ghost item" z eventu
            if (event.isShiftClick() && event.isRightClick()) {
                // Zapobiegamy duplikowaniu i przeniesieniu przedmiotu do EQ
                event.setCurrentItem(null);

                // Wywołaj okno do wpisania liczby
                String itemId = gui.getItemIdBySlot(event.getSlot());
                if (itemId != null) {
                    this.promptWithdrawAmount(player, itemId);
                    // Możesz zdecydować, czy chcesz zamykać GUI czy nie
                    player.closeInventory();
                }
                return;
            }
            // --- [A] KONIEC fixu SHIFT+PPM ---

            // Sprawdzamy, czy kliknięto w górny inventory (nasze GUI)
            if (clickedInventory == event.getView().getTopInventory()) {
                int clickedSlot = event.getSlot();
                // Obsługa przycisku "poprzednia strona"
                if (clickedSlot == 45) {
                    if (gui.getCurrentPage() > 1) {
                        gui.openPage(gui.getCurrentPage() - 1);
                    }
                    return;
                }
                // Obsługa przycisku "następna strona"
                if (clickedSlot == 53) {
                    if (gui.getCurrentPage() < gui.getMaxPage()) {
                        gui.openPage(gui.getCurrentPage() + 1);
                    }
                    return;
                }

                // Pobierz ID itemu (z mapy slotów)
                String itemId = gui.getItemIdBySlot(clickedSlot);
                if (itemId == null) {
                    return;
                }

                // Klik lewym = depozyt do sakwy (wszystkich itemów z eq)
                if (event.isLeftClick() && !event.isShiftClick()) {
                    this.depositItem(player, itemId);
                }
                // Klik prawym = wyjmij 1 / lub SHIFT+PPM - obsłużone wyżej
                else if (event.isRightClick()) {
                    // SHIFT+PPM obsłużyliśmy wyżej, więc tutaj tylko normalne PPM
                    this.withdrawItem(player, itemId, 1, false);
                }
            }
            // Jeśli kliknięto w inventory gracza (dolny) z wciśniętym SHIFT – blokujemy i tak
            else if (clickedInventory == player.getInventory() && event.isShiftClick()) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * Wyświetla graczowi prośbę o wpisanie ilości do wyjęcia z sakwy.
     */
    private void promptWithdrawAmount(Player player, String itemId) {
        player.sendMessage(ChatColor.GREEN + "Please enter the amount you wish to withdraw. Type 'cancel' to abort.");
        this.pendingWithdrawals.put(player.getUniqueId(), itemId);
        this.plugin.getChatListener().addPlayer(player.getUniqueId());
    }

    /**
     * Metoda wywoływana przez ChatListener, kiedy gracz wpisze liczbę/cancel.
     */
    public void processChatInput(Player player, String message) {
        UUID playerId = player.getUniqueId();
        if (this.pendingWithdrawals.containsKey(playerId)) {
            String itemId = this.pendingWithdrawals.get(playerId);
            if (message.equalsIgnoreCase("cancel")) {
                player.sendMessage(ChatColor.RED + "Withdrawal canceled.");
                this.pendingWithdrawals.remove(playerId);
            } else {
                int withdrawAmount;
                try {
                    withdrawAmount = Integer.parseInt(message);
                } catch (NumberFormatException ex) {
                    player.sendMessage(ChatColor.RED + "Invalid number. Please enter a valid integer or type 'cancel' to abort.");
                    return;
                }
                this.pendingWithdrawals.remove(playerId);
                this.withdrawItem(player, itemId, withdrawAmount, true);
            }
        }
    }

    /**
     * Wyjmowanie itemów z sakwy: "withdrawAmount" sztuk.
     * showMessage = czy wyświetlać tekst o tym fakcie.
     */
    private void withdrawItem(Player player, String itemId, int withdrawAmount, boolean showMessage) {
        if (withdrawAmount <= 0) {
            player.sendMessage(ChatColor.RED + "You must withdraw at least 1 item.");
            return;
        }
        int currentQuantity = this.getCurrentQuantity(player.getUniqueId().toString(), itemId);
        if (currentQuantity < withdrawAmount) {
            player.sendMessage(ChatColor.RED + "You don't have enough items to withdraw.");
            return;
        }
        ItemStack originalItem = this.plugin.getItemManager().getItem(itemId);
        if (originalItem == null) {
            player.sendMessage(ChatColor.RED + "Error: Item not found.");
            this.plugin.getLogger().warning("Item with ID '" + itemId + "' not found in ItemManager.");
            return;
        }

        // Ile maksymalnie w jednym stacku
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

        // Spróbuj włożyć do EQ gracza
        Map<Integer, ItemStack> leftover = player.getInventory().addItem(itemsToWithdraw.toArray(new ItemStack[0]));
        if (!leftover.isEmpty()) {
            player.sendMessage(ChatColor.RED + "You don't have enough inventory space.");
            return;
        }

        // Aktualizujemy bazę i GUI
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            // Zmniejszamy w bazie
            this.updatePlayerQuantity(player.getUniqueId().toString(), itemId, -withdrawAmount);

            // Następnie wracamy do wątku głównego i odświeżamy GUI
            Bukkit.getScheduler().runTask(this.plugin, () -> {
                PouchGUI gui = this.plugin.getPouchGuis().get(player.getUniqueId());
                if (gui != null) {
                    // Opcja 1: odśwież cały layout
                    gui.loadItems();
                    player.updateInventory();

                    // Opcja 2: tylko jeden slot (zamiast loadItems()):
                    // gui.updateSingleItem(itemId);
                }
            });
        });

        if (showMessage) {
            player.sendMessage(ChatColor.GREEN + "You have withdrawn " + withdrawAmount
                    + " " + originalItem.getItemMeta().getDisplayName() + ".");
        }
    }

    /**
     * Wkładanie wszystkich itemów o danym itemId z EQ do sakwy.
     */
    private void depositItem(Player player, String itemId) {
        ItemStack itemToDeposit = this.plugin.getItemManager().getItem(itemId);
        if (itemToDeposit == null) {
            player.sendMessage(ChatColor.RED + "Error: Item not found.");
            this.plugin.getLogger().warning("Item with ID '" + itemId + "' not found in ItemManager.");
            return;
        }
        int amountInInventory = this.countItemsInInventory(player, itemToDeposit);
        if (amountInInventory == 0) {
            player.sendMessage(ChatColor.RED + "You don't have any "
                    + itemToDeposit.getItemMeta().getDisplayName() + " to deposit.");
            return;
        }
        // Usuwamy te itemy z EQ (dowolne stacki, dopóki nie osiągniemy "amountInInventory")
        this.removeItemsFromInventory(player, itemToDeposit, amountInInventory);

        // Zaktualizuj DB i GUI
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            this.updatePlayerQuantity(player.getUniqueId().toString(), itemId, amountInInventory);

            Bukkit.getScheduler().runTask(this.plugin, () -> {
                PouchGUI gui = this.plugin.getPouchGuis().get(player.getUniqueId());
                if (gui != null) {
                    // Odśwież całą listę
                    gui.loadItems();
                    player.updateInventory();
                    // Lub jeśli wolisz 1 slot
                    // gui.updateSingleItem(itemId);
                }
            });
        });

        player.sendMessage(ChatColor.GREEN + "You have deposited "
                + amountInInventory + " " + itemToDeposit.getItemMeta().getDisplayName() + ".");
    }

    /**
     * Pobiera bieżącą ilość itemId w sakwie (z bazy) dla danego gracza.
     */
    private int getCurrentQuantity(String playerUUID, String itemId) {
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
     * Zwiększa/zmniejsza ilość itemId w sakwie o "amount" (może być ujemne).
     */
    private void updatePlayerQuantity(String playerUUID, String itemId, int amount) {
        String sql = "INSERT INTO player_pouch (player_uuid, item_id, quantity) VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE quantity = GREATEST(quantity + ?, 0)";
        try (Connection conn = this.plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, playerUUID);
            stmt.setString(2, itemId);
            stmt.setInt(3, Math.max(amount, 0));
            stmt.setInt(4, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Zlicza ilość itemów w EQ gracza podobnych do "itemToMatch".
     */
    private int countItemsInInventory(Player player, ItemStack itemToMatch) {
        int count = 0;
        for (ItemStack invItem : player.getInventory().getContents()) {
            if (this.areItemsSimilar(invItem, itemToMatch)) {
                count += invItem.getAmount();
            }
        }
        return count;
    }

    /**
     * Usuwa "amount" itemów z EQ gracza.
     */
    private void removeItemsFromInventory(Player player, ItemStack itemToMatch, int amount) {
        int remaining = amount;
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack invItem = player.getInventory().getItem(i);
            if (this.areItemsSimilar(invItem, itemToMatch)) {
                int invAmount = invItem.getAmount();
                if (invAmount <= remaining) {
                    // Wyzeruj slot
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

    /**
     * Porównuje, czy invItem i itemToMatch są identyczne (materiał, nazwa, lore).
     * Usuwa też ewentualną linię "You have:" z lore2 do porównania.
     */
    private boolean areItemsSimilar(ItemStack item1, ItemStack item2) {
        if (item1 == null || item2 == null) return false;
        if (item1.getType() != item2.getType()) return false;

        ItemMeta meta1 = item1.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();
        if (meta1 == null || meta2 == null) return false;

        String name1 = meta1.hasDisplayName() ? ChatColor.stripColor(meta1.getDisplayName()) : "";
        String name2 = meta2.hasDisplayName() ? ChatColor.stripColor(meta2.getDisplayName()) : "";
        if (!name1.equals(name2)) return false;

        List<String> lore1 = meta1.hasLore() ? meta1.getLore() : new ArrayList<>();
        List<String> lore2 = meta2.hasLore() ? meta2.getLore() : new ArrayList<>();

        // Usunięcie ostatniej linii "You have: X" z lore2, jeżeli istnieje
        if (!lore2.isEmpty()) {
            String lastLine = lore2.get(lore2.size() - 1);
            if (ChatColor.stripColor(lastLine).startsWith("You have:")) {
                lore2.remove(lore2.size() - 1);
            }
        }

        // Porównaj lore, usuwając kody kolorów
        List<String> strippedLore1 = new ArrayList<>();
        for (String line : lore1) {
            strippedLore1.add(ChatColor.stripColor(line));
        }
        List<String> strippedLore2 = new ArrayList<>();
        for (String line : lore2) {
            strippedLore2.add(ChatColor.stripColor(line));
        }
        if (!strippedLore1.equals(strippedLore2)) {
            return false;
        }

        // Porównaj enchanty
        if (!meta1.getEnchants().equals(meta2.getEnchants())) {
            return false;
        }

        return true;
    }
}
