package piotro15.biomeblends.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.NotNull;
import piotro15.biomeblends.blend.BlendType;

import javax.annotation.Nullable;

public class BlendWrapper extends BakedModelWrapper<BakedModel> {
    public BlendWrapper(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return new ItemOverrides() {
            @Override
            public BakedModel resolve(@NotNull BakedModel model, @NotNull ItemStack stack, @Nullable ClientLevel arg3, @Nullable LivingEntity arg4, int k) {
                ResourceLocation blendType = BlendType.fromItem(stack);

                if (blendType != null) {
                    ResourceLocation overrideModelLocation = new ResourceLocation(blendType.getNamespace(), "blend_type/" + blendType.getPath());
                    BakedModel overrideModel = Minecraft.getInstance().getModelManager().getModel(overrideModelLocation);

                    if (!overrideModel.equals(Minecraft.getInstance().getModelManager().getMissingModel())) {
                        return overrideModel;
                    }
                }

                return originalModel;
            }
        };
    }
}
