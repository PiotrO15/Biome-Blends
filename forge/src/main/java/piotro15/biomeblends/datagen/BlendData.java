package piotro15.biomeblends.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.blend.blend_action.SetBiomeAction;

import java.util.List;
import java.util.Map;

public record BlendData(String name, String id, BlendType blendType, String model, Map<Item, Integer> ingredients) {
    public static final List<BlendData> blends = List.of(
            defaultBlendData("Badlands Blend", "badlands", 0xD27D2C, "bumpy_blend", Map.of(Items.RED_SAND, 2, Items.TERRACOTTA, 2)),
            defaultBlendData("Bamboo Jungle Blend", "bamboo_jungle", 0x3FA348, "bumpy_blend", Map.of(Items.BAMBOO, 3, Items.JUNGLE_SAPLING, 1)),
            defaultBlendData("Basalt Deltas Blend", "basalt_deltas", 0x4A4A4A, "bumpy_blend", Map.of(Items.BASALT, 2, Items.MAGMA_BLOCK, 2)),
            defaultBlendData("Beach Blend", "beach", 0xFAE6A0, "bumpy_blend", Map.of(Items.SAND, 2, Items.SEAGRASS, 1)),
            defaultBlendData("Birch Forest Blend", "birch_forest", 0xFFFF3F, "fluffy_blend", Map.of(Items.GRASS, 2, Items.BIRCH_SAPLING, 1)),
            defaultBlendData("Cherry Grove Blend", "cherry_grove", 0xFFA7DD, "fluffy_blend", Map.of(Items.PINK_PETALS, 2, Items.CHERRY_SAPLING, 1)),
            defaultBlendData("Cold Ocean Blend", "cold_ocean", 0x2D5D87, "bumpy_blend", Map.of(Items.SALMON, 2, Items.SEAGRASS, 2)),
            defaultBlendData("Crimson Forest Blend", "crimson_forest", 0x842626, "fluffy_blend", Map.of(Items.CRIMSON_FUNGUS, 1, Items.NETHER_WART_BLOCK, 2)),
            defaultBlendData("Dark Forest Blend", "dark_forest", 0x3AA622, "fluffy_blend", Map.of(Items.RED_MUSHROOM, 1, Items.BROWN_MUSHROOM, 1, Items.DARK_OAK_SAPLING, 1)),
            defaultBlendData("Deep Cold Ocean Blend", "deep_cold_ocean", 0x1D3C5A, "bumpy_blend", Map.of(Items.SALMON, 2, Items.KELP, 2)),
            defaultBlendData("Deep Dark Blend", "deep_dark", 0x002F43, "rocky_blend", Map.of(Items.SCULK, 2, Items.ECHO_SHARD, 1)),
            defaultBlendData("Deep Frozen Ocean Blend", "deep_frozen_ocean", 0x3E6F8F, "bumpy_blend", Map.of(Items.ICE, 2, Items.KELP, 2)),
            defaultBlendData("Deep Lukewarm Ocean Blend", "deep_lukewarm_ocean", 0x478BA5, "bumpy_blend", Map.of(Items.PUFFERFISH, 1, Items.KELP, 2)),
            defaultBlendData("Deep Ocean Blend", "deep_ocean", 0x1C3E50, "bumpy_blend", Map.of(Items.COD, 2, Items.KELP, 2)),
            defaultBlendData("Desert Blend", "desert", 0xE5C27B, "bumpy_blend", Map.of(Items.SAND, 2, Items.DEAD_BUSH, 2)),
            defaultBlendData("Dripstone Caves Blend", "dripstone_caves", 0xA17E70, "rocky_blend", Map.of(Items.POINTED_DRIPSTONE, 2, Items.COPPER_INGOT, 2)),
            defaultBlendData("End Barrens Blend", "end_barrens", 0xCFCDBF, "bumpy_blend", Map.of(Items.END_STONE, 2, Items.ENDER_PEARL, 1)),
            defaultBlendData("End Highlands Blend", "end_highlands", 0xBBBBAA, "bumpy_blend", Map.of(Items.END_STONE, 2, Items.CHORUS_FLOWER, 2)),
            defaultBlendData("End Midlands Blend", "end_midlands", 0xC2C2AC, "bumpy_blend", Map.of(Items.END_STONE, 1, Items.POPPED_CHORUS_FRUIT, 2)),
            defaultBlendData("Eroded Badlands Blend", "eroded_badlands", 0xB34A1C, "bumpy_blend", Map.of(Items.DEAD_BUSH, 2, Items.TERRACOTTA, 2)),
            defaultBlendData("Flower Forest Blend", "flower_forest", 0x70D85B, "fluffy_blend", Map.of(Items.WHITE_TULIP, 1, Items.PINK_TULIP, 1, Items.RED_TULIP, 1, Items.ORANGE_TULIP, 1)),
            defaultBlendData("Forest Blend", "forest", 0x69D23C, "fluffy_blend", Map.of(Items.OAK_SAPLING, 1, Items.BIRCH_SAPLING, 1)),
            defaultBlendData("Frozen Ocean Blend", "frozen_ocean", 0x8AD2E3, "bumpy_blend", Map.of(Items.ICE, 2, Items.SEAGRASS, 2)),
            defaultBlendData("Frozen Peaks Blend", "frozen_peaks", 0xD6E7F2, "bumpy_blend", Map.of(Items.ICE, 2, Items.PACKED_ICE, 2)),
            defaultBlendData("Frozen River Blend", "frozen_river", 0xBCE3F2, "bumpy_blend", Map.of(Items.WATER_BUCKET, 1, Items.ICE, 2)),
            defaultBlendData("Grove Blend", "grove", 0x9FC77C, "bumpy_blend", Map.of(Items.POWDER_SNOW_BUCKET, 1, Items.SPRUCE_SAPLING, 1)),
            defaultBlendData("Ice Spikes Blend", "ice_spikes", 0xCDE7F5, "bumpy_blend", Map.of(Items.SNOW_BLOCK, 2, Items.PACKED_ICE, 2)),
            defaultBlendData("Jagged Peaks Blend", "jagged_peaks", 0xBFD4E0, "bumpy_blend", Map.of(Items.SNOW_BLOCK, 2, Items.EMERALD, 1)),
            defaultBlendData("Jungle Blend", "jungle", 0x4CAF50, "bumpy_blend", Map.of(Items.JUNGLE_SAPLING, 1, Items.COCOA_BEANS, 1, Items.MELON_SLICE, 2)),
            defaultBlendData("Lukewarm Ocean Blend", "lukewarm_ocean", 0x62C2A3, "bumpy_blend", Map.of(Items.PUFFERFISH, 1, Items.SEAGRASS, 2)),
            defaultBlendData("Lush Caves Blend", "lush_caves", 0x8ECB51, "rocky_blend", Map.of(Items.GLOW_BERRIES, 2, Items.MOSS_BLOCK, 1)),
            defaultBlendData("Mangrove Swamp Blend", "mangrove_swamp", 0x556B2F, "bumpy_blend", Map.of(Items.MANGROVE_PROPAGULE, 1, Items.MUD, 2)),
            defaultBlendData("Meadow Blend", "meadow", 0x9DD382, "bumpy_blend", Map.of(Items.OXEYE_DAISY, 2, Items.CORNFLOWER, 2)),
            defaultBlendData("Mushroom Fields Blend", "mushroom_fields", 0xA892B0, "bumpy_blend", Map.of(Items.RED_MUSHROOM, 2, Items.BROWN_MUSHROOM, 2)),
            defaultBlendData("Nether Wastes Blend", "nether_wastes", 0xB33A26, "bumpy_blend", Map.of(Items.NETHERRACK, 3, Items.QUARTZ, 1)),
            defaultBlendData("Ocean Blend", "ocean", 0x2A5B88, "bumpy_blend", Map.of(Items.SEAGRASS, 2, Items.COD, 2)),
            defaultBlendData("Old Growth Birch Forest Blend", "old_growth_birch_forest", 0x7DC875, "fluffy_blend", Map.of(Items.BIRCH_SAPLING, 2, Items.GRASS, 2)),
            defaultBlendData("Old Growth Pine Taiga Blend", "old_growth_pine_taiga", 0x597D48, "fluffy_blend", Map.of(Items.MOSSY_COBBLESTONE, 2, Items.SPRUCE_SAPLING, 1)),
            defaultBlendData("Old Growth Spruce Taiga Blend", "old_growth_spruce_taiga", 0x4C6A3D, "fluffy_blend", Map.of(Items.MOSSY_COBBLESTONE, 2, Items.FERN, 2)),
            defaultBlendData("Plains Blend", "plains", 0x91BD59, "bumpy_blend", Map.of(Items.GRASS, 2, Items.WHEAT_SEEDS, 2)),
            defaultBlendData("River Blend", "river", 0x4D9CB5, "bumpy_blend", Map.of(Items.CLAY_BALL, 2, Items.WATER_BUCKET, 1)),
            defaultBlendData("Savanna Blend", "savanna", 0xBDB855, "bumpy_blend", Map.of(Items.ACACIA_SAPLING, 1, Items.GRASS, 2)),
            defaultBlendData("Savanna Plateau Blend", "savanna_plateau", 0xC9C76C, "bumpy_blend", Map.of(Items.ACACIA_SAPLING, 2, Items.GRASS, 1)),
            defaultBlendData("Small End Islands Blend", "small_end_islands", 0xAFAFAF, "bumpy_blend", Map.of(Items.END_STONE, 1, Items.ENDER_PEARL, 2)),
            defaultBlendData("Snowy Beach Blend", "snowy_beach", 0xE8F4FA, "bumpy_blend", Map.of(Items.SNOWBALL, 2, Items.SAND, 2)),
            defaultBlendData("Snowy Plains Blend", "snowy_plains", 0xF5F5F5, "bumpy_blend", Map.of(Items.SNOW_BLOCK, 2, Items.WHEAT_SEEDS, 2)),
            defaultBlendData("Snowy Slopes Blend", "snowy_slopes", 0xE0EAF0, "bumpy_blend", Map.of(Items.SNOW_BLOCK, 2, Items.POWDER_SNOW_BUCKET, 1)),
            defaultBlendData("Snowy Taiga Blend", "snowy_taiga", 0xAECFD0, "fluffy_blend", Map.of(Items.SNOWBALL, 2, Items.SWEET_BERRIES, 2)),
            defaultBlendData("Soul Sand Valley Blend", "soul_sand_valley", 0x83624B, "bumpy_blend", Map.of(Items.SOUL_SOIL, 2, Items.BONE_BLOCK, 2)),
            defaultBlendData("Sparse Jungle Blend", "sparse_jungle", 0x6AA75E, "bumpy_blend", Map.of(Items.JUNGLE_SAPLING, 1, Items.COCOA_BEANS, 2)),
            defaultBlendData("Stony Peaks Blend", "stony_peaks", 0xA0A0A0, "bumpy_blend", Map.of(Items.EMERALD, 1, Items.CALCITE, 2)),
            defaultBlendData("Stony Shore Blend", "stony_shore", 0x808080, "bumpy_blend", Map.of(Items.STONE, 2, Items.GRAVEL, 2)),
            defaultBlendData("Sunflower Plains Blend", "sunflower_plains", 0xA2D84F, "bumpy_blend", Map.of(Items.SUNFLOWER, 2, Items.GRASS, 2)),
            defaultBlendData("Swamp Blend", "swamp", 0x556B2F, "bumpy_blend", Map.of(Items.VINE, 2, Items.SLIME_BALL, 2)),
            defaultBlendData("Taiga Blend", "taiga", 0x587C4A, "fluffy_blend", Map.of(Items.SPRUCE_SAPLING, 1, Items.SWEET_BERRIES, 2)),
            defaultBlendData("The End Blend", "the_end", 0xCFCDBF, "bumpy_blend", Map.of(Items.END_STONE, 2, Items.DRAGON_BREATH, 2)),
            defaultBlendData("The Void Blend", "the_void", 0x000000, "bumpy_blend", Map.of(Items.GLASS_BOTTLE, 2)),
            defaultBlendData("Warm Ocean Blend", "warm_ocean", 0x42C5B8, "bumpy_blend", Map.of(Items.SAND, 2, Items.TROPICAL_FISH, 2)),
            defaultBlendData("Warped Forest Blend", "warped_forest", 0x3CABA8, "fluffy_blend", Map.of(Items.WARPED_FUNGUS, 1, Items.WARPED_WART_BLOCK, 2)),
            defaultBlendData("Windswept Forest Blend", "windswept_forest", 0x5F8F3E, "fluffy_blend", Map.of(Items.OAK_SAPLING, 1, Items.SPRUCE_SAPLING, 1)),
            defaultBlendData("Windswept Gravelly Hills Blend", "windswept_gravelly_hills", 0x8A8A8A, "bumpy_blend", Map.of(Items.GRAVEL, 2, Items.SPRUCE_SAPLING, 1)),
            defaultBlendData("Windswept Hills Blend", "windswept_hills", 0x92A379, "bumpy_blend", Map.of(Items.GRASS, 2, Items.SPRUCE_SAPLING, 1)),
            defaultBlendData("Windswept Savanna Blend", "windswept_savanna", 0xA1A163, "bumpy_blend", Map.of(Items.COARSE_DIRT, 2, Items.ACACIA_SAPLING, 1)),
            defaultBlendData("Wooded Badlands Blend", "wooded_badlands", 0xC96435, "bumpy_blend", Map.of(Items.COARSE_DIRT, 2, Items.OAK_SAPLING, 1))
    );


    private static BlendData defaultBlendData(String name, String id, int color, String model, Map<Item, Integer> ingredients) {
        return new BlendData(name, id, defaultBlendType(id, color), model, ingredients);
    }

    private static BlendType defaultBlendType(String biomeId, int color) {
        return new BlendType.BlendTypeBuilder()
                .action(new SetBiomeAction(new ResourceLocation("minecraft", biomeId)))
                .color(color)
                .build();
    }
}
