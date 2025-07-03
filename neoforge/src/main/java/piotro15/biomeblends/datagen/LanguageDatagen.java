package piotro15.biomeblends.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import piotro15.biomeblends.registry.BiomeBlendsItems;

public class LanguageDatagen extends LanguageProvider {
    public LanguageDatagen(PackOutput output, String modId, String locale) {
        super(output, modId, locale);
    }

    @Override
    protected void addTranslations() {
        addItem(BiomeBlendsItems.BIOME_BLEND, "Bland Blend");

        BlendData.blends.forEach(blendData ->
                add("blend_type.minecraft." + blendData.id(), blendData.name()));

        add("itemGroup.biomeblends.blends", "Biome Blends");
    }
}
