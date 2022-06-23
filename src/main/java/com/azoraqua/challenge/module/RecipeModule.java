package com.azoraqua.challenge.module;

import com.azoraqua.challenge.ChallengePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public final class RecipeModule extends Module {
    private Plugin plugin;

    @Override
    public void initialize(ChallengePlugin plugin) {
        this.plugin = plugin;

        Bukkit.addRecipe(createGravelRecipe());
        Bukkit.addRecipe(createCopperIngot());
        Bukkit.addRecipe(createIronIngot());
        Bukkit.addRecipe(createGoldIngot());
        Bukkit.addRecipe(createRedstone());
        Bukkit.addRecipe(createCoalBlock());
        Bukkit.addRecipe(createPrismarineShard());
        Bukkit.addRecipe(createDiamond());
        Bukkit.addRecipe(createEnchantedGoldenApple());
        Bukkit.addRecipe(createIronArmourToIngot());
        Bukkit.addRecipe(createGoldArmourToIngot());
        Bukkit.addRecipe(createDiamondArmourToIngot());
    }

    private Recipe createEnchantedGoldenApple() {
        return new ShapedRecipe(new NamespacedKey(plugin, "enchanted_golden_apple"), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE))
                .shape("XXX", "XOX", "XXX")
                .setIngredient('X', new RecipeChoice.MaterialChoice(Material.GOLD_BLOCK, Material.DIAMOND))
                .setIngredient('O', Material.APPLE);
    }

    private Recipe createPrismarineShard() {
        return new ShapedRecipe(new NamespacedKey(plugin, "prismarine_Shard"), new ItemStack(Material.PRISMARINE_SHARD))
                .shape("XCX", "COC", "ICI")
                .setIngredient('X', Material.GOLD_INGOT)
                .setIngredient('O', Material.COPPER_INGOT)
                .setIngredient('I', Material.IRON_INGOT)
                .setIngredient('C', new RecipeChoice.MaterialChoice(Tag.ITEMS_COALS));
    }

    private Recipe createDiamond() {
        return new FurnaceRecipe(new NamespacedKey(plugin, "diamond"), new ItemStack(Material.DIAMOND, 1), new RecipeChoice.MaterialChoice(Material.PRISMARINE_SHARD), .5f, 10 * 20);
    }

    private Recipe createCoalBlock() {
        return new ShapedRecipe(new NamespacedKey(plugin, "coal_block"), new ItemStack(Material.COAL_BLOCK))
                .shape("XX", "XO")
                .setIngredient('X', Material.COPPER_INGOT)
                .setIngredient('O', Material.FLINT);
    }

    private Recipe createGravelRecipe() {
        return new ShapedRecipe(new NamespacedKey(plugin, "gravel"), new ItemStack(Material.GRAVEL))
                .shape("XX", "XX")
                .setIngredient('X', new RecipeChoice.MaterialChoice(Tag.SAND));
    }

    private Recipe createIronIngot() {
        return new ShapedRecipe(new NamespacedKey(plugin, "iron_ingot"), new ItemStack(Material.IRON_INGOT, 3))
                .shape("XX", "XO")
                .setIngredient('X', Material.COPPER_INGOT)
                .setIngredient('O', Material.IRON_INGOT);
    }

    private Recipe createGoldIngot() {
        return new ShapedRecipe(new NamespacedKey(plugin, "gold_ingot"), new ItemStack(Material.GOLD_INGOT, 3))
                .shape("XX", "XO")
                .setIngredient('X', Material.COPPER_INGOT)
                .setIngredient('O', Material.GOLD_INGOT);
    }

    private Recipe createCopperIngot() {
        return new ShapedRecipe(new NamespacedKey(plugin, "copper_ingot"), new ItemStack(Material.COPPER_INGOT, 4))
                .shape("XX", "XX")
                .setIngredient('X', new RecipeChoice.MaterialChoice(Material.REDSTONE, Material.IRON_INGOT, Material.GOLD_INGOT, Material.COAL, Material.FLINT));
    }

    private Recipe createRedstone() {
        return new ShapedRecipe(new NamespacedKey(plugin, "redstone"), new ItemStack(Material.REDSTONE, 4))
                .shape("XX", "XX")
                .setIngredient('X', Material.COPPER_INGOT);
    }

    private Recipe createIronArmourToIngot() {
        return new FurnaceRecipe(
                new NamespacedKey(plugin, "iron_armour_to_ingot"),
                new ItemStack(Material.IRON_INGOT, 2),
                new RecipeChoice.MaterialChoice(Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS),
                0.125f,
                15 * 20
        );
    }

    private Recipe createGoldArmourToIngot() {
        return new FurnaceRecipe(
                new NamespacedKey(plugin, "gold_armour_to_ingot"),
                new ItemStack(Material.GOLD_INGOT, 2),
                new RecipeChoice.MaterialChoice(Material.GOLDEN_HELMET, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS, Material.GOLDEN_BOOTS),
                0.125f,
                10 * 20
        );
    }

    private Recipe createDiamondArmourToIngot() {
        return new FurnaceRecipe(
                new NamespacedKey(plugin, "diamond_armour_to_ingot"),
                new ItemStack(Material.DIAMOND, 2),
                new RecipeChoice.MaterialChoice(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS),
                0.25f,
                20 * 20
        );
    }
}
