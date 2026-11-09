package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.crafting.CottonCandyMachineRecipe;
import com.cloudmeow.delightoflight.crafting.RawMoonCakeFillingRecipe;
import com.cloudmeow.delightoflight.crafting.SpaceConversionRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DFRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, DelightoFlight.MOD_ID);

    public static final RegistryObject<RecipeType<CottonCandyMachineRecipe>> SPINNING = RECIPE_TYPES.register("spinning", () -> registerRecipeType("cooking"));
    public static final RegistryObject<RecipeType<RawMoonCakeFillingRecipe>> RAW_MOON_CAKE_FILLING = RECIPE_TYPES.register("raw_moon_cake_filling", () -> registerRecipeType("raw_moon_cake_filling"));
    public static final RegistryObject<RecipeType<SpaceConversionRecipe>> SPACE_CONVERSION = RECIPE_TYPES.register("space_conversion", () -> registerRecipeType("space_conversion"));

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<>()
        {
            public String toString() {
                return DelightoFlight.MOD_ID + ":" + identifier;
            }
        };
    }
}
