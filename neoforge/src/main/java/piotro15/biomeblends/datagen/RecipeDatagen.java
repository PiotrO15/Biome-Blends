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
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import org.jetbrains.annotations.NotNull;
import piotro15.biomeblends.registry.BiomeBlendsDataComponents;
import piotro15.biomeblends.registry.BiomeBlendsItems;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RecipeDatagen extends RecipeProvider {

    public RecipeDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    public static class BOPRecipeDatagen extends RecipeProvider {
        public BOPRecipeDatagen(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(arg, completableFuture);
        }

        @Override
        protected void buildRecipes(@NotNull RecipeOutput output) {
            BlendData.biomesOPlentyBlends.forEach(blend -> {
                Map<Item, Integer> items = new HashMap<>();
                items.put(BiomeBlendsItems.BLAND_BLEND.get(), 1);
                items.putAll(blend.ingredients());
                conditionalBlendRecipe(output, blend.getResourceLocation(), items);
            });
        }
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BiomeBlendsItems.BLAND_BLEND.get())
                .requires(Items.CLAY_BALL, 3)
                .requires(Items.WHITE_DYE, 2)
                .requires(Items.PAPER)
                .unlockedBy("has_clay_ball", has(Items.CLAY_BALL))
                .save(output);

        BlendData.blends.forEach(blend -> {
            Map<Item, Integer> items = new HashMap<>();
            items.put(BiomeBlendsItems.BLAND_BLEND.get(), 1);
            items.putAll(blend.ingredients());
            shapelessBlendRecipe(output, blend.getResourceLocation(), items);
        });
    }

    private static void shapelessBlendRecipe(RecipeOutput output, ResourceLocation resourceLocation, Map<Item, Integer> ingredients) {
        ItemStack outputStack = new ItemStack(BiomeBlendsItems.BIOME_BLEND.get());
        outputStack.applyComponents(DataComponentMap.builder()
                .set(BiomeBlendsDataComponents.BLEND_TYPE, resourceLocation)
                .build());

        ShapelessRecipeBuilder recipeBuilder = ShapelessRecipeBuilder.shapeless(
                RecipeCategory.MISC,
                outputStack
        );
        ingredients.forEach(recipeBuilder::requires);
        recipeBuilder.unlockedBy("has_ingredients", has(ingredients.keySet().iterator().next()));
        recipeBuilder.save(output, recipeLocation(resourceLocation));
    }

    private static void conditionalBlendRecipe(RecipeOutput output, ResourceLocation resourceLocation, Map<Item, Integer> ingredients) {
        ItemStack outputStack = new ItemStack(BiomeBlendsItems.BIOME_BLEND.get());
        outputStack.applyComponents(DataComponentMap.builder()
                .set(BiomeBlendsDataComponents.BLEND_TYPE, resourceLocation)
                .build());

        ShapelessRecipeBuilder recipeBuilder = ShapelessRecipeBuilder.shapeless(
                RecipeCategory.MISC,
                outputStack
        );
        output = output.withConditions(new ModLoadedCondition(resourceLocation.getNamespace()));
        ingredients.forEach(recipeBuilder::requires);
        recipeBuilder.unlockedBy("has_ingredients", has(ingredients.keySet().iterator().next()));
        recipeBuilder.save(output, recipeLocation(resourceLocation));
    }

    private static ResourceLocation recipeLocation(ResourceLocation blendLocation) {
        return ResourceLocation.fromNamespaceAndPath(blendLocation.getNamespace(), "blend_type/" + blendLocation.getPath());
    }
}
