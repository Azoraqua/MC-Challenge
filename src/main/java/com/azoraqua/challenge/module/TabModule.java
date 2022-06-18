package com.azoraqua.challenge.module;

import com.azoraqua.challenge.ChallengePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public final class TabModule extends Module implements Listener {
    @Override
    public void initialize(ChallengePlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        final String name = player.getDisplayName();

        final Location loc = e.getPlayer().getLocation();
        final int x = loc.getBlockX();
        final int y = loc.getBlockY();
        final int z = loc.getBlockZ();


        final Player[] players = Bukkit.getOnlinePlayers().toArray(Player[]::new);

        if (players.length == 0) {
            return;
        }

        if (players.length == 1) {
            final Location spawn = player.getWorld().getSpawnLocation();

            e.getPlayer().setPlayerListName(ChatColor.RED + name);
            e.getPlayer().setPlayerListFooter(ChatColor.YELLOW + "You are " + ChatColor.AQUA + ((int) spawn.distance(player.getLocation())) + ChatColor.YELLOW + " blocks away from spawn.");
            return;
        }

        final Player player1 = players[0];
        final Player player2 = players[1];
        final Location oneLoc = player1.getLocation();
        final Location twoLoc = player2.getLocation();

        e.getPlayer().setPlayerListName(ChatColor.translateAlternateColorCodes('&',
                String.format("&c%s &e(&b%d&e, &b%d&e, &b%d&e)", name, x, y, z)));
        e.getPlayer().setPlayerListFooter(ChatColor.YELLOW + "You are " + ChatColor.AQUA + ((int) oneLoc.distance(twoLoc)) + ChatColor.YELLOW + " blocks away from " + ChatColor.RED + (player.equals(player1) ? player2.getDisplayName() : player1.getDisplayName()));
    }

    @EventHandler
    public void on(PlayerMoveEvent e) {
        final Player player = e.getPlayer();
        final String name = player.getDisplayName();

        final Player[] players = Bukkit.getOnlinePlayers().toArray(Player[]::new);

        if (players.length == 0) {
            return;
        }

        if (players.length == 1) {
            final Location spawn = player.getWorld().getSpawnLocation();

            e.getPlayer().setPlayerListName(ChatColor.RED + name);
            e.getPlayer().setPlayerListFooter(ChatColor.YELLOW + "You are " + ChatColor.AQUA + ((int) spawn.distance(player.getLocation())) + ChatColor.YELLOW + " blocks away from spawn.");
            return;
        }

        final Player player1 = players[0];
        final Player player2 = players[1];
        final Location oneLoc = player1.getLocation();
        final Location twoLoc = player2.getLocation();

        final Location loc = e.getPlayer().getLocation();
        final int x = loc.getBlockX();
        final int y = loc.getBlockY();
        final int z = loc.getBlockZ();

        e.getPlayer().setPlayerListName(ChatColor.translateAlternateColorCodes('&',
                String.format("&c%s &e(&b%d&e, &b%d&e, &b%d&e)", name, x, y, z)));
        e.getPlayer().setPlayerListFooter(ChatColor.YELLOW + "You are " + ChatColor.AQUA + ((int) oneLoc.distance(twoLoc)) + ChatColor.YELLOW + " blocks away from " + ChatColor.RED + (player.equals(player1) ? player2.getDisplayName() : player1.getDisplayName()));
    }
}
