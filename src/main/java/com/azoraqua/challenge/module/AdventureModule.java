package com.azoraqua.challenge.module;

import com.azoraqua.challenge.ChallengePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class AdventureModule extends Module implements Listener {

    @Override
    public void initialize(ChallengePlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(BlockBreakEvent e) {
        final Player player = e.getPlayer();
        final Block block = e.getBlock();

//        if ((block.getType().name().contains("ORE") || block.getType().name().contains("STONE")) && !player.getInventory().getItemInMainHand().getType().name().contains("PICKAXE")) {
//            e.setCancelled(true);
//        }
//
//        if ((block.getType().name().contains("DIRT") || block.getType().name().contains("SAND")) && !player.getInventory().getItemInMainHand().getType().name().contains("SHOVEL")) {
//            e.setCancelled(true);
//        }
//
//        if ((block.getType().name().contains("SPRUCE") || block.getType().name().contains("BIRCH") || block.getType().name().contains("OAK")) && !player.getInventory().getItemInMainHand().getType().name().contains("AXE")) {
//            e.setCancelled(true);
//        }

//        if ((block.getType().name().contains("")))
    }

    @EventHandler
    public void on(PrepareAnvilEvent e) {
        final AnvilInventory inventory = e.getInventory();

        final ItemStack base = inventory.getItem(0);
        final ItemStack addition = inventory.getItem(1);

        if (base != null && addition != null && addition.getType() == Material.ENCHANTED_BOOK) {
            final EnchantmentStorageMeta meta = (EnchantmentStorageMeta) addition.getItemMeta();
            ItemStack result;

            if (base.getType() == Material.BOOK) {
                result = new ItemStack(Material.ENCHANTED_BOOK);
            } else {
                result = base.clone();
            }

            result.addEnchantments(meta.getStoredEnchants());

            inventory.setRepairCost(3);
            e.setResult(result);
        } else if (base != null && addition != null && base.getType() == Material.BOOK) {
            final ItemStack result = new ItemStack(Material.ENCHANTED_BOOK);
            final Map<Enchantment, Integer> enchantments = addition.getEnchantments();
            result.addUnsafeEnchantments(enchantments);

            final AtomicInteger cost = new AtomicInteger(enchantments.size() + 1);
            enchantments.forEach((enchantment, level) -> cost.set(cost.get() + level));

            inventory.setRepairCost(cost.get());
            e.setResult(result);
        }
    }
}
