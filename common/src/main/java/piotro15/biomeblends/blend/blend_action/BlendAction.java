package piotro15.biomeblends.blend.blend_action;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import piotro15.biomeblends.blend.BlendType;

public interface BlendAction {
    ResourceLocation id();

    void apply(Level level, BlockPos blockPos, Player player, BlendType blendType);

    default boolean canApply(Level level, BlockPos blockPos, Player player, BlendType blendType) {
        if (level.isClientSide()) {
            return false;
        }

        boolean containsDimension = blendType.dimensionBlacklist().values().contains(level.dimension().location());
        return blendType.dimensionBlacklist().negate() == containsDimension;
    }
}
