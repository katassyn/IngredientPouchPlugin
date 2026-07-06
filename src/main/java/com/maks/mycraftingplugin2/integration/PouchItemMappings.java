package com.maks.mycraftingplugin2.integration;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

/**
 * Complete mappings between display names and pouch item IDs
 * Based on ItemManager.java from IngredientPouchPlugin
 */
public class PouchItemMappings {
    
    private static final Map<String, String> itemMappings = new HashMap<>();
    private static final Set<String> mineItems = new HashSet<>(Arrays.asList(
        "Hematite",
        "Black Spinel",
        "Black Diamond",
        "Magnetite",
        "Silver",
        "Osmium",
        "Azurite",
        "Tanzanite",
        "Blue Sapphire",
        "Carnelian",
        "Red Spinel",
        "Pigeon Blood Ruby",
        "Pyrite",
        "Yellow Topaz",
        "Yellow Sapphire",
        "Malachite",
        "Peridot",
        "Tropiche Emerald",
        "Danburite",
        "Goshenite",
        "Cerussite"
    ));
    
    static {
        initializeMappings();
    }
    
    private static void initializeMappings() {
        // CURRENCY category items
        itemMappings.put("Fragment of Infernal Passage", "ips");
        itemMappings.put("Blood Vial", "blood_vial");
        itemMappings.put("Joker", "joker");

        // EXPO category items
        itemMappings.put("Broken Armor Piece", "broken_armor_piece");
        itemMappings.put("Tousled Priest Robe", "tousled_priest_robe");
        itemMappings.put("Black Fur", "fur_black");
        itemMappings.put("Dragon Scale", "dragon_scale");
        itemMappings.put("Chain Fragment", "chain_fragment");
        itemMappings.put("Satyr`s Horn", "satyrs_horn");
        itemMappings.put("Gorgon`s Poison", "gorgons_poison");
        itemMappings.put("Dragon`s Gold", "dragon_gold");
        itemMappings.put("Protector`s Heart", "protectors_heart");
        itemMappings.put("Dead Bush", "deaddd_bush");
        itemMappings.put("Demon Blood", "demon_blood");
        itemMappings.put("Sticky Mucus", "sticky_mucus");
        itemMappings.put("Soul of an Acient Spartan", "soul_of_an_acient_spartan");
        itemMappings.put("Shadow Rose", "shadow_rose");
        itemMappings.put("Throphy of the Long Forgotten Bone Dragon", "Throphy_of_the_long_forgotten_bone_dragon");
        
        // MONSTER_FRAGMENTS category items
        itemMappings.put("[ I ] Monster Soul Fragment", "mob_soul_I");
        itemMappings.put("[ II ] Monster Soul Fragment", "mob_soul_II");
        itemMappings.put("[ III ] Monster Soul Fragment", "mob_soul_III");
        itemMappings.put("[ I ] Monster Heart Fragment", "elite_heart_I");
        itemMappings.put("[ II ] Monster Heart Fragment", "elite_heart_II");
        itemMappings.put("[ III ] Monster Heart Fragment", "elite_heart_III");
        
        // Q (boss) category items
        itemMappings.put("[ I ] Grimmor`s Cindered Cape", "grimmag_frag_I");
        itemMappings.put("[ II ] Grimmor`s Cindered Cape", "grimmag_frag_II");
        itemMappings.put("[ III ] Grimmor`s Cindered Cape", "grimmag_frag_III");
        itemMappings.put("[ I ] Araksha`s Venom Husk", "arachna_frag_I");
        itemMappings.put("[ II ] Araksha`s Venom Husk", "arachna_frag_II");
        itemMappings.put("[ III ] Araksha`s Venom Husk", "arachna_frag_III");
        itemMappings.put("[ I ] Heredorn`s Glacial Armor", "heredur_frag_I");
        itemMappings.put("[ II ] Heredorn`s Glacial Armor", "heredur_frag_II");
        itemMappings.put("[ III ] Heredorn`s Glacial Armor", "heredur_frag_III");
        itemMappings.put("[ I ] Bearok Honey Hide", "bearach_frag_I");
        itemMappings.put("[ II ] Bearok Honey Hide", "bearach_frag_II");
        itemMappings.put("[ III ] Bearok Honey Hide", "bearach_frag_III");
        itemMappings.put("[ I ] Kalith`s Ritual Robe", "khalys_frag_I");
        itemMappings.put("[ II ] Kalith`s Ritual Robe", "khalys_frag_II");
        itemMappings.put("[ III ] Kalith`s Ritual Robe", "khalys_frag_III");
        itemMappings.put("[ I ] Harbinger`s Dragon Skin", "heralds_frag_I");
        itemMappings.put("[ II ] Harbinger`s Dragon Skin", "heralds_frag_II");
        itemMappings.put("[ III ] Harbinger`s Dragon Skin", "heralds_frag_III");
        itemMappings.put("[ I ] Sigrosmar`s Eternal Ice", "sigrismar_frag_I");
        itemMappings.put("[ II ] Sigrosmar`s Eternal Ice", "sigrismar_frag_II");
        itemMappings.put("[ III ] Sigrosmar`s Eternal Ice", "sigrismar_frag_III");
        itemMappings.put("[ I ] M`Edara Stone Scales", "medusa_frag_I");
        itemMappings.put("[ II ] M`Edara Stone Scales", "medusa_frag_II");
        itemMappings.put("[ III ] M`Edara Stone Scales", "medusa_frag_III");
        itemMappings.put("[ I ] Gorgra`s Broken Tooth", "gorga_frag_I");
        itemMappings.put("[ II ] Gorgra`s Broken Tooth", "gorga_frag_II");
        itemMappings.put("[ III ] Gorgra`s Broken Tooth", "gorga_frag_III");
        itemMappings.put("[ I ] Mortrix Sacrificial Bones", "mortis_frag_I");
        itemMappings.put("[ II ] Mortrix Sacrificial Bones", "mortis_frag_II");
        itemMappings.put("[ III ] Mortrix Sacrificial Bones", "mortis_frag_III");
        
        // KOPALNIA (mine) category items
        itemMappings.put("[ I ] Ore", "ore_I");
        itemMappings.put("[ II ] Ore", "ore_II");
        itemMappings.put("[ III ] Ore", "ore_III");
        itemMappings.put("[ I ] Cursed Blood", "blood_I");
        itemMappings.put("[ II ] Cursed Blood", "blood_II");
        itemMappings.put("[ III ] Cursed Blood", "blood_III");
        itemMappings.put("[ I ] Shattered Bone", "bone_I");
        itemMappings.put("[ II ] Shattered Bone", "bone_II");
        itemMappings.put("[ III ] Shattered Bone", "bone_III");
        itemMappings.put("[ I ] Leaf", "leaf_I");
        itemMappings.put("[ II ] Leaf", "leaf_II");
        itemMappings.put("[ III ] Leaf", "leaf_III");

        // MINE category items
        itemMappings.put("Hematite", "hematite");
        itemMappings.put("Black Spinel", "black_spinel");
        itemMappings.put("Black Diamond", "black_diamond");
        itemMappings.put("Magnetite", "magnetite");
        itemMappings.put("Silver", "silver");
        itemMappings.put("Osmium", "osmium");
        itemMappings.put("Azurite", "azurite");
        itemMappings.put("Tanzanite", "tanzanite");
        itemMappings.put("Blue Sapphire", "blue_sapphire");
        itemMappings.put("Carnelian", "carnelian");
        itemMappings.put("Red Spinel", "red_spinel");
        itemMappings.put("Pigeon Blood Ruby", "pigeon_blood_ruby");
        itemMappings.put("Pyrite", "pyrite");
        itemMappings.put("Yellow Topaz", "yellow_topaz");
        itemMappings.put("Yellow Sapphire", "yellow_sapphire");
        itemMappings.put("Malachite", "malachite");
        itemMappings.put("Peridot", "peridot");
        itemMappings.put("Tropiche Emerald", "tropiche_emerald");
        itemMappings.put("Danburite", "danburite");
        itemMappings.put("Goshenite", "goshenite");
        itemMappings.put("Cerussite", "cerussite");

        // FARMING category items
        itemMappings.put("[ I ] Plant Fiber", "farmer_plant_fiber_I");
        itemMappings.put("[ II ] Plant Fiber", "farmer_plant_fiber_II");
        itemMappings.put("[ III ] Plant Fiber", "farmer_plant_fiber_III");
        itemMappings.put("[ I ] Seed Pouch", "farmer_seed_pouch_I");
        itemMappings.put("[ II ] Seed Pouch", "farmer_seed_pouch_II");
        itemMappings.put("[ III ] Seed Pouch", "farmer_seed_pouch_III");
        itemMappings.put("[ I ] Compost Dust", "farmer_compost_dust_I");
        itemMappings.put("[ II ] Compost Dust", "farmer_compost_dust_II");
        itemMappings.put("[ III ] Compost Dust", "farmer_compost_dust_III");
        itemMappings.put("[ I ] Herbal Extract", "farmer_herb_extract_I");
        itemMappings.put("[ II ] Herbal Extract", "farmer_herb_extract_II");
        itemMappings.put("[ III ] Herbal Extract", "farmer_herb_extract_III");
        itemMappings.put("[ I ] Mushroom Spores", "farmer_mushroom_spores_I");
        itemMappings.put("[ II ] Mushroom Spores", "farmer_mushroom_spores_II");
        itemMappings.put("[ III ] Mushroom Spores", "farmer_mushroom_spores_III");
        itemMappings.put("[ I ] Beeswax Chunk", "farmer_beeswax_chunk_I");
        itemMappings.put("[ II ] Beeswax Chunk", "farmer_beeswax_chunk_II");
        itemMappings.put("[ III ] Beeswax Chunk", "farmer_beeswax_chunk_III");
        itemMappings.put("[ I ] Druidic Essence", "farmer_druidic_essence_I");
        itemMappings.put("[ II ] Druidic Essence", "farmer_druidic_essence_II");
        itemMappings.put("[ III ] Druidic Essence", "farmer_druidic_essence_III");
        itemMappings.put("[ I ] Golden Truffle", "farmer_golden_truffle_I");
        itemMappings.put("[ II ] Golden Truffle", "farmer_golden_truffle_II");
        itemMappings.put("[ III ] Golden Truffle", "farmer_golden_truffle_III");
        itemMappings.put("[ I ] Ancient Grain Sheaf", "farmer_ancient_grain_I");
        itemMappings.put("[ II ] Ancient Grain Sheaf", "farmer_ancient_grain_II");
        itemMappings.put("[ III ] Ancient Grain Sheaf", "farmer_ancient_grain_III");

        // BEES category items
        itemMappings.put("[ I ] Honey Bottle", "honey_quality_basic");
        itemMappings.put("[ II ] Honey Bottle", "honey_quality_rare");
        itemMappings.put("[ III ] Honey Bottle", "honey_quality_legendary");
        itemMappings.put("[ I ] Queen Bee", "queen_bee_I");
        itemMappings.put("[ II ] Queen Bee", "queen_bee_II");
        itemMappings.put("[ III ] Queen Bee", "queen_bee_III");
        itemMappings.put("[ I ] Worker Bee", "worker_bee_I");
        itemMappings.put("[ II ] Worker Bee", "worker_bee_II");
        itemMappings.put("[ III ] Worker Bee", "worker_bee_III");
        itemMappings.put("[ I ] Drone Bee", "drone_bee_I");
        itemMappings.put("[ II ] Drone Bee", "drone_bee_II");
        itemMappings.put("[ III ] Drone Bee", "drone_bee_III");
        itemMappings.put("[ I ] Bee Larva", "larva_I");
        itemMappings.put("[ II ] Bee Larva", "larva_II");
        itemMappings.put("[ III ] Bee Larva", "larva_III");
        // LOWISKO (fishing) category items
        itemMappings.put("[ I ] Algal", "alga_I");
        itemMappings.put("[ II ] Algal", "alga_II");
        itemMappings.put("[ III ] Algal", "alga_III");
        itemMappings.put("[ I ] Shiny Pearl", "pearl_I");
        itemMappings.put("[ II ] Shiny Pearl", "pearl_II");
        itemMappings.put("[ III ] Shiny Pearl", "pearl_III");
        itemMappings.put("[ I ] Heart of the Ocean", "ocean_heart_I");
        itemMappings.put("[ II ] Heart of the Ocean", "ocean_heart_II");
        itemMappings.put("[ III ] Heart of the Ocean", "ocean_heart_III");
        
        // CURRENCY category items
        itemMappings.put("DrakenMelon", "draken");
        itemMappings.put("Glided Sunflower", "clover");
        itemMappings.put("Anderium", "andermant");
        itemMappings.put("Lockpick", "lockpick");
        itemMappings.put("Jewel Dust", "jewel_dust");
        itemMappings.put("Shiny Dust", "shiny_dust");
        itemMappings.put("Rune Dust", "rune_dust");
        itemMappings.put("Crystal", "crystal");
        itemMappings.put("Biologist Potion", "pota_biola");
        itemMappings.put("Essence Crystal", "essence_crystal");
        itemMappings.put("Power Crystal", "power_crystal");
        itemMappings.put("Primordial Soul Crystal", "primordial_soul_crystal");
        itemMappings.put("New Moon Parchment", "new_moon_scrol");
        itemMappings.put("Fairy Wood", "witch_wood");
        itemMappings.put("Snow Flakes", "snow_flakes");
        itemMappings.put("Honey Bait", "honey_bait");
        itemMappings.put("Winter Solstice Log", "winter_solstice_log");

        // THREAD_WEAVING category items
        itemMappings.put("Thread Essence", "thread_essence");
        itemMappings.put("Shadow Thread Essence", "shadow_thread_essence");
        itemMappings.put("Undead Thread Essence", "undead_thread_essence");
        itemMappings.put("Fire Thread Essence", "fire_thread_essence");
        itemMappings.put("Water Thread Essence", "water_thread_essence");
        itemMappings.put("Wild Thread Essence", "wild_thread_essence");
        itemMappings.put("Poison Thread Essence", "poison_thread_essence");
        itemMappings.put("Legendary Shadow Thread Essence", "legendary_shadow_thread_essence");
        itemMappings.put("Legendary Undead Thread Essence", "legendary_undead_thread_essence");
        itemMappings.put("Legendary Fire Thread Essence", "legendary_fire_thread_essence");
        itemMappings.put("Legendary Water Thread Essence", "legendary_water_thread_essence");
        itemMappings.put("Legendary Wild Thread Essence", "legendary_wild_thread_essence");
        itemMappings.put("Legendary Poison Thread Essence", "legendary_poison_thread_essence");

        // ========== SCIENTIST PLUGIN: Abyssal Bones (consumables) ==========
        // Display names match MythicMobs item display (colors stripped)
        // Belt bones
        itemMappings.put("[ I ] Belt Bone", "abyss_bone_belt_I");
        itemMappings.put("[ II ] Belt Bone", "abyss_bone_belt_II");
        itemMappings.put("[ III ] Belt Bone", "abyss_bone_belt_III");
        // Gloves bones
        itemMappings.put("[ I ] Gloves Bone", "abyss_bone_gloves_I");
        itemMappings.put("[ II ] Gloves Bone", "abyss_bone_gloves_II");
        itemMappings.put("[ III ] Gloves Bone", "abyss_bone_gloves_III");
        // Weapon bones
        itemMappings.put("[ I ] Weapon Bone", "abyss_bone_weapon_I");
        itemMappings.put("[ II ] Weapon Bone", "abyss_bone_weapon_II");
        itemMappings.put("[ III ] Weapon Bone", "abyss_bone_weapon_III");
        // Armor bones
        itemMappings.put("[ I ] Helm Bone", "abyss_bone_helm_I");
        itemMappings.put("[ II ] Helm Bone", "abyss_bone_helm_II");
        itemMappings.put("[ III ] Helm Bone", "abyss_bone_helm_III");
        itemMappings.put("[ I ] Chest Bone", "abyss_bone_chest_I");
        itemMappings.put("[ II ] Chest Bone", "abyss_bone_chest_II");
        itemMappings.put("[ III ] Chest Bone", "abyss_bone_chest_III");
        itemMappings.put("[ I ] Legs Bone", "abyss_bone_legs_I");
        itemMappings.put("[ II ] Legs Bone", "abyss_bone_legs_II");
        itemMappings.put("[ III ] Legs Bone", "abyss_bone_legs_III");
        itemMappings.put("[ I ] Boots Bone", "abyss_bone_boots_I");
        itemMappings.put("[ II ] Boots Bone", "abyss_bone_boots_II");
        itemMappings.put("[ III ] Boots Bone", "abyss_bone_boots_III");
        // Accessory bones
        itemMappings.put("[ I ] Ring 1 Bone", "abyss_bone_ring1_I");
        itemMappings.put("[ II ] Ring 1 Bone", "abyss_bone_ring1_II");
        itemMappings.put("[ III ] Ring 1 Bone", "abyss_bone_ring1_III");
        itemMappings.put("[ I ] Ring 2 Bone", "abyss_bone_ring2_I");
        itemMappings.put("[ II ] Ring 2 Bone", "abyss_bone_ring2_II");
        itemMappings.put("[ III ] Ring 2 Bone", "abyss_bone_ring2_III");
        itemMappings.put("[ I ] Necklace Bone", "abyss_bone_necklace_I");
        itemMappings.put("[ II ] Necklace Bone", "abyss_bone_necklace_II");
        itemMappings.put("[ III ] Necklace Bone", "abyss_bone_necklace_III");
        itemMappings.put("[ I ] Adornment Bone", "abyss_bone_adornment_I");
        itemMappings.put("[ II ] Adornment Bone", "abyss_bone_adornment_II");
        itemMappings.put("[ III ] Adornment Bone", "abyss_bone_adornment_III");
        itemMappings.put("[ I ] Cloak Bone", "abyss_bone_cloak_I");
        itemMappings.put("[ II ] Cloak Bone", "abyss_bone_cloak_II");
        itemMappings.put("[ III ] Cloak Bone", "abyss_bone_cloak_III");
        itemMappings.put("[ I ] Shield Bone", "abyss_bone_shield_I");
        itemMappings.put("[ II ] Shield Bone", "abyss_bone_shield_II");
        itemMappings.put("[ III ] Shield Bone", "abyss_bone_shield_III");

        // ========== SCIENTIST PLUGIN: Research Reagents (mob drops) ==========
        itemMappings.put("[ I ] Corrupted Petal", "corrupted_petal_I");
        itemMappings.put("[ II ] Corrupted Petal", "corrupted_petal_II");
        itemMappings.put("[ III ] Corrupted Petal", "corrupted_petal_III");
        itemMappings.put("[ I ] Blood Amber", "blood_amber_I");
        itemMappings.put("[ II ] Blood Amber", "blood_amber_II");
        itemMappings.put("[ III ] Blood Amber", "blood_amber_III");
        // Additional reagents
        itemMappings.put("[ I ] Void Shard", "void_shard_I");
        itemMappings.put("[ II ] Void Shard", "void_shard_II");
        itemMappings.put("[ III ] Void Shard", "void_shard_III");

        itemMappings.put("[ I ] Soul Residue", "soul_residue_I");
        itemMappings.put("[ II ] Soul Residue", "soul_residue_II");
        itemMappings.put("[ III ] Soul Residue", "soul_residue_III");

        itemMappings.put("[ I ] Gloom Lotus", "gloom_lotus_I");
        itemMappings.put("[ II ] Gloom Lotus", "gloom_lotus_II");
        itemMappings.put("[ III ] Gloom Lotus", "gloom_lotus_III");

        itemMappings.put("[ I ] Ancient Bone Dust", "ancient_bone_dust_I");
        itemMappings.put("[ II ] Ancient Bone Dust", "ancient_bone_dust_II");
        itemMappings.put("[ III ] Ancient Bone Dust", "ancient_bone_dust_III");

        itemMappings.put("[ I ] Eldritch Ichor", "eldritch_ichor_I");
        itemMappings.put("[ II ] Eldritch Ichor", "eldritch_ichor_II");
        itemMappings.put("[ III ] Eldritch Ichor", "eldritch_ichor_III");

        itemMappings.put("[ I ] Frost Core", "frost_core_I");
        itemMappings.put("[ II ] Frost Core", "frost_core_II");
        itemMappings.put("[ III ] Frost Core", "frost_core_III");

        itemMappings.put("[ I ] Storm Pearl", "storm_pearl_I");
        itemMappings.put("[ II ] Storm Pearl", "storm_pearl_II");
        itemMappings.put("[ III ] Storm Pearl", "storm_pearl_III");

        itemMappings.put("[ I ] Ember Powder", "ember_powder_I");
        itemMappings.put("[ II ] Ember Powder", "ember_powder_II");
        itemMappings.put("[ III ] Ember Powder", "ember_powder_III");

        itemMappings.put("[ I ] Rift Fibre", "rift_fibre_I");
        itemMappings.put("[ II ] Rift Fibre", "rift_fibre_II");
        itemMappings.put("[ III ] Rift Fibre", "rift_fibre_III");

        itemMappings.put("[ I ] Shadow Ash", "shadow_ash_I");
        itemMappings.put("[ II ] Shadow Ash", "shadow_ash_II");
        itemMappings.put("[ III ] Shadow Ash", "shadow_ash_III");

        // ========== DEMON TOWER ITEMS ==========
        // Demon Ash
        itemMappings.put("[ I ] Demonic Ash", "demon_ash_I");
        itemMappings.put("[ II ] Demonic Ash", "demon_ash_II");
        itemMappings.put("[ III ] Demonic Ash", "demon_ash_III");
        itemMappings.put("[ IV ] Demonic Ash", "demon_ash_IV");

        // Cursed Cloth
        itemMappings.put("[ I ] Cursed Cloth", "cursed_cloth_I");
        itemMappings.put("[ II ] Cursed Cloth", "cursed_cloth_II");
        itemMappings.put("[ III ] Cursed Cloth", "cursed_cloth_III");
        itemMappings.put("[ IV ] Cursed Cloth", "cursed_cloth_IV");

        // Demon Head
        itemMappings.put("[ I ] Demon Head", "demon_head_I");
        itemMappings.put("[ II ] Demon Head", "demon_head_II");
        itemMappings.put("[ III ] Demon Head", "demon_head_III");
        itemMappings.put("[ IV ] Demon Head", "demon_head_IV");

        // Raven Feather
        itemMappings.put("[ I ] Raven Feather", "raven_feather_I");
        itemMappings.put("[ II ] Raven Feather", "raven_feather_II");
        itemMappings.put("[ III ] Raven Feather", "raven_feather_III");
        itemMappings.put("[ IV ] Raven Feather", "raven_feather_IV");

        // Chaos Artifact
        itemMappings.put("[ I ] Chaos Artifact", "chaos_artifact_I");
        itemMappings.put("[ II ] Chaos Artifact", "chaos_artifact_II");
        itemMappings.put("[ III ] Chaos Artifact", "chaos_artifact_III");
        itemMappings.put("[ IV ] Chaos Artifact", "chaos_artifact_IV");

        // Mechanic Specific Items
        itemMappings.put("[ I ] Corrupted Steel Scrap", "blacksmith_scrap");
        itemMappings.put("[ II ] Smelter Magma", "smelter_coal");
        itemMappings.put("[ III ] Divine Droplet", "divine_droplet");
        itemMappings.put("[ IV ] Crucible Flux", "crucible_flux");

        // Boss Unique Items
        itemMappings.put("[ I ] Valgroth`s Dark Sigil", "valgroth_sigil");
        itemMappings.put("[ II ] Azhra`Morth`s Scepter", "azhramorth_scepter");
        itemMappings.put("[ III ] Korthamoth`s Void Eye", "korthamoth_eye");
        itemMappings.put("[ IV ] Vhar`Zhul`s Ripper Blade", "vharzhul_blade");
        itemMappings.put("[ IV ] Acheron`s Shadow Core", "acheron_core");

        // Corrupted Smithing Stone
        itemMappings.put("Corrupted Smithing Stone", "smith_stone");
    }
    
    /**
     * Get pouch item ID from display name
     */
    public static String getPouchItemId(String displayName) {
        // Remove color codes
        displayName = org.bukkit.ChatColor.stripColor(displayName);
        displayName = normalizeDisplayName(displayName);

        // First try exact match
        String id = itemMappings.get(displayName);
        if (id != null) {
            return id;
        }

        // Try case-insensitive match
        for (Map.Entry<String, String> entry : itemMappings.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(displayName)) {
                return entry.getValue();
            }
        }

        // Try without stack size (x100, x1000)
        if (displayName.contains(" x")) {
            String baseDisplay = displayName.substring(0, displayName.lastIndexOf(" x"));
            baseDisplay = normalizeDisplayName(baseDisplay);
            id = itemMappings.get(baseDisplay);
            if (id != null) {
                return id;
            }

            // Try case-insensitive match for base display
            for (Map.Entry<String, String> entry : itemMappings.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(baseDisplay)) {
                    return entry.getValue();
                }
            }
        }

        return null;
    }

    public static boolean isMineCategoryItem(String displayName) {
        return mineItems.contains(normalizeDisplayName(displayName));
    }

    /**
     * Add custom mapping
     */
    public static void addMapping(String displayName, String pouchId) {
        itemMappings.put(displayName, pouchId);
    }

    private static String normalizeDisplayName(String displayName) {
        if (displayName == null) {
            return "";
        }

        switch (displayName.toLowerCase(java.util.Locale.ROOT)) {
            case "andermant":
                return "Anderium";
            default:
                return displayName;
        }
    }
}


