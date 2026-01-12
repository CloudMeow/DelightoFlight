package com.cloudmeow.delightoflight.client.render;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.client.model.AerolopeModel;
import com.cloudmeow.delightoflight.entity.AerolopeEntity;
import com.cloudmeow.delightoflight.registry.DFModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AerolopeRenderer extends MobRenderer<AerolopeEntity, AerolopeModel<AerolopeEntity>> {
    private static final ResourceLocation AEROLOPE_TEXTURE = ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "textures/entity/aerolope.png");

    public AerolopeRenderer(EntityRendererProvider.Context context) {
        super(context, new AerolopeModel<>(context.bakeLayer(DFModelLayers.AEROLOPE)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(AerolopeEntity aerolope) {
        return AEROLOPE_TEXTURE;
    }

    @Override
    public void render(AerolopeEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
