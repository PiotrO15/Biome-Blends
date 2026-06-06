package piotro15.biomeblends.client;

import net.minecraft.client.Minecraft;
//import net.minecraft.client.resources.model.ModelIdentifier;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.neoforge.NeoForgePlatform;

import java.util.Map;

@Mod(value = BiomeBlends.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = BiomeBlends.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClient {
    public NeoForgeClient(IEventBus modBus, ModContainer container) {
        BiomeBlends.initClient();
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.ItemTintSources event) {
//        NeoForgePlatform.itemColors.forEach(
//                (item, color) -> event.register(color, item.get())
//        );
    }

//    @SubscribeEvent
//    public static void registerModels(ModelEvent.RegisterAdditional event) {
//        for (Map.Entry<Identifier, Resource> entry : FileToIdConverter.json("models/blend_type").listMatchingResources(Minecraft.getInstance().getResourceManager()).entrySet()) {
//            Identifier blendType = Identifier.parse(entry.getKey().toString().replace("models/blend_type", "blend_type").replace(".json", ""));
//            event.register(ModelIdentifier.standalone(blendType));
//        }
//    }
//
//    @SubscribeEvent
//    public static void modifyBakingResults(ModelEvent.ModifyBakingResult event) {
//        event.getModels().computeIfPresent(
//                ModelIdentifier.inventory(Identifier.fromNamespaceAndPath("biomeblends", "biome_blend")),
//                (location, model) -> new BlendWrapper(model)
//        );
//    }
}
