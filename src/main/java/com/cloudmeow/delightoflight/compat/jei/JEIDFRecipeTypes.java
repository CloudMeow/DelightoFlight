package com.cloudmeow.delightoflight.compat.jei;

import com.cloudmeow.delightoflight.crafting.CottonCandyMachineRecipe;
import com.cloudmeow.delightoflight.registry.DFRecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.crafting.RecipeHolder;

public final class JEIDFRecipeTypes {
    public static final RecipeType<RecipeHolder<CottonCandyMachineRecipe>> SPINNING = RecipeType.createFromVanilla(DFRecipeTypes.SPINNING.get());
}
