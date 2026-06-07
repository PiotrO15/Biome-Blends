package piotro15.biomeblends.fabric.client;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.BiomeBlendsClient;

public class BiomeBlendsModelLoadingPlugin implements ModelLoadingPlugin {
    @Override
    public void initialize(Context context) {
        ModelModifier.AfterBakeItem modifier = (model, renderContext) -> {
            if (renderContext.itemId().equals(BiomeBlends.id("biome_blend"))) {
                return new BiomeBlendsClient.BlendWrapper(model);
            }
            return model;
        };

        context.modifyItemModelAfterBake().register(modifier);
    }
}
