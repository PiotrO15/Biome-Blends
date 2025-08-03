package piotro15.biomeblends.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.forge.ForgePlatform;

import java.util.Map;

@Mod.EventBusSubscriber(modid = BiomeBlends.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClient {
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        BiomeBlends.initClient();
        ForgePlatform.itemColors.forEach(
                (item, color) -> event.register(color, item.get())
        );
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event) {
        for (Map.Entry<ResourceLocation, Resource> entry : FileToIdConverter.json("models/blend_type").listMatchingResources(Minecraft.getInstance().getResourceManager()).entrySet()) {
            ResourceLocation blendType = ResourceLocation.parse(entry.getKey().toString().replace("models/blend_type", "blend_type").replace(".json", ""));
            event.register(blendType);
        }
    }

    @SubscribeEvent
    public static void modifyBakingResults(ModelEvent.ModifyBakingResult event) {
        event.getModels().computeIfPresent(
                new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath("biomeblends", "biome_blend"), "inventory"),
                (location, model) -> new BlendWrapper(model)
        );
    }
}
