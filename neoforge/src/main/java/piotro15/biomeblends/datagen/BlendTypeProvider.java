package piotro15.biomeblends.datagen;

import biomesoplenty.api.biome.BOPBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

public class BlendTypeProvider {
    public static void registerBlendTypes(BootstrapContext<BlendType> bootstrapContext, HolderLookup.Provider lookupProvider) {
        HolderGetter<Biome> biomeHolderGetter = lookupProvider.lookupOrThrow(Registries.BIOME);
        System.out.println(biomeHolderGetter.getOrThrow(Biomes.CHERRY_GROVE).value().getFoliageColor()); // 11983713
        System.out.println(biomeHolderGetter.getOrThrow(BOPBiomes.ASPEN_GLADE).value().getFoliageColor()); // Missing element ResourceKey[minecraft:worldgen/biome / biomesoplenty:aspen_glade]
        BlendData.blends.forEach(blend ->
                bootstrapContext.register(
                        ResourceKey.create(
                                BiomeBlendsRegistries.BLEND_TYPE,
                                blend.getResourceLocation()
                        ),
                        blend.blendType()
                )
        );

        BlendData.registerBiomesOPlentyBlends(lookupProvider);
        BlendData.biomesOPlentyBlends.forEach(blend ->
                bootstrapContext.register(
                        ResourceKey.create(
                                BiomeBlendsRegistries.BLEND_TYPE,
                                blend.getResourceLocation()
                        ),
                        blend.blendType()
                )
        );
    }
}
