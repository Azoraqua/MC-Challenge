package com.azoraqua.challenge.module;

import com.azoraqua.challenge.ChallengePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class SpawnerModule extends Module implements Listener {
    @Override
    public void initialize(ChallengePlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(BlockBreakEvent e) {
        final Player player = e.getPlayer();
        final PlayerInventory inventory = player.getInventory();
        final Block block = e.getBlock();

        if (block.getType() == Material.SPAWNER) {
//            e.setDropItems(true);
            final CreatureSpawner spawner = (CreatureSpawner) block.getState();

            final ItemStack item = new ItemStack(Material.SPAWNER, 1);
            final ItemMeta meta = item.getItemMeta();
            meta.setLore(Collections.singletonList(spawner.getSpawnedType().name()));
            item.setData(spawner.getData());
            item.setItemMeta(meta);

            inventory.addItem(item);
            block.setType(Material.AIR);
            e.setDropItems(false);
        }
    }

    @EventHandler
    public void on(BlockPlaceEvent e) {
        final Block block = e.getBlock();

        if (block.getType() == Material.SPAWNER) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final PlayerInventory inventory = player.getInventory();
        final ItemStack item = inventory.getItemInMainHand();

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final Block block = e.getPlayer().getTargetBlock(null, 15);

            if (block.getType() == Material.SPAWNER && item.getType() == Material.ENCHANTED_BOOK) {
                final EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
                final int level = meta.getStoredEnchantLevel(Enchantment.DIG_SPEED);

                block.setMetadata("SPEED", new FixedMetadataValue(JavaPlugin.getPlugin(ChallengePlugin.class), level));
                inventory.remove(item);

                e.getPlayer().sendMessage("You have successfully upgraded the spawner to level " + level);
            } else if (block.getType() == Material.SPAWNER && item.getType() == Material.CLOCK) {
                if (block.hasMetadata("SPEED")) {
                    e.getPlayer().sendMessage("The current level of this spawner is " + block.getMetadata("SPEED").get(0).asInt());
                } else {
                    block.setMetadata("SPEED", new FixedMetadataValue(JavaPlugin.getPlugin(ChallengePlugin.class), 1));
                }
            }

            if (item == null || item.getType() != Material.SPAWNER) {
                return;
            }

            final Block targetBlock = player.getTargetBlock(null, 15).getRelative(BlockFace.UP);

            if (targetBlock == null) {
                return;
            }

            targetBlock.setType(Material.SPAWNER);

            final CreatureSpawner spawner = (CreatureSpawner) targetBlock.getState();
            spawner.setSpawnedType(EntityType.valueOf(ChatColor.stripColor(item.getItemMeta().getLore().get(0))));
            inventory.remove(item);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(SpawnerSpawnEvent e) {
        final CreatureSpawner spawner = e.getSpawner();
        System.out.println("Spawner - " + spawner.hasMetadata("SPEED"));

        if (spawner.hasMetadata("SPEED")) {
            final List<MetadataValue> list = spawner.getMetadata("SPEED");

            if (list.isEmpty()) {
                return;
            }

            final int speed = list.get(0).asInt();

            System.out.println("Spawn count - " + spawner.getSpawnCount());
            spawner.setSpawnCount(spawner.getSpawnCount() * (speed * 5));
            System.out.println("Spawn count (new) - " + spawner.getSpawnCount());

            spawner.setMaxNearbyEntities(256);
            spawner.setMinSpawnDelay(20 * 1);
            spawner.setMaxSpawnDelay(20 * 3);
            spawner.setDelay(-1);
        }
    }
}
