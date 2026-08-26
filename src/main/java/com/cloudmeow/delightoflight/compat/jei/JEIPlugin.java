package com.cloudmeow.delightoflight.compat.jei;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.compat.jei.category.SpinningRecipeCategory;
import com.cloudmeow.delightoflight.registry.DFItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new SpinningRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(DFItems.COTTON_CANDY_MACHINE.get()), JEIDFRecipeTypes.SPINNING);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        JEIDFRecipes modRecipes = new JEIDFRecipes();
        registration.addRecipes(JEIDFRecipeTypes.SPINNING, modRecipes.getSpanningRecipes());

        registration.addIngredientInfo(new ItemStack(DFItems.THUNDER_FRUIT_SEED.get()), VanillaTypes.ITEM_STACK, Component.translatable(DelightoFlight.MOD_ID + ".jei.info.thunder_fruit_seed"));
        registration.addIngredientInfo(new ItemStack(DFItems.COPPER_THUNDER_FRUIT.get()), VanillaTypes.ITEM_STACK, Component.translatable(DelightoFlight.MOD_ID + ".jei.info.copper_thunder_fruit"));
        registration.addIngredientInfo(new ItemStack(DFItems.MAGIC_CHEF_HAT.get()), VanillaTypes.ITEM_STACK, Component.translatable(DelightoFlight.MOD_ID + ".jei.info.magic_chef_hat"));
        registration.addIngredientInfo(new ItemStack(DFItems.COOK_BOOK.get()), VanillaTypes.ITEM_STACK, Component.translatable(DelightoFlight.MOD_ID + ".jei.info.cook_book"));
        registration.addIngredientInfo(new ItemStack(DFItems.CLOUD_BERRY.get()), VanillaTypes.ITEM_STACK, Component.translatable(DelightoFlight.MOD_ID + ".jei.info.cloud_berries"));
        registration.addIngredientInfo(new ItemStack(DFItems.LOTUS_ROOT.get()), VanillaTypes.ITEM_STACK, Component.translatable(DelightoFlight.MOD_ID + ".jei.info.lotus_root"));
        registration.addIngredientInfo(List.of(new ItemStack(DFItems.LOTUS_SEEDS.get()), new ItemStack(DFItems.WILD_LOTUS.get()), new ItemStack(DFItems.LOTUS_FLOWER.get())), VanillaTypes.ITEM_STACK, Component.translatable(DelightoFlight.MOD_ID + ".jei.info.wild_lotus"));
    }


}
