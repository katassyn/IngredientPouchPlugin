package com.maks.ingredientpouchplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DebugPouchCommand implements CommandExecutor {
    private final IngredientPouchPlugin plugin;

    public DebugPouchCommand(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();

        sender.sendMessage(ChatColor.GOLD + "=== Debug: Items in your pouch ===");
        sender.sendMessage(ChatColor.YELLOW + "UUID: " + playerUUID);

        List<String> items = getAllPouchItems(playerUUID);

        if (items.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "No items found in database!");
        } else {
            sender.sendMessage(ChatColor.GREEN + "Found " + items.size() + " items:");
            for (String itemInfo : items) {
                sender.sendMessage(ChatColor.WHITE + "  " + itemInfo);
            }
        }

        // Check specifically for "joker"
        int jokerCount = getItemCount(playerUUID, "joker");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GOLD + "Specific check for 'joker' (lowercase):");
        sender.sendMessage(ChatColor.WHITE + "  Count: " + jokerCount);

        // Check for "Joker" with capital
        int jokerCapitalCount = getItemCount(playerUUID, "Joker");
        sender.sendMessage(ChatColor.GOLD + "Specific check for 'Joker' (capital):");
        sender.sendMessage(ChatColor.WHITE + "  Count: " + jokerCapitalCount);

        return true;
    }

    private List<String> getAllPouchItems(String playerUUID) {
        List<String> items = new ArrayList<>();
        String sql = "SELECT item_id, quantity FROM player_pouch WHERE player_uuid = ?";

        try (Connection conn = plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playerUUID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String itemId = rs.getString("item_id");
                    int quantity = rs.getInt("quantity");

                    // Check if item exists in ItemManager
                    boolean existsInManager = plugin.getItemManager().getItem(itemId) != null;
                    String status = existsInManager ? ChatColor.GREEN + "✓" : ChatColor.RED + "✗";

                    items.add(status + ChatColor.WHITE + " " + itemId + " = " + quantity);
                }
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Error querying pouch items: " + e.getMessage());
            e.printStackTrace();
        }

        return items;
    }

    private int getItemCount(String playerUUID, String itemId) {
        String sql = "SELECT quantity FROM player_pouch WHERE player_uuid = ? AND item_id = ?";

        try (Connection conn = plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playerUUID);
            stmt.setString(2, itemId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantity");
                }
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Error querying item count: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }
}
