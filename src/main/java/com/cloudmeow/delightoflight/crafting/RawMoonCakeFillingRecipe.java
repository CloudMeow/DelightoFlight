package com.cloudmeow.delightoflight.crafting;

import com.cloudmeow.delightoflight.registry.DFRecipeSerializers;
import com.cloudmeow.delightoflight.registry.DFRecipeTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class RawMoonCakeFillingRecipe implements Recipe<SingleRecipeInput> {
    private final Ingredient filling;
    private final ResourceLocation result;

    public RawMoonCakeFillingRecipe(Ingredient filling, ResourceLocation result) {
        this.filling = filling;
        this.result = result;
    }

    public Ingredient getFilling() {
        return this.filling;
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
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.filling.test(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return new ItemStack(getResultBlock());
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
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
        private static final MapCodec<RawMoonCakeFillingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(RawMoonCakeFillingRecipe::getFilling),
                ResourceLocation.CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
        ).apply(inst, RawMoonCakeFillingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, RawMoonCakeFillingRecipe> STREAM_CODEC = StreamCodec.of(RawMoonCakeFillingRecipe.Serializer::toNetwork, RawMoonCakeFillingRecipe.Serializer::fromNetwork);

        public Serializer() {
        }

        private static RawMoonCakeFillingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient filling = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ResourceLocation result = ResourceLocation.STREAM_CODEC.decode(buffer);
            return new RawMoonCakeFillingRecipe(filling, result);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, RawMoonCakeFillingRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.filling);
            ResourceLocation.STREAM_CODEC.encode(buffer, recipe.result);
        }

        @Override
        public MapCodec<RawMoonCakeFillingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, RawMoonCakeFillingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
