package piotro15.biomeblends.util;

import com.mojang.serialization.Codec;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

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

    public abstract void registerItemTint(ItemColor itemColor, Supplier<Item> itemSupplier);
}
