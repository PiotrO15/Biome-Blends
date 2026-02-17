package piotro15.biomeblends.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
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

        FabricLoader.getInstance().getModContainer(BiomeBlends.MOD_ID).ifPresent(container -> {
            if (FabricLoader.getInstance().isModLoaded("biomesoplenty") && CommonConfig.INSTANCE.bopCompat.get()) {
                ResourceManagerHelper.registerBuiltinResourcePack(ResourceLocation.fromNamespaceAndPath(BiomeBlends.MOD_ID, "biomesoplenty"), "datapacks/biomesoplenty", container, true);
            }
        });
    }
}
