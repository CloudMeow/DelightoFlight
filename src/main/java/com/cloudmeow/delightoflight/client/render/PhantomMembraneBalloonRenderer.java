package com.cloudmeow.delightoflight.client.render;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.client.model.PhantomMembraneBalloonModel;
import com.cloudmeow.delightoflight.entity.PhantomMembraneBalloonEntity;
import com.cloudmeow.delightoflight.registry.DFModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import vectorwing.farmersdelight.common.registry.ModBlocks;

@OnlyIn(Dist.CLIENT)
public class PhantomMembraneBalloonRenderer extends EntityRenderer<PhantomMembraneBalloonEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(DelightoFlight.MOD_ID, "textures/entity/phantom_membrane_balloon.png");
    private static final ResourceLocation ROPE_TEXTURE = new ResourceLocation(DelightoFlight.MOD_ID, "textures/entity/rope.png");
    private static final float BALLOON_BOTTOM_Y = 1.6875F;
    private static final float ROPE_WIDTH = 0.03F;

    private final PhantomMembraneBalloonModel<PhantomMembraneBalloonEntity> model;
    private final BlockRenderDispatcher blockRenderer;

    public PhantomMembraneBalloonRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PhantomMembraneBalloonModel<>(context.bakeLayer(DFModelLayers.PHANTOM_MEMBRANE_BALLOON));
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(PhantomMembraneBalloonEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(PhantomMembraneBalloonEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        BlockState basketState = entity.isBambooBasket()
                ? ModBlocks.BAMBOO_BASKET.get().defaultBlockState()
                : ModBlocks.WOODEN_BASKET.get().defaultBlockState();

        float riseAge = entity.getRiseAge(partialTick);
        float fadeIn = Mth.clamp(riseAge / 20.0F, 0.0F, 1.0F);
        float phase = riseAge * 0.15F;
        float balloonSway = Mth.sin(phase) * 10.0F * fadeIn;
        float basketSway = Mth.sin(phase - 0.45F) * 9.0F * fadeIn;

        poseStack.pushPose();
        poseStack.translate(0.0F, BALLOON_BOTTOM_Y, 0.0F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(basketSway));
        poseStack.translate(0.0F, -BALLOON_BOTTOM_Y, 0.0F);
        poseStack.translate(-0.5F, 0.0F, -0.5F);
        this.blockRenderer.renderSingleBlock(basketState, poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.0F, 1.0F, 0.0F);
        poseStack.translate(0.0F, 1.0F, 0.0F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        poseStack.translate(0.0F, -1.0F, 0.0F);

        if (entity.isRising()) {
            poseStack.translate(0.0F, 2.0F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(balloonSway));
            poseStack.translate(0.0F, -2.0F, 0.0F);
        }

        this.model.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTick, 0.0F, 0.0F);
        VertexConsumer buffer = bufferSource.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();

        VoxelShape basketShape = basketState.getShape(entity.level(), entity.blockPosition());
        AABB basketBounds = basketShape.isEmpty()
                ? new AABB(0.1D, 0.0D, 0.1D, 0.9D, 0.75D, 0.9D)
                : basketShape.bounds();
        float top = (float) basketBounds.maxY;
        float inset = 1.0F / 16.0F;
        float minX = (float) basketBounds.minX - 0.5F + inset;
        float maxX = (float) basketBounds.maxX - 0.5F - inset;
        float minZ = (float) basketBounds.minZ - 0.5F + inset;
        float maxZ = (float) basketBounds.maxZ - 0.5F - inset;

        VertexConsumer ropeBuffer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(ROPE_TEXTURE));
        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        Vector3f cameraLocal = new Vector3f(
                (float) (cameraPos.x - entity.getX()),
                (float) (cameraPos.y - entity.getY()),
                (float) (cameraPos.z - entity.getZ())
        );
        Vector3f ropeStart = rotateAroundZ(0.0F, BALLOON_BOTTOM_Y, 0.0F, 2.0F, balloonSway);
        renderRope(ropeBuffer, poseStack, ropeStart, rotateAroundZ(minX, top, minZ, BALLOON_BOTTOM_Y, basketSway), packedLight, cameraLocal);
        renderRope(ropeBuffer, poseStack, ropeStart, rotateAroundZ(maxX, top, minZ, BALLOON_BOTTOM_Y, basketSway), packedLight, cameraLocal);
        renderRope(ropeBuffer, poseStack, ropeStart, rotateAroundZ(minX, top, maxZ, BALLOON_BOTTOM_Y, basketSway), packedLight, cameraLocal);
        renderRope(ropeBuffer, poseStack, ropeStart, rotateAroundZ(maxX, top, maxZ, BALLOON_BOTTOM_Y, basketSway), packedLight, cameraLocal);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private static Vector3f rotateAroundZ(float x, float y, float z, float pivotY, float angleDegrees) {
        float radians = angleDegrees * Mth.DEG_TO_RAD;
        float cos = Mth.cos(radians);
        float sin = Mth.sin(radians);
        float dy = y - pivotY;
        return new Vector3f(x * cos - dy * sin, pivotY + x * sin + dy * cos, z);
    }

    private static void renderRope(VertexConsumer buffer, PoseStack poseStack, Vector3f start, Vector3f end, int packedLight, Vector3f cameraLocal) {
        Matrix4f matrix = poseStack.last().pose();
        Vector3f direction = new Vector3f(end).sub(start).normalize();
        Vector3f reference = Math.abs(direction.y) > 0.99F ? new Vector3f(1.0F, 0.0F, 0.0F) : new Vector3f(0.0F, 1.0F, 0.0F);

        Vector3f side = new Vector3f(cameraLocal).sub(start).cross(direction);
        if (side.lengthSquared() < 1.0E-6F) {
            side = new Vector3f(reference).cross(direction);
        }
        side.normalize().mul(ROPE_WIDTH);

        renderRopeQuad(buffer, matrix, start, end, side, packedLight);
    }

    private static void renderRopeQuad(VertexConsumer buffer, Matrix4f matrix, Vector3f start, Vector3f end, Vector3f side, int packedLight) {
        Vector3f normal = new Vector3f(end).sub(start).cross(side).normalize();

        addRopeVertex(buffer, matrix, start.x - side.x, start.y - side.y, start.z - side.z, 0.0F, 0.0F, normal, packedLight);
        addRopeVertex(buffer, matrix, start.x + side.x, start.y + side.y, start.z + side.z, 1.0F, 0.0F, normal, packedLight);
        addRopeVertex(buffer, matrix, end.x + side.x, end.y + side.y, end.z + side.z, 1.0F, 1.0F, normal, packedLight);
        addRopeVertex(buffer, matrix, end.x - side.x, end.y - side.y, end.z - side.z, 0.0F, 1.0F, normal, packedLight);
    }

    private static void addRopeVertex(VertexConsumer buffer, Matrix4f matrix, float x, float y, float z, float u, float v, Vector3f normal, int packedLight) {
        buffer.vertex(matrix, x, y, z)
                .color(1.0F, 1.0F, 1.0F, 1.0F)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normal.x, normal.y, normal.z)
                .endVertex();
    }
}
