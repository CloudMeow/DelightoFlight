package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MoonshadeBlock extends FlowerBlock {
    public MoonshadeBlock(MobEffect effect, BlockBehaviour.Properties properties) {
        super(effect, 8, properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.DIRT);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isNight()) return;
        if (random.nextInt(3) != 0) return;
        for (int attempt = 0; attempt < 4; attempt++) {
            BlockPos target = pos.offset(random.nextInt(5) - 2, 0, random.nextInt(5) - 2);
            if (placePetals(level, target)) break;
        }
    }

    private boolean placePetals(ServerLevel level, BlockPos pos) {
        if (level.getBlockState(pos.below()).isAir()) return false;
        BlockState existing = level.getBlockState(pos);
        if (existing.isAir()) {
            level.setBlock(pos, DFBlocks.MOONSHADE_PETALS.get().defaultBlockState(), 2);
            return true;
        } else if (existing.is(DFBlocks.MOONSHADE_PETALS.get())) {
            int amount = existing.getValue(MoonshadePetalsBlock.AMOUNT);
            if (amount < MoonshadePetalsBlock.MAX_FLOWERS) {
                level.setBlock(pos, existing.setValue(MoonshadePetalsBlock.AMOUNT, amount + 1), 2);
                return true;
            }
            return false;
        }
        return false;
    }
}
