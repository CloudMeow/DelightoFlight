package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.utility.DFUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.function.Supplier;

public class CelestialPuddingBlock extends PieBlock {
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{
            Shapes.or(
                    Block.box(2.0, 0.0, 2.0, 14.0, 6.0, 14.0),
                    Block.box(3.0, 6.0, 3.0, 13.0, 11.0, 13.0)
            ),
            Shapes.or(
                    Block.box(2.0, 0.0, 2.0, 8.0, 6.0, 14.0),
                    Block.box(3.0, 6.0, 3.0, 8.0, 11.0, 13.0),
                    Block.box(8.0, 0.0, 8.0, 14.0, 6.0, 14.0),
                    Block.box(8.0, 6.0, 8.0, 13.0, 11.0, 13.0)
            ),
            Shapes.or(
                    Block.box(2.0, 0.0, 2.0, 8.0, 6.0, 14.0),
                    Block.box(3.0, 6.0, 3.0, 8.0, 11.0, 13.0)
            ),
            Shapes.or(
                    Block.box(2.0, 0.0, 8.0, 8.0, 6.0, 14.0),
                    Block.box(3.0, 6.0, 8.0, 8.0, 11.0, 13.0)
            ),
    };

    public CelestialPuddingBlock(Properties properties, Supplier<Item> pieSlice) {
        super(properties, pieSlice);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = SHAPES[state.getValue(BITES)];

        Rotation rotation = switch (state.getValue(FACING)) {
            case SOUTH -> Rotation.CLOCKWISE_90;
            case WEST  -> Rotation.CLOCKWISE_180;
            case NORTH -> Rotation.COUNTERCLOCKWISE_90;
            default    -> Rotation.NONE;
        };

        return DFUtilities.rotateShape(shape, rotation);
    }
}
