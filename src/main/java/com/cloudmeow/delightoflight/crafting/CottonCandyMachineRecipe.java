package com.cloudmeow.delightoflight.crafting;

import com.cloudmeow.delightoflight.registry.DFRecipeSerializers;
import com.cloudmeow.delightoflight.registry.DFRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

public class CottonCandyMachineRecipe implements Recipe<RecipeWrapper> {
    public static final int INPUT_SLOTS = 3;

    private final String group;
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final int spinTime;
    private final int color;

    public CottonCandyMachineRecipe(String group, NonNullList<Ingredient> inputItems, ItemStack output, int spinTime, int color) {
        this.group = group;
        this.inputItems = inputItems;
        this.output = output;
        this.spinTime = spinTime;
        this.color = color;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.output;
    }

    public int getSpinTime() {
        return this.spinTime;
    }

    public int getColor() {
        return this.color;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for (int j = 0; j < INPUT_SLOTS; ++j) {
            ItemStack itemstack = inv.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                inputs.add(itemstack);
            }
        }
        return i == this.inputItems.size() && RecipeMatcher.findMatches(inputs, this.inputItems) != null;
    }

    @Override
    public ItemStack assemble(RecipeWrapper recipeWrapper, HolderLookup.Provider provider) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DFRecipeSerializers.SPINNING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return DFRecipeTypes.SPINNING.get();
    }

    public static class Serializer implements RecipeSerializer<CottonCandyMachineRecipe> {
        private static final MapCodec<CottonCandyMachineRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(CottonCandyMachineRecipe::getGroup),
                Ingredient.LIST_CODEC_NONEMPTY.fieldOf("ingredients").xmap(ingredients -> {
                    NonNullList<Ingredient> nonNullList = NonNullList.create();
                    nonNullList.addAll(ingredients);
                    return nonNullList;
                }, ingredients -> ingredients).forGetter(CottonCandyMachineRecipe::getIngredients),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.output),
                Codec.INT.optionalFieldOf("spinningtime", 200).forGetter(CottonCandyMachineRecipe::getSpinTime),
                Codec.INT.optionalFieldOf("color", 0xFFFFFF).forGetter(CottonCandyMachineRecipe::getColor)
        ).apply(inst, CottonCandyMachineRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CottonCandyMachineRecipe> STREAM_CODEC = StreamCodec.of(CottonCandyMachineRecipe.Serializer::toNetwork, CottonCandyMachineRecipe.Serializer::fromNetwork);

        public Serializer() {
        }

        private static CottonCandyMachineRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            int i = buffer.readVarInt();
            NonNullList<Ingredient> inputItems = NonNullList.withSize(i, Ingredient.EMPTY);

            inputItems.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));

            ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);
            int spinTime = buffer.readVarInt();
            int color = buffer.readInt();
            return new CottonCandyMachineRecipe(group, inputItems, output, spinTime, color);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, CottonCandyMachineRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeVarInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.inputItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
            buffer.writeVarInt(recipe.spinTime);
            buffer.writeInt(recipe.color);
        }

        @Override
        public MapCodec<CottonCandyMachineRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CottonCandyMachineRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
