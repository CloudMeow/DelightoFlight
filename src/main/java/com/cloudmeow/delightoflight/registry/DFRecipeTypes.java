package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.crafting.CottonCandyMachineRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DFRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, DelightoFlight.MOD_ID);

    public static final RegistryObject<RecipeType<CottonCandyMachineRecipe>> SPINNING = RECIPE_TYPES.register("spinning", () -> registerRecipeType("cooking"));

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<>()
        {
            public String toString() {
                return DelightoFlight.MOD_ID + ":" + identifier;
            }
        };
    }
}
