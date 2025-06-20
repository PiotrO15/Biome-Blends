package piotro15.biomeblends.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.item.BlendItem;

public class BiomeBlendsItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BiomeBlends.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> BIOME_BLEND = ITEMS.register("biome_blend", BlendItem::new);
}
