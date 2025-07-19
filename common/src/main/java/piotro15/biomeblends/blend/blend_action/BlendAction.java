package piotro15.biomeblends.blend.blend_action;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import piotro15.biomeblends.blend.BlendType;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

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

    default Predicate<Holder<Biome>> predicate(Level level, BlendType blendType) {
        List<ResourceLocation> biomeBlacklist = blendType.biomeBlacklist().values();
        List<String> namespaceBlacklist = blendType.namespaceBlacklist().values();

        return biomeHolder -> {
            Registry<Biome> registry = level.registryAccess().registryOrThrow(Registries.BIOME);

            boolean containsBiome = biomeBlacklist.stream().anyMatch(b -> registry.containsKey(b) && Objects.equals(registry.get(b), biomeHolder.value()));
            if (containsBiome != blendType.biomeBlacklist().negate()) {
                return false;
            }

            boolean containsNamespace = namespaceBlacklist.stream().anyMatch(ns -> biomeHolder.unwrapKey().isEmpty() && biomeHolder.unwrapKey().get().location().getNamespace().equals(ns));
            return containsNamespace == blendType.namespaceBlacklist().negate();
        };
    }
}
