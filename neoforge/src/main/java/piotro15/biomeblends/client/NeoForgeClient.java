package piotro15.biomeblends.client;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.model.standalone.SimpleUnbakedStandaloneModel;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.BiomeBlendsClient;

import java.util.Map;

@Mod(value = BiomeBlends.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = BiomeBlends.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClient {
    public NeoForgeClient(ModContainer container) {
        BiomeBlends.initClient();
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.ItemTintSources event) {
//        NeoForgePlatform.itemColors.forEach(
//                (item, color) -> event.register(color, item.get())
//        );
        event.register(Identifier.fromNamespaceAndPath(BiomeBlends.MOD_ID, "biome_blend"), BiomeBlendsClient.BiomeBlend.MAP_CODEC);
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterStandalone event) {
        for (Map.Entry<Identifier, Resource> entry : FileToIdConverter.json("models/blend_type").listMatchingResources(Minecraft.getInstance().getResourceManager()).entrySet()) {
            Identifier blendType = Identifier.parse(entry.getKey().toString().replace("models/blend_type", "blend_type").replace(".json", ""));
            System.out.println(entry.getKey());
            event.register(new StandaloneModelKey<>(() -> "BiomeBlends Blend Model"), SimpleUnbakedStandaloneModel.simpleModelWrapper(blendType));
        }
    }

    @SubscribeEvent
    public static void modifyBakingResults(ModelEvent.ModifyBakingResult event) {
        event.getBakingResult().itemStackModels().computeIfPresent(
                Identifier.fromNamespaceAndPath(BiomeBlends.MOD_ID, "biome_blend"),
                (_, model) -> new BiomeBlendsClient.BlendWrapper(model)
        );
    }
}
