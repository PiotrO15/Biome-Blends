package piotro15.biomeblends.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.util.Platform;

import java.util.function.Supplier;

public class BiomeBlendsCreativeModeTabs {
    public static ResourceKey<CreativeModeTab> BLENDS_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, BiomeBlends.id("blends"));
    public static Supplier<CreativeModeTab> BLENDS_TAB;

    public static void load() {
        Platform.getInstance().registerCreativeModeTab();
    }
}
