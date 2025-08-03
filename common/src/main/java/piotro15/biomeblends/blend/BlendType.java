package piotro15.biomeblends.blend;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
        ItemStack useRemainder,
        SoundEvent sound,
        ParticleOptions particleOptions
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
                    ItemStack.CODEC.optionalFieldOf("use_remainder", ItemStack.EMPTY).forGetter(BlendType::useRemainder),
                    SoundEvent.DIRECT_CODEC.optionalFieldOf("sound", SoundEvents.GLOW_INK_SAC_USE).forGetter(BlendType::sound),
                    ParticleTypes.CODEC.optionalFieldOf("particle_type", ParticleTypes.HAPPY_VILLAGER).forGetter(BlendType::particleOptions)
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
        private SoundEvent sound = SoundEvents.GLOW_INK_SAC_USE;
        private ParticleOptions particleOptions = ParticleTypes.HAPPY_VILLAGER;

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

        public BlendTypeBuilder sound(SoundEvent sound) {
            this.sound = sound;
            return this;
        }

        public BlendTypeBuilder particleOptions(ParticleOptions particleOptions) {
            this.particleOptions = particleOptions;
            return this;
        }

        public BlendType build() {
            if (action == null) {
                throw new IllegalStateException("Blend type definition requires a blend action to work.");
            }
            return new BlendType(action, horizontalRadius, verticalRadius,
                    dimensionBlacklist, biomeBlacklist, namespaceBlacklist, color, useRemainder, sound, particleOptions);
        }
    }

    public static ResourceLocation fromItem(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag != null && tag.contains("blend_type"))
            return ResourceLocation.tryParse(tag.getString("blend_type"));
        return null;
    }

    public static void save(ItemStack itemStack, ResourceLocation value) {
        itemStack.getOrCreateTag().putString("blend_type", value.toString());
    }
}

