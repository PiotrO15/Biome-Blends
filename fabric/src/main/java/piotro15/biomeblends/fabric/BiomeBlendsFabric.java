package piotro15.biomeblends.fabric;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.minecraftforge.fml.config.ModConfig;
import piotro15.biomeblends.BiomeBlends;
import net.fabricmc.api.ModInitializer;
import piotro15.biomeblends.CommonConfig;
import piotro15.biomeblends.util.Platform;

public final class BiomeBlendsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Platform.setup(new FabricPlatform());
        BiomeBlends.init();

        ForgeConfigRegistry.INSTANCE.register(BiomeBlends.MOD_ID, ModConfig.Type.COMMON, CommonConfig.SPEC);
    }
}
