package piotro15.biomeblends.neoforge;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.registry.BiomeBlendsCreativeModeTabs;
import piotro15.biomeblends.registry.BiomeBlendsItems;
import piotro15.biomeblends.util.Platform;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class NeoForgePlatform extends Platform {
    public static final List<DataRegistryRegisterable<?>> dataRegistryRegisterables = new ArrayList<>();
    public static final Map<String, ModConfigSpec.BooleanValue> compatibilityDatapacks = new HashMap<>();
    public static final BiMap<Supplier<Item>, ItemTintSource> itemColors = HashBiMap.create();

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BiomeBlends.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BiomeBlends.MOD_ID);
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, BiomeBlends.MOD_ID);

    @Override
    public <T> void registerDataRegistry(ResourceKey<Registry<T>> key, Codec<T> codec) {
        dataRegistryRegisterables.add(new DataRegistryRegisterable<>(key, codec, codec));
    }

    @Override
    public void registerItemTint(ItemTintSource itemColor, Supplier<Item> itemSupplier) {
        itemColors.put(itemSupplier, itemColor);
    }

    @Override
    public void registerDatapack(String name, ModConfigSpec.BooleanValue register) {
        compatibilityDatapacks.put(name, register);
    }

    @Override
    public Optional<String> getModDisplayName(String modId) {
        return ModList.get().getModContainerById(modId)
                .map(mod -> mod.getModInfo().getDisplayName());
    }

    @Override
    public Supplier<Item> registerItem(String name, Function<Item.Properties, ? extends Item> func, Supplier<Item.Properties> properties) {
        return ITEMS.registerItem(name, func, properties);
    }

    @Override
    public <R> Supplier<DataComponentType<R>> registerDataComponentType(Identifier identifier, Supplier<DataComponentType<R>> supplier) {
        return DATA_COMPONENTS.register(identifier.getPath(), supplier);
    }

    @Override
    public void registerCreativeModeTab() {
        BiomeBlendsCreativeModeTabs.BLENDS_TAB = CREATIVE_MODE_TABS.register("blends", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + BiomeBlends.MOD_ID + ".blends"))
                .icon(() -> new ItemStack(BiomeBlendsItems.BLAND_BLEND.get()))
                .displayItems((_, output) -> output.accept(BiomeBlendsItems.BLAND_BLEND.get().getDefaultInstance()))
                .build()
        );
    }

    public record DataRegistryRegisterable<T>(ResourceKey<Registry<T>> key, Codec<T> codec, Codec<T> networkCodec) {
        public void register(DataPackRegistryEvent.NewRegistry event) {
            if(networkCodec == null)
                event.dataPackRegistry(key, codec);
            else
                event.dataPackRegistry(key, codec, networkCodec);
        }
    }
}
