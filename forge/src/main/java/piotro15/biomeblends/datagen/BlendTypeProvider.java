package piotro15.biomeblends.datagen;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

public class BlendTypeProvider {
    public static void registerBlendTypes(BootstapContext<BlendType> bootstrapContext) {
        BlendData.blends.forEach(blend ->
                bootstrapContext.register(
                        ResourceKey.create(
                                BiomeBlendsRegistries.BLEND_TYPE,
                                blend.getResourceLocation()
                        ),
                        blend.blendType()
                )
        );
    }

    public static void registerBOP(BootstapContext<BlendType> bootstrapContext) {
        BlendData.biomesOPlentyBlends.forEach(blend -> bootstrapContext.register(
                        ResourceKey.create(
                                BiomeBlendsRegistries.BLEND_TYPE,
                                blend.getResourceLocation()
                        ),
                        blend.blendType()
                )
        );
    }
}
