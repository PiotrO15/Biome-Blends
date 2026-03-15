package piotro15.biomeblends.item;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import piotro15.biomeblends.CommonConfig;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;
import piotro15.biomeblends.util.Platform;

import java.util.List;
import java.util.Optional;

public class BlendItem extends Item {
    public BlendItem() {
        super(new Properties());
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        ResourceLocation blendId = BlendType.fromItem(stack);
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
            ResourceLocation blendId = BlendType.fromItem(stack);

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

                ItemStack useRemainder = blendType.useRemainder();

                if (!useRemainder.isEmpty()) {
                    if (player.isCreative()) {
                        if (!player.getInventory().contains(useRemainder)) {
                            player.getInventory().add(useRemainder.copy());
                        }
                    } else {
                        useOnContext.getItemInHand().shrink(1);

                        if (!player.getInventory().add(useRemainder.copy())) {
                            player.drop(useRemainder.copy(), false);
                        }
                    }
                } else if (!player.isCreative()) {
                    useOnContext.getItemInHand().shrink(1);
                }
            }

            level.playSound(null, blockPos, blendType.sound(), SoundSource.PLAYERS, 1.0F, 1.0F);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        if (CommonConfig.INSTANCE.displayNamespace.get()) {
            ResourceLocation blendType = BlendType.fromItem(itemStack);
            if (blendType != null) {
                Optional<String> modName = Platform.getInstance().getModDisplayName(blendType.getNamespace());
                list.add(Component.literal(modName.orElseGet(blendType::getNamespace)).withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
