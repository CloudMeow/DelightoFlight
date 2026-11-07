package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

public class WaxingCrescentEggSoupBlock extends FeastBlock {
    protected static final VoxelShape SOUP_ALL = Block.box(2.0, 0.0, 2.0, 14.0, 10.0, 14.0);
    protected static final VoxelShape SOUP_MOST = Block.box(2.0, 0.0, 2.0, 14.0, 8.0, 14.0);
    protected static final VoxelShape SOUP_HALF = Block.box(2.0, 0.0, 2.0, 14.0, 6.0, 14.0);
    protected static final VoxelShape SOUP_FEW = Block.box(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);

    public WaxingCrescentEggSoupBlock(Properties properties) {
        super(properties, DFItems.WAXING_CRESCENT_EGG_SOUP, true);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int serving = state.getValue(SERVINGS);
        if (serving > 0) {
            switch (serving) {
                case 2:
                    return SOUP_HALF;
                case 3:
                    return SOUP_MOST;
                case 4:
                    return SOUP_ALL;
            }
        }
        return SOUP_FEW;
    }
}
