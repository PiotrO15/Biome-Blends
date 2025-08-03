package piotro15.biomeblends.fabric.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsItems;

@Mixin(ItemRenderer.class)
public abstract class BlendRendererMixin {
    @Shadow protected abstract void renderModelLists(BakedModel bakedModel, ItemStack itemStack, int i, int j, PoseStack poseStack, VertexConsumer vertexConsumer);

    @Inject(
            method = "render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void biomeblends$customRender(
            ItemStack itemStack,
            ItemDisplayContext itemDisplayContext,
            boolean bl,
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int i,
            int j,
            BakedModel bakedModel,
            CallbackInfo ci
    ) {
        if (!itemStack.isEmpty() && itemStack.getItem() == BiomeBlendsItems.BIOME_BLEND.get()) {
            ResourceLocation blendType = BlendType.fromItem(itemStack);

            if (blendType != null) {
                ResourceLocation overrideModelLocation = new ResourceLocation(blendType.getNamespace(), "blend_type/" + blendType.getPath());
                BakedModel overrideModel = Minecraft.getInstance().getModelManager().getModel(overrideModelLocation);

                if (overrideModel != null && !overrideModel.equals(Minecraft.getInstance().getModelManager().getMissingModel())) {
                    poseStack.pushPose();

                    overrideModel.getTransforms().getTransform(itemDisplayContext).apply(bl, poseStack);
                    poseStack.translate(-0.5F, -0.5F, -0.5F);

                    RenderType renderType = ItemBlockRenderTypes.getRenderType(itemStack, true);
                    VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);
                     this.renderModelLists(overrideModel, itemStack, i, j, poseStack, vertexConsumer);
                    poseStack.popPose();
                    ci.cancel();
                }
            }
        }
    }
}