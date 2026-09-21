package piotro15.biomeblends.datagen;

import com.mojang.datafixers.util.Either;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.NonNull;
import piotro15.biomeblends.registry.BiomeBlendsDataComponents;
import piotro15.biomeblends.registry.BiomeBlendsItems;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecipeDatagen extends RecipeProvider {

    public RecipeDatagen(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    public static class BlendRecipeProvider extends RecipeProvider {
        private final List<BlendData> blends;
        public BlendRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput, List<BlendData> blends) {
            super(recipeOutput, advancementOutput);
            this.blends = blends;
        }

        @Override
        protected void buildRecipes() {
            blends.forEach(blend -> {
                Map<Either<Item, TagKey<Item>>, Integer> items = new LinkedHashMap<>();
                items.put(Either.left(BiomeBlendsItems.BLAND_BLEND.get()), 1);
                items.putAll(blend.ingredients());
                shapelessBlendRecipe(this.items, output, blend.getIdentifier(), items);
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
            return blendLocation.getNamespace() + ":blend_type/" + blendLocation.getPath();
        }

        public static MultiRegistryBootstrap create(List<BlendData> blends) {
            return new MultiRegistryBootstrap() {
                @Override
                public @NonNull Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
                    return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
                }

                @Override
                public void run(MultiRegistryBootstrap.@NonNull BootstrapGetter registries) {
                    new BlendRecipeProvider(registries.get(Registries.RECIPE), registries.get(Registries.ADVANCEMENT), blends).buildRecipes();
                }
            };
        }
    }

    @Override
    protected void buildRecipes() {
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, BiomeBlendsItems.BLAND_BLEND.get(), 4)
                .requires(Items.CLAY_BALL, 3)
                .requires(Items.DYE.white(), 2)
                .requires(Items.PAPER)
                .unlockedBy("has_clay_ball", has(Items.CLAY_BALL))
                .save(output);

        BlendData.blends.forEach(blend -> {
            Map<Either<Item, TagKey<Item>>, Integer> items = new LinkedHashMap<>();
            items.put(Either.left(BiomeBlendsItems.BLAND_BLEND.get()), 1);
            items.putAll(blend.ingredients());
            shapelessBlendRecipe(this.items, output, blend.getIdentifier(), items);
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

    public static MultiRegistryBootstrap create() {
        return new MultiRegistryBootstrap() {
            @Override
            public @NonNull Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
                return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
            }

            @Override
            public void run(MultiRegistryBootstrap.@NonNull BootstrapGetter registries) {
                new RecipeDatagen(registries.get(Registries.RECIPE), registries.get(Registries.ADVANCEMENT)).buildRecipes();
            }
        };
    }
}
