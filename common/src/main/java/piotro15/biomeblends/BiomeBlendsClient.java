package piotro15.biomeblends;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

public class BiomeBlendsClient {
    public static final ItemColor TINT_HANDLER = (stack, tintIndex) -> {
        ResourceLocation blendId = BlendType.fromItem(stack);
        if (blendId == null)
            return -1;

        if (Minecraft.getInstance().getConnection() == null)
            return -1;

        Registry<BlendType> registry = Minecraft.getInstance().getConnection()
                .registryAccess().registryOrThrow(BiomeBlendsRegistries.BLEND_TYPE);

        BlendType blendType = registry.get(blendId);
        if (blendType == null)
            return -1;

        int color = blendType.color();
        return (color & 0xFF000000) == 0 ? color | 0xFF000000 : color;
    };
}
