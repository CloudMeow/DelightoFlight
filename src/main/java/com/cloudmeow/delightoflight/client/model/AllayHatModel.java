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
public class AllayHatModel extends HierarchicalModel<Allay> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart hat;

    public AllayHatModel(ModelPart root) {
        this.root = root.getChild("root");
        this.head = this.root.getChild("head");
        this.hat = this.head.getChild("hat");
    }

    @Override
    public void setupAnim(Allay entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root.getAllParts().forEach(ModelPart::resetPose);
        float f = ageInTicks - (float)entity.tickCount;
        float f1 = ageInTicks * 9.0F * ((float)Math.PI / 180F);
        float f2 = Math.min(limbSwingAmount / 0.3F, 1.0F);
        float f3 = 1.0F - f2;
        if (entity.isDancing()) {
            float f7 = ageInTicks * 8.0F * ((float)Math.PI / 180F) + limbSwingAmount;
            float f8 = Mth.cos(f7) * 16.0F * ((float)Math.PI / 180F);
            float f9 = entity.getSpinningProgress(f);
            float f10 = Mth.cos(f7) * 14.0F * ((float)Math.PI / 180F);
            float f11 = Mth.cos(f7) * 30.0F * ((float)Math.PI / 180F);
            this.root.yRot = entity.isSpinning() ? 12.566371F * f9 : this.root.yRot;
            this.root.zRot = f8 * (1.0F - f9);
            this.head.yRot = f11 * (1.0F - f9);
            this.head.zRot = f10 * (1.0F - f9);
        }
        this.root().y += (float)Math.cos((double)f1) * 0.25F * f3;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root().render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 23.5F, 0.0F));
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 11).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.99F, 0.0F));
        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -11.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }
}
