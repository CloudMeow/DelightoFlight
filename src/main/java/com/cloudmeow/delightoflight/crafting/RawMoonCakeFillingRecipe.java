package com.cloudmeow.delightoflight.crafting;

import com.cloudmeow.delightoflight.registry.DFRecipeSerializers;
import com.cloudmeow.delightoflight.registry.DFRecipeTypes;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class RawMoonCakeFillingRecipe implements Recipe<SimpleContainer> {
    private final Ingredient filling;
    private final ResourceLocation result;
    private final ResourceLocation id;

    public RawMoonCakeFillingRecipe(ResourceLocation id, Ingredient filling, ResourceLocation result) {
        this.filling = filling;
        this.result = result;
        this.id = id;
    }

    public Block getResultBlock() {
        return BuiltInRegistries.BLOCK.get(this.result);
    }

    @NotNull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(this.filling);
        return ingredients;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        return this.filling.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess access) {
        return new ItemStack(getResultBlock());
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registries) {
        return new ItemStack(getResultBlock());
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DFRecipeSerializers.RAW_MOON_CAKE_FILLING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return DFRecipeTypes.RAW_MOON_CAKE_FILLING.get();
    }

    public static class Serializer implements RecipeSerializer<RawMoonCakeFillingRecipe> {
        @Override
        public RawMoonCakeFillingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient filling = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            ResourceLocation result = new ResourceLocation(GsonHelper.getAsString(json, "result"));
            return new RawMoonCakeFillingRecipe(recipeId, filling, result);
        }

        @Override
        public RawMoonCakeFillingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient filling = Ingredient.fromNetwork(buffer);
            ResourceLocation result = buffer.readResourceLocation();
            return new RawMoonCakeFillingRecipe(recipeId, filling, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RawMoonCakeFillingRecipe recipe) {
            recipe.filling.toNetwork(buffer);
            buffer.writeResourceLocation(recipe.result);
        }
    }
}
