package com.cloudmeow.delightoflight.client.render;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.block.CloudSilkBedBlock;
import com.cloudmeow.delightoflight.block.entity.CloudSilkBedBlockEntity;
import com.cloudmeow.delightoflight.registry.DFBlockEntities;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CloudSilkBedBlockEntityRenderer implements BlockEntityRenderer<CloudSilkBedBlockEntity> {
    private final ModelPart headRoot;
    private final ModelPart footRoot;
    public static final Material CLOUD_SILK_BED_MATERIAL = new Material(Sheets.BED_SHEET, ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "entity/bed/cloud_silk_bed"));

    public CloudSilkBedBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.headRoot = context.bakeLayer(ModelLayers.BED_HEAD);
        this.footRoot = context.bakeLayer(ModelLayers.BED_FOOT);
    }

    @Override
    public void render(CloudSilkBedBlockEntity bed, float p_112206_, PoseStack p_112207_, MultiBufferSource p_112208_, int p_112209_, int p_112210_) {
        Material material = CLOUD_SILK_BED_MATERIAL;
        Level level = bed.getLevel();
        if (level != null) {
            BlockState blockstate = bed.getBlockState();
            DoubleBlockCombiner.NeighborCombineResult<? extends CloudSilkBedBlockEntity> neighborcombineresult = DoubleBlockCombiner.combineWithNeigbour(DFBlockEntities.CLOUD_SILK_BED.get(), CloudSilkBedBlock::getBlockType, CloudSilkBedBlock::getConnectedDirection, ChestBlock.FACING, blockstate, level, bed.getBlockPos(), (p_112202_, p_112203_) -> false);
            int i = neighborcombineresult.apply(new BrightnessCombiner<>()).get(p_112209_);
            this.renderPiece(p_112207_, p_112208_, blockstate.getValue(CloudSilkBedBlock.PART) == BedPart.HEAD ? this.headRoot : this.footRoot, blockstate.getValue(CloudSilkBedBlock.FACING), material, i, p_112210_, false);
        } else {
            this.renderPiece(p_112207_, p_112208_, this.headRoot, Direction.SOUTH, material, p_112209_, p_112210_, false);
            this.renderPiece(p_112207_, p_112208_, this.footRoot, Direction.SOUTH, material, p_112209_, p_112210_, true);
        }

    }

    private void renderPiece(PoseStack stack, MultiBufferSource buffer, ModelPart model, Direction dir, Material material, int p_173547_, int p_173548_, boolean p_173549_)
    {
        stack.pushPose();
        stack.translate(0.0D, 0.5625D, p_173549_ ? -1.0D : 0.0D);
        stack.mulPose(Axis.XP.rotationDegrees(90.0F));
        stack.translate(0.5D, 0.5D, 0.5D);
        stack.mulPose(Axis.ZP.rotationDegrees(180.0F + dir.toYRot()));
        stack.translate(-0.5D, -0.5D, -0.5D);
        VertexConsumer vertexconsumer = material.buffer(buffer, RenderType::entitySolid);
        model.render(stack, vertexconsumer, p_173547_, p_173548_);
        stack.popPose();
    }
}
