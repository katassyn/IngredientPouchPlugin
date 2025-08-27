package com.maks.ingredientpouchplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DropNotificationCommand implements CommandExecutor {
    private final IngredientPouchPlugin plugin;

    public DropNotificationCommand(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0 || args.length == 1 && args[0].equalsIgnoreCase("help")) {
            showHelp(player);
            return true;
        }

        if (args.length >= 2 && args[0].equalsIgnoreCase("show")) {
            String mode = args[1].toLowerCase();
            
            switch (mode) {
                case "all":
                    plugin.getNotificationManager().setNotificationMode(player, NotificationMode.ALL);
                    player.sendMessage(ChatColor.GREEN + "Drop notifications set to: " + ChatColor.YELLOW + "ALL" + 
                                     ChatColor.GREEN + " (showing all items)");
                    break;
                    
                case "best":
                    plugin.getNotificationManager().setNotificationMode(player, NotificationMode.BEST);
                    player.sendMessage(ChatColor.GREEN + "Drop notifications set to: " + ChatColor.GOLD + "BEST" + 
                                     ChatColor.GREEN + " (showing rare/currency/unlock/upgrade items)");
                    break;
                    
                case "off":
                    plugin.getNotificationManager().setNotificationMode(player, NotificationMode.OFF);
                    player.sendMessage(ChatColor.GREEN + "Drop notifications " + ChatColor.RED + "DISABLED");
                    break;
                    
                default:
                    player.sendMessage(ChatColor.RED + "Unknown mode. Use: all, best, or off");
                    return true;
            }
            
            // Save to database
            plugin.getNotificationManager().savePlayerPreference(player);
            return true;
        }
        
        // Show current status
        if (args.length == 1 && args[0].equalsIgnoreCase("status")) {
            NotificationMode mode = plugin.getNotificationManager().getNotificationMode(player);
            String modeText = mode == NotificationMode.ALL ? ChatColor.YELLOW + "ALL" :
                            mode == NotificationMode.BEST ? ChatColor.GOLD + "BEST" :
                            ChatColor.RED + "OFF";
            player.sendMessage(ChatColor.GREEN + "Current drop notification mode: " + modeText);
            return true;
        }

        showHelp(player);
        return true;
    }

    private void showHelp(Player player) {
        player.sendMessage(ChatColor.DARK_GREEN + "=== Drop Notifications Help ===");
        player.sendMessage(ChatColor.GREEN + "/drop show all" + ChatColor.GRAY + " - Show notifications for all items");
        player.sendMessage(ChatColor.GREEN + "/drop show best" + ChatColor.GRAY + " - Show rare/legendary/currency/unlock/upgrade items");
        player.sendMessage(ChatColor.GREEN + "/drop show off" + ChatColor.GRAY + " - Disable all notifications");
        player.sendMessage(ChatColor.GREEN + "/drop status" + ChatColor.GRAY + " - Check current notification mode");
    }
}