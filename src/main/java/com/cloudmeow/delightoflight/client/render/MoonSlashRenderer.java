package com.cloudmeow.delightoflight.client.render;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.entity.MoonSlashEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class MoonSlashRenderer extends EntityRenderer<MoonSlashEntity> {
    public MoonSlashRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(MoonSlashEntity entity) {
        int frame = Math.max(1, Math.min(entity.tickCount + 1, 12));
        if (entity.isFullMoon()) {
            return new ResourceLocation(DelightoFlight.MOD_ID, "textures/entity/moon_slash_full" + frame + ".png");
        } else {
            return new ResourceLocation(DelightoFlight.MOD_ID, "textures/entity/moon_slash_half" + frame + ".png");
        }
    }

    @Override
    public void render(MoonSlashEntity entity, float yaw, float partialTick, PoseStack pose, MultiBufferSource buffer, int light) {
        int shape = entity.getShape();

        pose.pushPose();
        pose.mulPose(Axis.XP.rotationDegrees(90));
        pose.mulPose(Axis.ZP.rotationDegrees(entity.getYRot()));

        float w = shape == 0 ? 3.0F : 2.0F;
        float h = shape == 0 ? 3.0F : 2.0F;

        VertexConsumer vc = buffer.getBuffer(RenderType.entityCutout(getTextureLocation(entity)));
        Matrix4f mat = pose.last().pose();

        vc.vertex(mat, entity.isRight() ? -w : w, -h, 0).color(1.0F, 1.0F, 1.0F, shape == 0 ? 0.5F : 1.0F).uv(0.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(0F, 1F, 0F).endVertex();
        vc.vertex(mat, entity.isRight() ? w : -w, -h, 0).color(1.0F, 1.0F, 1.0F, shape == 0 ? 1.0F : 0.0F).uv(1.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(0F, 1F, 0F).endVertex();
        vc.vertex(mat, entity.isRight() ? w : -w, h, 0).color(1.0F, 1.0F, 1.0F, shape == 0 ? 1.0F : 0.0F).uv(1.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(0F, 1F, 0F).endVertex();
        vc.vertex(mat, entity.isRight() ? -w : w, h, 0).color(1.0F, 1.0F, 1.0F, shape == 0 ? 0.5F : 1.0F).uv(0.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(0F, 1F, 0F).endVertex();

        pose.popPose();
    }
}
