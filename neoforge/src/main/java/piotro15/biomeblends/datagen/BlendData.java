package piotro15.biomeblends.datagen;

import biomesoplenty.api.biome.BOPBiomes;
import biomesoplenty.api.item.BOPItems;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.blend.blend_action.SetBiomeAction;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public record BlendData(String name, ResourceKey<Biome> id, BlendType blendType, String model, LinkedHashMap<Item, Integer> ingredients) {
    @SafeVarargs
    private static BlendData of(String name, ResourceKey<Biome> id, int color, String model, Map.Entry<Item, Integer>... ingredients) {
        return new BlendData(name, id, defaultBlendType(id, color), model, toLinkedMap(ingredients));    }

    @SafeVarargs
    private static BlendData of(String name, ResourceKey<Biome> id, ColorType colorType, String model, Map.Entry<Item, Integer>... ingredients) {
        BlendType.BlendTypeBuilder builder = new BlendType.BlendTypeBuilder()
                .action(new SetBiomeAction(id.location()))
                .color(findColorForBiome(id, colorType));
        return new BlendData(name, id, builder.build(), model, toLinkedMap(ingredients));    }

    @SafeVarargs
    private static LinkedHashMap<Item, Integer> toLinkedMap(Map.Entry<Item, Integer>... entries) {
        LinkedHashMap<Item, Integer> map = new LinkedHashMap<>();
        for (Map.Entry<Item, Integer> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public static final List<BlendData> blends = List.of(
            BlendData.of("Badlands Blend", Biomes.BADLANDS, 0xD27D2C, "bumpy_blend", Map.entry(Items.RED_SAND, 2), Map.entry(Items.TERRACOTTA, 2)),
            BlendData.of("Bamboo Jungle Blend", Biomes.BAMBOO_JUNGLE, 0x3FA348, "bumpy_blend", Map.entry(Items.BAMBOO, 3), Map.entry(Items.JUNGLE_SAPLING, 1)),
            BlendData.of("Basalt Deltas Blend", Biomes.BASALT_DELTAS, 0x4A4A4A, "bumpy_blend", Map.entry(Items.BASALT, 2), Map.entry(Items.MAGMA_BLOCK, 2)),
            BlendData.of("Beach Blend", Biomes.BEACH, 0xFAE6A0, "bumpy_blend", Map.entry(Items.SAND, 2), Map.entry(Items.SEAGRASS, 1)),
            BlendData.of("Birch Forest Blend", Biomes.BIRCH_FOREST, 0xFFFF3F, "fluffy_blend", Map.entry(Items.SHORT_GRASS, 2), Map.entry(Items.BIRCH_SAPLING, 1)),
            BlendData.of("Cherry Grove Blend", Biomes.CHERRY_GROVE, 0xFFA7DD, "fluffy_blend", Map.entry(Items.PINK_PETALS, 2), Map.entry(Items.CHERRY_SAPLING, 1)),
            BlendData.of("Cold Ocean Blend", Biomes.COLD_OCEAN, 0x2D5D87, "bumpy_blend", Map.entry(Items.SALMON, 2), Map.entry(Items.SEAGRASS, 2)),
            BlendData.of("Crimson Forest Blend", Biomes.CRIMSON_FOREST, 0x842626, "fluffy_blend", Map.entry(Items.CRIMSON_FUNGUS, 1), Map.entry(Items.NETHER_WART_BLOCK, 2)),
            BlendData.of("Dark Forest Blend", Biomes.DARK_FOREST, 0x3AA622, "fluffy_blend", Map.entry(Items.RED_MUSHROOM, 1), Map.entry(Items.BROWN_MUSHROOM, 1), Map.entry(Items.DARK_OAK_SAPLING, 1)),
            BlendData.of("Deep Cold Ocean Blend", Biomes.DEEP_COLD_OCEAN, 0x1D3C5A, "bumpy_blend", Map.entry(Items.SALMON, 2), Map.entry(Items.KELP, 2)),
            BlendData.of("Deep Dark Blend", Biomes.DEEP_DARK, 0x002F43, "rocky_blend", Map.entry(Items.SCULK, 2), Map.entry(Items.ECHO_SHARD, 1)),
            BlendData.of("Deep Frozen Ocean Blend", Biomes.DEEP_FROZEN_OCEAN, 0x3E6F8F, "bumpy_blend", Map.entry(Items.ICE, 2), Map.entry(Items.KELP, 2)),
            BlendData.of("Deep Lukewarm Ocean Blend", Biomes.DEEP_LUKEWARM_OCEAN, 0x478BA5, "bumpy_blend", Map.entry(Items.PUFFERFISH, 1), Map.entry(Items.KELP, 2)),
            BlendData.of("Deep Ocean Blend", Biomes.DEEP_OCEAN, 0x1C3E50, "bumpy_blend", Map.entry(Items.COD, 2), Map.entry(Items.KELP, 2)),
            BlendData.of("Desert Blend", Biomes.DESERT, 0xE5C27B, "bumpy_blend", Map.entry(Items.SAND, 2), Map.entry(Items.DEAD_BUSH, 2)),
            BlendData.of("Dripstone Caves Blend", Biomes.DRIPSTONE_CAVES, 0xA17E70, "rocky_blend", Map.entry(Items.POINTED_DRIPSTONE, 2), Map.entry(Items.COPPER_INGOT, 2)),
            BlendData.of("End Barrens Blend", Biomes.END_BARRENS, 0xCFCDBF, "bumpy_blend", Map.entry(Items.END_STONE, 2), Map.entry(Items.ENDER_PEARL, 1)),
            BlendData.of("End Highlands Blend", Biomes.END_HIGHLANDS, 0xBBBBAA, "bumpy_blend", Map.entry(Items.END_STONE, 2), Map.entry(Items.CHORUS_FLOWER, 2)),
            BlendData.of("End Midlands Blend", Biomes.END_MIDLANDS, 0xC2C2AC, "bumpy_blend", Map.entry(Items.END_STONE, 1), Map.entry(Items.POPPED_CHORUS_FRUIT, 2)),
            BlendData.of("Eroded Badlands Blend", Biomes.ERODED_BADLANDS, 0xB34A1C, "bumpy_blend", Map.entry(Items.DEAD_BUSH, 2), Map.entry(Items.TERRACOTTA, 2)),
            BlendData.of("Flower Forest Blend", Biomes.FLOWER_FOREST, 0x70D85B, "fluffy_blend", Map.entry(Items.WHITE_TULIP, 1), Map.entry(Items.PINK_TULIP, 1), Map.entry(Items.RED_TULIP, 1), Map.entry(Items.ORANGE_TULIP, 1)),
            BlendData.of("Forest Blend", Biomes.FOREST, 0x69D23C, "fluffy_blend", Map.entry(Items.OAK_SAPLING, 1), Map.entry(Items.BIRCH_SAPLING, 1)),
            BlendData.of("Frozen Ocean Blend", Biomes.FROZEN_OCEAN, 0x8AD2E3, "bumpy_blend", Map.entry(Items.ICE, 2), Map.entry(Items.SEAGRASS, 2)),
            BlendData.of("Frozen Peaks Blend", Biomes.FROZEN_PEAKS, 0xD6E7F2, "bumpy_blend", Map.entry(Items.ICE, 2), Map.entry(Items.PACKED_ICE, 2)),
            BlendData.of("Frozen River Blend", Biomes.FROZEN_RIVER, 0xBCE3F2, "bumpy_blend", Map.entry(Items.WATER_BUCKET, 1), Map.entry(Items.ICE, 2)),
            BlendData.of("Grove Blend", Biomes.GROVE, 0x9FC77C, "bumpy_blend", Map.entry(Items.POWDER_SNOW_BUCKET, 1), Map.entry(Items.SPRUCE_SAPLING, 1)),
            BlendData.of("Ice Spikes Blend", Biomes.ICE_SPIKES, 0xCDE7F5, "bumpy_blend", Map.entry(Items.SNOW_BLOCK, 2), Map.entry(Items.PACKED_ICE, 2)),
            BlendData.of("Jagged Peaks Blend", Biomes.JAGGED_PEAKS, 0xBFD4E0, "bumpy_blend", Map.entry(Items.SNOW_BLOCK, 2), Map.entry(Items.EMERALD, 1)),
            BlendData.of("Jungle Blend", Biomes.JUNGLE, 0x4CAF50, "bumpy_blend", Map.entry(Items.JUNGLE_SAPLING, 1), Map.entry(Items.COCOA_BEANS, 1), Map.entry(Items.MELON_SLICE, 2)),
            BlendData.of("Lukewarm Ocean Blend", Biomes.LUKEWARM_OCEAN, 0x62C2A3, "bumpy_blend", Map.entry(Items.PUFFERFISH, 1), Map.entry(Items.SEAGRASS, 2)),
            BlendData.of("Lush Caves Blend", Biomes.LUSH_CAVES, 0x8ECB51, "rocky_blend", Map.entry(Items.GLOW_BERRIES, 2), Map.entry(Items.MOSS_BLOCK, 1)),
            BlendData.of("Mangrove Swamp Blend", Biomes.MANGROVE_SWAMP, 0x556B2F, "bumpy_blend", Map.entry(Items.MANGROVE_PROPAGULE, 1), Map.entry(Items.MUD, 2)),
            BlendData.of("Meadow Blend", Biomes.MEADOW, 0x9DD382, "bumpy_blend", Map.entry(Items.OXEYE_DAISY, 2), Map.entry(Items.CORNFLOWER, 2)),
            BlendData.of("Mushroom Fields Blend", Biomes.MUSHROOM_FIELDS, 0xA892B0, "bumpy_blend", Map.entry(Items.RED_MUSHROOM, 2), Map.entry(Items.BROWN_MUSHROOM, 2)),
            BlendData.of("Nether Wastes Blend", Biomes.NETHER_WASTES, 0xB33A26, "bumpy_blend", Map.entry(Items.NETHERRACK, 3), Map.entry(Items.QUARTZ, 1)),
            BlendData.of("Ocean Blend", Biomes.OCEAN, 0x2A5B88, "bumpy_blend", Map.entry(Items.SEAGRASS, 2), Map.entry(Items.COD, 2)),
            BlendData.of("Old Growth Birch Forest Blend", Biomes.OLD_GROWTH_BIRCH_FOREST, 0x7DC875, "fluffy_blend", Map.entry(Items.BIRCH_SAPLING, 2), Map.entry(Items.SHORT_GRASS, 2)),
            BlendData.of("Old Growth Pine Taiga Blend", Biomes.OLD_GROWTH_PINE_TAIGA, 0x597D48, "fluffy_blend", Map.entry(Items.MOSSY_COBBLESTONE, 2), Map.entry(Items.SPRUCE_SAPLING, 1)),
            BlendData.of("Old Growth Spruce Taiga Blend", Biomes.OLD_GROWTH_SPRUCE_TAIGA, 0x4C6A3D, "fluffy_blend", Map.entry(Items.MOSSY_COBBLESTONE, 2), Map.entry(Items.FERN, 2)),
            BlendData.of("Plains Blend", Biomes.PLAINS, 0x91BD59, "bumpy_blend", Map.entry(Items.SHORT_GRASS, 2), Map.entry(Items.WHEAT_SEEDS, 2)),
            BlendData.of("River Blend", Biomes.RIVER, 0x4D9CB5, "bumpy_blend", Map.entry(Items.CLAY_BALL, 2), Map.entry(Items.WATER_BUCKET, 1)),
            BlendData.of("Savanna Blend", Biomes.SAVANNA, 0xBDB855, "bumpy_blend", Map.entry(Items.ACACIA_SAPLING, 1), Map.entry(Items.SHORT_GRASS, 2)),
            BlendData.of("Savanna Plateau Blend", Biomes.SAVANNA_PLATEAU, 0xC9C76C, "bumpy_blend", Map.entry(Items.ACACIA_SAPLING, 2), Map.entry(Items.SHORT_GRASS, 1)),
            BlendData.of("Small End Islands Blend", Biomes.SMALL_END_ISLANDS, 0xAFAFAF, "bumpy_blend", Map.entry(Items.END_STONE, 1), Map.entry(Items.ENDER_PEARL, 2)),
            BlendData.of("Snowy Beach Blend", Biomes.SNOWY_BEACH, 0xE8F4FA, "bumpy_blend", Map.entry(Items.SNOWBALL, 2), Map.entry(Items.SAND, 2)),
            BlendData.of("Snowy Plains Blend", Biomes.SNOWY_PLAINS, 0xF5F5F5, "bumpy_blend", Map.entry(Items.SNOW_BLOCK, 2), Map.entry(Items.WHEAT_SEEDS, 2)),
            BlendData.of("Snowy Slopes Blend", Biomes.SNOWY_SLOPES, 0xE0EAF0, "bumpy_blend", Map.entry(Items.SNOW_BLOCK, 2), Map.entry(Items.POWDER_SNOW_BUCKET, 1)),
            BlendData.of("Snowy Taiga Blend", Biomes.SNOWY_TAIGA, 0xAECFD0, "fluffy_blend", Map.entry(Items.SNOWBALL, 2), Map.entry(Items.SWEET_BERRIES, 2)),
            BlendData.of("Soul Sand Valley Blend", Biomes.SOUL_SAND_VALLEY, 0x83624B, "bumpy_blend", Map.entry(Items.SOUL_SOIL, 2), Map.entry(Items.BONE_BLOCK, 2)),
            BlendData.of("Sparse Jungle Blend", Biomes.SPARSE_JUNGLE, 0x6AA75E, "bumpy_blend", Map.entry(Items.JUNGLE_SAPLING, 1), Map.entry(Items.COCOA_BEANS, 2)),
            BlendData.of("Stony Peaks Blend", Biomes.STONY_PEAKS, 0xA0A0A0, "bumpy_blend", Map.entry(Items.EMERALD, 1), Map.entry(Items.CALCITE, 2)),
            BlendData.of("Stony Shore Blend", Biomes.STONY_SHORE, 0x808080, "bumpy_blend", Map.entry(Items.STONE, 2), Map.entry(Items.GRAVEL, 2)),
            BlendData.of("Sunflower Plains Blend", Biomes.SUNFLOWER_PLAINS, 0xA2D84F, "bumpy_blend", Map.entry(Items.SUNFLOWER, 2), Map.entry(Items.SHORT_GRASS, 2)),
            BlendData.of("Swamp Blend", Biomes.SWAMP, 0x556B2F, "bumpy_blend", Map.entry(Items.VINE, 2), Map.entry(Items.SLIME_BALL, 2)),
            BlendData.of("Taiga Blend", Biomes.TAIGA, 0x587C4A, "fluffy_blend", Map.entry(Items.SPRUCE_SAPLING, 1), Map.entry(Items.SWEET_BERRIES, 2)),
            BlendData.of("The End Blend", Biomes.THE_END, 0xCFCDBF, "bumpy_blend", Map.entry(Items.END_STONE, 2), Map.entry(Items.DRAGON_BREATH, 2)),
            BlendData.of("The Void Blend", Biomes.THE_VOID, 0x000000, "bumpy_blend", Map.entry(Items.GLASS_BOTTLE, 2)),
            BlendData.of("Warm Ocean Blend", Biomes.WARM_OCEAN, 0x42C5B8, "bumpy_blend", Map.entry(Items.SAND, 2), Map.entry(Items.TROPICAL_FISH, 2)),
            BlendData.of("Warped Forest Blend", Biomes.WARPED_FOREST, 0x3CABA8, "fluffy_blend", Map.entry(Items.WARPED_FUNGUS, 1), Map.entry(Items.WARPED_WART_BLOCK, 2)),
            BlendData.of("Windswept Forest Blend", Biomes.WINDSWEPT_FOREST, 0x5F8F3E, "fluffy_blend", Map.entry(Items.OAK_SAPLING, 1), Map.entry(Items.SPRUCE_SAPLING, 1)),
            BlendData.of("Windswept Gravelly Hills Blend", Biomes.WINDSWEPT_GRAVELLY_HILLS, 0x8A8A8A, "bumpy_blend", Map.entry(Items.GRAVEL, 2), Map.entry(Items.SPRUCE_SAPLING, 1)),
            BlendData.of("Windswept Hills Blend", Biomes.WINDSWEPT_HILLS, 0x92A379, "bumpy_blend", Map.entry(Items.SHORT_GRASS, 2), Map.entry(Items.SPRUCE_SAPLING, 1)),
            BlendData.of("Windswept Savanna Blend", Biomes.WINDSWEPT_SAVANNA, 0xA1A163, "bumpy_blend", Map.entry(Items.COARSE_DIRT, 2), Map.entry(Items.ACACIA_SAPLING, 1)),
            BlendData.of("Wooded Badlands Blend", Biomes.WOODED_BADLANDS, 0xC96435, "bumpy_blend", Map.entry(Items.COARSE_DIRT, 2), Map.entry(Items.OAK_SAPLING, 1))
    );

    public static List<BlendData> biomesOPlentyBlends = List.of(
            BlendData.of("Aspen Glade Blend", BOPBiomes.ASPEN_GLADE, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.YELLOW_MAPLE_SAPLING, 1), Map.entry(Items.BIRCH_LOG, 2)),
            BlendData.of("Auroral Garden Blend", BOPBiomes.AURORAL_GARDEN, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.RAINBOW_BIRCH_SAPLING, 1), Map.entry(Items.SNOWBALL, 2)),
            BlendData.of("Bayou Blend", BOPBiomes.BAYOU, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.WILLOW_SAPLING, 1), Map.entry(Items.FERN, 2)),
            BlendData.of("Bog Blend", BOPBiomes.BOG, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.RED_MAPLE_SAPLING, 1), Map.entry(BOPItems.BUSH, 2)),
            BlendData.of("Cold Desert Blend", BOPBiomes.COLD_DESERT, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(Items.GRAVEL, 3), Map.entry(Items.SNOWBALL, 1)),
            BlendData.of("Coniferous Forest Blend", BOPBiomes.CONIFEROUS_FOREST, ColorType.CALCULATED, "fluffy_blend", Map.entry(BOPItems.FIR_SAPLING, 1), Map.entry(Items.FERN, 1)),
            BlendData.of("Crag Blend", BOPBiomes.CRAG, ColorType.CALCULATED, "bumpy_blend", Map.entry(Items.MOSS_BLOCK, 2), Map.entry(Items.COBBLESTONE, 2)),
            BlendData.of("Crystalline Chasm Blend", BOPBiomes.CRYSTALLINE_CHASM, 0xE6BCC9,"bumpy_blend", Map.entry(BOPItems.ROSE_QUARTZ_CHUNK, 2), Map.entry(Items.NETHERRACK, 2)),
            BlendData.of("Dead Forest Blend", BOPBiomes.DEAD_FOREST, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.DEAD_SAPLING, 1), Map.entry(Items.DEAD_BUSH, 2)),
            BlendData.of("Dryland Blend", BOPBiomes.DRYLAND, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.PINE_SAPLING, 1), Map.entry(BOPItems.BUSH, 2)),
            BlendData.of("Dune Beach Blend", BOPBiomes.DUNE_BEACH, 0xE8D1A3, "bumpy_blend", Map.entry(BOPItems.SEA_OATS, 2), Map.entry(Items.SAND, 2)),
            BlendData.of("End Corruption Blend", BOPBiomes.END_CORRUPTION, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.NULL_BLOCK, 2), Map.entry(Items.END_STONE, 2)),
            BlendData.of("End Reef Blend", BOPBiomes.END_REEF, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.WISPJELLY, 1), Map.entry(Items.SAND, 2)),
            BlendData.of("End Wilds Blend", BOPBiomes.END_WILDS, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.EMPYREAL_SAPLING, 1), Map.entry(Items.END_STONE, 2)),
            BlendData.of("Erupting Inferno Blend", BOPBiomes.ERUPTING_INFERNO, 0xFF6B1A, "bumpy_blend", Map.entry(BOPItems.BRIMSTONE, 2), Map.entry(Items.NETHERRACK, 2)),
            BlendData.of("Field Blend", BOPBiomes.FIELD, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.TALL_WHITE_LAVENDER, 2), Map.entry(Items.SPRUCE_SAPLING, 1)),
            BlendData.of("Fir Clearing Blend", BOPBiomes.FIR_CLEARING, ColorType.CALCULATED, "fluffy_blend", Map.entry(BOPItems.FIR_SAPLING, 1), Map.entry(BOPItems.TOADSTOOL, 2)),
            BlendData.of("Floodplain Blend", BOPBiomes.FLOODPLAIN, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.WATERGRASS, 3), Map.entry(BOPItems.ORANGE_COSMOS, 1)),
            BlendData.of("Forested Field Blend", BOPBiomes.FORESTED_FIELD, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(Items.SPRUCE_SAPLING, 1), Map.entry(BOPItems.SPROUT, 2)),
            BlendData.of("Fungal Jungle Blend", BOPBiomes.FUNGAL_JUNGLE, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.TOADSTOOL, 2), Map.entry(Items.JUNGLE_SAPLING, 1)),
            BlendData.of("Glowing Grotto Blend", BOPBiomes.GLOWING_GROTTO, 0x3FEAD6, "rocky_blend", Map.entry(BOPItems.GLOWING_MOSS_BLOCK, 2), Map.entry(BOPItems.GLOWSHROOM, 1)),
            BlendData.of("Grassland Blend", BOPBiomes.GRASSLAND, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(Items.SHORT_GRASS, 4)),
            BlendData.of("Gravel Beach Blend", BOPBiomes.GRAVEL_BEACH, 0x9E9E9E, "bumpy_blend", Map.entry(Items.GRAVEL, 4)),
            BlendData.of("Highland Blend", BOPBiomes.HIGHLAND, 0x7B8F59, "bumpy_blend", Map.entry(Items.MOSSY_COBBLESTONE, 1), Map.entry(Items.SHORT_GRASS, 3)),
            BlendData.of("Hot Springs Blend", BOPBiomes.HOT_SPRINGS, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.PINE_SAPLING, 1), Map.entry(BOPItems.THERMAL_CALCITE, 2)),
            BlendData.of("Jacaranda Glade Blend", BOPBiomes.JACARANDA_GLADE, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.JACARANDA_SAPLING, 1), Map.entry(Items.SHORT_GRASS, 2)),
            BlendData.of("Jade Cliffs Blend", BOPBiomes.JADE_CLIFFS, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.PINE_SAPLING, 1), Map.entry(Items.SPRUCE_SAPLING, 2)),
            BlendData.of("Lavender Field Blend", BOPBiomes.LAVENDER_FIELD, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.LAVENDER, 3), Map.entry(BOPItems.JACARANDA_SAPLING, 1)),
            BlendData.of("Lush Desert Blend", BOPBiomes.LUSH_DESERT, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.ORANGE_SAND, 2), Map.entry(Items.ACACIA_SAPLING, 1)),
            BlendData.of("Lush Savanna Blend", BOPBiomes.LUSH_SAVANNA, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(Items.POPPY, 3), Map.entry(Items.ROSE_BUSH, 1)),
            BlendData.of("Maple Woods Blend", BOPBiomes.MAPLE_WOODS, 0xB94A2E, "fluffy_blend", Map.entry(BOPItems.RED_MAPLE_SAPLING, 2), Map.entry(BOPItems.VIOLET, 1)),
            BlendData.of("Marsh Blend", BOPBiomes.MARSH, 0x6A7E43, "bumpy_blend", Map.entry(BOPItems.REED, 2), Map.entry(BOPItems.WATERGRASS, 2)),
            BlendData.of("Mediterranean Forest Blend", BOPBiomes.MEDITERRANEAN_FOREST, ColorType.CALCULATED, "fluffy_blend", Map.entry(BOPItems.CYPRESS_SAPLING, 1), Map.entry(BOPItems.BUSH, 2)),
            BlendData.of("Moor Blend", BOPBiomes.MOOR, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.VIOLET, 3), Map.entry(Items.SHORT_GRASS, 1)),
            BlendData.of("Muskeg Blend", BOPBiomes.MUSKEG, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(Items.MUD, 2), Map.entry(Items.SNOWBALL, 2)),
            BlendData.of("Mystic Grove Blend", BOPBiomes.MYSTIC_GROVE, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.MAGIC_SAPLING, 1), Map.entry(BOPItems.BLUE_HYDRANGEA, 2)),
            BlendData.of("Old Growth Dead Forest Blend", BOPBiomes.OLD_GROWTH_DEAD_FOREST, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.DEAD_LOG, 2), Map.entry(Items.DEAD_BUSH, 1)),
            BlendData.of("Old Growth Woodland Blend", BOPBiomes.OLD_GROWTH_WOODLAND, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.TOADSTOOL, 1), Map.entry(Items.OAK_SAPLING, 2)),
            BlendData.of("Ominous Woods Blend", BOPBiomes.OMINOUS_WOODS, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.UMBRAN_SAPLING, 2), Map.entry(Items.SHORT_GRASS, 2)),
            BlendData.of("Orchard Blend", BOPBiomes.ORCHARD, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.FLOWERING_OAK_SAPLING, 1), Map.entry(BOPItems.CLOVER, 3)),
            BlendData.of("Origin Valley Blend", BOPBiomes.ORIGIN_VALLEY, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.ORIGIN_SAPLING, 1), Map.entry(Items.DIRT, 2)),
            BlendData.of("Overgrown Greens Blend", BOPBiomes.OVERGROWN_GREENS, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.HIGH_GRASS, 3), Map.entry(BOPItems.HUGE_CLOVER_PETAL, 1)),
            BlendData.of("Pasture Blend", BOPBiomes.PASTURE, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.BARLEY, 3)),
            BlendData.of("Prairie Blend", BOPBiomes.PRAIRIE, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.GOLDENROD, 2), Map.entry(BOPItems.BARLEY, 2)),
            BlendData.of("Pumpkin Patch Blend", BOPBiomes.PUMPKIN_PATCH, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.ORANGE_MAPLE_SAPLING, 1), Map.entry(Items.PUMPKIN, 3)),
            BlendData.of("Rainforest Blend", BOPBiomes.RAINFOREST, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.MAHOGANY_SAPLING, 1), Map.entry(Items.FERN, 2)),
            BlendData.of("Redwood Forest Blend", BOPBiomes.REDWOOD_FOREST, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.REDWOOD_SAPLING, 1), Map.entry(Items.FERN, 2)),
            BlendData.of("Rocky Rainforest Blend", BOPBiomes.ROCKY_RAINFOREST, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.MAHOGANY_SAPLING, 1), Map.entry(Items.TERRACOTTA, 2)),
            BlendData.of("Rocky Shrubland Blend", BOPBiomes.ROCKY_SHRUBLAND, ColorType.CALCULATED, "bumpy_blend", Map.entry(BOPItems.BUSH, 2), Map.entry(Items.COBBLESTONE, 2)),
            BlendData.of("Scrubland Blend", BOPBiomes.SCRUBLAND, ColorType.CALCULATED, "bumpy_blend", Map.entry(BOPItems.WILDFLOWER, 2), Map.entry(Items.SHORT_GRASS, 2)),
            BlendData.of("Seasonal Forest Blend", BOPBiomes.SEASONAL_FOREST, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.YELLOW_MAPLE_SAPLING, 1), Map.entry(BOPItems.ORANGE_MAPLE_SAPLING, 1), Map.entry(BOPItems.RED_MAPLE_SAPLING, 1)),
            BlendData.of("Shrubland Blend", BOPBiomes.SHRUBLAND, ColorType.CALCULATED, "bumpy_blend", Map.entry(BOPItems.BUSH, 1), Map.entry(Items.LILAC, 2)),
            BlendData.of("Snowblossom Grove Blend", BOPBiomes.SNOWBLOSSOM_GROVE, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.SNOWBLOSSOM_SAPLING, 1), Map.entry(BOPItems.WHITE_PETALS, 2)),
            BlendData.of("Snowy Coniferous Forest Blend", BOPBiomes.SNOWY_CONIFEROUS_FOREST, 0x9FC6B8, "fluffy_blend", Map.entry(BOPItems.FIR_SAPLING, 1), Map.entry(Items.SNOWBALL, 2)),
            BlendData.of("Snowy Fir Clearing Blend", BOPBiomes.SNOWY_FIR_CLEARING, 0xA7D0C2, "bumpy_blend", Map.entry(BOPItems.FIR_SAPLING, 1), Map.entry(Items.FERN, 1), Map.entry(Items.SNOWBALL, 2)),
            BlendData.of("Snowy Maple Woods Blend", BOPBiomes.SNOWY_MAPLE_WOODS, 0xC97B56, "fluffy_blend", Map.entry(BOPItems.RED_MAPLE_SAPLING, 1), Map.entry(Items.SNOWBALL, 2)),
            BlendData.of("Spider Nest Blend", BOPBiomes.SPIDER_NEST, 0x5E3E46, "rocky_blend", Map.entry(BOPItems.WEBBING, 2), Map.entry(BOPItems.HANGING_COBWEB, 1)),
            BlendData.of("Tropics Blend", BOPBiomes.TROPICS, 0x3CC7B2, "fluffy_blend", Map.entry(BOPItems.PALM_SAPLING, 1), Map.entry(BOPItems.BLUE_HYDRANGEA, 2)),
            BlendData.of("Tundra Blend", BOPBiomes.TUNDRA, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.RED_MAPLE_SAPLING, 1), Map.entry(Items.FERN, 2)),
            BlendData.of("Undergrowth Blend", BOPBiomes.UNDERGROWTH, 0x2E5D35, "bumpy_blend", Map.entry(BOPItems.HELLBARK_SAPLING, 1), Map.entry(BOPItems.BRAMBLE, 2)),
            BlendData.of("Visceral Heap Blend", BOPBiomes.VISCERAL_HEAP, 0x8E2B2F, "bumpy_blend", Map.entry(BOPItems.FLESH, 3)),
            BlendData.of("Volcanic Plains Blend", BOPBiomes.VOLCANIC_PLAINS, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.BLACK_SAND, 2), Map.entry(Items.FERN, 2)),
            BlendData.of("Volcano Blend", BOPBiomes.VOLCANO, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.BLACK_SANDSTONE, 1), Map.entry(Items.BASALT, 2)),
            BlendData.of("Wasteland Blend", BOPBiomes.WASTELAND, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.DRIED_SALT, 2), Map.entry(BOPItems.DEAD_LOG, 2)),
            BlendData.of("Wasteland Steppe Blend", BOPBiomes.WASTELAND_STEPPE, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.DESERT_GRASS, 2), Map.entry(Items.SHORT_GRASS, 2)),
            BlendData.of("Wetland Blend", BOPBiomes.WETLAND, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.WILLOW_SAPLING, 1), Map.entry(BOPItems.CATTAIL, 2)),
            BlendData.of("Wintry Origin Valley Blend", BOPBiomes.WINTRY_ORIGIN_VALLEY, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.ORIGIN_SAPLING, 1), Map.entry(Items.SNOWBALL, 3)),
            BlendData.of("Withered Abyss Blend", BOPBiomes.WITHERED_ABYSS, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.entry(BOPItems.BLACKSTONE_BULB, 1), Map.entry(Items.BLACKSTONE, 2)),
            BlendData.of("Woodland Blend", BOPBiomes.WOODLAND, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.entry(BOPItems.TOADSTOOL, 1), Map.entry(Items.ROSE_BUSH, 2))
    );

    private enum ColorType {
        FOLIAGE_COLOR,
        GRASS_COLOR,
        WATER_COLOR,
        CALCULATED
    }

    private static int findColorForBiome(ResourceKey<Biome> id, ColorType colorType) {
        String path = "data/" + id.location().getNamespace()
                + "/worldgen/biome/" + id.location().getPath() + ".json";

        try (InputStream is = BlendTypeProvider.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) throw new IllegalStateException("Biome JSON not found: " + path);
            JsonObject json = GsonHelper.parse(new InputStreamReader(is));
            JsonObject effects = json.getAsJsonObject("effects");
            switch (colorType) {
                case FOLIAGE_COLOR -> {
                    if (effects.has("foliage_color")) {
                        int color = GsonHelper.getAsInt(effects, "foliage_color");
                        System.out.println("Found foliage color for " + id.location() + ": " + Integer.toHexString(color));
                        return color;
                    }
                }
                case GRASS_COLOR -> {
                    if (effects.has("grass_color")) {
                        int color = GsonHelper.getAsInt(effects, "grass_color");
                        System.out.println("Found grass color for " + id.location() + ": " + Integer.toHexString(color));
                        return color;
                    }
                }
                case WATER_COLOR -> {
                    if (effects.has("water_color")) {
                        int color = GsonHelper.getAsInt(effects, "water_color");
                        System.out.println("Found water color for " + id.location() + ": " + Integer.toHexString(color));
                        return color;
                    }
                }
                case CALCULATED -> {
                    if (json.has("temperature") && json.has("downfall")) {
                        float temp = json.get("temperature").getAsFloat();
                        float downfall = json.get("downfall").getAsFloat();
                        int color = FoliageColor.get(downfall, temp);
                        System.out.println("Calculated color for " + id.location() + ": " + color);
                        return color;
                    }
                }
            }
            throw new IllegalStateException("Failed to find the required field for " + colorType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read biome JSON for " + id.location(), e);
        }
    }

    private static BlendType defaultBlendType(ResourceKey<Biome> biomeId, int color) {
        return new BlendType.BlendTypeBuilder()
                .action(new SetBiomeAction(biomeId.location()))
                .color(color)
                .build();
    }

    public ResourceLocation getResourceLocation() {
        return id.location();
    }

    public String getId() {
        return id.location().getPath();
    }

    public String getNamespace() {
        return id.location().getNamespace();
    }
}
