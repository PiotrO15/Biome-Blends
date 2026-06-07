package piotro15.biomeblends.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.BiomeBlendsClient;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsCreativeModeTabs;
import piotro15.biomeblends.registry.BiomeBlendsDataComponents;
import piotro15.biomeblends.registry.BiomeBlendsItems;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

public final class BiomeBlendsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BiomeBlends.initClient();
        ItemTintSources.ID_MAPPER.put(BiomeBlends.id("biome_blend"), BiomeBlendsClient.BiomeBlend.MAP_CODEC);

        CreativeModeTabEvents.modifyOutputEvent(BiomeBlendsCreativeModeTabs.BLENDS_TAB_KEY).register(entries -> {
            if (Minecraft.getInstance().level != null) {
                Registry<BlendType> blendTypeRegistry = Minecraft.getInstance().level.registryAccess().lookupOrThrow(BiomeBlendsRegistries.BLEND_TYPE);

                for (ResourceKey<BlendType> blendKey : blendTypeRegistry.registryKeySet()) {
                    ItemStack stack = new ItemStack(BiomeBlendsItems.BIOME_BLEND.get());
                    stack.set(BiomeBlendsDataComponents.BLEND_TYPE.get(), blendKey.identifier());
                    entries.accept(stack);
                }
            }
        });

        ModelLoadingPlugin.register(new BiomeBlendsModelLoadingPlugin());
    }
}
