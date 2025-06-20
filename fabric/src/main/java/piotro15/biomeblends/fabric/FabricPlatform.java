package piotro15.biomeblends.fabric;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import piotro15.biomeblends.util.Platform;

import java.util.function.Supplier;

public class FabricPlatform extends Platform {
    @Override
    public <T> void registerDataRegistry(ResourceKey<Registry<T>> key, Codec<T> codec) {
        DynamicRegistries.registerSynced(key, codec, codec);
    }

    @Override
    public void registerItemTint(ItemColor itemColor, Supplier<Item> itemSupplier) {
        ColorProviderRegistry.ITEM.register(itemColor, itemSupplier.get());
    }
}
