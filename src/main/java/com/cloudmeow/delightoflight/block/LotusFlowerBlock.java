package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFAdvancements;
import com.cloudmeow.delightoflight.registry.DFBlocks;
import com.cloudmeow.delightoflight.registry.DFItems;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class LotusFlowerBlock extends CropBlock implements BonemealableBlock {
    public static final IntegerProperty FLOWER_AGE = IntegerProperty.create("flower_age", 0, 3);
    public static final IntegerProperty HIGH = IntegerProperty.create("high", 0, 1);

    public LotusFlowerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FLOWER_AGE, 0).setValue(HIGH, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FLOWER_AGE).add(HIGH);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return blockState.getValue(FLOWER_AGE) < 3;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return FLOWER_AGE;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    protected int getBonemealAgeIncrease(Level level) {
        return super.getBonemealAgeIncrease(level) / 3;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos blockPos) {
        return state.is(DFBlocks.LOTUS_BUD.get());
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return (level.getRawBrightness(pos, 0) >= 8 || level.canSeeSky(pos)) && this.mayPlaceOn(level.getBlockState(pos.below()), level, pos);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        int flowerAge = state.getValue(FLOWER_AGE);
        if (flowerAge < 3 && stack.is(Items.BONE_MEAL)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else if (flowerAge == 3 && stack.is(Items.SHEARS)) {
            popResource(level, blockPos, new ItemStack(DFItems.LOTUS_SEEDS.get(), 3));
            level.playSound((Player)null, blockPos, SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            BlockState blockstate = state.setValue(FLOWER_AGE, 2);
            level.setBlock(blockPos, blockstate, 2);
            if (!level.isClientSide) {
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            }
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, blockstate));
            return ItemInteractionResult.SUCCESS;
        } else if (flowerAge == 2 && stack.is(Items.SHEARS)) {
            popResource(level, blockPos, new ItemStack(DFItems.LOTUS_FLOWER.get(), 1));
            level.playSound((Player)null, blockPos, SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            BlockState blockstate = state.setValue(FLOWER_AGE, 0);
            level.setBlock(blockPos, blockstate, 2);
            if (!level.isClientSide) {
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            }
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, blockstate));
            if (level.isNight() && player instanceof ServerPlayer) {
                DFAdvancements.CUT_LOTUS_FLOWER_NIGHT.get().trigger((ServerPlayer) player);
            }
            return ItemInteractionResult.SUCCESS;
        } else {
            return super.useItemOn(stack, state, level, blockPos, player, hand, hitResult);
        }
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return DFItems.LOTUS_FLOWER.get();
    }

    public BlockState getStateForAllState(int flower, int high) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), Integer.valueOf(flower)).setValue(HIGH, high);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        if (!level.isAreaLoaded(pos, 1)) return;
        if (level.getRawBrightness(pos, 0) >= 9 || DFUtilities.isFullMoon(level)) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getGrowthSpeed(state, level, pos);
                if (net.neoforged.neoforge.common.CommonHooks.canCropGrow(level, pos, state, randomSource.nextInt((int)(25.0F / f) + 1) == 0)) {
                    level.setBlock(pos, this.getStateForAllState(DFUtilities.isFullMoon(level) ? 3 : i + 1, getHigh(state)), 2);
                    net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(level, pos, state);
                }
            }
        }

    }

    @Override
    public void growCrops(Level level, BlockPos pos, BlockState state) {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(level);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        level.setBlock(pos, this.getStateForAllState(i, getHigh(state)), 2);
    }

    protected IntegerProperty getHighProperty() {
        return HIGH;
    }

    public int getHigh(BlockState state) {
        return state.getValue(this.getHighProperty());
    }
}
