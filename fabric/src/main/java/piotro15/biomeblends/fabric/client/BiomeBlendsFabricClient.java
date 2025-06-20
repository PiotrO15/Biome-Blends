package piotro15.biomeblends.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import piotro15.biomeblends.BiomeBlends;

public final class BiomeBlendsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BiomeBlends.initClient();
    }
}
