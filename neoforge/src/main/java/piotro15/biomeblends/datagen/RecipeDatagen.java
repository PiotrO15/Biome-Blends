package piotro15.biomeblends.datagen;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import piotro15.biomeblends.registry.BiomeBlendsDataComponents;
import piotro15.biomeblends.registry.BiomeBlendsItems;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RecipeDatagen extends RecipeProvider {

    public RecipeDatagen(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        super(provider, recipeOutput);
    }

    public static class BlendRecipeProvider extends RecipeProvider {
        private final List<BlendData> blends;
        public BlendRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput, List<BlendData> blends) {
            super(provider, recipeOutput);
            this.blends = blends;
        }

        @Override
        protected void buildRecipes() {
            blends.forEach(blend -> {
                Map<Either<Item, TagKey<Item>>, Integer> items = new LinkedHashMap<>();
                items.put(Either.left(BiomeBlendsItems.BLAND_BLEND.get()), 1);
                items.putAll(blend.ingredients());
                shapelessBlendRecipe(registries.lookupOrThrow(Registries.ITEM), output, blend.getIdentifier(), items);
            });
        }

        private void shapelessBlendRecipe(HolderGetter<Item> registry, RecipeOutput output, Identifier resourceLocation, Map<Either<Item, TagKey<Item>>, Integer> ingredients) {
            ItemStackTemplate outputStack = new ItemStackTemplate(BiomeBlendsItems.BIOME_BLEND.get(), DataComponentPatch.builder().set(BiomeBlendsDataComponents.BLEND_TYPE.get(), resourceLocation).build());

            ShapelessRecipeBuilder recipeBuilder = ShapelessRecipeBuilder.shapeless(
                    registry,
                    RecipeCategory.MISC,
                    outputStack
            );
            ingredients.forEach((either, count) -> {
                Ingredient ingredient = either.map(Ingredient::of, tag -> Ingredient.of(registry.getOrThrow(tag)));
                recipeBuilder.requires(ingredient, count);
            });
            Either<Item, TagKey<Item>> firstInput = ingredients.keySet().stream().skip(1).findFirst().orElseThrow();
            recipeBuilder.unlockedBy("has_ingredients", has(firstInput.orThrow()));

            recipeBuilder.save(output, recipeLocation(resourceLocation));
        }

        private static String recipeLocation(Identifier blendLocation) {
            return "blend_type/" + blendLocation.getPath();
        }

        public static final class Runner extends RecipeProvider.Runner
        {
            public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture)
            {
                super(output, completableFuture);
            }

            @Override
            protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output)
            {
                return new RecipeDatagen(provider, output);
            }

            @Override
            public String getName()
            {
                return "Biome Blends Recipes";
            }
        }
    }

    @Override
    protected void buildRecipes() {
        ShapelessRecipeBuilder.shapeless(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, BiomeBlendsItems.BLAND_BLEND.get(), 4)
                .requires(Items.CLAY_BALL, 3)
                .requires(Items.WHITE_DYE, 2)
                .requires(Items.PAPER)
                .unlockedBy("has_clay_ball", has(Items.CLAY_BALL))
                .save(output);

        BlendData.blends.forEach(blend -> {
            Map<Either<Item, TagKey<Item>>, Integer> items = new LinkedHashMap<>();
            items.put(Either.left(BiomeBlendsItems.BLAND_BLEND.get()), 1);
            items.putAll(blend.ingredients());
            shapelessBlendRecipe(registries.lookupOrThrow(Registries.ITEM), output, blend.getIdentifier(), items);
        });
    }

    private void shapelessBlendRecipe(HolderGetter<Item> registry, RecipeOutput output, Identifier resourceLocation, Map<Either<Item, TagKey<Item>>, Integer> ingredients) {
        ItemStackTemplate outputStack = new ItemStackTemplate(BiomeBlendsItems.BIOME_BLEND.get(), DataComponentPatch.builder().set(BiomeBlendsDataComponents.BLEND_TYPE.get(), resourceLocation).build());

        ShapelessRecipeBuilder recipeBuilder = ShapelessRecipeBuilder.shapeless(
                registry,
                RecipeCategory.MISC,
                outputStack
        );
        ingredients.forEach((either, count) -> {
            Ingredient ingredient = either.map(Ingredient::of, tag -> Ingredient.of(registry.getOrThrow(tag)));
            recipeBuilder.requires(ingredient, count);
        });
        Either<Item, TagKey<Item>> firstInput = ingredients.keySet().stream().skip(1).findFirst().orElseThrow();
        recipeBuilder.unlockedBy("has_ingredients", has(firstInput.orThrow()));

        recipeBuilder.save(output, recipeLocation(resourceLocation));
    }

    private static String recipeLocation(Identifier blendLocation) {
        return "blend_type/" + blendLocation.getPath();
    }

    public static final class Runner extends RecipeProvider.Runner
    {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture)
        {
            super(output, completableFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output)
        {
            return new RecipeDatagen(provider, output);
        }

        @Override
        public String getName()
        {
            return "Biome Blends Recipes";
        }
    }
}
