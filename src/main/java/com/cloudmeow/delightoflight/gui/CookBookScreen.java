package com.cloudmeow.delightoflight.gui;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CookBookScreen extends AbstractContainerScreen<CookBookMenu> {
    public CookBookScreen(CookBookMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(new ResourceLocation(DelightoFlight.MOD_ID, "textures/gui/cook_book.png"), this.leftPos, this.topPos, 0,0, this.imageWidth, this.imageHeight);
    }
}