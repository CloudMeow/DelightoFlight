package com.cloudmeow.delightoflight.client.render;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.client.model.CookBookModel;
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

@OnlyIn(Dist.CLIENT)
public class CookBookLayer extends RenderLayer<Allay, AllayModel> {
    public static final ResourceLocation BOOK_TEXTURE = new ResourceLocation(DelightoFlight.MOD_ID, "textures/entity/cook_book.png");
    private final CookBookModel bookModel;

    public CookBookLayer(RenderLayerParent<Allay, AllayModel> renderer, EntityModelSet modelSets) {
        super(renderer);
        this.bookModel = new CookBookModel(modelSets.bakeLayer(ClientEvent.COOK_BOOK));
    }

    public void getProperties(CookBookModel model) {
        model.body.copyFrom(this.getParentModel().root().getChild("body"));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Allay livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!DFUtilities.checkCookBookExist(livingEntity)) return;
        this.getProperties(bookModel);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(BOOK_TEXTURE));
        poseStack.pushPose();
        this.bookModel.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.bookModel.renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}