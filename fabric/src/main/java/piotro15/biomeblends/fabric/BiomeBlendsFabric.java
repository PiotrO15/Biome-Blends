package piotro15.biomeblends.fabric;

import piotro15.biomeblends.BiomeBlends;
import net.fabricmc.api.ModInitializer;
import piotro15.biomeblends.util.Platform;

public final class BiomeBlendsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Platform.setup(new FabricPlatform());
        BiomeBlends.init();
    }
}
