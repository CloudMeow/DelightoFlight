package com.cloudmeow.delightoflight.compat.jei;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.crafting.CottonCandyMachineRecipe;
import mezz.jei.api.recipe.RecipeType;

public final class JEIDFRecipeTypes {
    public static final RecipeType<CottonCandyMachineRecipe> SPINNING = RecipeType.create(DelightoFlight.MOD_ID, "spinning", CottonCandyMachineRecipe.class);
}
