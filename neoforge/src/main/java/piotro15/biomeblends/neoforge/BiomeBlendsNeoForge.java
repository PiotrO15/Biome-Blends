package piotro15.biomeblends.neoforge;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import piotro15.biomeblends.BiomeBlends;
import net.neoforged.fml.common.Mod;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsCreativeModeTabs;
import piotro15.biomeblends.registry.BiomeBlendsDataComponents;
import piotro15.biomeblends.registry.BiomeBlendsItems;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;
import piotro15.biomeblends.util.Platform;

@Mod(BiomeBlends.MOD_ID)
public final class BiomeBlendsNeoForge {
    public BiomeBlendsNeoForge(IEventBus modEventBus) {
        Platform.setup(new NeoForgePlatform());
        BiomeBlends.init();

        modEventBus.addListener(this::registerDatapackRegistries);
        modEventBus.addListener(this::registerBlendsInCreativeTab);

        BiomeBlends.initClient();
    }

    @SubscribeEvent
    public void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        for (NeoForgePlatform.DataRegistryRegisterable<?> registerable : NeoForgePlatform.dataRegistryRegisterables) {
            registerable.register(event);
        }
    }

    @SubscribeEvent
    public void registerBlendsInCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (!event.getTabKey().equals(BiomeBlendsCreativeModeTabs.BLENDS_TAB.getKey())) return;

        if (Minecraft.getInstance().level != null) {
            Registry<BlendType> blendTypeRegistry = Minecraft.getInstance().level.registryAccess().registryOrThrow(BiomeBlendsRegistries.BLEND_TYPE);

            for (ResourceKey<BlendType> blendKey : blendTypeRegistry.registryKeySet()) {
                ItemStack stack = new ItemStack(BiomeBlendsItems.BIOME_BLEND.get());
                stack.set(BiomeBlendsDataComponents.BLEND_TYPE.get(), blendKey.location());
                event.accept(stack);
            }
        }
    }
}
