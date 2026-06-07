package piotro15.biomeblends.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.item.BlendBagItem;
import piotro15.biomeblends.item.BlendItem;
import piotro15.biomeblends.util.Platform;

import java.util.function.Supplier;

public class BiomeBlendsItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BiomeBlends.MOD_ID, Registries.ITEM);

    public static final Supplier<Item> BIOME_BLEND = Platform.getInstance().registerItem("biome_blend", BlendItem::new, Item.Properties::new);
    public static final Supplier<Item> BLAND_BLEND = Platform.getInstance().registerItem("bland_blend", Item::new, Item.Properties::new);
//    public static final Supplier<Item> BLEND_BAG = ITEMS.register("blend_bag", BlendBagItem::new);
}
