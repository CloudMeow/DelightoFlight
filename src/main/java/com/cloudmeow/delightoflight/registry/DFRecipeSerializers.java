package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.crafting.CottonCandyMachineRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DFRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, DelightoFlight.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> SPINNING = RECIPE_SERIALIZERS.register("spinning", CottonCandyMachineRecipe.Serializer::new);
}
