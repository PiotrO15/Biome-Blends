package piotro15.biomeblends.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.neoforged.fml.config.ModConfig;
import piotro15.biomeblends.BiomeBlends;
import net.fabricmc.api.ModInitializer;
import piotro15.biomeblends.CommonConfig;
import piotro15.biomeblends.util.Platform;

public final class BiomeBlendsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Platform.setup(new FabricPlatform());
        BiomeBlends.init();

        NeoForgeConfigRegistry.INSTANCE.register(BiomeBlends.MOD_ID, ModConfig.Type.COMMON, CommonConfig.SPEC);
    }
}
