package piotro15.biomeblends.blend.blend_action;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.util.BlendBiomeResolver;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public record SetBiomeAction(ResourceLocation targetBiome) implements BlendAction {
    public static final ResourceLocation id = BiomeBlends.id("set_biome");

    public static final MapCodec<SetBiomeAction> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("target_biome").forGetter(action -> action.targetBiome)
            ).apply(instance, SetBiomeAction::new)
    );

    @Override
    public ResourceLocation id() {
        return id;
    }

    @Override
    public void apply(Level level, BlockPos blockPos, Player player, BlendType blendType) {
        if (!canApply(level, blockPos, player, blendType)) {
            return;
        }

        List<ResourceLocation> biomeBlacklist = blendType.biomeBlacklist().values();
        List<String> namespaceBlacklist = blendType.namespaceBlacklist().values();

        Predicate<Holder<Biome>> predicate = biomeHolder -> {
            Registry<Biome> registry = level.registryAccess().registryOrThrow(Registries.BIOME);

            if (biomeBlacklist.stream().anyMatch(b -> registry.containsKey(b) && Objects.equals(registry.get(b), biomeHolder.value()))) {
                return false;
            }

            if (namespaceBlacklist.stream().anyMatch(ns -> biomeHolder.unwrapKey().isEmpty() && biomeHolder.unwrapKey().get().location().getNamespace().equals(ns))) {
                return false;
            }

            return true;
        };

        BlendBiomeResolver.applyBiome((ServerLevel) level, blockPos, 8, targetBiome, predicate);
    }
}
