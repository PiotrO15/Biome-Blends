package piotro15.biomeblends.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import piotro15.biomeblends.BiomeBlends;

public class BiomeBlendsCreativeModeTabs {
    public static DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(BiomeBlends.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static RegistrySupplier<CreativeModeTab> BLENDS_TAB;

    public static void load() {
        BLENDS_TAB = CREATIVE_TABS.register("blends",
                () -> CreativeTabRegistry.create(
                        Component.translatable("itemGroup." + BiomeBlends.MOD_ID + ".blends"),
                        () -> new ItemStack(BiomeBlendsItems.BIOME_BLEND)
                ));

        CreativeTabRegistry.appendStack(BLENDS_TAB, () -> BiomeBlendsItems.BIOME_BLEND.get().getDefaultInstance());

        CREATIVE_TABS.register();
    }
}
