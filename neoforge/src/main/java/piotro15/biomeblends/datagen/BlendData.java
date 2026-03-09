package piotro15.biomeblends.datagen;

import biomesoplenty.api.biome.BOPBiomes;
import biomesoplenty.api.item.BOPItems;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.potionstudios.biomeswevegone.tags.BWGItemTags;
import net.potionstudios.biomeswevegone.world.item.BWGItems;
import net.potionstudios.biomeswevegone.world.level.block.BWGBlocks;
import net.potionstudios.biomeswevegone.world.level.block.wood.BWGWood;
import net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGBiomes;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.blend.blend_action.SetBiomeAction;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public record BlendData(String name, ResourceKey<Biome> id, BlendType blendType, String model, LinkedHashMap<Ingredient, Integer> ingredients) {
    @SafeVarargs
    private static BlendData of(String name, ResourceKey<Biome> id, int color, String model, Map.Entry<Ingredient, Integer>... ingredients) {
        return new BlendData(name, id, defaultBlendType(id, color), model, toLinkedMap(ingredients));
    }

    @SafeVarargs
    private static BlendData of(String name, ResourceKey<Biome> id, ColorType colorType, String model, Map.Entry<Ingredient, Integer>... ingredients) {
        BlendType.BlendTypeBuilder builder = new BlendType.BlendTypeBuilder()
                .action(new SetBiomeAction(id.location()))
                .color(findColorForBiome(id, colorType));
        return new BlendData(name, id, builder.build(), model, toLinkedMap(ingredients));
    }

    @SafeVarargs
    private static LinkedHashMap<Ingredient, Integer> toLinkedMap(Map.Entry<Ingredient, Integer>... entries) {
        LinkedHashMap<Ingredient, Integer> map = new LinkedHashMap<>();
        for (Map.Entry<Ingredient, Integer> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public static Map.Entry<Ingredient, Integer> entry(Item item, int count) {
        return Map.entry(Ingredient.of(item), count);
    }
    
    public static Map.Entry<Ingredient, Integer> entry(TagKey<Item> tag, int count) {
        return Map.entry(Ingredient.of(tag), count);
    }

    public static final List<BlendData> blends = List.of(
            BlendData.of("Badlands Blend", Biomes.BADLANDS, 0xD27D2C, "bumpy_blend", BlendData.entry(Items.RED_SAND, 2), BlendData.entry(Items.TERRACOTTA, 2)),
            BlendData.of("Bamboo Jungle Blend", Biomes.BAMBOO_JUNGLE, 0x3FA348, "bumpy_blend", BlendData.entry(Items.BAMBOO, 3), BlendData.entry(Items.JUNGLE_SAPLING, 1)),
            BlendData.of("Basalt Deltas Blend", Biomes.BASALT_DELTAS, 0x4A4A4A, "bumpy_blend", BlendData.entry(Items.BASALT, 2), BlendData.entry(Items.MAGMA_BLOCK, 2)),
            BlendData.of("Beach Blend", Biomes.BEACH, 0xFAE6A0, "bumpy_blend", BlendData.entry(Items.SAND, 2), BlendData.entry(Items.SEAGRASS, 1)),
            BlendData.of("Birch Forest Blend", Biomes.BIRCH_FOREST, 0xFFFF3F, "fluffy_blend", BlendData.entry(Items.SHORT_GRASS, 2), BlendData.entry(Items.BIRCH_SAPLING, 1)),
            BlendData.of("Cherry Grove Blend", Biomes.CHERRY_GROVE, 0xFFA7DD, "fluffy_blend", BlendData.entry(Items.PINK_PETALS, 2), BlendData.entry(Items.CHERRY_SAPLING, 1)),
            BlendData.of("Cold Ocean Blend", Biomes.COLD_OCEAN, 0x2D5D87, "bumpy_blend", BlendData.entry(Items.SALMON, 2), BlendData.entry(Items.SEAGRASS, 2)),
            BlendData.of("Crimson Forest Blend", Biomes.CRIMSON_FOREST, 0x842626, "fluffy_blend", BlendData.entry(Items.CRIMSON_FUNGUS, 1), BlendData.entry(Items.NETHER_WART_BLOCK, 2)),
            BlendData.of("Dark Forest Blend", Biomes.DARK_FOREST, 0x3AA622, "fluffy_blend", BlendData.entry(Items.RED_MUSHROOM, 1), BlendData.entry(Items.BROWN_MUSHROOM, 1), BlendData.entry(Items.DARK_OAK_SAPLING, 1)),
            BlendData.of("Deep Cold Ocean Blend", Biomes.DEEP_COLD_OCEAN, 0x1D3C5A, "bumpy_blend", BlendData.entry(Items.SALMON, 2), BlendData.entry(Items.KELP, 2)),
            BlendData.of("Deep Dark Blend", Biomes.DEEP_DARK, 0x002F43, "rocky_blend", BlendData.entry(Items.SCULK, 2), BlendData.entry(Items.ECHO_SHARD, 1)),
            BlendData.of("Deep Frozen Ocean Blend", Biomes.DEEP_FROZEN_OCEAN, 0x3E6F8F, "bumpy_blend", BlendData.entry(Items.ICE, 2), BlendData.entry(Items.KELP, 2)),
            BlendData.of("Deep Lukewarm Ocean Blend", Biomes.DEEP_LUKEWARM_OCEAN, 0x478BA5, "bumpy_blend", BlendData.entry(Items.PUFFERFISH, 1), BlendData.entry(Items.KELP, 2)),
            BlendData.of("Deep Ocean Blend", Biomes.DEEP_OCEAN, 0x1C3E50, "bumpy_blend", BlendData.entry(Items.COD, 2), BlendData.entry(Items.KELP, 2)),
            BlendData.of("Desert Blend", Biomes.DESERT, 0xE5C27B, "bumpy_blend", BlendData.entry(Items.SAND, 2), BlendData.entry(Items.DEAD_BUSH, 2)),
            BlendData.of("Dripstone Caves Blend", Biomes.DRIPSTONE_CAVES, 0xA17E70, "rocky_blend", BlendData.entry(Items.POINTED_DRIPSTONE, 2), BlendData.entry(Items.COPPER_INGOT, 2)),
            BlendData.of("End Barrens Blend", Biomes.END_BARRENS, 0xCFCDBF, "bumpy_blend", BlendData.entry(Items.END_STONE, 2), BlendData.entry(Items.ENDER_PEARL, 1)),
            BlendData.of("End Highlands Blend", Biomes.END_HIGHLANDS, 0xBBBBAA, "bumpy_blend", BlendData.entry(Items.END_STONE, 2), BlendData.entry(Items.CHORUS_FLOWER, 2)),
            BlendData.of("End Midlands Blend", Biomes.END_MIDLANDS, 0xC2C2AC, "bumpy_blend", BlendData.entry(Items.END_STONE, 1), BlendData.entry(Items.POPPED_CHORUS_FRUIT, 2)),
            BlendData.of("Eroded Badlands Blend", Biomes.ERODED_BADLANDS, 0xB34A1C, "bumpy_blend", BlendData.entry(Items.DEAD_BUSH, 2), BlendData.entry(Items.TERRACOTTA, 2)),
            BlendData.of("Flower Forest Blend", Biomes.FLOWER_FOREST, 0x70D85B, "fluffy_blend", BlendData.entry(Items.WHITE_TULIP, 1), BlendData.entry(Items.PINK_TULIP, 1), BlendData.entry(Items.RED_TULIP, 1), BlendData.entry(Items.ORANGE_TULIP, 1)),
            BlendData.of("Forest Blend", Biomes.FOREST, 0x69D23C, "fluffy_blend", BlendData.entry(Items.OAK_SAPLING, 1), BlendData.entry(Items.BIRCH_SAPLING, 1)),
            BlendData.of("Frozen Ocean Blend", Biomes.FROZEN_OCEAN, 0x8AD2E3, "bumpy_blend", BlendData.entry(Items.ICE, 2), BlendData.entry(Items.SEAGRASS, 2)),
            BlendData.of("Frozen Peaks Blend", Biomes.FROZEN_PEAKS, 0xD6E7F2, "bumpy_blend", BlendData.entry(Items.ICE, 2), BlendData.entry(Items.PACKED_ICE, 2)),
            BlendData.of("Frozen River Blend", Biomes.FROZEN_RIVER, 0xBCE3F2, "bumpy_blend", BlendData.entry(Items.WATER_BUCKET, 1), BlendData.entry(Items.ICE, 2)),
            BlendData.of("Grove Blend", Biomes.GROVE, 0x9FC77C, "bumpy_blend", BlendData.entry(Items.POWDER_SNOW_BUCKET, 1), BlendData.entry(Items.SPRUCE_SAPLING, 1)),
            BlendData.of("Ice Spikes Blend", Biomes.ICE_SPIKES, 0xCDE7F5, "bumpy_blend", BlendData.entry(Items.SNOW_BLOCK, 2), BlendData.entry(Items.PACKED_ICE, 2)),
            BlendData.of("Jagged Peaks Blend", Biomes.JAGGED_PEAKS, 0xBFD4E0, "bumpy_blend", BlendData.entry(Items.SNOW_BLOCK, 2), BlendData.entry(Items.EMERALD, 1)),
            BlendData.of("Jungle Blend", Biomes.JUNGLE, 0x4CAF50, "bumpy_blend", BlendData.entry(Items.JUNGLE_SAPLING, 1), BlendData.entry(Items.COCOA_BEANS, 1), BlendData.entry(Items.MELON_SLICE, 2)),
            BlendData.of("Lukewarm Ocean Blend", Biomes.LUKEWARM_OCEAN, 0x62C2A3, "bumpy_blend", BlendData.entry(Items.PUFFERFISH, 1), BlendData.entry(Items.SEAGRASS, 2)),
            BlendData.of("Lush Caves Blend", Biomes.LUSH_CAVES, 0x8ECB51, "rocky_blend", BlendData.entry(Items.GLOW_BERRIES, 2), BlendData.entry(Items.MOSS_BLOCK, 1)),
            BlendData.of("Mangrove Swamp Blend", Biomes.MANGROVE_SWAMP, 0x556B2F, "bumpy_blend", BlendData.entry(Items.MANGROVE_PROPAGULE, 1), BlendData.entry(Items.MUD, 2)),
            BlendData.of("Meadow Blend", Biomes.MEADOW, 0x9DD382, "bumpy_blend", BlendData.entry(Items.OXEYE_DAISY, 2), BlendData.entry(Items.CORNFLOWER, 2)),
            BlendData.of("Mushroom Fields Blend", Biomes.MUSHROOM_FIELDS, 0xA892B0, "bumpy_blend", BlendData.entry(Items.RED_MUSHROOM, 2), BlendData.entry(Items.BROWN_MUSHROOM, 2)),
            BlendData.of("Nether Wastes Blend", Biomes.NETHER_WASTES, 0xB33A26, "bumpy_blend", BlendData.entry(Items.NETHERRACK, 3), BlendData.entry(Items.QUARTZ, 1)),
            BlendData.of("Ocean Blend", Biomes.OCEAN, 0x2A5B88, "bumpy_blend", BlendData.entry(Items.SEAGRASS, 2), BlendData.entry(Items.COD, 2)),
            BlendData.of("Old Growth Birch Forest Blend", Biomes.OLD_GROWTH_BIRCH_FOREST, 0x7DC875, "fluffy_blend", BlendData.entry(Items.BIRCH_SAPLING, 2), BlendData.entry(Items.SHORT_GRASS, 2)),
            BlendData.of("Old Growth Pine Taiga Blend", Biomes.OLD_GROWTH_PINE_TAIGA, 0x597D48, "fluffy_blend", BlendData.entry(Items.MOSSY_COBBLESTONE, 2), BlendData.entry(Items.SPRUCE_SAPLING, 1)),
            BlendData.of("Old Growth Spruce Taiga Blend", Biomes.OLD_GROWTH_SPRUCE_TAIGA, 0x4C6A3D, "fluffy_blend", BlendData.entry(Items.MOSSY_COBBLESTONE, 2), BlendData.entry(Items.FERN, 2)),
            BlendData.of("Plains Blend", Biomes.PLAINS, 0x91BD59, "bumpy_blend", BlendData.entry(Items.SHORT_GRASS, 2), BlendData.entry(Items.WHEAT_SEEDS, 2)),
            BlendData.of("River Blend", Biomes.RIVER, 0x4D9CB5, "bumpy_blend", BlendData.entry(Items.CLAY_BALL, 2), BlendData.entry(Items.WATER_BUCKET, 1)),
            BlendData.of("Savanna Blend", Biomes.SAVANNA, 0xBDB855, "bumpy_blend", BlendData.entry(Items.ACACIA_SAPLING, 1), BlendData.entry(Items.SHORT_GRASS, 2)),
            BlendData.of("Savanna Plateau Blend", Biomes.SAVANNA_PLATEAU, 0xC9C76C, "bumpy_blend", BlendData.entry(Items.ACACIA_SAPLING, 2), BlendData.entry(Items.SHORT_GRASS, 1)),
            BlendData.of("Small End Islands Blend", Biomes.SMALL_END_ISLANDS, 0xAFAFAF, "bumpy_blend", BlendData.entry(Items.END_STONE, 1), BlendData.entry(Items.ENDER_PEARL, 2)),
            BlendData.of("Snowy Beach Blend", Biomes.SNOWY_BEACH, 0xE8F4FA, "bumpy_blend", BlendData.entry(Items.SNOWBALL, 2), BlendData.entry(Items.SAND, 2)),
            BlendData.of("Snowy Plains Blend", Biomes.SNOWY_PLAINS, 0xF5F5F5, "bumpy_blend", BlendData.entry(Items.SNOW_BLOCK, 2), BlendData.entry(Items.WHEAT_SEEDS, 2)),
            BlendData.of("Snowy Slopes Blend", Biomes.SNOWY_SLOPES, 0xE0EAF0, "bumpy_blend", BlendData.entry(Items.SNOW_BLOCK, 2), BlendData.entry(Items.POWDER_SNOW_BUCKET, 1)),
            BlendData.of("Snowy Taiga Blend", Biomes.SNOWY_TAIGA, 0xAECFD0, "fluffy_blend", BlendData.entry(Items.SNOWBALL, 2), BlendData.entry(Items.SWEET_BERRIES, 2)),
            BlendData.of("Soul Sand Valley Blend", Biomes.SOUL_SAND_VALLEY, 0x83624B, "bumpy_blend", BlendData.entry(Items.SOUL_SOIL, 2), BlendData.entry(Items.BONE_BLOCK, 2)),
            BlendData.of("Sparse Jungle Blend", Biomes.SPARSE_JUNGLE, 0x6AA75E, "bumpy_blend", BlendData.entry(Items.JUNGLE_SAPLING, 1), BlendData.entry(Items.COCOA_BEANS, 2)),
            BlendData.of("Stony Peaks Blend", Biomes.STONY_PEAKS, 0xA0A0A0, "bumpy_blend", BlendData.entry(Items.EMERALD, 1), BlendData.entry(Items.CALCITE, 2)),
            BlendData.of("Stony Shore Blend", Biomes.STONY_SHORE, 0x808080, "bumpy_blend", BlendData.entry(Items.STONE, 2), BlendData.entry(Items.GRAVEL, 2)),
            BlendData.of("Sunflower Plains Blend", Biomes.SUNFLOWER_PLAINS, 0xA2D84F, "bumpy_blend", BlendData.entry(Items.SUNFLOWER, 2), BlendData.entry(Items.SHORT_GRASS, 2)),
            BlendData.of("Swamp Blend", Biomes.SWAMP, 0x556B2F, "bumpy_blend", BlendData.entry(Items.VINE, 2), BlendData.entry(Items.SLIME_BALL, 2)),
            BlendData.of("Taiga Blend", Biomes.TAIGA, 0x587C4A, "fluffy_blend", BlendData.entry(Items.SPRUCE_SAPLING, 1), BlendData.entry(Items.SWEET_BERRIES, 2)),
            BlendData.of("The End Blend", Biomes.THE_END, 0xCFCDBF, "bumpy_blend", BlendData.entry(Items.END_STONE, 2), BlendData.entry(Items.DRAGON_BREATH, 2)),
            BlendData.of("The Void Blend", Biomes.THE_VOID, 0x000000, "bumpy_blend", BlendData.entry(Items.GLASS_BOTTLE, 2)),
            BlendData.of("Warm Ocean Blend", Biomes.WARM_OCEAN, 0x42C5B8, "bumpy_blend", BlendData.entry(Items.SAND, 2), BlendData.entry(Items.TROPICAL_FISH, 2)),
            BlendData.of("Warped Forest Blend", Biomes.WARPED_FOREST, 0x3CABA8, "fluffy_blend", BlendData.entry(Items.WARPED_FUNGUS, 1), BlendData.entry(Items.WARPED_WART_BLOCK, 2)),
            BlendData.of("Windswept Forest Blend", Biomes.WINDSWEPT_FOREST, 0x5F8F3E, "fluffy_blend", BlendData.entry(Items.OAK_SAPLING, 1), BlendData.entry(Items.SPRUCE_SAPLING, 1)),
            BlendData.of("Windswept Gravelly Hills Blend", Biomes.WINDSWEPT_GRAVELLY_HILLS, 0x8A8A8A, "bumpy_blend", BlendData.entry(Items.GRAVEL, 2), BlendData.entry(Items.SPRUCE_SAPLING, 1)),
            BlendData.of("Windswept Hills Blend", Biomes.WINDSWEPT_HILLS, 0x92A379, "bumpy_blend", BlendData.entry(Items.SHORT_GRASS, 2), BlendData.entry(Items.SPRUCE_SAPLING, 1)),
            BlendData.of("Windswept Savanna Blend", Biomes.WINDSWEPT_SAVANNA, 0xA1A163, "bumpy_blend", BlendData.entry(Items.COARSE_DIRT, 2), BlendData.entry(Items.ACACIA_SAPLING, 1)),
            BlendData.of("Wooded Badlands Blend", Biomes.WOODED_BADLANDS, 0xC96435, "bumpy_blend", BlendData.entry(Items.COARSE_DIRT, 2), BlendData.entry(Items.OAK_SAPLING, 1))
    );

    public static List<BlendData> biomesOPlentyBlends = List.of(
            BlendData.of("Aspen Glade Blend", BOPBiomes.ASPEN_GLADE, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.YELLOW_MAPLE_SAPLING, 1), BlendData.entry(Items.BIRCH_LOG, 2)),
            BlendData.of("Auroral Garden Blend", BOPBiomes.AURORAL_GARDEN, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.RAINBOW_BIRCH_SAPLING, 1), BlendData.entry(Items.SNOWBALL, 2)),
            BlendData.of("Bayou Blend", BOPBiomes.BAYOU, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.WILLOW_SAPLING, 1), BlendData.entry(Items.FERN, 2)),
            BlendData.of("Bog Blend", BOPBiomes.BOG, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.RED_MAPLE_SAPLING, 1), BlendData.entry(BOPItems.BUSH, 2)),
            BlendData.of("Cold Desert Blend", BOPBiomes.COLD_DESERT, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(Items.GRAVEL, 3), BlendData.entry(Items.SNOWBALL, 1)),
            BlendData.of("Coniferous Forest Blend", BOPBiomes.CONIFEROUS_FOREST, ColorType.CALCULATED, "fluffy_blend", BlendData.entry(BOPItems.FIR_SAPLING, 1), BlendData.entry(Items.FERN, 1)),
            BlendData.of("Crag Blend", BOPBiomes.CRAG, ColorType.CALCULATED, "bumpy_blend", BlendData.entry(Items.MOSS_BLOCK, 2), BlendData.entry(Items.COBBLESTONE, 2)),
            BlendData.of("Crystalline Chasm Blend", BOPBiomes.CRYSTALLINE_CHASM, 0xE6BCC9,"bumpy_blend", BlendData.entry(BOPItems.ROSE_QUARTZ_CHUNK, 2), BlendData.entry(Items.NETHERRACK, 2)),
            BlendData.of("Dead Forest Blend", BOPBiomes.DEAD_FOREST, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.DEAD_SAPLING, 1), BlendData.entry(Items.DEAD_BUSH, 2)),
            BlendData.of("Dryland Blend", BOPBiomes.DRYLAND, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.PINE_SAPLING, 1), BlendData.entry(BOPItems.BUSH, 2)),
            BlendData.of("Dune Beach Blend", BOPBiomes.DUNE_BEACH, 0xE8D1A3, "bumpy_blend", BlendData.entry(BOPItems.SEA_OATS, 2), BlendData.entry(Items.SAND, 2)),
            BlendData.of("End Corruption Blend", BOPBiomes.END_CORRUPTION, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.NULL_BLOCK, 2), BlendData.entry(Items.END_STONE, 2)),
            BlendData.of("End Reef Blend", BOPBiomes.END_REEF, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.WISPJELLY, 1), BlendData.entry(Items.SAND, 2)),
            BlendData.of("End Wilds Blend", BOPBiomes.END_WILDS, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.EMPYREAL_SAPLING, 1), BlendData.entry(Items.END_STONE, 2)),
            BlendData.of("Erupting Inferno Blend", BOPBiomes.ERUPTING_INFERNO, 0xFF6B1A, "bumpy_blend", BlendData.entry(BOPItems.BRIMSTONE, 2), BlendData.entry(Items.NETHERRACK, 2)),
            BlendData.of("Field Blend", BOPBiomes.FIELD, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.TALL_WHITE_LAVENDER, 2), BlendData.entry(Items.SPRUCE_SAPLING, 1)),
            BlendData.of("Fir Clearing Blend", BOPBiomes.FIR_CLEARING, ColorType.CALCULATED, "fluffy_blend", BlendData.entry(BOPItems.FIR_SAPLING, 1), BlendData.entry(BOPItems.TOADSTOOL, 2)),
            BlendData.of("Floodplain Blend", BOPBiomes.FLOODPLAIN, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.WATERGRASS, 3), BlendData.entry(BOPItems.ORANGE_COSMOS, 1)),
            BlendData.of("Forested Field Blend", BOPBiomes.FORESTED_FIELD, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(Items.SPRUCE_SAPLING, 1), BlendData.entry(BOPItems.SPROUT, 2)),
            BlendData.of("Fungal Jungle Blend", BOPBiomes.FUNGAL_JUNGLE, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.TOADSTOOL, 2), BlendData.entry(Items.JUNGLE_SAPLING, 1)),
            BlendData.of("Glowing Grotto Blend", BOPBiomes.GLOWING_GROTTO, 0x3FEAD6, "rocky_blend", BlendData.entry(BOPItems.GLOWING_MOSS_BLOCK, 2), BlendData.entry(BOPItems.GLOWSHROOM, 1)),
            BlendData.of("Grassland Blend", BOPBiomes.GRASSLAND, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(Items.SHORT_GRASS, 4)),
            BlendData.of("Gravel Beach Blend", BOPBiomes.GRAVEL_BEACH, 0x9E9E9E, "bumpy_blend", BlendData.entry(Items.GRAVEL, 4)),
            BlendData.of("Highland Blend", BOPBiomes.HIGHLAND, 0x7B8F59, "bumpy_blend", BlendData.entry(Items.MOSSY_COBBLESTONE, 1), BlendData.entry(Items.SHORT_GRASS, 3)),
            BlendData.of("Hot Springs Blend", BOPBiomes.HOT_SPRINGS, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.PINE_SAPLING, 1), BlendData.entry(BOPItems.THERMAL_CALCITE, 2)),
            BlendData.of("Jacaranda Glade Blend", BOPBiomes.JACARANDA_GLADE, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.JACARANDA_SAPLING, 1), BlendData.entry(Items.SHORT_GRASS, 2)),
            BlendData.of("Jade Cliffs Blend", BOPBiomes.JADE_CLIFFS, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.PINE_SAPLING, 1), BlendData.entry(Items.SPRUCE_SAPLING, 2)),
            BlendData.of("Lavender Field Blend", BOPBiomes.LAVENDER_FIELD, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.LAVENDER, 3), BlendData.entry(BOPItems.JACARANDA_SAPLING, 1)),
            BlendData.of("Lush Desert Blend", BOPBiomes.LUSH_DESERT, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.ORANGE_SAND, 2), BlendData.entry(Items.ACACIA_SAPLING, 1)),
            BlendData.of("Lush Savanna Blend", BOPBiomes.LUSH_SAVANNA, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(Items.POPPY, 3), BlendData.entry(Items.ROSE_BUSH, 1)),
            BlendData.of("Maple Woods Blend", BOPBiomes.MAPLE_WOODS, 0xB94A2E, "fluffy_blend", BlendData.entry(BOPItems.RED_MAPLE_SAPLING, 2), BlendData.entry(BOPItems.VIOLET, 1)),
            BlendData.of("Marsh Blend", BOPBiomes.MARSH, 0x6A7E43, "bumpy_blend", BlendData.entry(BOPItems.REED, 2), BlendData.entry(BOPItems.WATERGRASS, 2)),
            BlendData.of("Mediterranean Forest Blend", BOPBiomes.MEDITERRANEAN_FOREST, ColorType.CALCULATED, "fluffy_blend", BlendData.entry(BOPItems.CYPRESS_SAPLING, 1), BlendData.entry(BOPItems.BUSH, 2)),
            BlendData.of("Moor Blend", BOPBiomes.MOOR, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.VIOLET, 3), BlendData.entry(Items.SHORT_GRASS, 1)),
            BlendData.of("Muskeg Blend", BOPBiomes.MUSKEG, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(Items.MUD, 2), BlendData.entry(Items.SNOWBALL, 2)),
            BlendData.of("Mystic Grove Blend", BOPBiomes.MYSTIC_GROVE, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.MAGIC_SAPLING, 1), BlendData.entry(BOPItems.BLUE_HYDRANGEA, 2)),
            BlendData.of("Old Growth Dead Forest Blend", BOPBiomes.OLD_GROWTH_DEAD_FOREST, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.DEAD_LOG, 2), BlendData.entry(Items.DEAD_BUSH, 1)),
            BlendData.of("Old Growth Woodland Blend", BOPBiomes.OLD_GROWTH_WOODLAND, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.TOADSTOOL, 1), BlendData.entry(Items.OAK_SAPLING, 2)),
            BlendData.of("Ominous Woods Blend", BOPBiomes.OMINOUS_WOODS, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.UMBRAN_SAPLING, 2), BlendData.entry(Items.SHORT_GRASS, 2)),
            BlendData.of("Orchard Blend", BOPBiomes.ORCHARD, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.FLOWERING_OAK_SAPLING, 1), BlendData.entry(BOPItems.CLOVER, 3)),
            BlendData.of("Origin Valley Blend", BOPBiomes.ORIGIN_VALLEY, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.ORIGIN_SAPLING, 1), BlendData.entry(Items.DIRT, 2)),
            BlendData.of("Overgrown Greens Blend", BOPBiomes.OVERGROWN_GREENS, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.HIGH_GRASS, 3), BlendData.entry(BOPItems.HUGE_CLOVER_PETAL, 1)),
            BlendData.of("Pasture Blend", BOPBiomes.PASTURE, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.BARLEY, 3)),
            BlendData.of("Prairie Blend", BOPBiomes.PRAIRIE, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.GOLDENROD, 2), BlendData.entry(BOPItems.BARLEY, 2)),
            BlendData.of("Pumpkin Patch Blend", BOPBiomes.PUMPKIN_PATCH, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.ORANGE_MAPLE_SAPLING, 1), BlendData.entry(Items.PUMPKIN, 3)),
            BlendData.of("Rainforest Blend", BOPBiomes.RAINFOREST, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.MAHOGANY_SAPLING, 1), BlendData.entry(Items.FERN, 2)),
            BlendData.of("Redwood Forest Blend", BOPBiomes.REDWOOD_FOREST, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.REDWOOD_SAPLING, 1), BlendData.entry(Items.FERN, 2)),
            BlendData.of("Rocky Rainforest Blend", BOPBiomes.ROCKY_RAINFOREST, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.MAHOGANY_SAPLING, 1), BlendData.entry(Items.TERRACOTTA, 2)),
            BlendData.of("Rocky Shrubland Blend", BOPBiomes.ROCKY_SHRUBLAND, ColorType.CALCULATED, "bumpy_blend", BlendData.entry(BOPItems.BUSH, 2), BlendData.entry(Items.COBBLESTONE, 2)),
            BlendData.of("Scrubland Blend", BOPBiomes.SCRUBLAND, ColorType.CALCULATED, "bumpy_blend", BlendData.entry(BOPItems.WILDFLOWER, 2), BlendData.entry(Items.SHORT_GRASS, 2)),
            BlendData.of("Seasonal Forest Blend", BOPBiomes.SEASONAL_FOREST, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.YELLOW_MAPLE_SAPLING, 1), BlendData.entry(BOPItems.ORANGE_MAPLE_SAPLING, 1), BlendData.entry(BOPItems.RED_MAPLE_SAPLING, 1)),
            BlendData.of("Shrubland Blend", BOPBiomes.SHRUBLAND, ColorType.CALCULATED, "bumpy_blend", BlendData.entry(BOPItems.BUSH, 1), BlendData.entry(Items.LILAC, 2)),
            BlendData.of("Snowblossom Grove Blend", BOPBiomes.SNOWBLOSSOM_GROVE, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.SNOWBLOSSOM_SAPLING, 1), BlendData.entry(BOPItems.WHITE_PETALS, 2)),
            BlendData.of("Snowy Coniferous Forest Blend", BOPBiomes.SNOWY_CONIFEROUS_FOREST, 0x9FC6B8, "fluffy_blend", BlendData.entry(BOPItems.FIR_SAPLING, 1), BlendData.entry(Items.SNOWBALL, 2)),
            BlendData.of("Snowy Fir Clearing Blend", BOPBiomes.SNOWY_FIR_CLEARING, 0xA7D0C2, "bumpy_blend", BlendData.entry(BOPItems.FIR_SAPLING, 1), BlendData.entry(Items.FERN, 1), BlendData.entry(Items.SNOWBALL, 2)),
            BlendData.of("Snowy Maple Woods Blend", BOPBiomes.SNOWY_MAPLE_WOODS, 0xC97B56, "fluffy_blend", BlendData.entry(BOPItems.RED_MAPLE_SAPLING, 1), BlendData.entry(Items.SNOWBALL, 2)),
            BlendData.of("Spider Nest Blend", BOPBiomes.SPIDER_NEST, 0x5E3E46, "rocky_blend", BlendData.entry(BOPItems.WEBBING, 2), BlendData.entry(BOPItems.HANGING_COBWEB, 1)),
            BlendData.of("Tropics Blend", BOPBiomes.TROPICS, 0x3CC7B2, "fluffy_blend", BlendData.entry(BOPItems.PALM_SAPLING, 1), BlendData.entry(BOPItems.BLUE_HYDRANGEA, 2)),
            BlendData.of("Tundra Blend", BOPBiomes.TUNDRA, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.RED_MAPLE_SAPLING, 1), BlendData.entry(Items.FERN, 2)),
            BlendData.of("Undergrowth Blend", BOPBiomes.UNDERGROWTH, 0x2E5D35, "bumpy_blend", BlendData.entry(BOPItems.HELLBARK_SAPLING, 1), BlendData.entry(BOPItems.BRAMBLE, 2)),
            BlendData.of("Visceral Heap Blend", BOPBiomes.VISCERAL_HEAP, 0x8E2B2F, "bumpy_blend", BlendData.entry(BOPItems.FLESH, 3)),
            BlendData.of("Volcanic Plains Blend", BOPBiomes.VOLCANIC_PLAINS, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.BLACK_SAND, 2), BlendData.entry(Items.FERN, 2)),
            BlendData.of("Volcano Blend", BOPBiomes.VOLCANO, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.BLACK_SANDSTONE, 1), BlendData.entry(Items.BASALT, 2)),
            BlendData.of("Wasteland Blend", BOPBiomes.WASTELAND, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.DRIED_SALT, 2), BlendData.entry(BOPItems.DEAD_LOG, 2)),
            BlendData.of("Wasteland Steppe Blend", BOPBiomes.WASTELAND_STEPPE, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.DESERT_GRASS, 2), BlendData.entry(Items.SHORT_GRASS, 2)),
            BlendData.of("Wetland Blend", BOPBiomes.WETLAND, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.WILLOW_SAPLING, 1), BlendData.entry(BOPItems.CATTAIL, 2)),
            BlendData.of("Wintry Origin Valley Blend", BOPBiomes.WINTRY_ORIGIN_VALLEY, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.ORIGIN_SAPLING, 1), BlendData.entry(Items.SNOWBALL, 3)),
            BlendData.of("Withered Abyss Blend", BOPBiomes.WITHERED_ABYSS, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(BOPItems.BLACKSTONE_BULB, 1), BlendData.entry(Items.BLACKSTONE, 2)),
            BlendData.of("Woodland Blend", BOPBiomes.WOODLAND, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BOPItems.TOADSTOOL, 1), BlendData.entry(Items.ROSE_BUSH, 2))
    );

    public static List<BlendData> biomesWeveGoneBlends = List.of(
            BlendData.of("Allium Shrubland Blend", BWGBiomes.ALLIUM_SHRUBLAND, 0x9787B8, "bumpy_blend", BlendData.entry(Items.ALLIUM, 1), BlendData.entry(BWGBlocks.WHITE_ALLIUM.getItem(), 1), BlendData.entry(BWGBlocks.PINK_ALLIUM.getItem(), 1)),
            BlendData.of("Amaranth Grassland Blend", BWGBiomes.AMARANTH_GRASSLAND, 0xE83256, "bumpy_blend", BlendData.entry(BWGItemTags.AMARANTH, 3)),
            BlendData.of("Araucaria Savanna Blend", BWGBiomes.ARAUCARIA_SAVANNA, ColorType.CALCULATED, "bumpy_blend", BlendData.entry(BWGWood.ARAUCARIA_SAPLING.getItem(), 1), BlendData.entry(Items.ACACIA_SAPLING, 1)),
            BlendData.of("Aspen Boreal Blend", BWGBiomes.ASPEN_BOREAL, 0x9FC77C, "fluffy_blend", BlendData.entry(BWGWood.ASPEN.sapling().getItem(), 1), BlendData.entry(BWGBlocks.ORANGE_DAISY.getItem(), 1)),
            BlendData.of("Atacama Outback Blend", BWGBiomes.ATACAMA_OUTBACK, 0xC9B873, "bumpy_blend", BlendData.entry(BWGBlocks.ALOE_VERA.get().asItem(), 1), BlendData.entry(BWGBlocks.CRACKED_RED_SAND.get().asItem(), 2)),
            BlendData.of("Baobab Savanna Blend", BWGBiomes.BAOBAB_SAVANNA, ColorType.CALCULATED, "bumpy_blend", BlendData.entry(BWGWood.BAOBAB.sapling().getItem(), 1), BlendData.entry(BWGItems.BAOBAB_FRUIT.get(), 2)),
            BlendData.of("Basalt Barrera Blend", BWGBiomes.BASALT_BARRERA, 0x808080, "bumpy_blend", BlendData.entry(Items.BASALT, 3), BlendData.entry(Items.SMOOTH_BASALT, 1)),
            BlendData.of("Bayou Blend", BWGBiomes.BAYOU, ColorType.GRASS_COLOR, "bumpy_blend", BlendData.entry(BWGWood.WILLOW.sapling().getItem(), 1), BlendData.entry(Items.VINE, 2)),
            BlendData.of("Black Forest Blend", BWGBiomes.BLACK_FOREST, ColorType.GRASS_COLOR, "fluffy_blend", BlendData.entry(BWGWood.PINE.sapling().getItem(), 1), BlendData.entry(BWGWood.FIR.sapling().getItem(), 1), BlendData.entry(BWGBlocks.MOSSY_STONE_SET.getBase().asItem(), 2)),
            BlendData.of("Canadian Shield Blend", BWGBiomes.CANADIAN_SHIELD, ColorType.CALCULATED, "fluffy_blend", BlendData.entry(BWGWood.FIR.sapling().getItem(), 1), BlendData.entry(Items.SPRUCE_SAPLING, 1), BlendData.entry(BWGBlocks.HYDRANGEA_HEDGE.get().asItem(), 1)),
            BlendData.of("Cika Woods Blend", BWGBiomes.CIKA_WOODS, ColorType.GRASS_COLOR, "fluffy_blend", BlendData.entry(BWGWood.CIKA.sapling().getItem(), 1), BlendData.entry(Items.PUMPKIN, 2)),
            BlendData.of("Coconino Meadow Blend", BWGBiomes.COCONINO_MEADOW, ColorType.GRASS_COLOR, "bumpy_blend", BlendData.entry(BWGBlocks.LUSH_DIRT.get().asItem(), 2), BlendData.entry(BWGBlocks.ANGELICA.getItem(), 1)),
            BlendData.of("Coniferous Forest Blend", BWGBiomes.CONIFEROUS_FOREST, ColorType.CALCULATED, "fluffy_blend", BlendData.entry(BWGWood.FIR.sapling().getItem(), 1), BlendData.entry(Items.BROWN_MUSHROOM, 1), BlendData.entry(BWGBlocks.BLUE_ROSE_BUSH.get().asItem(), 1)),
            BlendData.of("Crag Gardens Blend", BWGBiomes.CRAG_GARDENS, ColorType.GRASS_COLOR, "fluffy_blend", BlendData.entry(BWGWood.MAHOGANY.sapling().getItem(), 1), BlendData.entry(BWGWood.RAINBOW_EUCALYPTUS.sapling().getItem(), 1), BlendData.entry(Items.BAMBOO, 2)),
            BlendData.of("Crimson Tundra Blend", BWGBiomes.CRIMSON_TUNDRA, ColorType.GRASS_COLOR, "bumpy_blend", BlendData.entry(BWGBlocks.ROSE.getItem(), 1), BlendData.entry(BWGBlocks.PEAT.get().asItem(), 2)),
            BlendData.of("Cypress Swamplands Blend", BWGBiomes.CYPRESS_SWAMPLANDS, ColorType.GRASS_COLOR, "bumpy_blend", BlendData.entry(BWGWood.CYPRESS.sapling().getItem(), 1), BlendData.entry(BWGBlocks.CATTAIL_SPROUT.get().asItem(), 2)),
            BlendData.of("Cypress Wetlands Blend", BWGBiomes.CYPRESS_WETLANDS, ColorType.CALCULATED, "bumpy_blend", BlendData.entry(BWGWood.CYPRESS.sapling().getItem(), 1), BlendData.entry(BWGBlocks.CATTAIL_SPROUT.get().asItem(), 1), BlendData.entry(BWGBlocks.FLUORESCENT_CATTAIL_SPROUT.get().asItem(), 1)),
            BlendData.of("Dacite Ridges Blend", BWGBiomes.DACITE_RIDGES, ColorType.GRASS_COLOR, "bumpy_blend", BlendData.entry(BWGWood.HOLLY.sapling().getItem(), 1), BlendData.entry(Items.SPRUCE_SAPLING, 1), BlendData.entry(BWGBlocks.DACITE_SET.getBase().asItem(), 2)),
            BlendData.of("Dacite Shore Blend", BWGBiomes.DACITE_SHORE, 0xA0A0A0, "bumpy_blend", BlendData.entry(BWGBlocks.WHITE_SAND_SET.getSand().asItem(), 2), BlendData.entry(BWGBlocks.WHITE_DACITE_SET.getBase().asItem(), 2)),
            BlendData.of("Dead Sea Blend", BWGBiomes.DEAD_SEA, ColorType.WATER_COLOR, "bumpy_blend", BlendData.entry(Items.GRAVEL, 2), BlendData.entry(Items.POINTED_DRIPSTONE, 2)),
            BlendData.of("Ebony Woods Blend", BWGBiomes.EBONY_WOODS, ColorType.GRASS_COLOR, "fluffy_blend", BlendData.entry(BWGWood.EBONY.sapling().getItem(), 1), BlendData.entry(BWGBlocks.WHITE_ANEMONE.getItem(), 2)),
            BlendData.of("Enchanted Tangle Blend", BWGBiomes.ENCHANTED_TANGLE, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BWGWood.BLUE_ENCHANTED.sapling().getItem(), 1), BlendData.entry(BWGWood.GREEN_ENCHANTED.sapling().getItem(), 1), BlendData.entry(BWGBlocks.WOOD_BLEWIT.get().asItem(), 1)),
            // Eroded Borealis?
            BlendData.of("Firecracker Chaparral Blend", BWGBiomes.FIRECRACKER_CHAPARRAL,0xE83256, "bumpy_blend", BlendData.entry(BWGBlocks.FIRECRACKER_FLOWER_BUSH.getItem(), 2), BlendData.entry(BWGBlocks.SHELF_FUNGI.get().asItem(), 1)),
            BlendData.of("Forgotten Forest Blend", BWGBiomes.FORGOTTEN_FOREST, ColorType.CALCULATED, "fluffy_blend", BlendData.entry(BWGWood.SILVER_MAPLE_SAPLING.getItem(), 1), BlendData.entry(BWGWood.EBONY.sapling().getItem(), 1), BlendData.entry(Items.DARK_OAK_SAPLING, 1), BlendData.entry(BWGBlocks.GREEN_MUSHROOM.get().asItem(), 1)),
            BlendData.of("Fragment Jungle Blend", BWGBiomes.FRAGMENT_JUNGLE, ColorType.GRASS_COLOR, "fluffy_blend", BlendData.entry(BWGWood.MAHOGANY.sapling().getItem(), 1), BlendData.entry(BWGWood.RAINBOW_EUCALYPTUS.sapling().getItem(), 1), BlendData.entry(BWGBlocks.DELPHINIUM.get().asItem(), 1)),
            BlendData.of("Frosted Coniferous Forest Blend", BWGBiomes.FROSTED_CONIFEROUS_FOREST, ColorType.CALCULATED, "fluffy_blend", BlendData.entry(BWGWood.FIR.sapling().getItem(), 1), BlendData.entry(Items.SPRUCE_SAPLING, 1), BlendData.entry(BWGBlocks.HYDRANGEA_HEDGE.get().asItem(), 1), BlendData.entry(Items.SNOWBALL, 1)),
            BlendData.of("Frosted Taiga Blend", BWGBiomes.FROSTED_TAIGA, ColorType.CALCULATED, "fluffy_blend", BlendData.entry(BWGWood.BLUE_SPRUCE_SAPLING.getItem(), 1), BlendData.entry(Items.SPRUCE_SAPLING, 1), BlendData.entry(Items.SNOWBALL, 1)),
            BlendData.of("Howling Peaks Blend", BWGBiomes.HOWLING_PEAKS, ColorType.GRASS_COLOR, "bumpy_blend", BlendData.entry(BWGWood.ORANGE_BIRCH_SAPLING.getItem(), 1), BlendData.entry(BWGWood.YELLOW_BIRCH_SAPLING.getItem(), 1), BlendData.entry(Items.SPRUCE_SAPLING, 1), BlendData.entry(Items.SNOWBALL, 1)),
            BlendData.of("Ironwood Gour Blend", BWGBiomes.IRONWOOD_GOUR, ColorType.CALCULATED, "bumpy_blend", BlendData.entry(BWGWood.IRONWOOD.sapling().getItem(), 1), BlendData.entry(Items.ACACIA_SAPLING, 1), BlendData.entry(BWGBlocks.CALIFORNIA_POPPY.getItem(), 1)),
            BlendData.of("Jacaranda Jungle Blend", BWGBiomes.JACARANDA_JUNGLE, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BWGWood.JACARANDA.sapling().getItem(), 1), BlendData.entry(BWGWood.INDIGO_JACARANDA_SAPLING.getItem(), 1)),
            BlendData.of("Lush Stacks Blend", BWGBiomes.LUSH_STACKS, ColorType.WATER_COLOR, "bumpy_blend", BlendData.entry(Items.MOSS_BLOCK, 2), BlendData.entry(BWGBlocks.MOSSY_STONE_SET.getBase().asItem(), 2)),
            BlendData.of("Maple Taiga Blend", BWGBiomes.MAPLE_TAIGA, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BWGWood.MAPLE.sapling().getItem(), 1), BlendData.entry(BWGWood.RED_MAPLE_SAPLING.getItem(), 1), BlendData.entry(Items.SPRUCE_SAPLING, 1)),
            BlendData.of("Mojave Desert Blend", BWGBiomes.MOJAVE_DESERT, 0xC9B873, "bumpy_blend", BlendData.entry(BWGBlocks.ALOE_VERA.get().asItem(), 1), BlendData.entry(BWGBlocks.BEACH_GRASS.get().asItem(), 2)),
            BlendData.of("Orchard Blend", BWGBiomes.ORCHARD, ColorType.GRASS_COLOR, "bumpy_blend", BlendData.entry(BWGWood.ORCHARD_SAPLING.getItem(), 1), BlendData.entry(BWGBlocks.WHITE_ALLIUM.getItem(), 1), BlendData.entry(BWGBlocks.LOLLIPOP_FLOWER.getItem(), 1)),
            BlendData.of("Overgrowth Woodlands Blend", BWGBiomes.OVERGROWTH_WOODLANDS, ColorType.CALCULATED, "fluffy_blend", BlendData.entry(BWGWood.YELLOW_BIRCH_SAPLING.getItem(), 1), BlendData.entry(Items.BIRCH_SAPLING, 1), BlendData.entry(BWGBlocks.SHELF_FUNGI.get().asItem(), 1)),
            BlendData.of("Pale Bog Blend", BWGBiomes.PALE_BOG, ColorType.GRASS_COLOR, "fluffy_blend", BlendData.entry(BWGWood.SPIRIT.sapling().getItem(), 1), BlendData.entry(BWGBlocks.PALE_MUD.get().asItem(), 2)),
            BlendData.of("Prairie Blend", BWGBiomes.PRAIRIE, ColorType.GRASS_COLOR, "bumpy_blend", BlendData.entry(BWGBlocks.PRAIRIE_GRASS.get().asItem(), 3), BlendData.entry(BWGBlocks.CALIFORNIA_POPPY.getItem(), 1)),
            BlendData.of("Pumpkin Valley Blend", BWGBiomes.PUMPKIN_VALLEY, ColorType.FOLIAGE_COLOR, "bumpy_blend", BlendData.entry(Items.PUMPKIN, 3), BlendData.entry(BWGBlocks.CROCUS.getItem(), 1)),
            BlendData.of("Rainbow Beach Blend", BWGBiomes.RAINBOW_BEACH, 0xA0A0A0, "bumpy_blend", BlendData.entry(BWGBlocks.BLUE_SAND_SET.getSand().asItem(), 2), BlendData.entry(BWGBlocks.BEACH_GRASS.get().asItem(), 2)),
            BlendData.of("Red Rock Peaks Blend", BWGBiomes.RED_ROCK_PEAKS, ColorType.GRASS_COLOR, "bumpy_blend", BlendData.entry(BWGWood.PINE.sapling().getItem(), 1), BlendData.entry(BWGBlocks.RED_ROCK_SET.getBase().asItem(), 2)),
            BlendData.of("Red Rock Valley Blend", BWGBiomes.RED_ROCK_VALLEY, ColorType.GRASS_COLOR, "bumpy_blend", BlendData.entry(BWGWood.PINE.sapling().getItem(), 1), BlendData.entry(BWGBlocks.RED_ROCK_SET.getBase().asItem(), 1), BlendData.entry(BWGBlocks.FIRECRACKER_FLOWER_BUSH.getItem(), 1)),
            BlendData.of("Redwood Thicket Blend", BWGBiomes.REDWOOD_THICKET, ColorType.GRASS_COLOR, "fluffy_blend", BlendData.entry(BWGWood.REDWOOD.sapling().getItem(), 1), BlendData.entry(BWGBlocks.FIRECRACKER_FLOWER_BUSH.getItem(), 1), BlendData.entry(BWGBlocks.ROCKY_STONE_SET.getBase().asItem(), 2)),
            BlendData.of("Rose Fields Blend", BWGBiomes.ROSE_FIELDS, ColorType.GRASS_COLOR, "bumpy_blend", BlendData.entry(BWGBlocks.BLACK_ROSE.getItem(), 1), BlendData.entry(Items.ROSE_BUSH, 1), BlendData.entry(BWGBlocks.OSIRIA_ROSE.getItem(), 1)),
            BlendData.of("Rugged Badlands Blend", BWGBiomes.RUGGED_BADLANDS, ColorType.GRASS_COLOR, "bumpy_blend", BlendData.entry(BWGWood.PALO_VERDE_SAPLING.getItem(), 1), BlendData.entry(BWGBlocks.BEACH_GRASS.get().asItem(), 2)),
            BlendData.of("Sakura Grove Blend", BWGBiomes.SAKURA_GROVE, ColorType.GRASS_COLOR, "fluffy_blend", BlendData.entry(BWGWood.WHITE_SAKURA_SAPLING.getItem(), 1), BlendData.entry(BWGWood.YELLOW_SAKURA_SAPLING.getItem(), 1), BlendData.entry(Items.CHERRY_SAPLING, 1)),
            BlendData.of("Shattered Glacier Blend", BWGBiomes.SHATTERED_GLACIER, 0xA0A0A0, "bumpy_blend", BlendData.entry(BWGBlocks.PACKED_BLACK_ICE.get().asItem(), 3)),
            BlendData.of("Sierra Badlands Blend", BWGBiomes.SIERRA_BADLANDS, ColorType.GRASS_COLOR, "bumpy_blend", BlendData.entry(Items.OAK_SAPLING, 1), BlendData.entry(BWGBlocks.FIRECRACKER_FLOWER_BUSH.getItem(), 1), BlendData.entry(Items.TERRACOTTA, 2)),
            BlendData.of("Skyris Vale Blend", BWGBiomes.SKYRIS_VALE, ColorType.GRASS_COLOR, "bumpy_blend", BlendData.entry(BWGWood.SKYRIS.sapling().getItem(), 1), BlendData.entry(BWGWood.BLUE_SPRUCE_SAPLING.getItem(), 1), BlendData.entry(BWGBlocks.FOXGLOVE.get().asItem(), 1)),
            BlendData.of("Temperate Grove Blend", BWGBiomes.TEMPERATE_GROVE, ColorType.GRASS_COLOR, "bumpy_blend", BlendData.entry(BWGWood.YELLOW_BIRCH_SAPLING.getItem(), 1), BlendData.entry(Items.BIRCH_SAPLING, 1), BlendData.entry(Items.SUNFLOWER, 1)),
            BlendData.of("Tropical Rainforest Blend", BWGBiomes.TROPICAL_RAINFOREST, ColorType.GRASS_COLOR, "fluffy_blend", BlendData.entry(BWGWood.MAHOGANY.sapling().getItem(), 1), BlendData.entry(BWGBlocks.GREEN_MUSHROOM.get().asItem(), 1), BlendData.entry(BWGBlocks.DELPHINIUM.get().asItem(), 1)),
            BlendData.of("Weeping Witch Forest Blend", BWGBiomes.WEEPING_WITCH_FOREST, ColorType.GRASS_COLOR, "fluffy_blend", BlendData.entry(BWGWood.WITCH_HAZEL.sapling().getItem(), 1), BlendData.entry(BWGBlocks.CROCUS.getItem(), 1), BlendData.entry(BWGBlocks.PEAT.get().asItem(), 2)),
            BlendData.of("White Mangrove Marshes Blend", BWGBiomes.WHITE_MANGROVE_MARSHES, ColorType.GRASS_COLOR, "fluffy_blend", BlendData.entry(BWGWood.WHITE_MANGROVE.sapling().getItem(), 1), BlendData.entry(BWGBlocks.CATTAIL_SPROUT.get().asItem(), 2)),
            BlendData.of("Windswept Desert Blend", BWGBiomes.WINDSWEPT_DESERT, 0xC9B873, "bumpy_blend", BlendData.entry(Items.CACTUS, 2), BlendData.entry(BWGBlocks.WINDSWEPT_SAND_SET.getSand().asItem(), 2)),
            BlendData.of("Zelkova Forest Blend", BWGBiomes.ZELKOVA_FOREST, ColorType.FOLIAGE_COLOR, "fluffy_blend", BlendData.entry(BWGWood.ZELKOVA.sapling().getItem(), 1), BlendData.entry(Items.SHORT_GRASS, 2))
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
