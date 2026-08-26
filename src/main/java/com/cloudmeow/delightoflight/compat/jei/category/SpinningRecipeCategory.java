package com.cloudmeow.delightoflight.compat.jei.category;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.compat.jei.JEIDFRecipeTypes;
import com.cloudmeow.delightoflight.crafting.CottonCandyMachineRecipe;
import com.cloudmeow.delightoflight.registry.DFBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import vectorwing.farmersdelight.common.utility.ClientRenderUtils;

import java.util.Arrays;

public class SpinningRecipeCategory implements IRecipeCategory<RecipeHolder<CottonCandyMachineRecipe>> {
    private final Component title;
    private final IDrawable timeIcon;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;

    public SpinningRecipeCategory(IGuiHelper helper) {
        title = Component.translatable(DelightoFlight.MOD_ID + ".jei.title.spinning");

        ResourceLocation bgLoc = ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "textures/gui/jei/cotton_candy_machine.png");
        timeIcon = helper.createDrawable(bgLoc, 116, 16, 8, 11);
        background = helper.createDrawable(bgLoc, 0, 0, 116, 56);
        icon = helper.createDrawableItemStack(new ItemStack(DFBlocks.COTTON_CANDY_MACHINE.get()));
        arrow = helper.drawableBuilder(bgLoc, 117, 0, 24, 16).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public RecipeType<RecipeHolder<CottonCandyMachineRecipe>> getRecipeType() {
        return JEIDFRecipeTypes.SPINNING;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public int getWidth() {
        return 116;
    }

    @Override
    public int getHeight() {
        return 56;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CottonCandyMachineRecipe> recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> ingredients = recipe.value().getIngredients();

        builder.addSlot(RecipeIngredientRole.INPUT, 10, 1)
                .addItemStacks(Arrays.asList(ingredients.get(0).getItems()));
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 19)
                .addItemStacks(Arrays.asList(ingredients.get(1).getItems()));
        builder.addSlot(RecipeIngredientRole.INPUT, 19, 19)
                .addItemStacks(Arrays.asList(ingredients.get(2).getItems()));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 77, 10)
                .addItemStack(recipe.value().getResultItem(null));
    }

    @Override
    public void draw(RecipeHolder<CottonCandyMachineRecipe> recipe, IRecipeSlotsView slotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);
        arrow.draw(guiGraphics, 43, 9);
        timeIcon.draw(guiGraphics, 46, 1);
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, RecipeHolder<CottonCandyMachineRecipe> recipe, IRecipeSlotsView slotsView, double mouseX, double mouseY) {
        if (ClientRenderUtils.isCursorInsideBounds(43, 1, 22, 20, mouseX, mouseY)) {
            int spinTime = recipe.value().getSpinTime();
            if (spinTime > 0) {
                int cookTimeSeconds = spinTime / 20;
                tooltip.add(Component.translatable(DelightoFlight.MOD_ID + ".jei.tooltip.time", cookTimeSeconds));
            }
        }
    }
}
