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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
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
            return ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "textures/entity/moon_slash_full" + frame + ".png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "textures/entity/moon_slash_half" + frame + ".png");
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

        VertexConsumer vc = buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity)));
        Matrix4f mat = pose.last().pose();

        vc.addVertex(mat, entity.isRight() ? -w : w, -h, 0).setColor(1.0F, 1.0F, 1.0F, shape == 0 ? 0.5F : 1.0F).setUv(0.0F, 1.0F).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(0F, 1F, 0F).setLight(240);
        vc.addVertex(mat, entity.isRight() ? w : -w, -h, 0).setColor(1.0F, 1.0F, 1.0F, shape == 0 ? 0.5F : 0.0F).setUv(1.0F, 1.0F).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(0F, 1F, 0F).setLight(240);
        vc.addVertex(mat, entity.isRight() ? w : -w, h, 0).setColor(1.0F, 1.0F, 1.0F, shape == 0 ? 0.5F : 0.0F).setUv(1.0F, 0.0F).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(0F, 1F, 0F).setLight(240);
        vc.addVertex(mat, entity.isRight() ? -w : w, h, 0).setColor(1.0F, 1.0F, 1.0F, shape == 0 ? 0.5F : 1.0F).setUv(0.0F, 0.0F).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(0F, 1F, 0F).setLight(240);

        pose.popPose();
    }
}
