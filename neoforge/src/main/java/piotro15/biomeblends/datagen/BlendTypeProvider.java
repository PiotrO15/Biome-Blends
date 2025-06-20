package piotro15.biomeblends.datagen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.blend.BlendFilter;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.blend.blend_action.SetBiomeAction;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

import java.util.ArrayList;
import java.util.List;

public class BlendTypeProvider {
    public static void RegisterBlendTypes(BootstrapContext<BlendType> bootstrapContext) {
        registerBlendType(bootstrapContext, "default_blend", new BlendType(
                new SetBiomeAction(minecraft("plains")),
                4,
                4,
                new BlendFilter<>(
                        List.of(
                                minecraft("overworld"),
                                minecraft("the_nether"),
                                minecraft("the_end")
                        ),
                        true
                ),
                new BlendFilter<>(new ArrayList<>(), false),
                new BlendFilter<>(new ArrayList<>(), false),
                0xFFFFFF,
                ItemStack.EMPTY
        ));

        registerBlendType(bootstrapContext, "bad_blend", new BlendType(
                new SetBiomeAction(minecraft("badlands")),
                4,
                4,
                new BlendFilter<>(
                        List.of(
                                minecraft("overworld"),
                                minecraft("the_nether"),
                                minecraft("the_end")
                        ),
                        true
                ),
                new BlendFilter<>(new ArrayList<>(), false),
                new BlendFilter<>(new ArrayList<>(), false),
                0xA17E70,
                ItemStack.EMPTY
        ));

        registerBlendType(bootstrapContext, "badder_blend", new BlendType(
                new SetBiomeAction(minecraft("jungle")),
                4,
                4,
                new BlendFilter<>(
                        List.of(
                                minecraft("overworld"),
                                minecraft("the_nether"),
                                minecraft("the_end")
                        ),
                        true
                ),
                new BlendFilter<>(List.of(
                        minecraft("plains")
                ), false),
                new BlendFilter<>(new ArrayList<>(), false),
                0x8ECB51,
                ItemStack.EMPTY
        ));
    }

    private static void registerBlendType(BootstrapContext<BlendType> bootstrapContext, String id, BlendType blendType) {
        bootstrapContext.register(ResourceKey.create(BiomeBlendsRegistries.BLEND_TYPE, ResourceLocation.fromNamespaceAndPath(BiomeBlends.MOD_ID, id)), blendType);
    }

    private static ResourceLocation minecraft(String path) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", path);
    }
}
