package piotro15.biomeblends.blend.blend_action;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.blend.BlendType;

import java.util.Optional;

public record SetNamespaceAction(String targetNamespace, Optional<ResourceLocation> fallbackBiome) implements BlendAction {
    public static final ResourceLocation id = BiomeBlends.id("set_namespace");

    public static final MapCodec<SetNamespaceAction> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.STRING.fieldOf("target_namespace").forGetter(action -> action.targetNamespace),
                    ResourceLocation.CODEC.optionalFieldOf("fallback_biome").forGetter(action -> action.fallbackBiome)
            ).apply(instance, SetNamespaceAction::new)
    );

    @Override
    public ResourceLocation id() {
        return id;
    }

    @Override
    public void apply(Level level, BlockPos blockPos, Player player, BlendType blendType) {

    }
}
