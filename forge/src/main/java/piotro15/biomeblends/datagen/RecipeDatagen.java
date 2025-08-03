package piotro15.biomeblends.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
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

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BiomeBlendsItems.BIOME_BLEND.get())
                .requires(Items.CLAY_BALL, 3)
                .requires(Items.WHITE_DYE, 2)
                .requires(Items.PAPER)
                .unlockedBy("has_clay_ball", has(Items.CLAY_BALL))
                .save(output);

        BlendData.blends.forEach(blend -> {
            Map<Item, Integer> items = new LinkedHashMap<>();
            items.put(BiomeBlendsItems.BIOME_BLEND.get(), 1);
            items.putAll(blend.ingredients());
            shapelessBlendRecipe(output, blend.id(), items);
        });
    }

    private static void shapelessBlendRecipe(Consumer<FinishedRecipe> output, String path, Map<Item, Integer> ingredients) {
        ItemStack outputStack = new ItemStack(BiomeBlendsItems.BIOME_BLEND.get());
        BlendType.save(outputStack, minecraft(path));

        List<Ingredient> ingredientsList = new ArrayList<>();
        for (Map.Entry<Item, Integer> entry : ingredients.entrySet()) {
            Item item = entry.getKey();
            int count = entry.getValue();
            for (int i = 0; i < count; i++) {
                ingredientsList.add(Ingredient.of(item));
            }
        }

        NBTShapelessRecipe recipe = new NBTShapelessRecipe(RecipeCategory.MISC, minecraft("blend_type/" + path), outputStack, ingredientsList);
        recipe.unlockedBy("has_ingredients", has(ingredients.keySet().stream().skip(1).findFirst().orElseThrow(() -> new IllegalStateException("Map has fewer than 2 items"))));

        output.accept(recipe);
    }

    private static ResourceLocation minecraft(String path) {
        return new ResourceLocation("minecraft", path);
    }
}
