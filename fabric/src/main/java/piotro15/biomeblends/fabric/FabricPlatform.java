package piotro15.biomeblends.fabric;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.ModConfigSpec;
import piotro15.biomeblends.util.Platform;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class FabricPlatform extends Platform {
    public static final Map<String, ModConfigSpec.BooleanValue> compatibilityDatapacks = new HashMap<>();

    @Override
    public <T> void registerDataRegistry(ResourceKey<Registry<T>> key, Codec<T> codec) {
        DynamicRegistries.registerSynced(key, codec, codec);
    }

    @Override
    public void registerItemTint(ItemColor itemColor, Supplier<Item> itemSupplier) {
        ColorProviderRegistry.ITEM.register(itemColor, itemSupplier.get());
    }

    @Override
    public void registerDatapack(String name, ModConfigSpec.BooleanValue register) {
        compatibilityDatapacks.put(name, register);
    }

    @Override
    public Optional<String> getModDisplayName(String modId) {
        return FabricLoader.getInstance().getModContainer(modId)
                .map(mod -> mod.getMetadata().getName());
    }
}
