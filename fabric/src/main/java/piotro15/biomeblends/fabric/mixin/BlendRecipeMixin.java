package piotro15.biomeblends.fabric.mixin;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapelessRecipe.Serializer.class)
public class BlendRecipeMixin {
    @Inject(method = "fromJson", at = @At("RETURN"), cancellable = true)
    private void injectNBT(ResourceLocation resourceLocation, JsonObject jsonObject, CallbackInfoReturnable<ShapelessRecipe> cir) {
        JsonObject resultObj = jsonObject.getAsJsonObject("result");
        if (resultObj.has("nbt")) {
            try {
                ShapelessRecipe original = cir.getReturnValue();

                CompoundTag nbt = TagParser.parseTag(GsonHelper.getAsString(resultObj, "nbt"));
                ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
                result.setTag(nbt);

                cir.setReturnValue(new ShapelessRecipe(
                        original.getId(),
                        original.getGroup(),
                        original.category(),
                        result,
                        original.getIngredients()
                ));
            } catch (CommandSyntaxException e) {
                throw new JsonSyntaxException("Invalid NBT tag in recipe result: " + e.getMessage());
            }
        }
    }
}
