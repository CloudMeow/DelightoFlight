package com.cloudmeow.delightoflight.client.model;


import com.cloudmeow.delightoflight.client.animation.AerolopeAnimation;
import com.cloudmeow.delightoflight.entity.AerolopeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AerolopeModel<T extends AerolopeEntity> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart lefthorn;
	private final ModelPart righthorn;
	private final ModelPart rightear;
	private final ModelPart leftear;
	private final ModelPart leftlegback;
	private final ModelPart rightlegback;
	private final ModelPart rightlegfront;
	private final ModelPart leftlegfront;

	public AerolopeModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.tail = this.body.getChild("tail");
		this.neck = this.root.getChild("neck");
		this.head = this.neck.getChild("head");
		this.lefthorn = this.head.getChild("lefthorn");
		this.righthorn = this.head.getChild("righthorn");
		this.rightear = this.head.getChild("rightear");
		this.leftear = this.head.getChild("leftear");
		this.leftlegback = this.root.getChild("leftlegback");
		this.rightlegback = this.root.getChild("rightlegback");
		this.rightlegfront = this.root.getChild("rightlegfront");
		this.leftlegfront = this.root.getChild("leftlegfront");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 20).addBox(-3.5F, -9.1667F, 1.6667F, 7.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-4.5F, -10.1667F, -8.3333F, 9.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.8333F, -1.6667F));
		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(20, 47).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.1667F, 11.6667F, 0.2138F, 0.0F, 0.0F));
		PartDefinition neck = root.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(34, 35).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -20.0F, -9.0F, 0.3927F, 0.0F, 0.0F));
		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(34, 20).addBox(-3.0F, -7.0F, -4.0F, 6.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(12, 39).addBox(-1.5F, -4.0F, -8.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -2.0F, -0.3927F, 0.0F, 0.0F));
		PartDefinition lefthorn = head.addOrReplaceChild("lefthorn", CubeListBuilder.create().texOffs(26, 39).addBox(-1.0F, -6.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(12, 47).addBox(-1.0F, -10.0F, 2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -7.0F, 1.0F, -0.7854F, 0.0F, 0.0F));
		PartDefinition righthorn = head.addOrReplaceChild("righthorn", CubeListBuilder.create().texOffs(26, 39).mirror().addBox(-1.0F, -6.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(12, 47).mirror().addBox(-1.0F, -10.0F, 2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -7.0F, 1.0F, -0.7854F, 0.0F, 0.0F));
		PartDefinition rightear = head.addOrReplaceChild("rightear", CubeListBuilder.create().texOffs(38, 17).addBox(-2.0F, 2.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(38, 13).addBox(-4.0F, -1.0F, 0.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -6.0F, 3.0F));
		PartDefinition leftear = head.addOrReplaceChild("leftear", CubeListBuilder.create().texOffs(38, 17).mirror().addBox(0.0F, 2.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(38, 13).mirror().addBox(0.0F, -1.0F, 0.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, -6.0F, 3.0F));
		PartDefinition leftlegback = root.addOrReplaceChild("leftlegback", CubeListBuilder.create().texOffs(38, 0).addBox(-2.0F, 0.0F, -1.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -10.0F, 8.0F));
		PartDefinition rightlegback = root.addOrReplaceChild("rightlegback", CubeListBuilder.create().texOffs(38, 0).mirror().addBox(-1.0F, 0.0F, -1.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, -10.0F, 8.0F));
		PartDefinition rightlegfront = root.addOrReplaceChild("rightlegfront", CubeListBuilder.create().texOffs(0, 39).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -10.0F, -7.5F));
		PartDefinition leftlegfront = root.addOrReplaceChild("leftlegfront", CubeListBuilder.create().texOffs(0, 39).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.5F, -10.0F, -7.5F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.translate(0D, 1.5D, 0D);
		this.root().render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T t, float v, float v1, float v2, float v3, float v4) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.getChild("lefthorn").visible = t.hasLeftHorn();
		this.head.getChild("righthorn").visible = t.hasRightHorn();
		this.neck.yRot = v3 * (float)Math.PI / 180F * 0.5F;
		this.head.yRot = v3 * (float)Math.PI / 180F * 0.25F;
		this.head.xRot = (v4 - 21F) * (float)Math.PI / 180F;
		this.animateWalk(AerolopeAnimation.WALK, v, v1, 5.0f, 5.5f);
		this.animate(t.shakeHeadAnimationState, AerolopeAnimation.SHAKE, v2);
	}
}