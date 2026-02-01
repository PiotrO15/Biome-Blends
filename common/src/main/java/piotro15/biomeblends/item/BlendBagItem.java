package piotro15.biomeblends.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class BlendBagItem extends Item {
    public BlendBagItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        return super.useOn(useOnContext);
    }

    @Override
    public @NotNull Component getDescription() {
        return super.getDescription();
    }
}
