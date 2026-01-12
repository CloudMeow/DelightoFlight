package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFBlocks;
import com.cloudmeow.delightoflight.registry.DFItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class LotusBudBlock extends GrowingPlantHeadBlock implements LiquidBlockContainer {
    public static final IntegerProperty BUD_AGE = IntegerProperty.create("bud_age", 0, 2);
    public static final IntegerProperty HIGH = IntegerProperty.create("high", 0, 1);
    public static final IntegerProperty SLOPE = IntegerProperty.create("slope", 0, 1);
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public LotusBudBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, false, 0.14D);
        this.registerDefaultState(this.stateDefinition.any().setValue(BUD_AGE, 0).setValue(HIGH, 0).setValue(SLOPE, 0));
    }

    @Override
    protected boolean canAttachTo(BlockState state) {
        return state.is(DFBlocks.ROOTED_MUD.get()) || state.is(DFBlocks.LOTUS_RHIZOME.get());
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.is(Blocks.WATER);
    }

    @Override
    protected Block getBodyBlock() {
        return DFBlocks.LOTUS_RHIZOME.get();
    }

    @Override
    public boolean canPlaceLiquid(@org.jetbrains.annotations.Nullable Player player, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
        return 1;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState blockState1, LevelAccessor level, BlockPos blockPos, BlockPos blockPos1) {
        if (direction == this.growthDirection.getOpposite() && !state.canSurvive(level, blockPos)) {
            level.scheduleTick(blockPos, this, 1);
        }

        if (direction != this.growthDirection || !blockState1.is(this) && !blockState1.is(this.getBodyBlock())) {
            if (this.scheduleFluidTicks) {
                level.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }

            return super.updateShape(state, direction, blockState1, level, blockPos, blockPos1);
        } else {
            return this.updateBodyAfterConvertedFromHead(state, this.getBodyBlock().defaultBlockState().setValue(LotusRhizomeBlock.RHIZOME_AGE, level.getBlockState(blockPos.below()).is(DFBlocks.ROOTED_MUD.get()) ? 0 : 1));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BUD_AGE).add(HIGH).add(SLOPE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        FluidState fluidstate = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        return fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8 ? super.getStateForPlacement(blockPlaceContext) : null;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getSource(false);
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos pos, BlockState state) {
        BlockPos nextGrowth = pos.relative(this.growthDirection);
        if (this.canGrowInto(serverLevel.getBlockState(nextGrowth))) {
            serverLevel.setBlockAndUpdate(nextGrowth, this.getGrowIntoState(state, serverLevel.random));
        }
        growLotusFlower(serverLevel, pos, state);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return super.isValidBonemealTarget(levelReader, blockPos, blockState) || levelReader.getBlockState(blockPos.above()).isAir();
    }

    @Override
    protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return null;
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        super.randomTick(blockState, serverLevel, blockPos, randomSource);
        growLotusFlower(serverLevel, blockPos, blockState);
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
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        int budAge = state.getValue(BUD_AGE);
        if (budAge < 2 && stack.is(Items.BONE_MEAL)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else if (budAge == 2 && stack.is(Items.SHEARS)) {
            popResource(level, pos, new ItemStack(DFItems.LOTUS_LEAF.get(), 1));
            level.playSound((Player)null, pos, SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            BlockState blockstate = state.setValue(BUD_AGE, 1);
            level.setBlock(pos, blockstate, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockstate));
            return ItemInteractionResult.SUCCESS;
        } else {
            return super.useItemOn(stack, state, level, pos, player, hand, result);
        }
    }
}
