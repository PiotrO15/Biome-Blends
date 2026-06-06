package piotro15.biomeblends;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsDataComponents;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

public class BiomeBlendsClient {
    public static final ItemTintSource TINT_HANDLER = new ItemTintSource() {
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
            return null;
        }
    };
}
