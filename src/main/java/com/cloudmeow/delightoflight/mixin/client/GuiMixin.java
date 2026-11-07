package com.cloudmeow.delightoflight.mixin.client;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.registry.DFAttachments;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Unique
    private static final ResourceLocation[] HEARTS = new ResourceLocation[]{
            delightoFlight$heartTexture("stardrop_full"),
            delightoFlight$heartTexture("stardrop_full_blinking"),
            delightoFlight$heartTexture("stardrop_half"),
            delightoFlight$heartTexture("stardrop_half_blinking"),
            delightoFlight$heartTexture("hardcore_stardrop_full"),
            delightoFlight$heartTexture("hardcore_stardrop_full_blinking"),
            delightoFlight$heartTexture("hardcore_stardrop_half"),
            delightoFlight$heartTexture("hardcore_stardrop_half_blinking")
    };

    @Unique
    private Gui.HeartType delightoFlight$lastHeartType;
    @Unique
    private boolean delightoFlight$lastHardcore;
    @Unique
    private boolean delightoFlight$lastHalfHeart;
    @Unique
    private boolean delightoFlight$lastBlinking;
    @Unique
    private int delightoFlight$lastHeartX;
    @Unique
    private int delightoFlight$lastHeartY;

    @Unique
    private static ResourceLocation delightoFlight$heartTexture(String name) {
        return ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "textures/gui/" + name + ".png");
    }

    @Inject(method = "renderHeart", at = @At("HEAD"))
    private void delightoFlight$captureLastHeart(GuiGraphics guiGraphics, Gui.HeartType heartType, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking, CallbackInfo ci) {
        this.delightoFlight$lastHeartType = heartType;
        this.delightoFlight$lastHardcore = hardcore;
        this.delightoFlight$lastHalfHeart = halfHeart;
        this.delightoFlight$lastBlinking = blinking;
        this.delightoFlight$lastHeartX = x;
        this.delightoFlight$lastHeartY = y;
    }

    @Inject(method = "renderHearts", at = @At("TAIL"))
    private void delightoFlight$renderStardropHeart(GuiGraphics guiGraphics, Player player, int x, int y, int height, int offsetHeartIndex, float maxHealth, int currentHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, CallbackInfo ci) {
        if (!Boolean.TRUE.equals(player.getExistingDataOrNull(DFAttachments.STARDROP_EATER))) {
            return;
        }
        if (Boolean.TRUE.equals(player.getExistingDataOrNull(DFAttachments.STARDROP_COOLDOWN))) {
            return;
        }
        if (this.delightoFlight$lastHeartType != Gui.HeartType.NORMAL) {
            return;
        }

        boolean half = this.delightoFlight$lastBlinking;
        boolean blinking = this.delightoFlight$lastHalfHeart;
        int index = (this.delightoFlight$lastHardcore ? 4 : 0) | (half ? 2 : 0) | (blinking ? 1 : 0);

        RenderSystem.enableBlend();
        guiGraphics.blit(HEARTS[index], this.delightoFlight$lastHeartX, this.delightoFlight$lastHeartY, 0, 0.0F, 0.0F, 9, 9, 9, 9);
        RenderSystem.disableBlend();
    }
}
