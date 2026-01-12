package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

public class LotusLeafRiceBlock extends FeastBlock {
    protected static final VoxelShape RICEPOT_SHAPE = Shapes.or(
            Block.box(3.0, 0.0, 3.0, 13.0, 8.0, 13.0),
            Block.box(4.0, 8.0, 4.0, 12.0, 10.0, 12.0)
    );
    protected static final VoxelShape POT_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);

    public LotusLeafRiceBlock(Properties properties) {
        super(properties, DFItems.LOTUS_LEAF_RICE, true);
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int serving = state.getValue(SERVINGS);
        if (serving == 4) return RICEPOT_SHAPE;
        return POT_SHAPE;
    }
}
