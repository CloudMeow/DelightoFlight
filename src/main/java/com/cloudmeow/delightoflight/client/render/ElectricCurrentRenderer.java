package com.cloudmeow.delightoflight.client.render;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.entity.ElectricCurrent;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class ElectricCurrentRenderer extends EntityRenderer<ElectricCurrent> {
    private static final ResourceLocation CENTER_TEXTURE = ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "textures/entity/center.png");
    public ElectricCurrentRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ElectricCurrent entity) {
        return CENTER_TEXTURE;
    }

    //code from ISS
    @Override
    public void render(ElectricCurrent entity, float p_114486_, float p_114487_, PoseStack poseStack, MultiBufferSource bufferSource, int p_114490_) {
        List<Vec3> targetVecs = getTargets(entity);
        if (targetVecs.isEmpty()) {
            return;
        }
        if (entity.level().getGameTime() % 3 != 0 || Minecraft.getInstance().isPaused()) {
            return;
        }
        poseStack.pushPose();
        PoseStack.Pose pose = poseStack.last();
        for (Vec3 targetVec : targetVecs) {
            List<Vec3> segments = generateArc(targetVec);
            renderArc(segments, pose, bufferSource, entity);
        }
        poseStack.popPose();
        super.render(entity, p_114486_, p_114487_, poseStack, bufferSource, p_114490_);
    }

    private void renderArc(List<Vec3> segments, PoseStack.Pose pose, MultiBufferSource bufferSource,ElectricCurrent entity) {
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(getTextureLocation(entity)));
        float width = 0.25f;
        float height = width;
        Vec3 start = Vec3.ZERO;
        for (int i = 0; i < segments.size() - 1; i += 2) {
            var from = segments.get(i).add(start);
            var to = segments.get(i + 1).add(start);
            drawHull(from, to, width * 0.3f, height * 0.3f, pose, consumer, 140, 0, 170, 40);
            drawHull(from, to, width * 0.5f, height * 0.5f, pose, consumer, 203, 105, 248, 60);
        }
        consumer = bufferSource.getBuffer(RenderType.energySwirl(getTextureLocation(entity), 0, 0));
        for (int i = 0; i < segments.size() - 1; i += 2) {
            var from = segments.get(i).add(start);
            var to = segments.get(i + 1).add(start);
            drawHull(from, to, width * 0.1f, height * 0.1f, pose, consumer, 255, 255, 255, 255);
        }
    }

    public void drawHull(Vec3 from, Vec3 to, float width, float height, PoseStack.Pose pose, VertexConsumer consumer, int r, int g, int b, int a) {
        drawQuad(from.subtract(0, height * 0.5f, 0), to.subtract(0, height * .5f, 0), width, 0, pose, consumer, r, g, b, a);
        drawQuad(from.add(0, height * 0.5f, 0), to.add(0, height * 0.5f, 0), width, 0, pose, consumer, r, g, b, a);
        drawQuad(from.subtract(width * 0.5f, 0, 0), to.subtract(width * 0.5f, 0, 0), 0, height, pose, consumer, r, g, b, a);
        drawQuad(from.add(width * 0.5f, 0, 0), to.add(width * 0.5f, 0, 0), 0, height, pose, consumer, r, g, b, a);
    }

    public void drawQuad(Vec3 from, Vec3 to, float width, float height, PoseStack.Pose pose, VertexConsumer consumer, int r, int g, int b, int a) {
        Matrix4f poseMatrix = pose.pose();

        float halfWidth = width * 0.5f;
        float halfHeight = height * 0.5f;
        consumer.addVertex(poseMatrix, (float) from.x - halfWidth, (float) from.y - halfHeight, (float) from.z).setColor(r, g, b, a).setUv(0f, 1f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(240).setNormal(pose, 0f, 1f, 0f);
        consumer.addVertex(poseMatrix, (float) from.x + halfWidth, (float) from.y + halfHeight, (float) from.z).setColor(r, g, b, a).setUv(1f, 1f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(240).setNormal(pose, 0f, 1f, 0f);
        consumer.addVertex(poseMatrix, (float) to.x + halfWidth, (float) to.y + halfHeight, (float) to.z).setColor(r, g, b, a).setUv(1f, 0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(240).setNormal(pose, 0f, 1f, 0f);
        consumer.addVertex(poseMatrix, (float) to.x - halfWidth, (float) to.y - halfHeight, (float) to.z).setColor(r, g, b, a).setUv(0f, 0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(240).setNormal(pose, 0f, 1f, 0f);
    }

    public List<Vec3> generateArc(Vec3 targetVectors) {
        Random random = new Random();
        List<Vec3> beamVectors = new ArrayList<>();
        Vec3 coreStart = new Vec3(0, 0, 0);
        int coreLength = random.nextInt(3) + 7;
        Vec3 direction = targetVectors.normalize();
        for (int core = 0; core < coreLength; core++) {
            Vec3 coreEnd = coreStart.add(direction.scale((core + 1.0) / coreLength)).add(randomVector(0.3f).multiply(0.5, 0.5, 0.5));
            beamVectors.add(coreStart);
            beamVectors.add(coreEnd);
            coreStart = coreEnd;
        }
        beamVectors.add(coreStart);
        beamVectors.add(targetVectors);
        return beamVectors;
    }

    public static Vec3 randomVector(float radius) {
        double x = Math.random() * 2 * radius - radius;
        double y = Math.random() * 2 * radius - radius;
        double z = Math.random() * 2 * radius - radius;
        return new Vec3(x, y, z);
    }

    public List<Vec3> getTargets(ElectricCurrent entity) {
        List<Vec3> targetVectors = new ArrayList<>();
        for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(10))) {
            if (living != entity.getOwner() && !living.getUUID().equals(entity.getUUID()) && living.distanceTo(entity) - living.getBbWidth() / 2 < 7) {
                Vec3 relativePosition = living.position().subtract(entity.position());
                targetVectors.add(relativePosition);
            }
        }
        return targetVectors;
    }
}
