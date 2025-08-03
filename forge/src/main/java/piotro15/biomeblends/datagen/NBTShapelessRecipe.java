package piotro15.biomeblends.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NBTShapelessRecipe implements FinishedRecipe {
    private static final ResourceLocation ROOT_RECIPE_ADVANCEMENT = new ResourceLocation("recipes/root");

    private final RecipeCategory category;
    private final ResourceLocation id;
    private final Item result;
    private final int count;
    private final List<Ingredient> ingredients;
    private final @Nullable String nbt;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();

    public NBTShapelessRecipe(RecipeCategory category, ResourceLocation id, ItemStack result, List<Ingredient> ingredients) {
        this.category = category;
        this.id = id;
        this.result = result.getItem();
        this.count = result.getCount();
        this.ingredients = ingredients;
        this.nbt = result.getOrCreateTag().toString();
    }

    @Override
    public void serializeRecipeData(@NotNull JsonObject json) {
        JsonArray ingredientsArray = new JsonArray();
        for (Ingredient ingredient : ingredients) {
            ingredientsArray.add(ingredient.toJson());
        }
        json.add("ingredients", ingredientsArray);

        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("item", BuiltInRegistries.ITEM.getKey(result).toString());
        if (count > 1) {
            resultObject.addProperty("count", count);
        }
        if (nbt != null && !nbt.isEmpty()) {
            resultObject.addProperty("nbt", nbt);
        }

        json.add("result", resultObject);
        json.addProperty("type", "minecraft:crafting_shapeless");
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getType() {
        return RecipeSerializer.SHAPELESS_RECIPE;
    }

    @Override
    public @Nullable JsonObject serializeAdvancement() {
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
        return this.advancement.serializeToJson();
    }

    @Override
    public @Nullable ResourceLocation getAdvancementId() {
        return id.withPrefix("recipes/" + this.category.getFolderName() + "/");
    }

    public void unlockedBy(String string, CriterionTriggerInstance arg) {
        this.advancement.addCriterion(string, arg);
    }
}
