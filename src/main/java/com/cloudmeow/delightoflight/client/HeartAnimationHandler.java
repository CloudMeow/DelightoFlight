package com.cloudmeow.delightoflight.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class HeartAnimationHandler {
    private HeartAnimationHandler() {
    }

    public static void handle(ItemStack stack) {
        if (stack.isEmpty()) return;
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
        }
    }
}
