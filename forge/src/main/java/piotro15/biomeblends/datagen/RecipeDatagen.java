package piotro15.biomeblends.datagen;

import com.mojang.datafixers.util.Either;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.registry.BiomeBlendsItems;

import java.util.*;
import java.util.function.Consumer;

public class RecipeDatagen extends RecipeProvider {

    public RecipeDatagen(PackOutput output) {
        super(output);
    }

    public static class BlendRecipeProvider extends RecipeProvider {
        private final List<BlendData> blends;
        public BlendRecipeProvider(PackOutput arg, List<BlendData> blends) {
            super(arg);
            this.blends = blends;
        }

        @Override
        protected void buildRecipes(@NotNull Consumer<FinishedRecipe> output) {
            blends.forEach(blend -> {
                Map<Either<Item, TagKey<Item>>, Integer> items = new LinkedHashMap<>();
                items.put(Either.left(BiomeBlendsItems.BLAND_BLEND.get()), 1);
                items.putAll(blend.ingredients());
                shapelessBlendRecipe(output, blend.getResourceLocation(), items);
            });
        }
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BiomeBlendsItems.BLAND_BLEND.get(), 4)
                .requires(Items.CLAY_BALL, 3)
                .requires(Items.WHITE_DYE, 2)
                .requires(Items.PAPER)
                .unlockedBy("has_clay_ball", has(Items.CLAY_BALL))
                .save(output);

        BlendData.blends.forEach(blend -> {
            Map<Either<Item, TagKey<Item>>, Integer> items = new LinkedHashMap<>();
            items.put(Either.left(BiomeBlendsItems.BLAND_BLEND.get()), 1);
            items.putAll(blend.ingredients());
            shapelessBlendRecipe(output, blend.getResourceLocation(), items);
        });
    }

    private static void shapelessBlendRecipe(Consumer<FinishedRecipe> output, ResourceLocation resourceLocation, Map<Either<Item, TagKey<Item>>, Integer> ingredients) {
        ItemStack outputStack = new ItemStack(BiomeBlendsItems.BIOME_BLEND.get());
        BlendType.save(outputStack, resourceLocation);

        List<Ingredient> ingredientsList = new ArrayList<>();
        ingredients.forEach((either, count) -> {
            Ingredient ingredient = either.map(Ingredient::of, Ingredient::of);
            for (int i = 0; i < count; i++) {
                ingredientsList.add(ingredient);
            }
        });

        NBTShapelessRecipe recipe = new NBTShapelessRecipe(RecipeCategory.MISC, recipeLocation(resourceLocation), outputStack, ingredientsList);
        Either<Item, TagKey<Item>> firstInput = ingredients.keySet().stream().skip(1).findFirst().orElseThrow();
        recipe.unlockedBy("has_ingredients", firstInput.map(RecipeProvider::has, RecipeProvider::has));

        output.accept(recipe);
    }

    private static ResourceLocation recipeLocation(ResourceLocation blendLocation) {
        return ResourceLocation.fromNamespaceAndPath(blendLocation.getNamespace(), "blend_type/" + blendLocation.getPath());
    }
}
