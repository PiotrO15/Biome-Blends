package piotro15.biomeblends.blend;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import piotro15.biomeblends.registry.BiomeBlendsDataComponents;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

public class BlendRenderer extends BlockEntityWithoutLevelRenderer
{
    public BlendRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
    }

//    @Override
//    public void renderByItem(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
//        ResourceLocation blendId = itemStack.getComponents().get(BiomeBlendsDataComponents.BLEND_TYPE.get());
//        if (blendId == null)
//            return;
//
//        if (Minecraft.getInstance().getConnection() == null)
//            return;
//
//        Registry<BlendType> registry = Minecraft.getInstance().getConnection()
//                .registryAccess().registryOrThrow(BiomeBlendsRegistries.BLEND_TYPE);
//
//        BlendType blendType = registry.get(blendId);
//        if (blendType == null)
//            return;
//
//        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
//
//        ItemOverrides itemOverrides = ItemOverrides.EMPTY;
//
//        poseStack.popPose();
//        poseStack.pushPose();
//
//        seedModel = seedModel.applyTransform(itemDisplayContext, poseStack, isLeftHand(itemDisplayContext));
//        poseStack.translate(-.5, -.5, -.5);
//
//        boolean glint = stack.hasFoil();
//        for (RenderType type : seedModel.getRenderTypes(stack, true)) {
//            type = RenderTypeHelper.getEntityRenderType(type, true);
//            VertexConsumer consumer = ItemRenderer.getFoilBuffer(buffer, type, true, glint);
//            renderer.renderModelLists(seedModel, stack, packedLight, packedOverlay, poseStack, consumer);
//        }
//    }
}
