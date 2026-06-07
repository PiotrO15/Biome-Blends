package piotro15.biomeblends.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.util.Platform;

import java.util.function.Supplier;

public class BiomeBlendsDataComponents {
    public static Supplier<DataComponentType<Identifier>> BLEND_TYPE;

    public static void load() {
        BLEND_TYPE = Platform.getInstance().registerDataComponentType(
                BiomeBlends.id("blend_type"),
                () -> DataComponentType.<Identifier>builder()
                        .persistent(Identifier.CODEC)
                        .networkSynchronized(Identifier.STREAM_CODEC)
                        .cacheEncoding()
                        .build()
        );
    }
}
