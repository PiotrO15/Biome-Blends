package piotro15.biomeblends.datagen;

import net.minecraft.resources.ResourceLocation;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.blend.blend_action.SetBiomeAction;

import java.util.List;

public record BlendData(
        String name,
        String id,
        BlendType blendType,
        String texture
        ) {

    public static final List<BlendData> blends = List.of(
            new BlendData("Deep Dark Blend", "deep_dark", new BlendType.BlendTypeBuilder().action(new SetBiomeAction(minecraft("deep_dark"))).color(0x002F43).build(), "rocky_blend"),
            new BlendData("Dripstone Caves Blend", "dripstone_caves", new BlendType.BlendTypeBuilder().action(new SetBiomeAction(minecraft("dripstone_caves"))).color(0xA17E70).build(), "rocky_blend"),
            new BlendData("Lush Caves Blend", "lush_caves", new BlendType.BlendTypeBuilder().action(new SetBiomeAction(minecraft("lush_caves"))).color(0x8ECB51).build(), "rocky_blend"),

            new BlendData("Forest Blend", "forest", new BlendType.BlendTypeBuilder().action(new SetBiomeAction(minecraft("forest"))).color(0x69D23C).build(), "fluffy_blend")
    );

    private static ResourceLocation minecraft(String path) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", path);
    }
}
