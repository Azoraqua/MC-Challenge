package com.azoraqua.challenge.module;

import com.azoraqua.challenge.ChallengePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public final class TorchModule extends Module implements Listener {
    private Plugin plugin;

    @Override
    public void initialize(ChallengePlugin plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(PlayerDropItemEvent e) {
        final Item item = e.getItemDrop();
        final ItemStack stack = item.getItemStack();

        if (stack.getType().name().contains("TORCH")) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                final Location loc = e.getItemDrop().getLocation();
                final int x = loc.getBlockX();
                final int z = loc.getBlockZ();

                loc.getWorld().getHighestBlockAt(x, z).getRelative(BlockFace.UP).setType(stack.getType());
                item.remove();
            }, 20L);
        }
    }
}
