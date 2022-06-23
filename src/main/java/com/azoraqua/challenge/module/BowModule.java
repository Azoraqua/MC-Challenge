package com.azoraqua.challenge.module;

import com.azoraqua.challenge.ChallengePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;

public final class BowModule extends Module implements Listener {
    private Plugin plugin;
    @Override
    public void initialize(ChallengePlugin plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(ProjectileHitEvent e) {
        final Projectile projectile = e.getEntity();
        final ProjectileSource shooter = projectile.getShooter();
        final Block block = e.getHitBlock();

        if (shooter instanceof Player) {
            final Player player = (Player) shooter;
            final PlayerInventory inventory = player.getInventory();
            final ItemStack item = inventory.getItemInOffHand();

            if (item != null && item.getType() != Material.AIR && item.getType().isBlock() || item.getType().isSolid()) {
                block.getRelative(BlockFace.UP).setType(item.getType(), true);
                projectile.remove();

                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);

                    inventory.setItemInOffHand(item);
                } else {
                    inventory.setItemInOffHand(new ItemStack(Material.AIR));
                }
            }
         }
    }
}
