package com.cloudmeow.delightoflight.mixin;

import com.cloudmeow.delightoflight.registry.DFBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LayeredCauldronBlock.class)
public abstract class LayeredCauldronBlockMixin extends AbstractCauldronBlock {
    public LayeredCauldronBlockMixin(Properties properties, CauldronInteraction.InteractionMap interactions) {
        super(properties, interactions);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
        BlockPos blockpos = PointedDripstoneBlock.findStalactiteTipAboveCauldron(serverLevel, blockPos);
        if (blockpos == null) {
            boolean canSpawnCloud = blockPos.getY() < 319 && blockPos.getY() > 192 && serverLevel.getBlockState(blockPos.above()).isAir() && serverLevel.canSeeSky(blockPos);
            if (canSpawnCloud) {
                serverLevel.setBlockAndUpdate(blockPos.above(), DFBlocks.CLOUD.get().defaultBlockState());
                LayeredCauldronBlock.lowerFillLevel(blockState, serverLevel, blockPos);
            }
        }
    }
}
