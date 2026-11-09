package com.cloudmeow.delightoflight.crafting;

import com.cloudmeow.delightoflight.registry.DFRecipeSerializers;
import com.cloudmeow.delightoflight.registry.DFRecipeTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SpaceConversionRecipe implements Recipe<SingleRecipeInput> {
    private final Ingredient ingredient;
    private final ItemStack result;

    public SpaceConversionRecipe(Ingredient ingredient, ItemStack result) {
        this.ingredient = ingredient;
        this.result = result;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public ItemStack getResult() {
        return this.result;
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.ingredient.test(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(this.ingredient);
        return ingredients;
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
        private static final MapCodec<SpaceConversionRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(SpaceConversionRecipe::getIngredient),
                ItemStack.CODEC.fieldOf("result").forGetter(SpaceConversionRecipe::getResult)
        ).apply(inst, SpaceConversionRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, SpaceConversionRecipe> STREAM_CODEC =
                StreamCodec.of(SpaceConversionRecipe.Serializer::toNetwork, SpaceConversionRecipe.Serializer::fromNetwork);

        private static SpaceConversionRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
            return new SpaceConversionRecipe(ingredient, result);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, SpaceConversionRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
        }

        @Override
        public MapCodec<SpaceConversionRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SpaceConversionRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
