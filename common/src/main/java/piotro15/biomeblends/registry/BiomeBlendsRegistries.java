package piotro15.biomeblends.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.util.Platform;

public class BiomeBlendsRegistries {
    public static final ResourceKey<Registry<BlendType>> BLEND_TYPE =
            ResourceKey.createRegistryKey(new ResourceLocation(BiomeBlends.MOD_ID, "blend_type"));

    public static void init() {
        Platform.getInstance().registerDataRegistry(BLEND_TYPE, BlendType.CODEC);
    }
}
