package com.cloudmeow.delightoflight.client.render;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.client.model.AllayHatModel;
import com.cloudmeow.delightoflight.event.ClientEvent;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AllayModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class AllayHatLayer extends RenderLayer<Allay, AllayModel> {
    public static final ResourceLocation HAT_TEXTURE = new ResourceLocation(DelightoFlight.MOD_ID, "textures/entity/magic_chef_hat.png");
    private final AllayHatModel hatModel;

    public AllayHatLayer(RenderLayerParent<Allay, AllayModel> renderer, EntityModelSet modelSets) {
        super(renderer);
        this.hatModel = new AllayHatModel(modelSets.bakeLayer(ClientEvent.ALLAY_HAT));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Allay entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!DFUtilities.checkChefHatExist(entity)) {
            return;
        }
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(HAT_TEXTURE));
        poseStack.pushPose();
        if(!entity.isDancing()) {
            float headPosY = 1.223F;
            Quaternionf yRotation = new Quaternionf().rotationY(netHeadYaw * ((float) Math.PI / 180F));
            Quaternionf xRotation = new Quaternionf().rotationX(headPitch * ((float) Math.PI / 180F));
            poseStack.rotateAround(yRotation, 0, headPosY, 0);
            poseStack.rotateAround(xRotation, 0, headPosY, 0);
        }
        this.hatModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.hatModel.renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
