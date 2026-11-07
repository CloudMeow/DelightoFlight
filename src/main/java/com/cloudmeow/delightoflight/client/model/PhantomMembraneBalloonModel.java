package com.cloudmeow.delightoflight.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PhantomMembraneBalloonModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart root;

    public PhantomMembraneBalloonModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root",
                CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -13.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        root.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, 0.0F, -2.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
        root.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, 0.0F, -2.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
        root.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(8, 20).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, 2.0F, 0.7854F, 0.0F, 0.0F));
        root.addOrReplaceChild("cube_r4",
                CubeListBuilder.create().texOffs(8, 20).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, -2.0F, -0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}