package piotro15.biomeblends.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelDatagen extends ItemModelProvider {
    public ItemModelDatagen(PackOutput output, String modId, ExistingFileHelper existingFileHelper) {
        super(output, modId, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        BlendData.blends.forEach(blend -> this.registerBlendTypeModel(blend.id(), blend.texture()));
    }

    public void registerBlendTypeModel(String blendType, String texture) {
        getBuilder("minecraft:blend_type/" + blendType)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath("biomeblends", "item/" + texture));
    }
}
