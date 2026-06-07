package piotro15.biomeblends.util;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Platform {
    private static Platform platform = null;

    public static void setup(Platform platform) {
        Platform.platform = platform;
    }

    public static Platform getInstance() {
        return platform;
    }

    public abstract <T> void registerDataRegistry(ResourceKey<Registry<T>> key, Codec<T> codec);

    public abstract void registerDatapack(String name, ModConfigSpec.BooleanValue register);

    public abstract Optional<String> getModDisplayName(String modId);

    public abstract Supplier<Item> registerItem(String name, Function<Item.Properties, ? extends Item> func, Supplier<Item.Properties> properties);

    public abstract <R> Supplier<DataComponentType<R>> registerDataComponentType(Identifier identifier, Supplier<DataComponentType<R>> supplier);

    public abstract void registerCreativeModeTab();
}
