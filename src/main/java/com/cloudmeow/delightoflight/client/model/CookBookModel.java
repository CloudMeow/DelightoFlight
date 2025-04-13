package com.cloudmeow.delightoflight.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CookBookModel<T extends Allay> extends HierarchicalModel<T> {
    private final ModelPart root;
    public final ModelPart body;
    private final ModelPart book;

    public CookBookModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.book = this.body.getChild("book");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 23.5F, 0.0F));
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));
        PartDefinition book = body.addOrReplaceChild("book", CubeListBuilder.create().texOffs(0, 27).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 3.0F, -0.25F, 0.5F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//		this.root().getAllParts().forEach(ModelPart::resetPose);
//		animationState.updateTime(ageInTicks, 1.0F);
//		this.animate(animationState, CookBookAnimations.BOOK, ageInTicks);

        if (entity.isDancing()) {
            float f = ageInTicks - (float)entity.tickCount;
            float f7 = ageInTicks * 8.0F * ((float)Math.PI / 180F) + limbSwingAmount;
            float f8 = Mth.cos(f7) * 16.0F * ((float)Math.PI / 180F);
            float f9 = entity.getSpinningProgress(f);
            this.root.yRot = entity.isSpinning() ? 12.566371F * f9 : this.root.yRot;
            this.root.zRot = f8 * (1.0F - f9);
        } else {

        }
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int overlayCoords, float v, float v1, float v2, float v3) {
        root().render(poseStack, vertexConsumer, packedLight, overlayCoords);
    }
}