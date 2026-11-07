package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFItems;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

public class WaningCrescentJellyBlock extends FeastBlock {
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{
            Shapes.or(
                    Block.box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0),
                    Block.box(4.0, 3.0, 4.0, 12.0, 6.0, 12.0)
            ),
            Shapes.or(
                    Block.box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0),
                    Block.box(4.0, 3.0, 8.0, 12.0, 6.0, 12.0),
                    Block.box(8.0, 3.0, 4.0, 12.0, 6.0, 8.0)
            ),
            Shapes.or(
                    Block.box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0),
                    Block.box(4.0, 3.0, 8.0, 12.0, 6.0, 12.0)
            ),
            Shapes.or(
                    Block.box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0),
                    Block.box(8.0, 3.0, 8.0, 12.0, 6.0, 12.0)
            ),
            Shapes.or(
                    Block.box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0)
            )
    };

    public WaningCrescentJellyBlock(Properties properties) {
        super(properties, DFItems.WANING_CRESCENT_JELLY_SLICE, true);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = SHAPES[getMaxServings() - state.getValue(getServingsProperty())];

        Rotation rotation = switch (state.getValue(FACING)) {
            case SOUTH -> Rotation.CLOCKWISE_180;
            case WEST  -> Rotation.COUNTERCLOCKWISE_90;
            case NORTH -> Rotation.NONE;
            default    -> Rotation.CLOCKWISE_90;
        };

        return DFUtilities.rotateShape(shape, rotation);
    }
}
