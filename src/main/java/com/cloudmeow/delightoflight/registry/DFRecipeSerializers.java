package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.crafting.CottonCandyMachineRecipe;
import com.cloudmeow.delightoflight.crafting.RawMoonCakeFillingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DFRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, DelightoFlight.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> SPINNING = RECIPE_SERIALIZERS.register("spinning", CottonCandyMachineRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> RAW_MOON_CAKE_FILLING = RECIPE_SERIALIZERS.register("raw_moon_cake_filling", RawMoonCakeFillingRecipe.Serializer::new);
}
