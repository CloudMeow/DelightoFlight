package com.cloudmeow.delightoflight.crafting;

import com.cloudmeow.delightoflight.registry.DFRecipeSerializers;
import com.cloudmeow.delightoflight.registry.DFRecipeTypes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

public class CottonCandyMachineRecipe implements Recipe<RecipeWrapper> {
    public static final int INPUT_SLOTS = 3;

    private final ResourceLocation id;
    private final String group;
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final int spinTime;
    private final int color;

    public CottonCandyMachineRecipe(ResourceLocation id, String group, NonNullList<Ingredient> inputItems, ItemStack output, int spinningTime, int color) {
        this.id = id;
        this.group = group;
        this.inputItems = inputItems;
        this.output = output;
        this.spinTime = spinningTime;
        this.color = color;
    }

    @NotNull
    @Override
    public String getGroup() {
        return this.group;
    }

    @NotNull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < inv.getContainerSize(); i++) {
            remaining.set(i, inv.getItem(i).copy());
        }
        for (Ingredient ingredient : inputItems) {
            if (ingredient.isEmpty()) continue;
            boolean found = false;
            for (int i = 0; i < remaining.size(); i++) {
                ItemStack stack = remaining.get(i);
                if (!stack.isEmpty() && ingredient.test(stack)) {
                    remaining.set(i, ItemStack.EMPTY);
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv, RegistryAccess access) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= inputItems.size();
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DFRecipeSerializers.SPINNING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return DFRecipeTypes.SPINNING.get();
    }

    public int getSpinTime() {
        return spinTime;
    }

    public int getColor() {
        return this.color;
    }

    public static class Serializer implements RecipeSerializer<CottonCandyMachineRecipe> {
        @Override
        public CottonCandyMachineRecipe fromJson(ResourceLocation id, JsonObject json) {
            final String group = GsonHelper.getAsString(json, "group", "");
            NonNullList<Ingredient> ingredients = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new com.google.gson.JsonSyntaxException("A cotton candy recipe must have at least one ingredient");
            } else if (ingredients.size() > CottonCandyMachineRecipe.INPUT_SLOTS) {
                throw new JsonParseException("Too many ingredients for cotton candy recipe! The max is " + CottonCandyMachineRecipe.INPUT_SLOTS);
            } else {
                ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
                int spinTime = GsonHelper.getAsInt(json, "spinningtime", 200);
                int color = GsonHelper.getAsInt(json, "color", 0xFFFFFF);
                return new CottonCandyMachineRecipe(id, group, ingredients, output, spinTime, color);
            }
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray array) {
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (int i = 0; i < array.size() && i < INPUT_SLOTS; i++) {
                Ingredient ingredient = Ingredient.fromJson(array.get(i));
                if (!ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            }
            return ingredients;
        }

        @Override
        public CottonCandyMachineRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            int size = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
            for (int i = 0; i < size; i++) {
                ingredients.set(i, Ingredient.fromNetwork(buffer));
            }
            ItemStack output = buffer.readItem();
            int spinningTime = buffer.readVarInt();
            int color = buffer.readInt();
            return new CottonCandyMachineRecipe(id, group, ingredients, output, spinningTime, color);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CottonCandyMachineRecipe recipe) {
            buffer.writeVarInt(recipe.inputItems.size());
            for (Ingredient ingredient : recipe.inputItems) {
                ingredient.toNetwork(buffer);
            }
            buffer.writeItem(recipe.output);
            buffer.writeVarInt(recipe.spinTime);
            buffer.writeInt(recipe.color);
        }
    }
}
