package com.cloudmeow.delightoflight.mixin;

import com.cloudmeow.delightoflight.registry.DFBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractCauldronBlock.class)
public abstract class AbstractCauldronBlockMixin extends Block {
    public AbstractCauldronBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return blockState.getBlock() instanceof LayeredCauldronBlock;
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource, CallbackInfo ci) {
        BlockPos blockpos = PointedDripstoneBlock.findStalactiteTipAboveCauldron(serverLevel, blockPos);
        if (blockpos == null) {
            boolean canSpawnCloud = blockPos.getY() < 319 && blockPos.getY() > 192 && blockState.getBlock() instanceof LayeredCauldronBlock && serverLevel.getBlockState(blockPos.above()).isAir() && serverLevel.canSeeSky(blockPos);
            if (canSpawnCloud) {
                serverLevel.setBlockAndUpdate(blockPos.above(), DFBlocks.CLOUD.get().defaultBlockState());
                LayeredCauldronBlock.lowerFillLevel(blockState, serverLevel, blockPos);
            }
        }
    }
}
