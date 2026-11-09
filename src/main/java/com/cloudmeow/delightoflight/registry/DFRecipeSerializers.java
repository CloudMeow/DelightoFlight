package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.crafting.CottonCandyMachineRecipe;
import com.cloudmeow.delightoflight.crafting.RawMoonCakeFillingRecipe;
import com.cloudmeow.delightoflight.crafting.SpaceConversionRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DFRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, DelightoFlight.MOD_ID);

    public static final Supplier<RecipeSerializer<?>> SPINNING = RECIPE_SERIALIZERS.register("spinning", CottonCandyMachineRecipe.Serializer::new);
    public static final Supplier<RecipeSerializer<?>> RAW_MOON_CAKE_FILLING = RECIPE_SERIALIZERS.register("raw_moon_cake_filling", RawMoonCakeFillingRecipe.Serializer::new);
    public static final Supplier<RecipeSerializer<?>> SPACE_CONVERSION = RECIPE_SERIALIZERS.register("space_conversion", SpaceConversionRecipe.Serializer::new);
}
