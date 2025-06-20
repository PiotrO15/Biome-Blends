package piotro15.biomeblends.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import piotro15.biomeblends.BiomeBlends;

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
    }
}
