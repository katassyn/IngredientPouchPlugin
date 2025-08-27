package com.maks.mycraftingplugin2.integration;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides mapping from farming plugin material names to
 * IngredientPouch item keys. Updated to match the new naming
 * convention used by IngredientPouchPlugin (farmer_*_I/II/III).
 */
public class PouchIntegrationManager {

    private static final Map<String, String> FARMING_BASE_KEYS = new HashMap<>();

    static {
        // Map base farming material identifiers to their pouch key prefix
        FARMING_BASE_KEYS.put("plant_fiber", "farmer_plant_fiber");
        FARMING_BASE_KEYS.put("seed_pouch", "farmer_seed_pouch");
        FARMING_BASE_KEYS.put("compost_dust", "farmer_compost_dust");
        FARMING_BASE_KEYS.put("herb_extract", "farmer_herb_extract");
        FARMING_BASE_KEYS.put("mushroom_spores", "farmer_mushroom_spores");
        FARMING_BASE_KEYS.put("beeswax_chunk", "farmer_beeswax_chunk");
        FARMING_BASE_KEYS.put("druidic_essence", "farmer_druidic_essence");
        FARMING_BASE_KEYS.put("golden_truffle", "farmer_golden_truffle");
        FARMING_BASE_KEYS.put("ancient_grain", "farmer_ancient_grain");
    }

    /**
     * Get the pouch item key for a farming material at a given level.
     *
     * @param material the base material identifier, e.g. "plant_fiber"
     * @param level the farming tier (1, 2 or 3)
     * @return the full IngredientPouch item key, e.g. "farmer_plant_fiber_I"
     */
    public static String getPouchItemKey(String material, int level) {
        String base = FARMING_BASE_KEYS.get(material);
        if (base == null) {
            // Fallback to the provided material name if not mapped
            return material;
        }

        switch (level) {
            case 2:
                return base + "_II";
            case 3:
                return base + "_III";
            case 1:
            default:
                return base + "_I";
        }
    }
}

