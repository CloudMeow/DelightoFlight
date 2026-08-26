package com.cloudmeow.delightoflight.client.render;

import com.cloudmeow.delightoflight.block.CottonCandyMachineBlock;
import com.cloudmeow.delightoflight.block.entity.CottonCandyMachineBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class CottonCandyMachineRenderer implements BlockEntityRenderer<CottonCandyMachineBlockEntity> {
    public CottonCandyMachineRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(CottonCandyMachineBlockEntity machine, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ItemStack firstItemStack = machine.getInventory().getStackInSlot(0);
        ItemStack secondItemStack = machine.getInventory().getStackInSlot(1);
        ItemStack thirdItemStack = machine.getInventory().getStackInSlot(2);

        BlockState state = machine.getBlockState();
        Direction facing = Direction.NORTH;
        if (state.getBlock() instanceof CottonCandyMachineBlock) {
            facing = state.getValue(CottonCandyMachineBlock.FACING);
        }

        if (!firstItemStack.isEmpty()) {
            renderItem(poseStack, buffer, firstItemStack, facing, 0, packedLight, packedOverlay);
        }
        if (!secondItemStack.isEmpty()) {
            renderItem(poseStack, buffer, secondItemStack, facing, 1, packedLight, packedOverlay);
        }
        if (!thirdItemStack.isEmpty()) {
            renderItem(poseStack, buffer, thirdItemStack, facing, 2, packedLight, packedOverlay);
        }
    }

    private void renderItem(PoseStack poseStack, MultiBufferSource buffer, ItemStack stack, Direction facing, int slotIndex, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        float basinY = 9.3f / 16f;
        float[] xOffsets = {0f, 0.28f, -0.28f};
        float xOffset = xOffsets[slotIndex];
        float[] zOffsets = {0.3f, -0.3f, -0.3f};
        float zOffset = zOffsets[slotIndex];

        float rotY = -facing.toYRot();

        poseStack.translate(0.5, basinY, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotY));
        poseStack.translate(xOffset, 0, zOffset);

        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.scale(0.3f, 0.3f, 0.3f);

        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, buffer, null, 0);
        poseStack.popPose();
    }
}
