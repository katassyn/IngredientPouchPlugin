package com.maks.ingredientpouchplugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemManager {

    private final IngredientPouchPlugin plugin;
    private final Map<String, ItemStack> items = new HashMap<>();
    private final Map<String, String> itemCategories = new HashMap<>();

    public ItemManager(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
        loadItems();
    }
    private void loadItems() {
        // Przedmioty z kategorii EXPO
        addItem("ips", Material.IRON_NUGGET, "&4Fragment of Infernal Passage", "CURRENCY", "&7&oUsed to unlock the dangerous dungeons");

        addItem("broken_armor_piece", Material.IRON_INGOT, "&2Broken Armor Piece", "EXPO", "&7&oCrafting and Quest resource");
        addItem("tousled_priest_robe", Material.SHULKER_SHELL, "&2Tousled Priest Robe", "EXPO", "&7&oCrafting and Quest resource");
        addItem("fur_black", Material.RABBIT_HIDE, "&2Black Fur", "EXPO", "&7&oCrafting and Quest resource");
        addItem("dragon_scale", Material.PHANTOM_MEMBRANE, "&2Dragon Scale", "EXPO", "&7&oCrafting and Quest resource");
        addItem("chain_fragment", Material.CHAIN, "&2Chain Fragment", "EXPO", "&7&oCrafting and Quest resource");
        addItem("satyrs_horn", Material.POINTED_DRIPSTONE, "&2Satyr`s Horn", "EXPO", "&7&oCrafting and Quest resource");
        addItem("gorgons_poison", Material.SCUTE, "&2Gorgon`s Poison", "EXPO", "&7&oCrafting and Quest resource");
        addItem("dragon_gold", Material.GOLD_INGOT, "&2Dragon`s Gold", "EXPO", "&7&oCrafting and Quest resource");
        addItem("protectors_heart", Material.ORANGE_DYE, "&2Protector`s Heart", "EXPO", "&7&oCrafting and Quest resource");
        addItem("deaddd_bush", Material.DEAD_BUSH, "&2Dead Bush", "EXPO", "&7&oCrafting and Quest resource");
        addItem("demon_blood", Material.RED_DYE, "&dDemon Blood", "EXPO", "&7&oCrafting and Quest resource");
        addItem("sticky_mucus", Material.SLIME_BALL, "&dSticky Mucus", "EXPO", "&7&oCrafting and Quest resource");
        addItem("soul_of_an_acient_spartan", Material.PITCHER_POD, "&dSoul of an Ancient Spartan", "EXPO", "&7&oCrafting and Quest resource");
        addItem("shadow_rose", Material.WITHER_ROSE, "&dShadow Rose", "EXPO", "&7&oCrafting and Quest resource");
        addItem("Throphy_of_the_long_forgotten_bone_dragon", Material.DRAGON_HEAD, "&dThrophy of the Long Forgotten Bone Dragon", "EXPO", "&7&oCrafting and Quest resource");

        // Przedmioty z kategorii Q (Quest)
        addItem("mob_soul_I", Material.FLINT, "&9[ I ] Monster Soul Fragment", "Q", "&7&oBasic crafting material");
        addItem("mob_soul_II", Material.FLINT, "&5[ II ] Monster Soul Fragment", "Q", "&7&oBasic crafting material");
        addItem("mob_soul_III", Material.FLINT, "&6&6[ III ] Monster Soul Fragment ", "Q", "&7&oBasic crafting material");

        addItem("elite_heart_I", Material.NETHER_WART, "&9[ I ] Monster Heart Fragment", "Q", "&a&oRare crafting material");
        addItem("elite_heart_II", Material.NETHER_WART, "&5[ II ] Monster Heart Fragment", "Q", "&a&oRare crafting material");
        addItem("elite_heart_III", Material.NETHER_WART, "&6[ III ] Monster Heart Fragment", "Q", "&a&oRare crafting material");

        addItem("grimmag_frag_I", Material.LEATHER, "&9[ I ] Grimmage Burned Cape", "Q", "&c&oLegendary crafting material");
        addItem("grimmag_frag_II", Material.LEATHER, "&5[ II ] Grimmage Burned Cape", "Q", "&c&oLegendary crafting material");
        addItem("grimmag_frag_III", Material.LEATHER, "&6[ III ] Grimmage Burned Cape", "Q", "&c&oLegendary crafting material");

        addItem("arachna_frag_I", Material.PHANTOM_MEMBRANE, "&9[ I ] Arachna Poisonous Skeleton", "Q", "&c&oLegendary crafting material");
        addItem("arachna_frag_II", Material.PHANTOM_MEMBRANE, "&5[ II ] Arachna Poisonous Skeleton", "Q", "&c&oLegendary crafting material");
        addItem("arachna_frag_III", Material.PHANTOM_MEMBRANE, "&6[ III ] Arachna Poisonous Skeleton", "Q", "&c&oLegendary crafting material");

        addItem("heredur_frag_I", Material.PRISMARINE_CRYSTALS, "&9[ I ] Heredur's Glacial Armor", "Q", "&c&oLegendary crafting material");
        addItem("heredur_frag_II", Material.PRISMARINE_CRYSTALS, "&5[ II ] Heredur's Glacial Armor", "Q", "&c&oLegendary crafting material");
        addItem("heredur_frag_III", Material.PRISMARINE_CRYSTALS, "&6[ III ] Heredur's Glacial Armor", "Q", "&c&oLegendary crafting material");

        addItem("bearach_frag_I", Material.HONEYCOMB, "&9[ I ] Bearach Honey Hide", "Q", "&c&oLegendary crafting material");
        addItem("bearach_frag_II", Material.HONEYCOMB, "&5[ II ] Bearach Honey Hide", "Q", "&c&oLegendary crafting material");
        addItem("bearach_frag_III", Material.HONEYCOMB, "&6[ III ] Bearach Honey Hide", "Q", "&c&oLegendary crafting material");

        addItem("khalys_frag_I", Material.BLACK_DYE, "&9[ I ] Khalys Magic Robe", "Q", "&c&oLegendary crafting material");
        addItem("khalys_frag_II", Material.BLACK_DYE, "&5[ II ] Khalys Magic Robe", "Q", "&c&oLegendary crafting material");
        addItem("khalys_frag_III", Material.BLACK_DYE, "&6[ III ] Khalys Magic Robe", "Q", "&c&oLegendary crafting material");

        addItem("heralds_frag_I", Material.NETHERITE_SCRAP, "&9[ I ] Herald's Dragon Skin", "Q", "&c&oLegendary crafting material");
        addItem("heralds_frag_II", Material.NETHERITE_SCRAP, "&5[ II ] Herald's Dragon Skin", "Q", "&c&oLegendary crafting material");
        addItem("heralds_frag_III", Material.NETHERITE_SCRAP, "&6[ III ] Herald's Dragon Skin", "Q", "&c&oLegendary crafting material");

        addItem("sigrismar_frag_I", Material.AMETHYST_SHARD, "&9[ I ] Sigrismarr's Eternal Ice", "Q", "&c&oLegendary crafting material");
        addItem("sigrismar_frag_II", Material.AMETHYST_SHARD, "&5[ II ] Sigrismarr's Eternal Ice", "Q", "&c&oLegendary crafting material");
        addItem("sigrismar_frag_III", Material.AMETHYST_SHARD, "&6[ III ] Sigrismarr's Eternal Ice", "Q", "&c&oLegendary crafting material");

        addItem("medusa_frag_I", Material.SCUTE, "&9[ I ] Medusa Stone Scales", "Q", "&c&oLegendary crafting material");
        addItem("medusa_frag_II", Material.SCUTE, "&5[ II ] Medusa Stone Scales", "Q", "&c&oLegendary crafting material");
        addItem("medusa_frag_III", Material.SCUTE, "&6[ III ] Medusa Stone Scales", "Q", "&c&oLegendary crafting material");

        addItem("gorga_frag_I", Material.LIGHTNING_ROD, "&9[ I ] Gorga's Broken Tooth", "Q", "&c&oLegendary crafting material");
        addItem("gorga_frag_II", Material.LIGHTNING_ROD, "&5[ II ] Gorga's Broken Tooth", "Q", "&c&oLegendary crafting material");
        addItem("gorga_frag_III", Material.LIGHTNING_ROD, "&6[ III ] Gorga's Broken Tooth", "Q", "&c&oLegendary crafting material");

        addItem("mortis_frag_I", Material.BONE, "&9[ I ] Mortis Sacrificial Bones", "Q", "&c&oLegendary crafting material");
        addItem("mortis_frag_II", Material.BONE, "&5[ II ] Mortis Sacrificial Bones", "Q", "&c&oLegendary crafting material");
        addItem("mortis_frag_III", Material.BONE, "&6[ III ] Mortis Sacrificial Bones", "Q", "&c&oLegendary crafting material");

        // Przedmioty z kategorii KOPALNIA
        addItem("ore_I", Material.COBBLESTONE, "&9[ I ] Ore", "KOPALNIA", "&7&oBasic crafting material");
        addItem("ore_II", Material.COBBLESTONE, "&5[ II ] Ore", "KOPALNIA", "&7&oBasic crafting material");
        addItem("ore_III", Material.COBBLESTONE, "&6[ III ] Ore", "KOPALNIA", "&7&oBasic crafting material");

        addItem("blood_I", Material.REDSTONE, "&9[ I ] Cursed Blood", "KOPALNIA", "&a&oRare crafting material");
        addItem("blood_II", Material.REDSTONE, "&5[ II ] Cursed Blood", "KOPALNIA", "&a&oRare crafting material");
        addItem("blood_III", Material.REDSTONE, "&6[ III ] Cursed Blood", "KOPALNIA", "&a&oRare crafting material");

        addItem("bone_I", Material.BONE, "&9[ I ] Shattered Bone", "KOPALNIA", "&a&oRare crafting material");
        addItem("bone_II", Material.BONE, "&5[ II ] Shattered Bone", "KOPALNIA", "&a&oRare crafting material");
        addItem("bone_III", Material.BONE, "&6[ III ] Shattered Bone", "KOPALNIA", "&a&oRare crafting material");

        addItem("leaf_I", Material.KELP, "&9[ I ] Leaf", "KOPALNIA", "&7&oBasic crafting material");
        addItem("leaf_II", Material.KELP, "&5[ II ] Leaf", "KOPALNIA", "&7&oBasic crafting material");
        addItem("leaf_III", Material.KELP, "&6[ III ] Leaf", "KOPALNIA", "&7&oBasic crafting material");

        // Przedmioty z kategorii LOWISKO (Łowisko)
        addItem("alga_I", Material.HORN_CORAL, "&9[ I ] Algal", "LOWISKO", "&7&oBasic crafting material");
        addItem("alga_II", Material.HORN_CORAL, "&5[ II ] Algal", "LOWISKO", "&7&oBasic crafting material");
        addItem("alga_III", Material.HORN_CORAL, "&6[ III ] Algal", "LOWISKO", "&7&oBasic crafting material");

        addItem("pearl_I", Material.TURTLE_EGG, "&9[ I ] Shiny Pearl", "LOWISKO", "&a&oRare crafting material");
        addItem("pearl_II", Material.TURTLE_EGG, "&5[ II ] Shiny Pearl", "LOWISKO", "&a&oRare crafting material");
        addItem("pearl_III", Material.TURTLE_EGG, "&6[ III ] Shiny Pearl", "LOWISKO", "&a&oRare crafting material");

        addItem("ocean_heart_I", Material.HEART_OF_THE_SEA, "&9[ I ] Heart of the Ocean", "LOWISKO", "&c&oLegendary crafting material");
        addItem("ocean_heart_II", Material.HEART_OF_THE_SEA, "&5[ II ] Heart of the Ocean", "LOWISKO", "&c&oLegendary crafting material");
        addItem("ocean_heart_III", Material.HEART_OF_THE_SEA, "&6[ III ] Heart of the Ocean", "LOWISKO", "&c&oLegendary crafting material");

        // Przedmioty z kategorii CURRENCY (Waluty)
        addItem("draken", Material.GLISTERING_MELON_SLICE, "&e&lDrakenMelon", "CURRENCY", "&7&oEvent currency");
        addItem("clover", Material.SUNFLOWER, "&6&lGlided Sunflower", "CURRENCY", "&7&oUnique currency");
        addItem("andermant", Material.SMALL_AMETHYST_BUD, "&5&lAndermant", "CURRENCY", "&7&oPremium currency");

        plugin.getLogger().info("Loaded " + items.size() + " items.");
    }


    private void addItem(String itemId, Material material, String displayName, String category, String loreText) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        // Translate color codes in the display name
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));

        // Apply loreText directly, translating color codes
        List<String> lore = new ArrayList<>();
        String formattedLore = ChatColor.translateAlternateColorCodes('&', loreText);
        lore.add(formattedLore);
        meta.setLore(lore);

        // Set any additional item meta properties here
        meta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 10, true);
        meta.setUnbreakable(true);
        meta.addItemFlags(
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_UNBREAKABLE,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_POTION_EFFECTS,
                ItemFlag.HIDE_PLACED_ON
        );

        item.setItemMeta(meta);

        // Debugging: Print the item's lore to verify formatting
        plugin.getLogger().info("Item added with lore: " + meta.getLore());

        // Store the item
        items.put(itemId, item);
        itemCategories.put(itemId, category.toUpperCase());
    }

    public ItemStack getItem(String itemId) {
        ItemStack item = items.get(itemId);
        if (item != null) {
            return item.clone(); // Return a clone to prevent modification of the original item
        }
        return null;
    }

    public Set<String> getItemIds() {
        return items.keySet();
    }

    public String getItemCategory(String itemId) {
        return itemCategories.getOrDefault(itemId, "CRAFTING");
    }
}
