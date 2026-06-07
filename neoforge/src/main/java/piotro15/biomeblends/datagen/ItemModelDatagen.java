package piotro15.biomeblends.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.BiomeBlendsClient;
import piotro15.biomeblends.registry.BiomeBlendsItems;

public class ItemModelDatagen extends ModelProvider {

    public ItemModelDatagen(PackOutput output) {
        super(output, BiomeBlends.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(BiomeBlendsItems.BLAND_BLEND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.itemModelOutput.accept(
                BiomeBlendsItems.BIOME_BLEND.get(),
                ItemModelUtils.tintedModel(itemModels.createFlatItemModel(BiomeBlendsItems.BIOME_BLEND.get(), ModelTemplates.FLAT_ITEM), new BiomeBlendsClient.BiomeBlend(0xFF0000FF))
        );
        registerBlendModel(itemModels, "bumpy_blend");
        registerBlendModel(itemModels, "fluffy_blend");
        registerBlendModel(itemModels, "rocky_blend");
    }

    private void registerBlendModel(ItemModelGenerators itemModels, String name) {
        Identifier itemId = BiomeBlends.id("item/" + name);
        itemModels.itemModelOutput.register(
                BiomeBlends.id(name),
                new ClientItem(ItemModelUtils.tintedModel(ModelTemplates.FLAT_ITEM.create(itemId, TextureMapping.layer0(new Material(itemId)), itemModels.modelOutput), new BiomeBlendsClient.BiomeBlend(0xFF0000FF)), ClientItem.Properties.DEFAULT)
        );
    }
}
