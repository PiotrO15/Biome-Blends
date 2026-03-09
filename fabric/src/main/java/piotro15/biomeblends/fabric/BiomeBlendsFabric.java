package piotro15.biomeblends.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.neoforged.fml.config.ModConfig;
import piotro15.biomeblends.BiomeBlends;
import net.fabricmc.api.ModInitializer;
import piotro15.biomeblends.CommonConfig;
import piotro15.biomeblends.command.GenerateBlendsCommand;
import piotro15.biomeblends.util.Platform;

public final class BiomeBlendsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Platform.setup(new FabricPlatform());
        BiomeBlends.init();

        NeoForgeConfigRegistry.INSTANCE.register(BiomeBlends.MOD_ID, ModConfig.Type.COMMON, CommonConfig.SPEC);

        FabricLoader.getInstance().getModContainer(BiomeBlends.MOD_ID).ifPresent(container -> {
            FabricPlatform.compatibilityDatapacks.forEach((s, booleanValue) -> {
                if (FabricLoader.getInstance().isModLoaded(s) && booleanValue.get()) {
                    ResourceManagerHelper.registerBuiltinResourcePack(BiomeBlends.id(s), "datapacks/" + s, container, true);
                }
            });
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, selection) -> GenerateBlendsCommand.register(dispatcher));
    }
}
