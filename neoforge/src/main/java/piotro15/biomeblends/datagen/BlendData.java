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
import java.util.List;
import java.util.Map;

public record BlendData(String name, ResourceKey<Biome> id, BlendType blendType, String model, Map<Item, Integer> ingredients) {
    public static final List<BlendData> blends = List.of(
            defaultBlendData("Badlands Blend", Biomes.BADLANDS, 0xD27D2C, "bumpy_blend", Map.of(Items.RED_SAND, 2, Items.TERRACOTTA, 2)),
            defaultBlendData("Bamboo Jungle Blend", Biomes.BAMBOO_JUNGLE, 0x3FA348, "bumpy_blend", Map.of(Items.BAMBOO, 3, Items.JUNGLE_SAPLING, 1)),
            defaultBlendData("Basalt Deltas Blend", Biomes.BASALT_DELTAS, 0x4A4A4A, "bumpy_blend", Map.of(Items.BASALT, 2, Items.MAGMA_BLOCK, 2)),
            defaultBlendData("Beach Blend", Biomes.BEACH, 0xFAE6A0, "bumpy_blend", Map.of(Items.SAND, 2, Items.SEAGRASS, 1)),
            defaultBlendData("Birch Forest Blend", Biomes.BIRCH_FOREST, 0xFFFF3F, "fluffy_blend", Map.of(Items.SHORT_GRASS, 2, Items.BIRCH_SAPLING, 1)),
            defaultBlendData("Cherry Grove Blend", Biomes.CHERRY_GROVE, 0xFFA7DD, "fluffy_blend", Map.of(Items.PINK_PETALS, 2, Items.CHERRY_SAPLING, 1)),
            defaultBlendData("Cold Ocean Blend", Biomes.COLD_OCEAN, 0x2D5D87, "bumpy_blend", Map.of(Items.SALMON, 2, Items.SEAGRASS, 2)),
            defaultBlendData("Crimson Forest Blend", Biomes.CRIMSON_FOREST, 0x842626, "fluffy_blend", Map.of(Items.CRIMSON_FUNGUS, 1, Items.NETHER_WART_BLOCK, 2)),
            defaultBlendData("Dark Forest Blend", Biomes.DARK_FOREST, 0x3AA622, "fluffy_blend", Map.of(Items.RED_MUSHROOM, 1, Items.BROWN_MUSHROOM, 1, Items.DARK_OAK_SAPLING, 1)),
            defaultBlendData("Deep Cold Ocean Blend", Biomes.DEEP_COLD_OCEAN, 0x1D3C5A, "bumpy_blend", Map.of(Items.SALMON, 2, Items.KELP, 2)),
            defaultBlendData("Deep Dark Blend", Biomes.DEEP_DARK, 0x002F43, "rocky_blend", Map.of(Items.SCULK, 2, Items.ECHO_SHARD, 1)),
            defaultBlendData("Deep Frozen Ocean Blend", Biomes.DEEP_FROZEN_OCEAN, 0x3E6F8F, "bumpy_blend", Map.of(Items.ICE, 2, Items.KELP, 2)),
            defaultBlendData("Deep Lukewarm Ocean Blend", Biomes.DEEP_LUKEWARM_OCEAN, 0x478BA5, "bumpy_blend", Map.of(Items.PUFFERFISH, 1, Items.KELP, 2)),
            defaultBlendData("Deep Ocean Blend", Biomes.DEEP_OCEAN, 0x1C3E50, "bumpy_blend", Map.of(Items.COD, 2, Items.KELP, 2)),
            defaultBlendData("Desert Blend", Biomes.DESERT, 0xE5C27B, "bumpy_blend", Map.of(Items.SAND, 2, Items.DEAD_BUSH, 2)),
            defaultBlendData("Dripstone Caves Blend", Biomes.DRIPSTONE_CAVES, 0xA17E70, "rocky_blend", Map.of(Items.POINTED_DRIPSTONE, 2, Items.COPPER_INGOT, 2)),
            defaultBlendData("End Barrens Blend", Biomes.END_BARRENS, 0xCFCDBF, "bumpy_blend", Map.of(Items.END_STONE, 2, Items.ENDER_PEARL, 1)),
            defaultBlendData("End Highlands Blend", Biomes.END_HIGHLANDS, 0xBBBBAA, "bumpy_blend", Map.of(Items.END_STONE, 2, Items.CHORUS_FLOWER, 2)),
            defaultBlendData("End Midlands Blend", Biomes.END_MIDLANDS, 0xC2C2AC, "bumpy_blend", Map.of(Items.END_STONE, 1, Items.POPPED_CHORUS_FRUIT, 2)),
            defaultBlendData("Eroded Badlands Blend", Biomes.ERODED_BADLANDS, 0xB34A1C, "bumpy_blend", Map.of(Items.DEAD_BUSH, 2, Items.TERRACOTTA, 2)),
            defaultBlendData("Flower Forest Blend", Biomes.FLOWER_FOREST, 0x70D85B, "fluffy_blend", Map.of(Items.WHITE_TULIP, 1, Items.PINK_TULIP, 1, Items.RED_TULIP, 1, Items.ORANGE_TULIP, 1)),
            defaultBlendData("Forest Blend", Biomes.FOREST, 0x69D23C, "fluffy_blend", Map.of(Items.OAK_SAPLING, 1, Items.BIRCH_SAPLING, 1)),
            defaultBlendData("Frozen Ocean Blend", Biomes.FROZEN_OCEAN, 0x8AD2E3, "bumpy_blend", Map.of(Items.ICE, 2, Items.SEAGRASS, 2)),
            defaultBlendData("Frozen Peaks Blend", Biomes.FROZEN_PEAKS, 0xD6E7F2, "bumpy_blend", Map.of(Items.ICE, 2, Items.PACKED_ICE, 2)),
            defaultBlendData("Frozen River Blend", Biomes.FROZEN_RIVER, 0xBCE3F2, "bumpy_blend", Map.of(Items.WATER_BUCKET, 1, Items.ICE, 2)),
            defaultBlendData("Grove Blend", Biomes.GROVE, 0x9FC77C, "bumpy_blend", Map.of(Items.POWDER_SNOW_BUCKET, 1, Items.SPRUCE_SAPLING, 1)),
            defaultBlendData("Ice Spikes Blend", Biomes.ICE_SPIKES, 0xCDE7F5, "bumpy_blend", Map.of(Items.SNOW_BLOCK, 2, Items.PACKED_ICE, 2)),
            defaultBlendData("Jagged Peaks Blend", Biomes.JAGGED_PEAKS, 0xBFD4E0, "bumpy_blend", Map.of(Items.SNOW_BLOCK, 2, Items.EMERALD, 1)),
            defaultBlendData("Jungle Blend", Biomes.JUNGLE, 0x4CAF50, "bumpy_blend", Map.of(Items.JUNGLE_SAPLING, 1, Items.COCOA_BEANS, 1, Items.MELON_SLICE, 2)),
            defaultBlendData("Lukewarm Ocean Blend", Biomes.LUKEWARM_OCEAN, 0x62C2A3, "bumpy_blend", Map.of(Items.PUFFERFISH, 1, Items.SEAGRASS, 2)),
            defaultBlendData("Lush Caves Blend", Biomes.LUSH_CAVES, 0x8ECB51, "rocky_blend", Map.of(Items.GLOW_BERRIES, 2, Items.MOSS_BLOCK, 1)),
            defaultBlendData("Mangrove Swamp Blend", Biomes.MANGROVE_SWAMP, 0x556B2F, "bumpy_blend", Map.of(Items.MANGROVE_PROPAGULE, 1, Items.MUD, 2)),
            defaultBlendData("Meadow Blend", Biomes.MEADOW, 0x9DD382, "bumpy_blend", Map.of(Items.OXEYE_DAISY, 2, Items.CORNFLOWER, 2)),
            defaultBlendData("Mushroom Fields Blend", Biomes.MUSHROOM_FIELDS, 0xA892B0, "bumpy_blend", Map.of(Items.RED_MUSHROOM, 2, Items.BROWN_MUSHROOM, 2)),
            defaultBlendData("Nether Wastes Blend", Biomes.NETHER_WASTES, 0xB33A26, "bumpy_blend", Map.of(Items.NETHERRACK, 3, Items.QUARTZ, 1)),
            defaultBlendData("Ocean Blend", Biomes.OCEAN, 0x2A5B88, "bumpy_blend", Map.of(Items.SEAGRASS, 2, Items.COD, 2)),
            defaultBlendData("Old Growth Birch Forest Blend", Biomes.OLD_GROWTH_BIRCH_FOREST, 0x7DC875, "fluffy_blend", Map.of(Items.BIRCH_SAPLING, 2, Items.SHORT_GRASS, 2)),
            defaultBlendData("Old Growth Pine Taiga Blend", Biomes.OLD_GROWTH_PINE_TAIGA, 0x597D48, "fluffy_blend", Map.of(Items.MOSSY_COBBLESTONE, 2, Items.SPRUCE_SAPLING, 1)),
            defaultBlendData("Old Growth Spruce Taiga Blend", Biomes.OLD_GROWTH_SPRUCE_TAIGA, 0x4C6A3D, "fluffy_blend", Map.of(Items.MOSSY_COBBLESTONE, 2, Items.FERN, 2)),
            defaultBlendData("Plains Blend", Biomes.PLAINS, 0x91BD59, "bumpy_blend", Map.of(Items.SHORT_GRASS, 2, Items.WHEAT_SEEDS, 2)),
            defaultBlendData("River Blend", Biomes.RIVER, 0x4D9CB5, "bumpy_blend", Map.of(Items.CLAY_BALL, 2, Items.WATER_BUCKET, 1)),
            defaultBlendData("Savanna Blend", Biomes.SAVANNA, 0xBDB855, "bumpy_blend", Map.of(Items.ACACIA_SAPLING, 1, Items.SHORT_GRASS, 2)),
            defaultBlendData("Savanna Plateau Blend", Biomes.SAVANNA_PLATEAU, 0xC9C76C, "bumpy_blend", Map.of(Items.ACACIA_SAPLING, 2, Items.SHORT_GRASS, 1)),
            defaultBlendData("Small End Islands Blend", Biomes.SMALL_END_ISLANDS, 0xAFAFAF, "bumpy_blend", Map.of(Items.END_STONE, 1, Items.ENDER_PEARL, 2)),
            defaultBlendData("Snowy Beach Blend", Biomes.SNOWY_BEACH, 0xE8F4FA, "bumpy_blend", Map.of(Items.SNOWBALL, 2, Items.SAND, 2)),
            defaultBlendData("Snowy Plains Blend", Biomes.SNOWY_PLAINS, 0xF5F5F5, "bumpy_blend", Map.of(Items.SNOW_BLOCK, 2, Items.WHEAT_SEEDS, 2)),
            defaultBlendData("Snowy Slopes Blend", Biomes.SNOWY_SLOPES, 0xE0EAF0, "bumpy_blend", Map.of(Items.SNOW_BLOCK, 2, Items.POWDER_SNOW_BUCKET, 1)),
            defaultBlendData("Snowy Taiga Blend", Biomes.SNOWY_TAIGA, 0xAECFD0, "fluffy_blend", Map.of(Items.SNOWBALL, 2, Items.SWEET_BERRIES, 2)),
            defaultBlendData("Soul Sand Valley Blend", Biomes.SOUL_SAND_VALLEY, 0x83624B, "bumpy_blend", Map.of(Items.SOUL_SOIL, 2, Items.BONE_BLOCK, 2)),
            defaultBlendData("Sparse Jungle Blend", Biomes.SPARSE_JUNGLE, 0x6AA75E, "bumpy_blend", Map.of(Items.JUNGLE_SAPLING, 1, Items.COCOA_BEANS, 2)),
            defaultBlendData("Stony Peaks Blend", Biomes.STONY_PEAKS, 0xA0A0A0, "bumpy_blend", Map.of(Items.EMERALD, 1, Items.CALCITE, 2)),
            defaultBlendData("Stony Shore Blend", Biomes.STONY_SHORE, 0x808080, "bumpy_blend", Map.of(Items.STONE, 2, Items.GRAVEL, 2)),
            defaultBlendData("Sunflower Plains Blend", Biomes.SUNFLOWER_PLAINS, 0xA2D84F, "bumpy_blend", Map.of(Items.SUNFLOWER, 2, Items.SHORT_GRASS, 2)),
            defaultBlendData("Swamp Blend", Biomes.SWAMP, 0x556B2F, "bumpy_blend", Map.of(Items.VINE, 2, Items.SLIME_BALL, 2)),
            defaultBlendData("Taiga Blend", Biomes.TAIGA, 0x587C4A, "fluffy_blend", Map.of(Items.SPRUCE_SAPLING, 1, Items.SWEET_BERRIES, 2)),
            defaultBlendData("The End Blend", Biomes.THE_END, 0xCFCDBF, "bumpy_blend", Map.of(Items.END_STONE, 2, Items.DRAGON_BREATH, 2)),
            defaultBlendData("The Void Blend", Biomes.THE_VOID, 0x000000, "bumpy_blend", Map.of(Items.GLASS_BOTTLE, 2)),
            defaultBlendData("Warm Ocean Blend", Biomes.WARM_OCEAN, 0x42C5B8, "bumpy_blend", Map.of(Items.SAND, 2, Items.TROPICAL_FISH, 2)),
            defaultBlendData("Warped Forest Blend", Biomes.WARPED_FOREST, 0x3CABA8, "fluffy_blend", Map.of(Items.WARPED_FUNGUS, 1, Items.WARPED_WART_BLOCK, 2)),
            defaultBlendData("Windswept Forest Blend", Biomes.WINDSWEPT_FOREST, 0x5F8F3E, "fluffy_blend", Map.of(Items.OAK_SAPLING, 1, Items.SPRUCE_SAPLING, 1)),
            defaultBlendData("Windswept Gravelly Hills Blend", Biomes.WINDSWEPT_GRAVELLY_HILLS, 0x8A8A8A, "bumpy_blend", Map.of(Items.GRAVEL, 2, Items.SPRUCE_SAPLING, 1)),
            defaultBlendData("Windswept Hills Blend", Biomes.WINDSWEPT_HILLS, 0x92A379, "bumpy_blend", Map.of(Items.SHORT_GRASS, 2, Items.SPRUCE_SAPLING, 1)),
            defaultBlendData("Windswept Savanna Blend", Biomes.WINDSWEPT_SAVANNA, 0xA1A163, "bumpy_blend", Map.of(Items.COARSE_DIRT, 2, Items.ACACIA_SAPLING, 1)),
            defaultBlendData("Wooded Badlands Blend", Biomes.WOODED_BADLANDS, 0xC96435, "bumpy_blend", Map.of(Items.COARSE_DIRT, 2, Items.OAK_SAPLING, 1))
    );

    public static List<BlendData> biomesOPlentyBlends = List.of(
            generatedColorBlendData("Aspen Glade Blend", BOPBiomes.ASPEN_GLADE, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.of(BOPItems.YELLOW_MAPLE_SAPLING, 1, Items.BIRCH_LOG, 2)),
            generatedColorBlendData("Auroral Garden Blend", BOPBiomes.AURORAL_GARDEN, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.of(BOPItems.RAINBOW_BIRCH_SAPLING, 1, Items.SNOWBALL, 2)),
            generatedColorBlendData("Bayou Blend", BOPBiomes.BAYOU, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.of(BOPItems.WILLOW_SAPLING, 1, Items.FERN, 2)),
            generatedColorBlendData("Bog Blend", BOPBiomes.BOG, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.of(BOPItems.RED_MAPLE_SAPLING, 1, BOPItems.BUSH, 2)),
            generatedColorBlendData("Cold Desert Blend", BOPBiomes.COLD_DESERT, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.of(Items.GRAVEL, 3, Items.SNOWBALL, 1)),
            defaultBlendData("Coniferous Forest Blend", BOPBiomes.CONIFEROUS_FOREST, 0xC96435, "fluffy_blend", Map.of(BOPItems.FIR_SAPLING, 1, Items.FERN, 1)),
            defaultBlendData("Crag Blend", BOPBiomes.CRAG, 0xC96435, "bumpy_blend", Map.of(Items.MOSS_BLOCK, 2, Items.COBBLESTONE, 2)),
            defaultBlendData("Crystalline Chasm Blend", BOPBiomes.CRYSTALLINE_CHASM, 0xC96435,"bumpy_blend", Map.of(BOPItems.ROSE_QUARTZ_CHUNK, 2, Items.NETHERRACK, 2)),
            generatedColorBlendData("Dead Forest Blend", BOPBiomes.DEAD_FOREST, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.of(BOPItems.DEAD_SAPLING, 1, Items.DEAD_BUSH, 2)),
            generatedColorBlendData("Dryland Blend", BOPBiomes.DRYLAND, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.of(BOPItems.PINE_SAPLING, 1, BOPItems.BUSH, 2)),
            defaultBlendData("Dune Beach Blend", BOPBiomes.DUNE_BEACH, 0xC96435, "bumpy_blend", Map.of(BOPItems.SEA_OATS, 2, Items.SAND, 2)),
            generatedColorBlendData("End Corruption Blend", BOPBiomes.END_CORRUPTION, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.of(BOPItems.NULL_BLOCK, 2, Items.END_STONE, 2)),
            generatedColorBlendData("End Reef Blend", BOPBiomes.END_REEF, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.of(BOPItems.WISPJELLY, 1, Items.SAND, 2)),
            generatedColorBlendData("End Wilds Blend", BOPBiomes.END_WILDS, ColorType.FOLIAGE_COLOR, "fluffy_blend", Map.of(BOPItems.EMPYREAL_SAPLING, 1, Items.END_STONE, 2)),
            defaultBlendData("Erupting Inferno Blend", BOPBiomes.ERUPTING_INFERNO, 0xC96435, "bumpy_blend", Map.of(BOPItems.BRIMSTONE, 2, Items.NETHERRACK, 2)),
            generatedColorBlendData("Field Blend", BOPBiomes.FIELD, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.of(BOPItems.TALL_WHITE_LAVENDER, 2, Items.SPRUCE_SAPLING, 1)),
            defaultBlendData("Fir Clearing Blend", BOPBiomes.FIR_CLEARING, 0xC96435, "fluffy_blend", Map.of(BOPItems.FIR_SAPLING, 1, BOPItems.TOADSTOOL, 2)),
            generatedColorBlendData("Floodplain Blend", BOPBiomes.FLOODPLAIN, ColorType.FOLIAGE_COLOR, "bumpy_blend", Map.of(BOPItems.WATERGRASS, 3, BOPItems.ORANGE_COSMOS, 1))
    );

    private static BlendData generatedColorBlendData(String name, ResourceKey<Biome> id, ColorType colorType, String model, Map<Item, Integer> ingredients) {
        BlendType.BlendTypeBuilder builder = new BlendType.BlendTypeBuilder()
                .action(new SetBiomeAction(id.location()))
                .color(findColorForBiome(id, colorType));

        return new BlendData(name, id, builder.build(), model, ingredients);
    }

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
                    if (effects.has("temperature") && effects.has("downfall")) {
                        float temp = json.get("temperature").getAsFloat();
                        float downfall = json.get("downfall").getAsFloat();
                        int color = FoliageColor.get(temp, downfall);
                        System.out.println("Calculated color for " + id.location() + ": " + Integer.toHexString(color));
                        return color;
                    }
                }
            }
            throw new IllegalStateException("Failed to find the required field for " + colorType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read biome JSON for " + id.location(), e);
        }
    }

    private static BlendData defaultBlendData(String name, ResourceKey<Biome> id, int color, String model, Map<Item, Integer> ingredients) {
        return new BlendData(name, id, defaultBlendType(id, color), model, ingredients);
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
