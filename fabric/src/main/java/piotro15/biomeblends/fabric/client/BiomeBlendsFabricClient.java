package piotro15.biomeblends.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.ItemStack;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsCreativeModeTabs;
import piotro15.biomeblends.registry.BiomeBlendsDataComponents;
import piotro15.biomeblends.registry.BiomeBlendsItems;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

import java.util.Map;

public final class BiomeBlendsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BiomeBlends.initClient();

        ModelLoadingPlugin.register(plugin -> {
            for (Map.Entry<ResourceLocation, Resource> entry : FileToIdConverter.json("models/blend_type").listMatchingResources(Minecraft.getInstance().getResourceManager()).entrySet()) {
                ResourceLocation blendType = ResourceLocation.parse(entry.getKey().toString().replace("models/blend_type", "blend_type").replace(".json", ""));
                plugin.addModels(blendType);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(BiomeBlendsCreativeModeTabs.BLENDS_TAB.getKey()).register(entries -> {
            if (Minecraft.getInstance().level != null) {
                Registry<BlendType> blendTypeRegistry = Minecraft.getInstance().level.registryAccess().registryOrThrow(BiomeBlendsRegistries.BLEND_TYPE);

                for (ResourceKey<BlendType> blendKey : blendTypeRegistry.registryKeySet()) {
                    ItemStack stack = new ItemStack(BiomeBlendsItems.BIOME_BLEND.get());
                    stack.set(BiomeBlendsDataComponents.BLEND_TYPE.get(), blendKey.location());
                    entries.accept(stack);
                }
            }
        });
    }
}
