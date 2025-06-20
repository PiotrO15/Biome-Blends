package piotro15.biomeblends.registry;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import piotro15.biomeblends.BiomeBlends;

import java.util.function.Supplier;

public class BiomeBlendsDataComponents {
    public static final DeferredRegister<DataComponentType<?>> REGISTRAR = DeferredRegister.create(BiomeBlends.MOD_ID, Registries.DATA_COMPONENT_TYPE);
    public static final Supplier<DataComponentType<ResourceLocation>> BLEND_TYPE = REGISTRAR.register(
            "blend_type",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
                    .cacheEncoding()
                    .build()
    );


    public static void load() {
        // This method is used to ensure the static initializer gets called
    }
}
