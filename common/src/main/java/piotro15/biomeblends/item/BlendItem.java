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
import net.minecraft.world.item.ItemUtils;
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
                return InteractionResult.PASS;
            }

            Registry<BlendType> registry = level.registryAccess().registryOrThrow(BiomeBlendsRegistries.BLEND_TYPE);
            BlendType blendType = registry.get(blendId);

            if (blendType == null) {
                player.displayClientMessage(Component.literal("Unknown blend type: " + blendId), true);
                return InteractionResult.FAIL;
            }

            if (blendType.action().canApply(level, blockPos, player, blendType)) {
                blendType.action().apply(level, blockPos, player, blendType);

                if (!blendType.useRemainder().isEmpty()) {
                    ItemUtils.createFilledResult(useOnContext.getItemInHand(), player, blendType.useRemainder());
                } else if (!player.isCreative()) {
                    useOnContext.getItemInHand().shrink(1);
                }
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
