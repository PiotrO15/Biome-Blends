package piotro15.biomeblends;

import net.minecraft.resources.ResourceLocation;
import piotro15.biomeblends.blend.BlendActionRegistry;
import piotro15.biomeblends.registry.BiomeBlendsCreativeModeTabs;
import piotro15.biomeblends.registry.BiomeBlendsItems;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;
import piotro15.biomeblends.util.Platform;

public final class BiomeBlends {
    public static final String MOD_ID = "biomeblends";

    public static void init() {
        BiomeBlendsItems.ITEMS.register();
        BlendActionRegistry.registerActions();
        BiomeBlendsRegistries.init();
        BiomeBlendsCreativeModeTabs.load();
    }

    public static void initClient() {
        Platform.getInstance().registerItemTint(BiomeBlendsClient.TINT_HANDLER, BiomeBlendsItems.BIOME_BLEND);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
