package com.azoraqua.challenge.module;

import com.azoraqua.challenge.ChallengePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class ChatModule extends Module implements Listener {
    @Override
    public void initialize(ChallengePlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(PlayerJoinEvent e) {
        final Player player = e.getPlayer();

        if (player.getName().equalsIgnoreCase("Woetroe_")) {
            player.setDisplayName(ChatColor.RED + player.getName());
        }

        if (player.getName().equalsIgnoreCase("Azoraqua_")) {
            player.setDisplayName(ChatColor.BLUE + player.getName());
        }

        e.setJoinMessage(player.getDisplayName() + ChatColor.YELLOW + " joined.");
    }

    @EventHandler
    public void on(PlayerQuitEvent e) {
        final Player player = e.getPlayer();

        e.setQuitMessage(player.getDisplayName() + ChatColor.YELLOW + " left.");
    }

    @EventHandler
    public void on(AsyncPlayerChatEvent e) {
        final Player player = e.getPlayer();
        final String message = e.getMessage();

        e.setFormat(ChatColor.translateAlternateColorCodes('&', String.format("%s&f: %s", player.getDisplayName(), message)));
    }
}
