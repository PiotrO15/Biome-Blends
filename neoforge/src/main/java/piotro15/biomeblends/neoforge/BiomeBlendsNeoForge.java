package piotro15.biomeblends.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import piotro15.biomeblends.BiomeBlends;
import net.neoforged.fml.common.Mod;
import piotro15.biomeblends.util.Platform;

@Mod(BiomeBlends.MOD_ID)
public final class BiomeBlendsNeoForge {
    public BiomeBlendsNeoForge(IEventBus modEventBus) {
        Platform.setup(new NeoForgePlatform());
        BiomeBlends.init();

        modEventBus.addListener(this::registerDatapackRegistries);

        BiomeBlends.initClient();
    }

    @SubscribeEvent
    public void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        for (NeoForgePlatform.DataRegistryRegisterable<?> registerable : NeoForgePlatform.dataRegistryRegisterables) {
            registerable.register(event);
        }
    }
}
