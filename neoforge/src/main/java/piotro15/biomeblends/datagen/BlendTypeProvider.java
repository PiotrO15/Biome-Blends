package piotro15.biomeblends.datagen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.blend.blend_action.SetBiomeAction;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

public class BlendTypeProvider {
    public static void registerBlendTypes(BootstrapContext<BlendType> bootstrapContext) {
        BlendData.blends.forEach(blend ->
                bootstrapContext.register(
                        ResourceKey.create(BiomeBlendsRegistries.BLEND_TYPE, minecraft(blend.id())),
                        blend.blendType()
                )
        );
    }

    private static void registerMinecraftBlendType(BootstrapContext<BlendType> bootstrapContext, String path) {
        bootstrapContext.register(
                ResourceKey.create(BiomeBlendsRegistries.BLEND_TYPE, ResourceLocation.fromNamespaceAndPath(BiomeBlends.MOD_ID, path)),
                new BlendType.BlendTypeBuilder().action(new SetBiomeAction(minecraft(path))).build()
        );
    }

    private static ResourceLocation minecraft(String path) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", path);
    }
}
