package piotro15.biomeblends.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsItems;

@JeiPlugin
public class BiomeBlendsJeiPlugin implements IModPlugin {
    public static final ResourceLocation ID = new ResourceLocation(BiomeBlends.MOD_ID, "jei_compat");

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(BiomeBlendsItems.BIOME_BLEND.get(), (stack, context) -> {
            ResourceLocation blendType = BlendType.fromItem(stack);
            if (blendType != null) {
                return blendType.toString();
            }
            return "biomeblends:none";
        });
    }
}
