package piotro15.biomeblends.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import piotro15.biomeblends.registry.BiomeBlendsItems;

import java.util.concurrent.CompletableFuture;

public class RecipeDatagen extends RecipeProvider {

    public RecipeDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BiomeBlendsItems.BIOME_BLEND.get())
                .requires(Items.CLAY_BALL, 3)
                .requires(Items.WHITE_DYE, 2)
                .requires(Items.PAPER)
                .unlockedBy("has_clay_ball", has(Items.CLAY_BALL))
                .save(output);
    }
}
