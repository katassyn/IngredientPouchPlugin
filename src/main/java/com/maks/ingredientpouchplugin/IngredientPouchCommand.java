package com.maks.ingredientpouchplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Upewnij się, że masz poprawne importy do innych klas

public class IngredientPouchCommand implements CommandExecutor {

    private final IngredientPouchPlugin plugin;

    public IngredientPouchCommand(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Sprawdź, czy nadawcą jest gracz
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        // Sprawdź, czy nie podano żadnych argumentów
        if (args.length == 0) {
            // Otwórz GUI sakiewki dla gracza
            PouchGUI gui = new PouchGUI(plugin, player);
            plugin.getPouchGuis().put(player.getUniqueId(), gui);
            gui.open();
            return true;
        } else {
            // Jeśli są jakieś argumenty, możesz obsłużyć je tutaj
            // Jeśli nie obsługujesz żadnych argumentów, wyświetl informację o użyciu
            player.sendMessage(ChatColor.RED + "Usage: /ingredient_pouch");
            return true;
        }
    }
}
