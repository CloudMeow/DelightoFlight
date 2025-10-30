package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import vectorwing.farmersdelight.common.block.FeastBlock;

public class MushroomHotpotBlock extends FeastBlock {
    protected static final VoxelShape HOTPOT = Shapes.or(
            Block.box(3.0, 0.0, 3.0, 13.0, 6.0, 13.0),
            Block.box(0.0, 6.0, 0.0, 16.0, 12.0, 16.0)
    );

    public MushroomHotpotBlock(Properties properties) {
        super(properties, DFItems.MUSHROOM_HOTPOT, true);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return HOTPOT;
    }
}
