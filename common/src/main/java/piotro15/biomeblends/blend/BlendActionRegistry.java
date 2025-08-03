package piotro15.biomeblends.blend;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;
import piotro15.biomeblends.blend.blend_action.BlendAction;
import piotro15.biomeblends.blend.blend_action.SetBiomeAction;
import piotro15.biomeblends.blend.blend_action.SetNamespaceAction;

public class BlendActionRegistry {
    private static final BiMap<ResourceLocation, MapCodec<? extends BlendAction>> actionCodecs = HashBiMap.create();

    public static final Codec<BlendAction> CODEC = ResourceLocation.CODEC.dispatch(BlendAction::id, loc -> actionCodecs.get(loc).codec());

    public static <T extends BlendAction> void registerAction(ResourceLocation id, MapCodec<T> codec) {
        if (actionCodecs.containsKey(id)) {
            throw new IllegalArgumentException("Action with id " + id + " is already registered.");
        }
        actionCodecs.put(id, codec);
    }

    public static void registerActions() {
        registerAction(SetBiomeAction.id, SetBiomeAction.CODEC);
        registerAction(SetNamespaceAction.id, SetNamespaceAction.CODEC);
    }
}
