package piotro15.biomeblends.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsDataComponents;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

import javax.annotation.Nullable;
import java.util.Optional;

public class BlendWrapper implements ItemModel {
    private final ItemModel originalModel;

    public BlendWrapper(ItemModel originalModel) {
        this.originalModel = originalModel;
    }

    @Override
    public void update(ItemStackRenderState renderState, ItemStack stack, ItemModelResolver resolver, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed
    ) {
        Identifier blendId = stack.has(BiomeBlendsDataComponents.BLEND_TYPE)
                ? stack.get(BiomeBlendsDataComponents.BLEND_TYPE)
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
