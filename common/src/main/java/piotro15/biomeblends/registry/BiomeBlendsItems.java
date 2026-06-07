package piotro15.biomeblends.registry;

import net.minecraft.world.item.Item;
import piotro15.biomeblends.item.BlendBagItem;
import piotro15.biomeblends.item.BlendItem;
import piotro15.biomeblends.util.Platform;

import java.util.function.Supplier;

public class BiomeBlendsItems {
    public static Supplier<Item> BIOME_BLEND;
    public static Supplier<Item> BLAND_BLEND;
//    public static final Supplier<Item> BLEND_BAG = ITEMS.register("blend_bag", BlendBagItem::new);

    public static void load() {
        BIOME_BLEND = Platform.getInstance().registerItem("biome_blend", BlendItem::new, Item.Properties::new);
        BLAND_BLEND = Platform.getInstance().registerItem("bland_blend", Item::new, Item.Properties::new);
    }
}
