package com.cloudmeow.delightoflight.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
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

        return rotateShape(shape, rotation);
    }

    private static VoxelShape rotateShape(VoxelShape shape, Rotation rotation) {
        if (rotation == Rotation.NONE) return shape;

        VoxelShape result = Shapes.empty();
        for (AABB aabb : shape.toAabbs()) {
            double x1 = aabb.minX, y1 = aabb.minY, z1 = aabb.minZ;
            double x2 = aabb.maxX, y2 = aabb.maxY, z2 = aabb.maxZ;
            AABB rotated;
            switch (rotation) {
                case CLOCKWISE_90 -> rotated = new AABB(1 - z2, y1, x1, 1 - z1, y2, x2);
                case CLOCKWISE_180 -> rotated = new AABB(1 - x2, y1, 1 - z2, 1 - x1, y2, 1 - z1);
                case COUNTERCLOCKWISE_90 -> rotated = new AABB(z1, y1, 1 - x2, z2, y2, 1 - x1);
                default -> rotated = aabb;
            }
            result = Shapes.or(result, Block.box(
                    rotated.minX * 16, rotated.minY * 16, rotated.minZ * 16,
                    rotated.maxX * 16, rotated.maxY * 16, rotated.maxZ * 16
            ));
        }
        return result;
    }
}
