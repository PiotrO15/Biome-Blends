package piotro15.biomeblends.fabric;

import com.mojang.serialization.Codec;
//import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.BiomeBlendsClient;
import piotro15.biomeblends.registry.BiomeBlendsCreativeModeTabs;
import piotro15.biomeblends.registry.BiomeBlendsItems;
import piotro15.biomeblends.util.Platform;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class FabricPlatform extends Platform {
    public static final Map<String, ModConfigSpec.BooleanValue> compatibilityDatapacks = new HashMap<>();

    @Override
    public <T> void registerDataRegistry(ResourceKey<Registry<T>> key, Codec<T> codec) {
        DynamicRegistries.registerSynced(key, codec, codec);
    }

    @Override
    public void registerItemTint(ItemTintSource itemColor, Supplier<Item> itemSupplier) {
        ItemTintSources.ID_MAPPER.put(BiomeBlends.id("biome_blend"), BiomeBlendsClient.BiomeBlend.MAP_CODEC);
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

    @Override
    public Supplier<Item> registerItem(String name, Function<Item.Properties, ? extends Item> func, Supplier<Item.Properties> properties) {
        Item item = BiomeBlendsFabric.register(name, func, properties.get());
        return () -> item;
    }

    @Override
    public <R> Supplier<DataComponentType<R>> registerDataComponentType(Identifier identifier, Supplier<DataComponentType<R>> supplier) {
        DataComponentType<R> dataComponentType = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, identifier, supplier.get());
        return () -> dataComponentType;
    }

    @Override
    public void registerCreativeModeTab() {
        BiomeBlendsCreativeModeTabs.BLENDS_TAB = () -> FabricCreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + BiomeBlends.MOD_ID + ".blends"))
                .icon(() -> new ItemStack(BiomeBlendsItems.BLAND_BLEND.get()))
                .displayItems((_, output) -> output.accept(BiomeBlendsItems.BLAND_BLEND.get().getDefaultInstance()))
                .build();

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, BiomeBlendsCreativeModeTabs.BLENDS_TAB_KEY, BiomeBlendsCreativeModeTabs.BLENDS_TAB.get());
    }
}
