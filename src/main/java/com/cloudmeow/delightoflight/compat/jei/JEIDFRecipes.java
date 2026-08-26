package com.cloudmeow.delightoflight.compat.jei;

import com.cloudmeow.delightoflight.crafting.CottonCandyMachineRecipe;
import com.cloudmeow.delightoflight.registry.DFRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

public class JEIDFRecipes {
    private final RecipeManager recipeManager;

    public JEIDFRecipes() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;

        if (level != null) {
            this.recipeManager = level.getRecipeManager();
        } else {
            throw new NullPointerException("Minecraft level must not be null.");
        }
    }

    public List<RecipeHolder<CottonCandyMachineRecipe>> getSpanningRecipes() {
        return recipeManager.getAllRecipesFor(DFRecipeTypes.SPINNING.get());
    }
}
