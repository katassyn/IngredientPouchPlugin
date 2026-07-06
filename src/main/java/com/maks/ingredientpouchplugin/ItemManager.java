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
    private final Map<String, ItemStack> items = new LinkedHashMap<>();
    private final Map<String, String> itemCategories = new LinkedHashMap<>();

    public ItemManager(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
        loadItems();
    }
    private void loadItems() {
        // Przedmioty z kategorii CURRENCY
        addItem("ips", Material.IRON_NUGGET, "&4Fragment of Infernal Passage", "CURRENCY", "&7&oUsed to unlock the dangerous dungeons");

        // Przedmioty z kategorii EXPO

        addItem("broken_armor_piece", Material.IRON_INGOT, "&2Broken Armor Piece", "EXPO", "&7&oCrafting and Quest resource");
        addItem("tousled_priest_robe", Material.SHULKER_SHELL, "&2Tousled Priest Robe", "EXPO", "&7&oCrafting and Quest resource");
        addItem("fur_black", Material.RABBIT_HIDE, "&2Black Fur", "EXPO", "&7&oCrafting and Quest resource");
        addItem("dragon_scale", Material.PHANTOM_MEMBRANE, "&2Dragon Scale", "EXPO", "&7&oCrafting and Quest resource");
        addItem("chain_fragment", Material.CHAIN, "&2Chain Fragment", "EXPO", "&7&oCrafting and Quest resource");
        addItem("satyrs_horn", Material.POINTED_DRIPSTONE, "&2Satyr`s Horn", "EXPO", "&7&oCrafting and Quest resource");
        addItem("gorgons_poison", Material.LIGHT_BLUE_DYE, "&2Gorgon`s Poison", "EXPO", "&7&oCrafting and Quest resource");
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
        addItem("grimmag_frag_I", Material.LEATHER, "&9[ I ] Grimmor`s Cindered Cape", "Q", "&c&oLegendary crafting material");
        addItem("grimmag_frag_II", Material.LEATHER, "&5[ II ] Grimmor`s Cindered Cape", "Q", "&c&oLegendary crafting material");
        addItem("grimmag_frag_III", Material.LEATHER, "&6[ III ] Grimmor`s Cindered Cape", "Q", "&c&oLegendary crafting material");

        addItem("arachna_frag_I", Material.PHANTOM_MEMBRANE, "&9[ I ] Araksha`s Venom Husk", "Q", "&c&oLegendary crafting material");
        addItem("arachna_frag_II", Material.PHANTOM_MEMBRANE, "&5[ II ] Araksha`s Venom Husk", "Q", "&c&oLegendary crafting material");
        addItem("arachna_frag_III", Material.PHANTOM_MEMBRANE, "&6[ III ] Araksha`s Venom Husk", "Q", "&c&oLegendary crafting material");

        addItem("heredur_frag_I", Material.PRISMARINE_CRYSTALS, "&9[ I ] Heredorn`s Glacial Armor", "Q", "&c&oLegendary crafting material");
        addItem("heredur_frag_II", Material.PRISMARINE_CRYSTALS, "&5[ II ] Heredorn`s Glacial Armor", "Q", "&c&oLegendary crafting material");
        addItem("heredur_frag_III", Material.PRISMARINE_CRYSTALS, "&6[ III ] Heredorn`s Glacial Armor", "Q", "&c&oLegendary crafting material");

        addItem("bearach_frag_I", Material.HONEYCOMB, "&9[ I ] Bearok Honey Hide", "Q", "&c&oLegendary crafting material");
        addItem("bearach_frag_II", Material.HONEYCOMB, "&5[ II ] Bearok Honey Hide", "Q", "&c&oLegendary crafting material");
        addItem("bearach_frag_III", Material.HONEYCOMB, "&6[ III ] Bearok Honey Hide", "Q", "&c&oLegendary crafting material");

        addItem("khalys_frag_I", Material.BLACK_DYE, "&9[ I ] Kalith`s Ritual Robe", "Q", "&c&oLegendary crafting material");
        addItem("khalys_frag_II", Material.BLACK_DYE, "&5[ II ] Kalith`s Ritual Robe", "Q", "&c&oLegendary crafting material");
        addItem("khalys_frag_III", Material.BLACK_DYE, "&6[ III ] Kalith`s Ritual Robe", "Q", "&c&oLegendary crafting material");

        addItem("heralds_frag_I", Material.NETHERITE_SCRAP, "&9[ I ] Harbinger`s Dragon Skin", "Q", "&c&oLegendary crafting material");
        addItem("heralds_frag_II", Material.NETHERITE_SCRAP, "&5[ II ] Harbinger`s Dragon Skin", "Q", "&c&oLegendary crafting material");
        addItem("heralds_frag_III", Material.NETHERITE_SCRAP, "&6[ III ] Harbinger`s Dragon Skin", "Q", "&c&oLegendary crafting material");

        addItem("sigrismar_frag_I", Material.AMETHYST_SHARD, "&9[ I ] Sigrosmar`s Eternal Ice", "Q", "&c&oLegendary crafting material");
        addItem("sigrismar_frag_II", Material.AMETHYST_SHARD, "&5[ II ] Sigrosmar`s Eternal Ice", "Q", "&c&oLegendary crafting material");
        addItem("sigrismar_frag_III", Material.AMETHYST_SHARD, "&6[ III ] Sigrosmar`s Eternal Ice", "Q", "&c&oLegendary crafting material");

        addItem("medusa_frag_I", Material.SCUTE, "&9[ I ] M`Edara Stone Scales", "Q", "&c&oLegendary crafting material");
        addItem("medusa_frag_II", Material.SCUTE, "&5[ II ] M`Edara Stone Scales", "Q", "&c&oLegendary crafting material");
        addItem("medusa_frag_III", Material.SCUTE, "&6[ III ] M`Edara Stone Scales", "Q", "&c&oLegendary crafting material");

        addItem("gorga_frag_I", Material.LIGHTNING_ROD, "&9[ I ] Gorgra`s Broken Tooth", "Q", "&c&oLegendary crafting material");
        addItem("gorga_frag_II", Material.LIGHTNING_ROD, "&5[ II ] Gorgra`s Broken Tooth", "Q", "&c&oLegendary crafting material");
        addItem("gorga_frag_III", Material.LIGHTNING_ROD, "&6[ III ] Gorgra`s Broken Tooth", "Q", "&c&oLegendary crafting material");

        addItem("mortis_frag_I", Material.BONE, "&9[ I ] Mortrix Sacrificial Bones", "Q", "&c&oLegendary crafting material");
        addItem("mortis_frag_II", Material.BONE, "&5[ II ] Mortrix Sacrificial Bones", "Q", "&c&oLegendary crafting material");
        addItem("mortis_frag_III", Material.BONE, "&6[ III ] Mortrix Sacrificial Bones", "Q", "&c&oLegendary crafting material");

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
        // Przedmioty z kategorii LOWISKO (Ĺowisko)
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
        addItem("andermant", Material.SMALL_AMETHYST_BUD, "&5&lAnderium", "CURRENCY", "&7&oPremium currency");
        addItem("lockpick", Material.GREEN_DYE,"&8&lLockpick", "CURRENCY", "&7&oUsed to unlock mysterious chests");
        addItem("jewel_dust", Material.INK_SAC, "§9Jewel Dust", "CURRENCY", "&7&oUsed to upgrade jewels");
        addItem("shiny_dust", Material.GLOW_INK_SAC, "§5Shiny Dust", "CURRENCY", "&7&oUsed to upgrade gems");
        addItem("rune_dust", Material.CLAY_BALL, "§cRune Dust", "CURRENCY", "&7&oUsed to upgrade runes");
        addItem("crystal", Material.BRICK, "&d&lCrystal", "CURRENCY", "&7&oMine currency");
        addItem("pota_biola", Material.BOWL, "&6Biologist Potion", "CURRENCY", "&7&oRight click to reset Biologist Cooldown");
        addItem("essence_crystal", Material.QUARTZ, "&9Essence Crystal", "CURRENCY", "&7&oBasic crafting material");
        addItem("power_crystal", Material.RAW_GOLD, "&5Power Crystal", "CURRENCY", "&a&oRare crafting material");
        addItem("primordial_soul_crystal", Material.ECHO_SHARD, "&6Primordial Soul Crystal", "CURRENCY", "&c&oLegendary crafting material");
        addItem("blood_vial", Material.BEETROOT_SOUP, "&4Blood Vial", "CURRENCY", "&7&oUsed to summon Amarok, First Werewolf");
        addItem("joker", Material.COPPER_INGOT, "&6&lJoker", "CURRENCY", "&7&oPrecious enchanting gemstone");
        addItem("new_moon_scrol", Material.POTATO, "&aNew Moon Parchment", "CURRENCY", "&7&oUsed to enter the land of New Moon Lords");
        addItem("witch_wood", Material.BAMBOO, "&2Fairy Wood", "CURRENCY", "&7&oUsed to inflict damage on New Moon Lords");
        addItem("snow_flakes", Material.SNOWBALL, "&f&lSnow Flakes", "CURRENCY", "&7&oWinter event currency");
        addItem("honey_bait", Material.HONEY_BOTTLE, "&6Honey Bait", "CURRENCY", "&7&oUsed to enter the Gluttonous Bear`s cave");
        addItem("winter_solstice_log", Material.SPRUCE_WOOD, "&8Winter Solstice Log", "CURRENCY", "&7&oUsed to enter Krampus`s domain");

        // Items from category THREAD_WEAVING
        // Basic Thread Weaving materials
        addItem("thread_essence", Material.NETHER_BRICK, "&7Thread Essence", "THREAD_WEAVING", "&7&oBasic Thread Weaving material");

        // Rare Thread Weaving materials
        addItem("shadow_thread_essence", Material.MAGMA_CREAM, "&5Shadow Thread Essence", "THREAD_WEAVING", "&a&oRare Thread Weaving material");
        addItem("undead_thread_essence", Material.MAGMA_CREAM, "&8Undead Thread Essence", "THREAD_WEAVING", "&a&oRare Thread Weaving material");
        addItem("fire_thread_essence", Material.MAGMA_CREAM, "&cFire Thread Essence", "THREAD_WEAVING", "&a&oRare Thread Weaving material");
        addItem("water_thread_essence", Material.MAGMA_CREAM, "&bWater Thread Essence", "THREAD_WEAVING", "&a&oRare Thread Weaving material");
        addItem("wild_thread_essence", Material.MAGMA_CREAM, "&aWild Thread Essence", "THREAD_WEAVING", "&a&oRare Thread Weaving material");
        addItem("poison_thread_essence", Material.MAGMA_CREAM, "&2Poison Thread Essence", "THREAD_WEAVING", "&a&oRare Thread Weaving material");

        // Legendary Thread Weaving materials
        addItem("legendary_shadow_thread_essence", Material.RABBIT_FOOT, "&5&lLegendary Shadow Thread Essence", "THREAD_WEAVING", "&c&oLegendary Thread Weaving material");
        addItem("legendary_undead_thread_essence", Material.RABBIT_FOOT, "&8&lLegendary Undead Thread Essence", "THREAD_WEAVING", "&c&oLegendary Thread Weaving material");
        addItem("legendary_fire_thread_essence", Material.RABBIT_FOOT, "&c&lLegendary Fire Thread Essence", "THREAD_WEAVING", "&c&oLegendary Thread Weaving material");
        addItem("legendary_water_thread_essence", Material.RABBIT_FOOT, "&b&lLegendary Water Thread Essence", "THREAD_WEAVING", "&c&oLegendary Thread Weaving material");
        addItem("legendary_wild_thread_essence", Material.RABBIT_FOOT, "&a&lLegendary Wild Thread Essence", "THREAD_WEAVING", "&c&oLegendary Thread Weaving material");
        addItem("legendary_poison_thread_essence", Material.RABBIT_FOOT, "&2&lLegendary Poison Thread Essence", "THREAD_WEAVING", "&c&oLegendary Thread Weaving material");

        // ===================== SCIENTIST: Abyssal Bones (consumables) =====================
        // Weapon & Armor bones
        addItem("abyss_bone_weapon_I", Material.POPPY, "&9[ I ] Weapon Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Weapon)");
        addItem("abyss_bone_weapon_II", Material.POPPY, "&5[ II ] Weapon Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Weapon)");
        addItem("abyss_bone_weapon_III", Material.POPPY, "&6[ III ] Weapon Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Weapon)");

        addItem("abyss_bone_helm_I", Material.DANDELION, "&9[ I ] Helm Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Helm)");
        addItem("abyss_bone_helm_II", Material.DANDELION, "&5[ II ] Helm Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Helm)");
        addItem("abyss_bone_helm_III", Material.DANDELION, "&6[ III ] Helm Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Helm)");

        addItem("abyss_bone_chest_I", Material.ORANGE_TULIP, "&9[ I ] Chest Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Chest)");
        addItem("abyss_bone_chest_II", Material.ORANGE_TULIP, "&5[ II ] Chest Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Chest)");
        addItem("abyss_bone_chest_III", Material.ORANGE_TULIP, "&6[ III ] Chest Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Chest)");

        addItem("abyss_bone_legs_I", Material.WHITE_TULIP, "&9[ I ] Legs Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Legs)");
        addItem("abyss_bone_legs_II", Material.WHITE_TULIP, "&5[ II ] Legs Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Legs)");
        addItem("abyss_bone_legs_III", Material.WHITE_TULIP, "&6[ III ] Legs Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Legs)");

        addItem("abyss_bone_boots_I", Material.RED_TULIP, "&9[ I ] Boots Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Boots)");
        addItem("abyss_bone_boots_II", Material.RED_TULIP, "&5[ II ] Boots Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Boots)");
        addItem("abyss_bone_boots_III", Material.RED_TULIP, "&6[ III ] Boots Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Boots)");

        // Accessory bones
        addItem("abyss_bone_ring1_I", Material.CORNFLOWER, "&9[ I ] Ring 1 Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Ring 1)");
        addItem("abyss_bone_ring1_II", Material.CORNFLOWER, "&5[ II ] Ring 1 Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Ring 1)");
        addItem("abyss_bone_ring1_III", Material.CORNFLOWER, "&6[ III ] Ring 1 Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Ring 1)");

        addItem("abyss_bone_ring2_I", Material.AZURE_BLUET, "&9[ I ] Ring 2 Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Ring 2)");
        addItem("abyss_bone_ring2_II", Material.AZURE_BLUET, "&5[ II ] Ring 2 Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Ring 2)");
        addItem("abyss_bone_ring2_III", Material.AZURE_BLUET, "&6[ III ] Ring 2 Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Ring 2)");

        addItem("abyss_bone_necklace_I", Material.BLUE_ORCHID, "&9[ I ] Necklace Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Necklace)");
        addItem("abyss_bone_necklace_II", Material.BLUE_ORCHID, "&5[ II ] Necklace Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Necklace)");
        addItem("abyss_bone_necklace_III", Material.BLUE_ORCHID, "&6[ III ] Necklace Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Necklace)");

        addItem("abyss_bone_adornment_I", Material.OXEYE_DAISY, "&9[ I ] Adornment Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Adornment)");
        addItem("abyss_bone_adornment_II", Material.OXEYE_DAISY, "&5[ II ] Adornment Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Adornment)");
        addItem("abyss_bone_adornment_III", Material.OXEYE_DAISY, "&6[ III ] Adornment Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Adornment)");

        addItem("abyss_bone_cloak_I", Material.LILY_OF_THE_VALLEY, "&9[ I ] Cloak Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Cloak)");
        addItem("abyss_bone_cloak_II", Material.LILY_OF_THE_VALLEY, "&5[ II ] Cloak Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Cloak)");
        addItem("abyss_bone_cloak_III", Material.LILY_OF_THE_VALLEY, "&6[ III ] Cloak Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Cloak)");

        addItem("abyss_bone_shield_I", Material.OXEYE_DAISY, "&9[ I ] Shield Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Shield)");
        addItem("abyss_bone_shield_II", Material.OXEYE_DAISY, "&5[ II ] Shield Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Shield)");
        addItem("abyss_bone_shield_III", Material.OXEYE_DAISY, "&6[ III ] Shield Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Shield)");

        addItem("abyss_bone_belt_I", Material.ALLIUM, "&9[ I ] Belt Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Belt)");
        addItem("abyss_bone_belt_II", Material.ALLIUM, "&5[ II ] Belt Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Belt)");
        addItem("abyss_bone_belt_III", Material.ALLIUM, "&6[ III ] Belt Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Belt)");

        addItem("abyss_bone_gloves_I", Material.PINK_TULIP, "&9[ I ] Gloves Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Gloves)");
        addItem("abyss_bone_gloves_II", Material.PINK_TULIP, "&5[ II ] Gloves Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Gloves)");
        addItem("abyss_bone_gloves_III", Material.PINK_TULIP, "&6[ III ] Gloves Bone", "SCIENTIST", "&7&oAbyssal enchanting catalyst (Gloves)");

        // ===================== SCIENTIST: Research Reagents (mob drops) =====================
        addItem("corrupted_petal_I", Material.OXEYE_DAISY, "&9[ I ] Corrupted Petal", "SCIENTIST", "&7&oResearch reagent");
        addItem("corrupted_petal_II", Material.OXEYE_DAISY, "&5[ II ] Corrupted Petal", "SCIENTIST", "&7&oResearch reagent");
        addItem("corrupted_petal_III", Material.OXEYE_DAISY, "&6[ III ] Corrupted Petal", "SCIENTIST", "&7&oResearch reagent");

        addItem("blood_amber_I", Material.AMETHYST_SHARD, "&9[ I ] Blood Amber", "SCIENTIST", "&7&oResearch reagent");
        addItem("blood_amber_II", Material.AMETHYST_SHARD, "&5[ II ] Blood Amber", "SCIENTIST", "&7&oResearch reagent");
        addItem("blood_amber_III", Material.AMETHYST_SHARD, "&6[ III ] Blood Amber", "SCIENTIST", "&7&oResearch reagent");

        // ===================== SCIENTIST: Additional Research Reagents =====================
        addItem("void_shard_I", Material.OBSIDIAN, "&9[ I ] Void Shard", "SCIENTIST", "&7&oResearch reagent (Void)");
        addItem("void_shard_II", Material.OBSIDIAN, "&5[ II ] Void Shard", "SCIENTIST", "&7&oResearch reagent (Void)");
        addItem("void_shard_III", Material.OBSIDIAN, "&6[ III ] Void Shard", "SCIENTIST", "&7&oResearch reagent (Void)");

        addItem("soul_residue_I", Material.GHAST_TEAR, "&9[ I ] Soul Residue", "SCIENTIST", "&7&oResearch reagent (Soul)");
        addItem("soul_residue_II", Material.GHAST_TEAR, "&5[ II ] Soul Residue", "SCIENTIST", "&7&oResearch reagent (Soul)");
        addItem("soul_residue_III", Material.GHAST_TEAR, "&6[ III ] Soul Residue", "SCIENTIST", "&7&oResearch reagent (Soul)");

        addItem("gloom_lotus_I", Material.WITHER_ROSE, "&9[ I ] Gloom Lotus", "SCIENTIST", "&7&oResearch reagent (Gloom)");
        addItem("gloom_lotus_II", Material.WITHER_ROSE, "&5[ II ] Gloom Lotus", "SCIENTIST", "&7&oResearch reagent (Gloom)");
        addItem("gloom_lotus_III", Material.WITHER_ROSE, "&6[ III ] Gloom Lotus", "SCIENTIST", "&7&oResearch reagent (Gloom)");

        addItem("ancient_bone_dust_I", Material.BONE_MEAL, "&9[ I ] Ancient Bone Dust", "SCIENTIST", "&7&oResearch reagent (Bone)");
        addItem("ancient_bone_dust_II", Material.BONE_MEAL, "&5[ II ] Ancient Bone Dust", "SCIENTIST", "&7&oResearch reagent (Bone)");
        addItem("ancient_bone_dust_III", Material.BONE_MEAL, "&6[ III ] Ancient Bone Dust", "SCIENTIST", "&7&oResearch reagent (Bone)");

        addItem("eldritch_ichor_I", Material.SLIME_BALL, "&9[ I ] Eldritch Ichor", "SCIENTIST", "&7&oResearch reagent (Ichor)");
        addItem("eldritch_ichor_II", Material.SLIME_BALL, "&5[ II ] Eldritch Ichor", "SCIENTIST", "&7&oResearch reagent (Ichor)");
        addItem("eldritch_ichor_III", Material.SLIME_BALL, "&6[ III ] Eldritch Ichor", "SCIENTIST", "&7&oResearch reagent (Ichor)");

        addItem("frost_core_I", Material.SNOWBALL, "&9[ I ] Frost Core", "SCIENTIST", "&7&oResearch reagent (Frost)");
        addItem("frost_core_II", Material.SNOWBALL, "&5[ II ] Frost Core", "SCIENTIST", "&7&oResearch reagent (Frost)");
        addItem("frost_core_III", Material.SNOWBALL, "&6[ III ] Frost Core", "SCIENTIST", "&7&oResearch reagent (Frost)");

        addItem("storm_pearl_I", Material.ENDER_PEARL, "&9[ I ] Storm Pearl", "SCIENTIST", "&7&oResearch reagent (Storm)");
        addItem("storm_pearl_II", Material.ENDER_PEARL, "&5[ II ] Storm Pearl", "SCIENTIST", "&7&oResearch reagent (Storm)");
        addItem("storm_pearl_III", Material.ENDER_PEARL, "&6[ III ] Storm Pearl", "SCIENTIST", "&7&oResearch reagent (Storm)");

        addItem("ember_powder_I", Material.BLAZE_POWDER, "&9[ I ] Ember Powder", "SCIENTIST", "&7&oResearch reagent (Ember)");
        addItem("ember_powder_II", Material.BLAZE_POWDER, "&5[ II ] Ember Powder", "SCIENTIST", "&7&oResearch reagent (Ember)");
        addItem("ember_powder_III", Material.BLAZE_POWDER, "&6[ III ] Ember Powder", "SCIENTIST", "&7&oResearch reagent (Ember)");

        addItem("rift_fibre_I", Material.STRING, "&9[ I ] Rift Fibre", "SCIENTIST", "&7&oResearch reagent (Rift)");
        addItem("rift_fibre_II", Material.STRING, "&5[ II ] Rift Fibre", "SCIENTIST", "&7&oResearch reagent (Rift)");
        addItem("rift_fibre_III", Material.STRING, "&6[ III ] Rift Fibre", "SCIENTIST", "&7&oResearch reagent (Rift)");

        addItem("shadow_ash_I", Material.CHARCOAL, "&9[ I ] Shadow Ash", "SCIENTIST", "&7&oResearch reagent (Shadow)");
        addItem("shadow_ash_II", Material.CHARCOAL, "&5[ II ] Shadow Ash", "SCIENTIST", "&7&oResearch reagent (Shadow)");
        addItem("shadow_ash_III", Material.CHARCOAL, "&6[ III ] Shadow Ash", "SCIENTIST", "&7&oResearch reagent (Shadow)");

        // ===================== DEMON TOWER ITEMS =====================
        // Generic Demon Tower drops (Tiers I-IV)

        // Demon Ash (Basic)
        addItem("demon_ash_I", Material.CLAY_BALL, "&9[ I ] Demonic Ash", "DEMON_TOWER", "&7&oBasic crafting material");
        addItem("demon_ash_II", Material.CLAY_BALL, "&5[ II ] Demonic Ash", "DEMON_TOWER", "&7&oBasic crafting material");
        addItem("demon_ash_III", Material.CLAY_BALL, "&6[ III ] Demonic Ash", "DEMON_TOWER", "&7&oBasic crafting material");
        addItem("demon_ash_IV", Material.CLAY_BALL, "&c[ IV ] Demonic Ash", "DEMON_TOWER", "&7&oBasic crafting material");

        // Cursed Cloth (Basic)
        addItem("cursed_cloth_I", Material.LEATHER, "&9[ I ] Cursed Cloth", "DEMON_TOWER", "&7&oBasic crafting material");
        addItem("cursed_cloth_II", Material.LEATHER, "&5[ II ] Cursed Cloth", "DEMON_TOWER", "&7&oBasic crafting material");
        addItem("cursed_cloth_III", Material.LEATHER, "&6[ III ] Cursed Cloth", "DEMON_TOWER", "&7&oBasic crafting material");
        addItem("cursed_cloth_IV", Material.LEATHER, "&c[ IV ] Cursed Cloth", "DEMON_TOWER", "&7&oBasic crafting material");

        // Demon Head (Rare)
        addItem("demon_head_I", Material.WITHER_SKELETON_SKULL, "&9[ I ] Demon Head", "DEMON_TOWER", "&a&oRare crafting material");
        addItem("demon_head_II", Material.WITHER_SKELETON_SKULL, "&5[ II ] Demon Head", "DEMON_TOWER", "&a&oRare crafting material");
        addItem("demon_head_III", Material.WITHER_SKELETON_SKULL, "&6[ III ] Demon Head", "DEMON_TOWER", "&a&oRare crafting material");
        addItem("demon_head_IV", Material.WITHER_SKELETON_SKULL, "&c[ IV ] Demon Head", "DEMON_TOWER", "&a&oRare crafting material");

        // Raven Feather (Rare)
        addItem("raven_feather_I", Material.CHERRY_SAPLING, "&9[ I ] Raven Feather", "DEMON_TOWER", "&a&oRare crafting material");
        addItem("raven_feather_II", Material.CHERRY_SAPLING, "&5[ II ] Raven Feather", "DEMON_TOWER", "&a&oRare crafting material");
        addItem("raven_feather_III", Material.CHERRY_SAPLING, "&6[ III ] Raven Feather", "DEMON_TOWER", "&a&oRare crafting material");
        addItem("raven_feather_IV", Material.CHERRY_SAPLING, "&c[ IV ] Raven Feather", "DEMON_TOWER", "&a&oRare crafting material");

        // Chaos Artifact (Legendary)
        addItem("chaos_artifact_I", Material.NAUTILUS_SHELL, "&9[ I ] Chaos Artifact", "DEMON_TOWER", "&c&oLegendary crafting material");
        addItem("chaos_artifact_II", Material.NAUTILUS_SHELL, "&5[ II ] Chaos Artifact", "DEMON_TOWER", "&c&oLegendary crafting material");
        addItem("chaos_artifact_III", Material.NAUTILUS_SHELL, "&6[ III ] Chaos Artifact", "DEMON_TOWER", "&c&oLegendary crafting material");
        addItem("chaos_artifact_IV", Material.NAUTILUS_SHELL, "&c[ IV ] Chaos Artifact", "DEMON_TOWER", "&c&oLegendary crafting material");

        // Mechanic Specific Items (Tier Locked)
        addItem("blacksmith_scrap", Material.IRON_INGOT, "&9[ I ] Corrupted Steel Scrap", "DEMON_TOWER", "&a&oRare crafting material");
        addItem("smelter_coal", Material.RABBIT_FOOT, "&5[ II ] Smelter Magma", "DEMON_TOWER", "&a&oRare crafting material");
        addItem("divine_droplet", Material.MUSHROOM_STEW, "&6[ III ] Divine Droplet", "DEMON_TOWER", "&a&oRare crafting material");
        addItem("crucible_flux", Material.BLAZE_POWDER, "&c[ IV ] Crucible Flux", "DEMON_TOWER", "&a&oRare crafting material");

        // Boss Unique Items
        addItem("valgroth_sigil", Material.NETHER_STAR, "&9[ I ] Valgroth`s Dark Sigil", "DEMON_TOWER", "&c&oLegendary crafting material");
        addItem("azhramorth_scepter", Material.BLAZE_ROD, "&5[ II ] Azhra`Morth`s Scepter", "DEMON_TOWER", "&c&oLegendary crafting material");
        addItem("korthamoth_eye", Material.ENDER_EYE, "&6[ III ] Korthamoth`s Void Eye", "DEMON_TOWER", "&c&oLegendary crafting material");
        addItem("vharzhul_blade", Material.IRON_SWORD, "&c[ IV ] Vhar`Zhul`s Ripper Blade", "DEMON_TOWER", "&c&oLegendary crafting material");
        addItem("acheron_core", Material.ECHO_SHARD, "&c[ IV ] Acheron`s Shadow Core", "DEMON_TOWER", "&c&oLegendary crafting material");

        // Corrupted Smithing Stone
        addItem("smith_stone", Material.STONE, "&4Corrupted Smithing Stone", "DEMON_TOWER", "&c&oLegendary crafting material");

        // plugin.getLogger().info("Loaded " + items.size() + " items.");
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
        //plugin.getLogger().info("Item added with lore: " + meta.getLore());

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

