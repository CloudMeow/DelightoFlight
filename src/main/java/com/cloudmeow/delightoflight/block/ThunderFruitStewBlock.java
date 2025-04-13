package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

public class ThunderFruitStewBlock extends FeastBlock {
    protected static final VoxelShape POT = Block.box(3.0, 0.0, 3.0, 13.0, 12.0, 13.0);

    public ThunderFruitStewBlock(Properties properties) {
        super(properties, DFItems.THUNDER_FRUIT_STEW, true);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return POT;
    }
}
