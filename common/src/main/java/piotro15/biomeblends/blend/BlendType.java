package piotro15.biomeblends.blend;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import piotro15.biomeblends.blend.blend_action.BlendAction;

import java.util.ArrayList;

public record BlendType(
        BlendAction action,
        int horizontalRadius,
        int verticalRadius,
        BlendFilter<ResourceLocation> dimensionBlacklist,
        BlendFilter<ResourceLocation> biomeBlacklist,
        BlendFilter<String> namespaceBlacklist,
        int color,
        ItemStack useRemainder
) {
    public static final Codec<BlendType> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BlendActionRegistry.CODEC.fieldOf("action").forGetter(BlendType::action),
                    Codec.INT.optionalFieldOf("horizontal_radius", 4).forGetter(BlendType::horizontalRadius),
                    Codec.INT.optionalFieldOf("vertical_radius", 4).forGetter(BlendType::verticalRadius),
                    BlendFilter.RESOURCE_LOCATION_CODEC.optionalFieldOf("dimension_blacklist", new BlendFilter<>(new ArrayList<>(), false)).forGetter(BlendType::dimensionBlacklist),
                    BlendFilter.RESOURCE_LOCATION_CODEC.optionalFieldOf("biome_blacklist", new BlendFilter<>(new ArrayList<>(), false)).forGetter(BlendType::biomeBlacklist),
                    BlendFilter.STRING_CODEC.optionalFieldOf("namespace_blacklist", new BlendFilter<>(new ArrayList<>(), false)).forGetter(BlendType::namespaceBlacklist),
                    Codec.INT.optionalFieldOf("color", 0xFFFFFF).forGetter(BlendType::color),
                    ItemStack.SINGLE_ITEM_CODEC.optionalFieldOf("use_remainder", ItemStack.EMPTY).forGetter(BlendType::useRemainder)
            ).apply(instance, BlendType::new)
    );

    @SuppressWarnings("unused")
    public static class BlendTypeBuilder {
        private BlendAction action;
        private int horizontalRadius = 4;
        private int verticalRadius = 4;
        private BlendFilter<ResourceLocation> dimensionBlacklist = new BlendFilter<>(new ArrayList<>(), false);
        private BlendFilter<ResourceLocation> biomeBlacklist = new BlendFilter<>(new ArrayList<>(), false);
        private BlendFilter<String> namespaceBlacklist = new BlendFilter<>(new ArrayList<>(), false);
        private int color = 0xFFFFFF;
        private ItemStack useRemainder = ItemStack.EMPTY;

        public BlendTypeBuilder action(BlendAction action) {
            this.action = action;
            return this;
        }

        public BlendTypeBuilder horizontalRadius(int radius) {
            this.horizontalRadius = radius;
            return this;
        }

        public BlendTypeBuilder verticalRadius(int radius) {
            this.verticalRadius = radius;
            return this;
        }

        public BlendTypeBuilder dimensionBlacklist(BlendFilter<ResourceLocation> filter) {
            this.dimensionBlacklist = filter;
            return this;
        }

        public BlendTypeBuilder biomeBlacklist(BlendFilter<ResourceLocation> filter) {
            this.biomeBlacklist = filter;
            return this;
        }

        public BlendTypeBuilder namespaceBlacklist(BlendFilter<String> filter) {
            this.namespaceBlacklist = filter;
            return this;
        }

        public BlendTypeBuilder color(int color) {
            this.color = color;
            return this;
        }

        public BlendTypeBuilder useRemainder(ItemStack stack) {
            this.useRemainder = stack;
            return this;
        }

        public BlendType build() {
            if (action == null) {
                throw new IllegalStateException("BlendAction must be set");
            }
            return new BlendType(action, horizontalRadius, verticalRadius,
                    dimensionBlacklist, biomeBlacklist, namespaceBlacklist, color, useRemainder);
        }
    }
}

