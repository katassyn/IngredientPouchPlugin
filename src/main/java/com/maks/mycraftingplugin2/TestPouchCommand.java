package com.maks.mycraftingplugin2;

import com.maks.mycraftingplugin2.integration.PouchIntegrationHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Command to test the IngredientPouch integration
 * Usage: /testpouch check - Check the item in your hand
 *        /testpouch inventory - Check all items in your inventory
 */
public class TestPouchCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            showHelp(player);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "check":
                checkHandItem(player);
                break;
            case "inventory":
                checkInventory(player);
                break;
            default:
                showHelp(player);
                break;
        }

        return true;
    }

    private void showHelp(Player player) {
        player.sendMessage(ChatColor.YELLOW + "=== Pouch Integration Test ===");
        player.sendMessage(ChatColor.WHITE + "/testpouch check " + ChatColor.GRAY + "- Check the item in your hand");
        player.sendMessage(ChatColor.WHITE + "/testpouch inventory " + ChatColor.GRAY + "- Check all items in your inventory");
    }

    private void checkHandItem(Player player) {
        ItemStack handItem = player.getInventory().getItemInMainHand();

        if (handItem == null || handItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You must hold an item in your hand.");
            return;
        }

        // Use the PouchIntegrationHelper to get debug info
        PouchIntegrationHelper.sendDebugInfo(player, handItem);
    }

    private void checkInventory(Player player) {
        player.sendMessage(ChatColor.YELLOW + "=== Inventory Items ===");
        
        boolean foundItems = false;
        
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && !item.getType().isAir()) {
                String name = getItemName(item);
                int total = PouchIntegrationHelper.getTotalItemAmount(player, item);
                
                player.sendMessage(ChatColor.WHITE + name + ": " + ChatColor.GREEN + total);
                foundItems = true;
            }
        }
        
        if (!foundItems) {
            player.sendMessage(ChatColor.RED + "No items found in your inventory.");
        }
        
        player.sendMessage(ChatColor.YELLOW + "API Available: " + 
                          (PouchIntegrationHelper.isAPIAvailable() ? 
                           ChatColor.GREEN + "Yes" : ChatColor.RED + "No"));
    }
    
    private String getItemName(ItemStack item) {
        if (item.getItemMeta() != null && item.getItemMeta().hasDisplayName()) {
            return ChatColor.stripColor(item.getItemMeta().getDisplayName());
        }
        return item.getType().toString();
    }
}