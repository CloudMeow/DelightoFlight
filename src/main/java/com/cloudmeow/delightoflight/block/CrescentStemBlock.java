package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.block.state.properties.Connection;
import com.cloudmeow.delightoflight.registry.DFBlocks;
import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

public class CrescentStemBlock extends BushBlock implements BonemealableBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final EnumProperty<Connection> CONNECTION = EnumProperty.create("connection", Connection.class);

    protected static final VoxelShape[] NORTH_SHAPES = new VoxelShape[]{
            Block.box(5.0, 4.0, 9.0, 13.0, 12.0, 16.0),
            Block.box(3.0, 3.0, 6.0, 15.0, 13.0, 16.0),
            Block.box(3.0, 1.0, 10.0, 14.0, 14.0, 16.0),
            Block.box(2.0, 1.0, 3.0, 16.0, 15.0, 16.0)
    };
    protected static final VoxelShape[] SOUTH_SHAPES = new VoxelShape[]{
            Block.box(3.0, 4.0, 0.0, 11.0, 12.0, 7.0),
            Block.box(1.0, 3.0, 0.0, 13.0, 13.0, 10.0),
            Block.box(2.0, 1.0, 0.0, 13.0, 14.0, 6.0),
            Block.box(0.0, 1.0, 0.0, 14.0, 15.0, 13.0)
    };
    protected static final VoxelShape[] WEST_SHAPES = new VoxelShape[]{
            Block.box(9.0, 4.0, 3.0, 16.0, 12.0, 11.0),
            Block.box(6.0, 3.0, 1.0, 16.0, 13.0, 13.0),
            Block.box(10.0, 1.0, 2.0, 16.0, 14.0, 13.0),
            Block.box(3.0, 1.0, 0.0, 16.0, 15.0, 14.0)
    };
    protected static final VoxelShape[] EAST_SHAPES = new VoxelShape[]{
            Block.box(0.0, 4.0, 5.0, 7.0, 12.0, 13.0),
            Block.box(0.0, 3.0, 3.0, 10.0, 13.0, 15.0),
            Block.box(0.0, 1.0, 3.0, 6.0, 14.0, 14.0),
            Block.box(0.0, 1.0, 2.0, 13.0, 15.0, 16.0)
    };

    public CrescentStemBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AGE, 0).setValue(CONNECTION, Connection.NONE));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int age = state.getValue(AGE);
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH_SHAPES[age];
            case SOUTH -> SOUTH_SHAPES[age];
            case WEST -> WEST_SHAPES[age];
            default -> EAST_SHAPES[age];
        };
    }

    private boolean canAttachTo(BlockGetter level, BlockPos pos, Direction direction) {
        BlockState support = level.getBlockState(pos);
        return support.is(BlockTags.DIRT) && support.isFaceSturdy(level, pos, direction);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        return this.canAttachTo(level, pos.relative(direction.getOpposite()), direction);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getLevel().getBlockState(context.getClickedPos()).is(this)) {
            return null;
        }

        BlockState state = this.defaultBlockState();
        LevelReader level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        for (Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                state = state.setValue(FACING, direction.getOpposite());
                if (state.canSurvive(level, pos)) {
                    return state;
                }
            }
        }
        return null;
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
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        Connection connection = state.getValue(CONNECTION);
        if (direction == Direction.UP && connection.hasUp()
                && !neighborState.is(DFBlocks.WAXING_CRESCENT_SLIVERMELON.get())
                && !neighborState.is(DFBlocks.MIXING_CRESCENT_SLIVERMELON.get())) {
            state = state.setValue(CONNECTION, connection.withoutUp());
        }
        else if (direction == Direction.DOWN && connection.hasDown()
                && !neighborState.is(DFBlocks.WANING_CRESCENT_SLIVERMELON.get())
                && !neighborState.is(DFBlocks.MIXING_CRESCENT_SLIVERMELON.get())) {
            state = state.setValue(CONNECTION, connection.withoutDown());
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;
        if (!level.isNight()) return;
        if (state.getValue(AGE) == MAX_AGE && state.getValue(CONNECTION) == Connection.BOTH) return;
        if (ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(4) == 0)) {
            int i = state.getValue(AGE);
            if (i < MAX_AGE) {
                level.setBlock(pos, state.setValue(AGE, i + 1), 2);
            } else {
                Connection connection = state.getValue(CONNECTION);
                boolean growUp;
                if (connection == Connection.UP) {
                    growUp = false;
                } else if (connection == Connection.DOWN) {
                    growUp = true;
                } else {
                    growUp = random.nextBoolean();
                }

                BlockPos fruitPos = growUp ? pos.above() : pos.below();
                BlockState fruitState = level.getBlockState(fruitPos);

                if (level.isEmptyBlock(fruitPos)) {
                    Block fruit = growUp ? DFBlocks.WAXING_CRESCENT_SLIVERMELON.get() : DFBlocks.WANING_CRESCENT_SLIVERMELON.get();
                    level.setBlockAndUpdate(fruitPos, fruit.defaultBlockState());
                    Connection newConnection = growUp ? connection.withUp() : connection.withDown();
                    level.setBlockAndUpdate(pos, state.setValue(CONNECTION, newConnection));
                } else if (growUp && fruitState.is(DFBlocks.WANING_CRESCENT_SLIVERMELON.get())) {
                    level.setBlockAndUpdate(fruitPos, DFBlocks.MIXING_CRESCENT_SLIVERMELON.get().defaultBlockState());
                    Connection newConnection = connection.withUp();
                    level.setBlockAndUpdate(pos, state.setValue(CONNECTION, newConnection));
                } else if (!growUp && fruitState.is(DFBlocks.WAXING_CRESCENT_SLIVERMELON.get())) {
                    level.setBlockAndUpdate(fruitPos, DFBlocks.MIXING_CRESCENT_SLIVERMELON.get().defaultBlockState());
                    Connection newConnection = connection.withDown();
                    level.setBlockAndUpdate(pos, state.setValue(CONNECTION, newConnection));
                }
            }
            ForgeHooks.onCropsGrowPost(level, pos, state);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(DFItems.CRESCENT_SEEDS.get());
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(AGE) < MAX_AGE;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int i = Math.min(MAX_AGE, state.getValue(AGE) + 1);
        BlockState blockstate = state.setValue(AGE, i);
        level.setBlock(pos, blockstate, 2);
        if (i == MAX_AGE) {
            blockstate.randomTick(level, pos, level.random);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, AGE, CONNECTION);
    }
}
