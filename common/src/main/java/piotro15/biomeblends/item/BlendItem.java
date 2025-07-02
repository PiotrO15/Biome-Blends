package piotro15.biomeblends.item;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsDataComponents;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

public class BlendItem extends Item {
    public BlendItem() {
        super(new Item.Properties());
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        ResourceLocation blendId = stack.get(BiomeBlendsDataComponents.BLEND_TYPE.get());
        if (blendId != null) {
            return Component.translatable(Util.makeDescriptionId("blend_type", blendId));
        }
        return super.getName(stack);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        Player player = useOnContext.getPlayer();
        BlockPos blockPos = useOnContext.getClickedPos();

        if (player != null && !level.isClientSide) {
            ItemStack stack = useOnContext.getItemInHand();
            ResourceLocation blendId = stack.get(BiomeBlendsDataComponents.BLEND_TYPE.get());

            if (blendId == null) {
                player.displayClientMessage(Component.literal("Missing blend type."), true);
                return InteractionResult.FAIL;
            }

            Registry<BlendType> registry = level.registryAccess().registryOrThrow(BiomeBlendsRegistries.BLEND_TYPE);
            BlendType blendType = registry.get(blendId);

            if (blendType == null) {
                player.displayClientMessage(Component.literal("Unknown blend type: " + blendId), true);
                return InteractionResult.FAIL;
            }

            player.displayClientMessage(Component.literal("Purifying biome with blend: " + blendId), true);

            blendType.action().apply(level, blockPos, player, blendType);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static final ItemColor TINT_HANDLER = (stack, tintIndex) -> {
        ResourceLocation blendId = stack.getComponents().get(BiomeBlendsDataComponents.BLEND_TYPE.get());
        if (blendId == null)
            return -1;

        if (Minecraft.getInstance().getConnection() == null)
            return -1;

        Registry<BlendType> registry = Minecraft.getInstance().getConnection()
                .registryAccess().registryOrThrow(BiomeBlendsRegistries.BLEND_TYPE);

        BlendType blendType = registry.get(blendId);
        if (blendType == null)
            return -1;

        int color = blendType.color();
        return (color & 0xFF000000) == 0 ? color | 0xFF000000 : color;
    };
}
