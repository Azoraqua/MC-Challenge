package com.azoraqua.challenge.module;

import com.azoraqua.challenge.ChallengePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.Plugin;

public final class MotdModule extends Module implements CommandExecutor, Listener {
    private Plugin plugin;

    @Override
    public void initialize(ChallengePlugin plugin) {
        this.plugin = plugin;

        plugin.getCommand("motd").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(ServerListPingEvent e) {
        e.setMotd(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("modules.motd.message", "Survival Server | Van 12:00 tot 00:00")));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/motd <message|none>");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("none")) {
            plugin.getConfig().set("modules.motd.message", null);
            plugin.saveConfig();
            sender.sendMessage("Removed motd, falling back to default.");
            return true;
        }

        final String message = String.join(" ", args);
        plugin.getConfig().set("modules.motd.message", message);
        plugin.saveConfig();
        sender.sendMessage("Changed motd to: " + message);
        return true;
    }
}
