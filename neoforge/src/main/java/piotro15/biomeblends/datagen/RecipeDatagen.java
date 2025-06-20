package piotro15.biomeblends.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import piotro15.biomeblends.registry.BiomeBlendsDataComponents;
import piotro15.biomeblends.registry.BiomeBlendsItems;

import java.util.Map;
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

        shapelessBlendRecipe(output, "lush_caves", Map.of(
                BiomeBlendsItems.BIOME_BLEND.get(), 1,
                Items.GLOW_BERRIES, 2,
                Items.MOSS_BLOCK, 1
        ));
    }

    private static void shapelessBlendRecipe(RecipeOutput output, String path, Map<Item, Integer> ingredients) {
        ItemStack outputStack = new ItemStack(BiomeBlendsItems.BIOME_BLEND.get());
        outputStack.applyComponents(DataComponentMap.builder()
                .set(BiomeBlendsDataComponents.BLEND_TYPE, minecraft(path))
                .build());

        ShapelessRecipeBuilder recipeBuilder = ShapelessRecipeBuilder.shapeless(
                RecipeCategory.MISC,
                outputStack
        );
        ingredients.forEach(recipeBuilder::requires);
        recipeBuilder.unlockedBy("has_ingredients", has(ingredients.keySet().iterator().next()));
        recipeBuilder.save(output, minecraft("blend_type/" + path));
    }

    private static ResourceLocation minecraft(String path) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", path);
    }
}
