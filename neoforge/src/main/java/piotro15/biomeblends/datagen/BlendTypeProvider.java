package piotro15.biomeblends.datagen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

public class BlendTypeProvider {
    public static void registerBlendTypes(BootstrapContext<BlendType> bootstrapContext) {
        BlendData.blends.forEach(blend ->
                bootstrapContext.register(
                        ResourceKey.create(
                                BiomeBlendsRegistries.BLEND_TYPE,
                                ResourceLocation.fromNamespaceAndPath("minecraft", blend.id())
                        ),
                        blend.blendType()
                )
        );
    }
}
