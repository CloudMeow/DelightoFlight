package com.cloudmeow.delightoflight.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.BiFunction;

public class MoonshadePetalsBlock extends BushBlock {
    public static final MapCodec<MoonshadePetalsBlock> CODEC = simpleCodec(MoonshadePetalsBlock::new);
    public static final int MAX_FLOWERS = 4;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty AMOUNT = BlockStateProperties.FLOWER_AMOUNT;

    private static final BiFunction<Direction, Integer, VoxelShape> SHAPE_BY_PROPERTIES = Util.memoize(
            (p_296142_, p_294775_) -> {
                VoxelShape[] avoxelshape = new VoxelShape[]{
                        Block.box(8.0, 0.0, 8.0, 16.0, 3.0, 16.0),
                        Block.box(8.0, 0.0, 0.0, 16.0, 3.0, 8.0),
                        Block.box(0.0, 0.0, 0.0, 8.0, 3.0, 8.0),
                        Block.box(0.0, 0.0, 8.0, 8.0, 3.0, 16.0)
                };
                VoxelShape voxelshape = Shapes.empty();

                for (int i = 0; i < p_294775_; i++) {
                    int j = Math.floorMod(i - p_296142_.get2DDataValue(), 4);
                    voxelshape = Shapes.or(voxelshape, avoxelshape[j]);
                }

                return voxelshape.singleEncompassing();
            }
    );

    @Override
    public MapCodec<MoonshadePetalsBlock> codec() {
        return CODEC;
    }

    public MoonshadePetalsBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AMOUNT, 1));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return (!context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && state.getValue(AMOUNT) < MAX_FLOWERS) || super.canBeReplaced(state, context);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_PROPERTIES.apply(state.getValue(FACING), state.getValue(AMOUNT));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        return state.is(this)
                ? state.setValue(AMOUNT, Math.min(MAX_FLOWERS, state.getValue(AMOUNT) + 1))
                : this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, AMOUNT);
    }
}
