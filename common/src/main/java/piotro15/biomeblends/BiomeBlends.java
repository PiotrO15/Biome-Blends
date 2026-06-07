package piotro15.biomeblends;

import net.minecraft.resources.Identifier;
import piotro15.biomeblends.blend.BlendActionRegistry;
import piotro15.biomeblends.registry.BiomeBlendsCreativeModeTabs;
import piotro15.biomeblends.registry.BiomeBlendsDataComponents;
import piotro15.biomeblends.registry.BiomeBlendsItems;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;
import piotro15.biomeblends.util.Platform;

public final class BiomeBlends {
    public static final String MOD_ID = "biomeblends";

    public static void init() {
//        BiomeBlendsItems.ITEMS.register();
//        BiomeBlendsDataComponents.REGISTRAR.register();
        BiomeBlendsDataComponents.load();
        BlendActionRegistry.registerActions();
        BiomeBlendsRegistries.init();
        BiomeBlendsCreativeModeTabs.load();

        Platform.getInstance().registerDatapack("biomesoplenty", CommonConfig.INSTANCE.bopCompat);
        Platform.getInstance().registerDatapack("biomeswevegone", CommonConfig.INSTANCE.bwgCompat);
    }

    public static void initClient() {
        Platform.getInstance().registerItemTint(new BiomeBlendsClient.BiomeBlend(0xFF0000FF), BiomeBlendsItems.BIOME_BLEND);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
