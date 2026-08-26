package com.cloudmeow.delightoflight.client.render;

import com.cloudmeow.delightoflight.block.entity.CottonCandyMachineBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;

public class CottonCandyMachineHudLayer {
    public static void render(GuiGraphics guiGraphics, float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) {
            return;
        }
        Player player = mc.player;
        if (!(player.pick(5.0D, partialTick, false) instanceof BlockHitResult hit)) {
            return;
        }
        if (!(mc.level.getBlockEntity(hit.getBlockPos()) instanceof CottonCandyMachineBlockEntity machine)) {
            return;
        }
        if (!machine.isSpinning() || machine.getSpinningPlayerId() == null) {
            return;
        }
        if (!machine.getSpinningPlayerId().equals(player.getUUID()) || machine.getCurrentOutput().isEmpty()) {
            return;
        }

        int total = machine.getSpinTimeTotal();
        if (total <= 0) {
            return;
        }
        float progress = Math.min(1.0F, (float) machine.getSpinTime() / (float) total);

        int x = guiGraphics.guiWidth() / 2 - 8;
        int y = guiGraphics.guiHeight() / 2 - 28;

        guiGraphics.renderItem(new ItemStack(Items.STICK), x, y);

        int cw = (int) (16.0F * progress);
        int ch = (int) (16.0F * progress);
        if (cw > 0 && ch > 0) {
            int minX = x;
            int minY = y + 16 - ch;
            guiGraphics.enableScissor(minX, minY, minX + cw, minY + ch);
            guiGraphics.renderItem(machine.getCurrentOutput(), x, y);
            guiGraphics.disableScissor();
        }
    }
}
