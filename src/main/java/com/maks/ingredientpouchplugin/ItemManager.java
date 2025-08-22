package com.maks.ingredientpouchplugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

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
        addItem("soul_of_an_acient_spartan", Material.PITCHER_POD, "&dSoul of an Acient Spartan", "EXPO", "&7&oCrafting and Quest resource");
        addItem("shadow_rose", Material.WITHER_ROSE, "&dShadow Rose", "EXPO", "&7&oCrafting and Quest resource");
        addItem("Throphy_of_the_long_forgotten_bone_dragon", Material.DRAGON_HEAD, "&dThrophy of the Long Forgotten Bone Dragon", "EXPO", "&7&oCrafting and Quest resource");

        // Przedmioty z kategorii MONSTER_FRAGMENTS
        addItem("mob_soul_I", Material.FLINT, "&9[ I ] Monster Soul Fragment", "MONSTER_FRAGMENTS", "&7&oBasic crafting material");
        addItem("mob_soul_II", Material.FLINT, "&5[ II ] Monster Soul Fragment", "MONSTER_FRAGMENTS", "&7&oBasic crafting material");
        addItem("mob_soul_III", Material.FLINT, "&6&6[ III ] Monster Soul Fragment ", "MONSTER_FRAGMENTS", "&7&oBasic crafting material");

        addItem("elite_heart_I", Material.NETHER_WART, "&9[ I ] Monster Heart Fragment", "MONSTER_FRAGMENTS", "&a&oRare crafting material");
        addItem("elite_heart_II", Material.NETHER_WART, "&5[ II ] Monster Heart Fragment", "MONSTER_FRAGMENTS", "&a&oRare crafting material");
        addItem("elite_heart_III", Material.NETHER_WART, "&6[ III ] Monster Heart Fragment", "MONSTER_FRAGMENTS", "&a&oRare crafting material");
        // Przedmioty z kategorii Q - bossy
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

        // Items from category MINE
        addItem("hematite", Material.COAL_ORE, "&8Hematite", "MINE",
                "&7A common black ore with metallic luster and reddish streaks.",
                "&eCoal-based mineral");
        addItem("black_spinel", Material.COAL_ORE, "&8Black Spinel", "MINE",
                "&7An uncommon black crystal with exceptional hardness and luster.",
                "&eCoal-based gemstone");
        addItem("black_diamond", Material.COAL_ORE, "&8&lBlack Diamond", "MINE",
                "&7A rare and precious black diamond formed under extreme pressure.",
                "&eHighest quality coal gem");

        addItem("magnetite", Material.IRON_ORE, "&7Magnetite", "MINE",
                "&7A common metallic ore with magnetic properties.",
                "&eIron-based mineral");
        addItem("silver", Material.IRON_ORE, "&f&lSilver", "MINE",
                "&7An uncommon precious metal with lustrous white appearance.",
                "&eIron-based metal");
        addItem("osmium", Material.IRON_ORE, "&7&lOsmium", "MINE",
                "&7A rare and dense bluish-white metal, one of the heaviest natural elements.",
                "&ePremium iron metal");

        addItem("azurite", Material.LAPIS_ORE, "&9Azurite", "MINE",
                "&7A common deep blue mineral with intense azure color.",
                "&eLapis-based mineral");
        addItem("tanzanite", Material.LAPIS_ORE, "&9&lTanzanite", "MINE",
                "&7An uncommon blue-purple gemstone known for its trichroic properties.",
                "&eLapis-based gem");
        addItem("blue_sapphire", Material.LAPIS_ORE, "&1&lBlue Sapphire", "MINE",
                "&7A rare and precious blue gemstone, second only to diamond in hardness.",
                "&ePremium lapis gem");

        addItem("carnelian", Material.REDSTONE_ORE, "&cCarnelian", "MINE",
                "&7A common reddish-orange mineral with translucent properties.",
                "&eRedstone-based mineral");
        addItem("red_spinel", Material.REDSTONE_ORE, "&c&lRed Spinel", "MINE",
                "&7An uncommon vibrant red gemstone often mistaken for ruby.",
                "&eRedstone-based gem");
        addItem("pigeon_blood_ruby", Material.REDSTONE_ORE, "&4&lPigeon Blood Ruby", "MINE",
                "&7A rare and precious deep red gemstone with the coveted \"pigeon blood\" color.",
                "&ePremium redstone gem");

        addItem("pyrite", Material.GOLD_ORE, "&ePyrite", "MINE",
                "&7A common brassy-yellow mineral often called \"Fool`s Gold\".",
                "&eGold-based mineral");
        addItem("yellow_topaz", Material.GOLD_ORE, "&e&lYellow Topaz", "MINE",
                "&7An uncommon golden-yellow gemstone with excellent clarity.",
                "&eGold-based gem");
        addItem("yellow_sapphire", Material.GOLD_ORE, "&6&lYellow Sapphire", "MINE",
                "&7A rare and precious golden gemstone, prized for its brilliance and hardness.",
                "&ePremium gold gem");

        addItem("malachite", Material.EMERALD_ORE, "&aMalachite", "MINE",
                "&7A common green mineral with distinctive banded patterns.",
                "&eEmerald-based mineral");
        addItem("peridot", Material.EMERALD_ORE, "&a&lPeridot", "MINE",
                "&7An uncommon olive-green gemstone formed in volcanic environments.",
                "&eEmerald-based gem");
        addItem("tropiche_emerald", Material.EMERALD_ORE, "&2&lTropiche Emerald", "MINE",
                "&7A rare and precious deep green gemstone with exceptional clarity and color.",
                "&ePremium emerald");

        addItem("danburite", Material.DIAMOND_ORE, "&fDanburite", "MINE",
                "&7A common colorless crystal with diamond-like brilliance.",
                "&eDiamond-based mineral");
        addItem("goshenite", Material.DIAMOND_ORE, "&f&lGoshenite", "MINE",
                "&7An uncommon colorless beryl with exceptional clarity.",
                "&eDiamond-based gemstone");
        addItem("cerussite", Material.DIAMOND_ORE, "&f&l&nCerussite", "MINE",
                "&7A rare and precious crystal with the highest refractive index.",
                "&eSupreme diamond gemstone");

        // Items from category FARMING
        addItem("farmer_plant_fiber_I", Material.STRING, "&9[ I ] Plant Fiber", "FARMING", "&7&oBasic crafting material");
        addItem("farmer_plant_fiber_II", Material.STRING, "&5[ II ] Plant Fiber", "FARMING", "&7&oBasic crafting material");
        addItem("farmer_plant_fiber_III", Material.STRING, "&6[ III ] Plant Fiber", "FARMING", "&7&oBasic crafting material");

        addItem("farmer_seed_pouch_I", Material.WHEAT_SEEDS, "&9[ I ] Seed Pouch", "FARMING", "&7&oBasic crafting material");
        addItem("farmer_seed_pouch_II", Material.WHEAT_SEEDS, "&5[ II ] Seed Pouch", "FARMING", "&7&oBasic crafting material");
        addItem("farmer_seed_pouch_III", Material.WHEAT_SEEDS, "&6[ III ] Seed Pouch", "FARMING", "&7&oBasic crafting material");

        addItem("farmer_compost_dust_I", Material.BONE_MEAL, "&9[ I ] Compost Dust", "FARMING", "&7&oBasic crafting material");
        addItem("farmer_compost_dust_II", Material.BONE_MEAL, "&5[ II ] Compost Dust", "FARMING", "&7&oBasic crafting material");
        addItem("farmer_compost_dust_III", Material.BONE_MEAL, "&6[ III ] Compost Dust", "FARMING", "&7&oBasic crafting material");

        addItem("farmer_herb_extract_I", Material.SPIDER_EYE, "&9[ I ] Herbal Extract", "FARMING", "&a&oRare crafting material");
        addItem("farmer_herb_extract_II", Material.SPIDER_EYE, "&5[ II ] Herbal Extract", "FARMING", "&a&oRare crafting material");
        addItem("farmer_herb_extract_III", Material.SPIDER_EYE, "&6[ III ] Herbal Extract", "FARMING", "&a&oRare crafting material");

        addItem("farmer_mushroom_spores_I", Material.BROWN_MUSHROOM, "&9[ I ] Mushroom Spores", "FARMING", "&a&oRare crafting material");
        addItem("farmer_mushroom_spores_II", Material.BROWN_MUSHROOM, "&5[ II ] Mushroom Spores", "FARMING", "&a&oRare crafting material");
        addItem("farmer_mushroom_spores_III", Material.BROWN_MUSHROOM, "&6[ III ] Mushroom Spores", "FARMING", "&a&oRare crafting material");

        addItem("farmer_beeswax_chunk_I", Material.BOOK, "&9[ I ] Beeswax Chunk", "FARMING", "&a&oRare crafting material");
        addItem("farmer_beeswax_chunk_II", Material.BOOK, "&5[ II ] Beeswax Chunk", "FARMING", "&a&oRare crafting material");
        addItem("farmer_beeswax_chunk_III", Material.BOOK, "&6[ III ] Beeswax Chunk", "FARMING", "&a&oRare crafting material");

        addItem("farmer_druidic_essence_I", Material.GLOW_INK_SAC, "&9[ I ] Druidic Essence", "FARMING", "&c&oLegendary crafting material");
        addItem("farmer_druidic_essence_II", Material.GLOW_INK_SAC, "&5[ II ] Druidic Essence", "FARMING", "&c&oLegendary crafting material");
        addItem("farmer_druidic_essence_III", Material.GLOW_INK_SAC, "&6[ III ] Druidic Essence", "FARMING", "&c&oLegendary crafting material");

        addItem("farmer_golden_truffle_I", Material.GOLDEN_CARROT, "&9[ I ] Golden Truffle", "FARMING", "&c&oLegendary crafting material");
        addItem("farmer_golden_truffle_II", Material.GOLDEN_CARROT, "&5[ II ] Golden Truffle", "FARMING", "&c&oLegendary crafting material");
        addItem("farmer_golden_truffle_III", Material.GOLDEN_CARROT, "&6[ III ] Golden Truffle", "FARMING", "&c&oLegendary crafting material");

        addItem("farmer_ancient_grain_I", Material.HAY_BLOCK, "&9[ I ] Ancient Grain Sheaf", "FARMING", "&c&oLegendary crafting material");
        addItem("farmer_ancient_grain_II", Material.HAY_BLOCK, "&5[ II ] Ancient Grain Sheaf", "FARMING", "&c&oLegendary crafting material");
        addItem("farmer_ancient_grain_III", Material.HAY_BLOCK, "&6[ III ] Ancient Grain Sheaf", "FARMING", "&c&oLegendary crafting material");

        // Items from category BEES
        addItem("honey_quality_basic", Material.HONEY_BOTTLE, "&9[ I ] Honey Bottle", "BEES",
                "&7&oApplies a new &fQuality&7 to an item.",
                "&7&oRoll range: &f-10% &7to &f+10%.",
                "&7&oBasic crafting material");
        addItem("honey_quality_rare", Material.HONEY_BOTTLE, "&5[ II ] Honey Bottle", "BEES",
                "&7&oApplies a new &fQuality&7 to an item.",
                "&7&oRoll range: &f0% &7to &f+20%.",
                "&a&oRare crafting material");
        addItem("honey_quality_legendary", Material.HONEY_BOTTLE, "&6[ III ] Honey Bottle", "BEES",
                "&7&oApplies a new &fQuality&7 to an item.",
                "&7&oRoll range: &f+10% &7to &f+30%.",
                "&c&oLegendary crafting material");

        addItem("queen_bee_I", Material.BREAD, "&9[ I ] Queen Bee", "BEES",
                "&7&oHive multiplier: &f1.0x",
                "&7&oRarer honey chance: &a+5%");
        addItem("queen_bee_II", Material.BREAD, "&5[ II ] Queen Bee", "BEES",
                "&7&oHive multiplier: &f1.2x",
                "&7&oRarer honey chance: &a+10%");
        addItem("queen_bee_III", Material.BREAD, "&6[ III ] Queen Bee", "BEES",
                "&7&oHive multiplier: &f1.5x",
                "&7&oRarer honey chance: &a+15%");

        addItem("worker_bee_I", Material.BREAD, "&9[ I ] Worker Bee", "BEES",
                "&7&oBase honey production: &f0.50");
        addItem("worker_bee_II", Material.BREAD, "&5[ II ] Worker Bee", "BEES",
                "&7&oBase honey production: &f0.75");
        addItem("worker_bee_III", Material.BREAD, "&6[ III ] Worker Bee", "BEES",
                "&7&oBase honey production: &f1.00");

        addItem("drone_bee_I", Material.BREAD, "&9[ I ] Drone Bee", "BEES",
                "&7&oLarvae production: &f0.50",
                "&7&oReduces base honey production: &f-1.00");
        addItem("drone_bee_II", Material.BREAD, "&5[ II ] Drone Bee", "BEES",
                "&7&oLarvae production: &f0.75",
                "&7&oReduces base honey production: &f-0.75");
        addItem("drone_bee_III", Material.BREAD, "&6[ III ] Drone Bee", "BEES",
                "&7&oLarvae production: &f1.00",
                "&7&oReduces base honey production: &f-0.50");

        addItem("larva_I", Material.COOKIE, "&9[ I ] Bee Larva", "BEES",
                "&7&oCan transform into any type of bee.");
        addItem("larva_II", Material.COOKIE, "&5[ II ] Bee Larva", "BEES",
                "&7&oCan transform into any type of bee.");
        addItem("larva_III", Material.COOKIE, "&6[ III ] Bee Larva", "BEES",
                "&7&oCan transform into any type of bee.");
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
        addItem("lockpick", Material.GREEN_DYE,"&8&lLockpick", "CURRENCY", "&7&oUsed to unlock mysterious chests");
        addItem("jewel_dust", Material.INK_SAC, "§9Jewel Dust", "CURRENCY", "&7&oUsed to upgrade jewels");
        addItem("shiny_dust", Material.GLOW_INK_SAC, "§5Shiny Dust", "CURRENCY", "&7&oUsed to upgrade gems");
        addItem("rune_dust", Material.CLAY_BALL, "§cRune Dust", "CURRENCY", "&7&oUsed to upgrade runes");
        addItem("crystal", Material.BRICK, "&d&lCrystal", "CURRENCY", "&7&oMine currency");

        plugin.getLogger().info("Loaded " + items.size() + " items.");
    }


    private void addItem(String itemId, Material material, String displayName, @NotNull String category, String... loreText) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        // Translate color codes in the display name
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));

        // Apply lore, translating color codes for each line
        List<String> lore = new ArrayList<>();
        for (String line : loreText) {
            lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
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
