//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.maks.ingredientpouchplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IngredientPouchCommand implements CommandExecutor {
    private final IngredientPouchPlugin plugin;

    public IngredientPouchCommand(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        } else {
            Player player = (Player)sender;
            if (args.length == 0) {
                PouchGUI gui = new PouchGUI(this.plugin, player);
                this.plugin.getPouchGuis().put(player.getUniqueId(), gui);
                gui.open();
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /ingredient_pouch");
                return true;
            }
        }
    }
}
