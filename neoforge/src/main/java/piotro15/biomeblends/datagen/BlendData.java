package piotro15.biomeblends.datagen;

import net.minecraft.resources.ResourceLocation;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.blend.blend_action.SetBiomeAction;

import java.util.List;

public record BlendData(String name, String id, BlendType blendType, String model) {
    public static final List<BlendData> blends = List.of(
            defaultBlendData("Badlands Blend", "badlands", 0xD27D2C, "bumpy_blend"),
            defaultBlendData("Bamboo Jungle Blend", "bamboo_jungle", 0x3FA348, "bumpy_blend"),
            defaultBlendData("Basalt Deltas Blend", "basalt_deltas", 0x4A4A4A, "bumpy_blend"),
            defaultBlendData("Beach Blend", "beach", 0xFAE6A0, "bumpy_blend"),
            defaultBlendData("Birch Forest Blend", "birch_forest", 0xFFFF3F, "fluffy_blend"),
            defaultBlendData("Cherry Grove Blend", "cherry_grove", 0xFFA7DD, "fluffy_blend"),
            defaultBlendData("Cold Ocean Blend", "cold_ocean", 0x2D5D87, "bumpy_blend"),
            defaultBlendData("Crimson Forest Blend", "crimson_forest", 0x842626, "fluffy_blend"),
            defaultBlendData("Dark Forest Blend", "dark_forest", 0x3AA622, "fluffy_blend"),
            defaultBlendData("Deep Cold Ocean Blend", "deep_cold_ocean", 0x1D3C5A, "bumpy_blend"),
            defaultBlendData("Deep Dark Blend", "deep_dark", 0x002F43, "rocky_blend"), //
            defaultBlendData("Deep Frozen Ocean Blend", "deep_frozen_ocean", 0x3E6F8F, "bumpy_blend"),
            defaultBlendData("Deep Lukewarm Ocean Blend", "deep_lukewarm_ocean", 0x478BA5, "bumpy_blend"),
            defaultBlendData("Deep Ocean Blend", "deep_ocean", 0x1C3E50, "bumpy_blend"),
            defaultBlendData("Desert Blend", "desert", 0xE5C27B, "bumpy_blend"),
            defaultBlendData("Dripstone Caves Blend", "dripstone_caves", 0xA17E70, "rocky_blend"), //
            defaultBlendData("End Barrens Blend", "end_barrens", 0xCFCDBF, "bumpy_blend"),
            defaultBlendData("End Highlands Blend", "end_highlands", 0xBBBBAA, "bumpy_blend"),
            defaultBlendData("End Midlands Blend", "end_midlands", 0xC2C2AC, "bumpy_blend"),
            defaultBlendData("Eroded Badlands Blend", "eroded_badlands", 0xB34A1C, "bumpy_blend"),
            defaultBlendData("Flower Forest Blend", "flower_forest", 0x70D85B, "fluffy_blend"),
            defaultBlendData("Forest Blend", "forest", 0x69D23C, "fluffy_blend"), //
            defaultBlendData("Frozen Ocean Blend", "frozen_ocean", 0x8AD2E3, "bumpy_blend"),
            defaultBlendData("Frozen Peaks Blend", "frozen_peaks", 0xD6E7F2, "bumpy_blend"),
            defaultBlendData("Frozen River Blend", "frozen_river", 0xBCE3F2, "bumpy_blend"),
            defaultBlendData("Grove Blend", "grove", 0x9FC77C, "bumpy_blend"),
            defaultBlendData("Ice Spikes Blend", "ice_spikes", 0xCDE7F5, "bumpy_blend"),
            defaultBlendData("Jagged Peaks Blend", "jagged_peaks", 0xBFD4E0, "bumpy_blend"),
            defaultBlendData("Jungle Blend", "jungle", 0x4CAF50, "bumpy_blend"),
            defaultBlendData("Lukewarm Ocean Blend", "lukewarm_ocean", 0x62C2A3, "bumpy_blend"),
            defaultBlendData("Lush Caves Blend", "lush_caves", 0x8ECB51, "rocky_blend"), //
            defaultBlendData("Mangrove Swamp Blend", "mangrove_swamp", 0x556B2F, "bumpy_blend"),
            defaultBlendData("Meadow Blend", "meadow", 0x9DD382, "bumpy_blend"),
            defaultBlendData("Mushroom Fields Blend", "mushroom_fields", 0xA892B0, "bumpy_blend"),
            defaultBlendData("Nether Wastes Blend", "nether_wastes", 0xB33A26, "bumpy_blend"),
            defaultBlendData("Ocean Blend", "ocean", 0x2A5B88, "bumpy_blend"),
            defaultBlendData("Old Growth Birch Forest Blend", "old_growth_birch_forest", 0x7DC875, "fluffy_blend"),
            defaultBlendData("Old Growth Pine Taiga Blend", "old_growth_pine_taiga", 0x597D48, "fluffy_blend"),
            defaultBlendData("Old Growth Spruce Taiga Blend", "old_growth_spruce_taiga", 0x4C6A3D, "fluffy_blend"),
            defaultBlendData("Plains Blend", "plains", 0x91BD59, "bumpy_blend"),
            defaultBlendData("River Blend", "river", 0x4D9CB5, "bumpy_blend"),
            defaultBlendData("Savanna Blend", "savanna", 0xBDB855, "bumpy_blend"),
            defaultBlendData("Savanna Plateau Blend", "savanna_plateau", 0xC9C76C, "bumpy_blend"),
            defaultBlendData("Small End Islands Blend", "small_end_islands", 0xAFAFAF, "bumpy_blend"),
            defaultBlendData("Snowy Beach Blend", "snowy_beach", 0xE8F4FA, "bumpy_blend"),
            defaultBlendData("Snowy Plains Blend", "snowy_plains", 0xF5F5F5, "bumpy_blend"),
            defaultBlendData("Snowy Slopes Blend", "snowy_slopes", 0xE0EAF0, "bumpy_blend"),
            defaultBlendData("Snowy Taiga Blend", "snowy_taiga", 0xAECFD0, "fluffy_blend"),
            defaultBlendData("Soul Sand Valley Blend", "soul_sand_valley", 0x83624B, "bumpy_blend"),
            defaultBlendData("Sparse Jungle Blend", "sparse_jungle", 0x6AA75E, "bumpy_blend"),
            defaultBlendData("Stony Peaks Blend", "stony_peaks", 0xA0A0A0, "bumpy_blend"),
            defaultBlendData("Stony Shore Blend", "stony_shore", 0x808080, "bumpy_blend"),
            defaultBlendData("Sunflower Plains Blend", "sunflower_plains", 0xA2D84F, "bumpy_blend"),
            defaultBlendData("Swamp Blend", "swamp", 0x556B2F, "bumpy_blend"),
            defaultBlendData("Taiga Blend", "taiga", 0x587C4A, "fluffy_blend"),
            defaultBlendData("The End Blend", "the_end", 0xCFCDBF, "bumpy_blend"),
            defaultBlendData("The Void Blend", "the_void", 0x000000, "bumpy_blend"),
            defaultBlendData("Warm Ocean Blend", "warm_ocean", 0x42C5B8, "bumpy_blend"),
            defaultBlendData("Warped Forest Blend", "warped_forest", 0x3CABA8, "fluffy_blend"),
            defaultBlendData("Windswept Forest Blend", "windswept_forest", 0x5F8F3E, "fluffy_blend"),
            defaultBlendData("Windswept Gravelly Hills Blend", "windswept_gravelly_hills", 0x8A8A8A, "bumpy_blend"),
            defaultBlendData("Windswept Hills Blend", "windswept_hills", 0x92A379, "bumpy_blend"),
            defaultBlendData("Windswept Savanna Blend", "windswept_savanna", 0xA1A163, "bumpy_blend"),
            defaultBlendData("Wooded Badlands Blend", "wooded_badlands", 0xC96435, "bumpy_blend")
    );

    private static BlendData defaultBlendData(String name, String id, int color, String model) {
        return new BlendData(name, id, defaultBlendType(id, color), model);
    }

    private static BlendType defaultBlendType(String biomeId, int color) {
        return new BlendType.BlendTypeBuilder()
                .action(new SetBiomeAction(ResourceLocation.fromNamespaceAndPath("minecraft", biomeId)))
                .color(color)
                .build();
    }
}
