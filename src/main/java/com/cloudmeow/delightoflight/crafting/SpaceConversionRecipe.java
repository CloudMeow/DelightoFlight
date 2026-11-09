package com.cloudmeow.delightoflight.crafting;

import com.cloudmeow.delightoflight.registry.DFRecipeSerializers;
import com.cloudmeow.delightoflight.registry.DFRecipeTypes;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SpaceConversionRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Ingredient ingredient;
    private final ItemStack result;

    public SpaceConversionRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result) {
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public ItemStack getResult() {
        return this.result;
    }

    public ItemStack createResult(ItemStack input) {
        if (input.isEmpty()) {
            return ItemStack.EMPTY;
        }
        ItemStack output = this.result.copy();
        output.setCount(this.result.getCount() * input.getCount());
        return output;
    }

    @NotNull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(this.ingredient);
        return ingredients;
    }

    @NotNull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        return this.ingredient.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess access) {
        return createResult(container.getItem(0));
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DFRecipeSerializers.SPACE_CONVERSION.get();
    }

    @Override
    public RecipeType<?> getType() {
        return DFRecipeTypes.SPACE_CONVERSION.get();
    }

    public static class Serializer implements RecipeSerializer<SpaceConversionRecipe> {
        @Override
        public SpaceConversionRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new SpaceConversionRecipe(recipeId, ingredient, result);
        }

        @Override
        public SpaceConversionRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            return new SpaceConversionRecipe(recipeId, ingredient, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SpaceConversionRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}
