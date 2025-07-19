package piotro15.biomeblends.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class BlendBiomeResolver {
    public static BiomeResolver makeResolver(ChunkAccess chunk, BoundingBox boundingBox, ServerLevel level, ResourceLocation targetBiome, Predicate<Holder<Biome>> predicate, ParticleOptions particleOptions) {
        return (x, y, z, sampler) -> {
            int quartX = QuartPos.toBlock(x);
            int quartY = QuartPos.toBlock(y);
            int quartZ = QuartPos.toBlock(z);
            Holder<Biome> oldBiomeHolder = chunk.getNoiseBiome(x, y, z);
            if (boundingBox.isInside(quartX, quartY, quartZ)) {
                ResourceLocation oldBiome = level.registryAccess().registryOrThrow(Registries.BIOME).getKey(oldBiomeHolder.value());
                if (oldBiome != null) {
                    if (predicate.test(oldBiomeHolder)) {
                        level.sendParticles(particleOptions, quartX + 2,  quartY + 2, quartZ + 2, 8, 2, 2, 2, 1);

                        return (Holder<Biome>) (level.registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow(ResourceKey.create(Registries.BIOME, targetBiome)));
                    } else {
                        return oldBiomeHolder; // Return the old biome if it doesn't match the predicate
                    }
                }
            }

            return oldBiomeHolder;
        };
    }

    public static BiomeResolver makeNamespaceResolver(ChunkAccess chunk, BoundingBox boundingBox, ServerLevel level, String targetNamespace, Optional<ResourceLocation> fallbackBiome, Predicate<Holder<Biome>> predicate, ParticleOptions particleOptions) {
        return (x, y, z, sampler) -> {
            int quartX = QuartPos.toBlock(x);
            int quartY = QuartPos.toBlock(y);
            int quartZ = QuartPos.toBlock(z);
            Holder<Biome> oldBiomeHolder = chunk.getNoiseBiome(x, y, z);
            if (boundingBox.isInside(quartX, quartY, quartZ)) {
                ResourceLocation oldBiome = level.registryAccess().registryOrThrow(Registries.BIOME).getKey(oldBiomeHolder.value());
                if (oldBiome != null) {
                    if (predicate.test(oldBiomeHolder)) {
                        level.sendParticles(particleOptions, quartX + 2,  quartY + 2, quartZ + 2, 8, 2, 2, 2, 1);

                        Optional<Holder.Reference<Biome>> newBiome = level.registryAccess().registryOrThrow(Registries.BIOME).getHolder(ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(targetNamespace, oldBiome.getPath())));
                        Optional<Holder.Reference<Biome>> fallbackBiomeHolder = fallbackBiome.flatMap(resourceLocation -> level.registryAccess().registryOrThrow(Registries.BIOME).getHolder(ResourceKey.create(Registries.BIOME, resourceLocation)));

                        return newBiome.isPresent() ? newBiome.get() : fallbackBiomeHolder.isPresent() ? fallbackBiomeHolder.get() : oldBiomeHolder;
                    } else {
                        return oldBiomeHolder; // Return the old biome if it doesn't match the predicate
                    }
                }
            }

            return oldBiomeHolder;
        };
    }

    private static int quantize(int pos) {
        return QuartPos.toBlock(QuartPos.fromBlock(pos));
    }

    private static BlockPos quantize(BlockPos blockPos) {
        return new BlockPos(quantize(blockPos.getX()), quantize(blockPos.getY()), quantize(blockPos.getZ()));
    }

    public static void applyResolver(
            ServerLevel level,
            BlockPos pos,
            int horizontalRange,
            int verticalRange,
            Predicate<Holder<Biome>> predicate,
            ParticleOptions particleOptions,
            BiFunction<ChunkAccess, BoundingBox, BiomeResolver> resolverFactory
    ) {
        BoundingBox boundingBox = prepareBoundingBox(pos, level, horizontalRange, verticalRange);

        List<ChunkAccess> chunks = new ArrayList<>();
        for (int z = SectionPos.blockToSectionCoord(boundingBox.minZ()); z <= SectionPos.blockToSectionCoord(boundingBox.maxZ()); z++) {
            for (int x = SectionPos.blockToSectionCoord(boundingBox.minX()); x <= SectionPos.blockToSectionCoord(boundingBox.maxX()); x++) {
                ChunkAccess chunk = level.getChunk(x, z, ChunkStatus.FULL, false);
                if (chunk != null) {
                    chunks.add(chunk);
                }
            }
        }

        for (ChunkAccess chunk : chunks) {
            BiomeResolver resolver = resolverFactory.apply(chunk, boundingBox);
            chunk.fillBiomesFromNoise(resolver, level.getChunkSource().randomState().sampler());
            chunk.setUnsaved(true);
        }

        level.getChunkSource().chunkMap.resendBiomesForChunks(chunks);
    }

    private static BoundingBox prepareBoundingBox(BlockPos pos, Level level, int horizontalRange, int verticalRange) {
        BlockPos corner1 = quantize(
                new BlockPos(pos.getX() - horizontalRange, verticalRange == -1 ? level.getMinBuildHeight() : pos.getY() - verticalRange, pos.getZ() - horizontalRange)
        );
        BlockPos corner2 = quantize(
                new BlockPos(pos.getX() + horizontalRange, verticalRange == -1 ? level.getMaxBuildHeight() : pos.getY() + verticalRange, pos.getZ() + horizontalRange)
        );

        return BoundingBox.fromCorners(corner1, corner2);
    }
}
