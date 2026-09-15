package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFBlocks;
import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class LotusBudBlock extends Block implements LiquidBlockContainer, BonemealableBlock  {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty BUD_AGE = IntegerProperty.create("bud_age", 0, 2);
    public static final IntegerProperty HIGH = IntegerProperty.create("high", 0, 1);
    public static final IntegerProperty SLOPE = IntegerProperty.create("slope", 0, 1);
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public LotusBudBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BUD_AGE, 0).setValue(HIGH, 0).setValue(SLOPE, 0));
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
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        return below.is(DFBlocks.ROOTED_MUD.get()) || below.is(DFBlocks.LOTUS_RHIZOME.get());
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        if (fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8 && this.canSurvive(this.defaultBlockState(), context.getLevel(), context.getClickedPos())) {
            return super.getStateForPlacement(context);
        }
        return null;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(level, pos)) {
            level.scheduleTick(pos, this, 1);
        }

        if (direction == Direction.UP && (neighborState.is(this) || neighborState.is(DFBlocks.LOTUS_RHIZOME.get()))) {
            return DFBlocks.LOTUS_RHIZOME.get().defaultBlockState();
        }

        level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }



    @Override
    public boolean canPlaceLiquid(BlockGetter blockGetter, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BUD_AGE, HIGH, SLOPE);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getSource(false);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getBlockState(pos.above()).is(Blocks.WATER) && random.nextDouble() < 0.14D) {
            level.setBlockAndUpdate(pos.above(), state);
        }
        growLotusFlower(level, pos, state);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return level.getBlockState(pos.above()).is(Blocks.WATER) || state.getValue(BUD_AGE) < 2 || level.getBlockState(pos.above()).isAir();
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos.above()).is(Blocks.WATER)) {
            level.setBlockAndUpdate(pos.above(), state);
        }
        growLotusFlower(level, pos, state);
    }

    @Override
    public boolean isBonemealSuccess(Level p_220878_, RandomSource p_220879_, BlockPos p_220880_, BlockState p_220881_) {
        return true;
    }

    public void growLotusFlower(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState) {
        int budAge = blockState.getValue(BUD_AGE);
        if (serverLevel.getBlockState(blockPos.above()).isAir()) {
            if (budAge == 0) {
                int state = serverLevel.random.nextInt(3);
                if (state == 0) {
                    serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(BUD_AGE, 1));
                }
                if (state == 1) {
                    serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(BUD_AGE, 1).setValue(HIGH, 1));
                }
                if (state == 2) {
                    serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(BUD_AGE, 1).setValue(HIGH, 1).setValue(SLOPE, 1));
                }
            } else if (budAge == 1) {
                serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(BUD_AGE, budAge + 1));
            } else if (budAge == 2) {
                int high = blockState.getValue(HIGH);
                if (high == 1) {
                    serverLevel.setBlockAndUpdate(blockPos.above(), DFBlocks.LOTUS_FLOWER.get().defaultBlockState().setValue(LotusFlowerBlock.HIGH, 1));
                } else {
                    serverLevel.setBlockAndUpdate(blockPos.above(), DFBlocks.LOTUS_FLOWER.get().defaultBlockState());
                }
            }
        } else {
            if (budAge == 1) {
                serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(BUD_AGE, budAge + 1));
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack stack = player.getItemInHand(hand);
        int budAge = state.getValue(BUD_AGE);
        if (budAge < 2 && stack.is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        } else if (budAge == 2 && stack.is(Items.SHEARS)) {
            popResource(level, pos, new ItemStack(DFItems.LOTUS_LEAF.get(), 1));
            level.playSound((Player)null, pos, SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            BlockState blockstate = state.setValue(BUD_AGE, 1);
            level.setBlock(pos, blockstate, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockstate));
            return InteractionResult.SUCCESS;
        } else {
            return super.use(state, level, pos, player, hand, result);
        }
    }
}
