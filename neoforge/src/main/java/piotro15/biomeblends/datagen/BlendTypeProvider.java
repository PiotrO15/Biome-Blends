package piotro15.biomeblends.datagen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

import java.util.List;

public class BlendTypeProvider {
    public static void registerBlendTypes(BootstrapContext<BlendType> bootstrapContext) {
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

    public static void registerBlendTypes(BootstrapContext<BlendType> bootstrapContext, List<BlendData> blends) {
        blends.forEach(blend ->
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
