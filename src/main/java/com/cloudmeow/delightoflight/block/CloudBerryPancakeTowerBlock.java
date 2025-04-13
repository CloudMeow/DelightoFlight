package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

public class CloudBerryPancakeTowerBlock extends FeastBlock {
    protected static final VoxelShape PLATE_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0);
    protected static final VoxelShape TOWER_SHAPE_ALL = Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(2.0, 2.0, 2.0, 14.0, 10.0, 14.0), BooleanOp.OR);
    protected static final VoxelShape TOWER_SHAPE_MOST = Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(2.0, 2.0, 2.0, 14.0, 8.0, 14.0), BooleanOp.OR);
    protected static final VoxelShape TOWER_SHAPE_HALF = Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(2.0, 2.0, 2.0, 14.0, 6.0, 14.0), BooleanOp.OR);
    protected static final VoxelShape TOWER_SHAPE_FEW = Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(2.0, 2.0, 2.0, 14.0, 4.0, 14.0), BooleanOp.OR);

    public CloudBerryPancakeTowerBlock(Properties properties) {
        super(properties, DFItems.CLOUD_BERRY_PANCAKE, true);
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int serving = state.getValue(SERVINGS);
        if (serving > 0) {
            switch (serving) {
                case 1:
                    return TOWER_SHAPE_FEW;
                case 2:
                    return TOWER_SHAPE_HALF;
                case 3:
                    return TOWER_SHAPE_MOST;
                case 4:
                    return TOWER_SHAPE_ALL;
            }
        }
        return PLATE_SHAPE;
    }
}
