package piotro15.biomeblends;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsDataComponents;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

import java.util.Optional;

public class BiomeBlendsClient {
    public record BiomeBlend(int defaultColor) implements ItemTintSource {
        public static final MapCodec<BiomeBlend> MAP_CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default").forGetter(BiomeBlend::defaultColor)).apply(i, BiomeBlend::new));

        @Override
        public int calculate(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity) {
            Identifier blendId = itemStack.getComponents().get(BiomeBlendsDataComponents.BLEND_TYPE.get());
            if (blendId == null)
                return -1;

            if (Minecraft.getInstance().getConnection() == null)
                return -1;

            Registry<BlendType> registry = Minecraft.getInstance().getConnection()
                    .registryAccess().lookupOrThrow(BiomeBlendsRegistries.BLEND_TYPE);

            BlendType blendType = registry.getValue(blendId);
            if (blendType == null)
                return -1;

            int color = blendType.color();
            return (color & 0xFF000000) == 0 ? color | 0xFF000000 : color;
        }

        @Override
        public MapCodec<? extends ItemTintSource> type() {
            return MAP_CODEC;
        }
    }

    public static class BlendWrapper implements ItemModel {
        private final ItemModel originalModel;

        public BlendWrapper(ItemModel originalModel) {
            this.originalModel = originalModel;
        }

        @Override
        public void update(ItemStackRenderState renderState, ItemStack stack, ItemModelResolver resolver, ItemDisplayContext displayContext, ClientLevel level, ItemOwner owner, int seed
        ) {
            Identifier blendId = stack.has(BiomeBlendsDataComponents.BLEND_TYPE.get())
                    ? stack.get(BiomeBlendsDataComponents.BLEND_TYPE.get())
                    : null;

            if (level != null && blendId != null) {
                Optional<Holder.Reference<BlendType>> blendType = level.registryAccess().lookupOrThrow(BiomeBlendsRegistries.BLEND_TYPE).get(blendId);

                if (blendType.isPresent()) {
                    if (!blendType.get().value().model().equals(Identifier.fromNamespaceAndPath(BiomeBlends.MOD_ID, "biome_blend"))) {
                        ItemModel overrideModel = Minecraft.getInstance().getModelManager().getItemModel(blendType.get().value().model());

                        overrideModel.update(renderState, stack, resolver, displayContext, level, owner, seed);
                        return;
                    }
                }
            }

            this.originalModel.update(renderState, stack, resolver, displayContext, level, owner, seed);
        }
    }
}
