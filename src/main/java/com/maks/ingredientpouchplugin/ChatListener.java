//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.maks.ingredientpouchplugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private final IngredientPouchPlugin plugin;
    private final Set<UUID> playersAwaitingInput = new HashSet();

    public ChatListener(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void addPlayer(UUID playerId) {
        this.playersAwaitingInput.add(playerId);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        if (this.playersAwaitingInput.contains(playerId)) {
            event.setCancelled(true);
            String message = event.getMessage();
            Bukkit.getScheduler().runTask(this.plugin, () -> {
                this.plugin.getPouchListener().processChatInput(player, message);
            });
            this.playersAwaitingInput.remove(playerId);
        }

    }
}
