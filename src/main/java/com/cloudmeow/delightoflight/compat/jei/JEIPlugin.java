package com.cloudmeow.delightoflight.compat.jei;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.registry.DFItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(DFItems.THUNDER_FRUIT_SEED.get()), VanillaTypes.ITEM_STACK, Component.translatable(DelightoFlight.MOD_ID + ".jei.info.thunder_fruit_seed"));
        registration.addIngredientInfo(new ItemStack(DFItems.COPPER_THUNDER_FRUIT.get()), VanillaTypes.ITEM_STACK, Component.translatable(DelightoFlight.MOD_ID + ".jei.info.copper_thunder_fruit"));
        registration.addIngredientInfo(new ItemStack(DFItems.MAGIC_CHEF_HAT.get()), VanillaTypes.ITEM_STACK, Component.translatable(DelightoFlight.MOD_ID + ".jei.info.magic_chef_hat"));
        registration.addIngredientInfo(new ItemStack(DFItems.COOK_BOOK.get()), VanillaTypes.ITEM_STACK, Component.translatable(DelightoFlight.MOD_ID + ".jei.info.cook_book"));
        registration.addIngredientInfo(new ItemStack(DFItems.CLOUD_BERRY.get()), VanillaTypes.ITEM_STACK, Component.translatable(DelightoFlight.MOD_ID + ".jei.info.cloud_berries"));
    }
}
