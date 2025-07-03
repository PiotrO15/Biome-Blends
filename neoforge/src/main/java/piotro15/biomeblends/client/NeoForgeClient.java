package piotro15.biomeblends.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.neoforge.NeoForgePlatform;

import java.util.Map;

@EventBusSubscriber(modid = BiomeBlends.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NeoForgeClient {
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        NeoForgePlatform.itemColors.forEach(
                (item, color) -> event.register(color, item.get())
        );
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event) {
        for (Map.Entry<ResourceLocation, Resource> entry : FileToIdConverter.json("models/blend_type").listMatchingResources(Minecraft.getInstance().getResourceManager()).entrySet()) {
            ResourceLocation blendType = ResourceLocation.parse(entry.getKey().toString().replace("models/blend_type", "blend_type").replace(".json", ""));
            event.register(ModelResourceLocation.standalone(blendType));
        }
    }

    @SubscribeEvent
    public static void modifyBakingResults(ModelEvent.ModifyBakingResult event) {
        event.getModels().computeIfPresent(
                ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath("biomeblends", "biome_blend")),
                (location, model) -> new BlendWrapper(model)
        );
    }
}
