package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFBlocks;
import com.cloudmeow.delightoflight.registry.DFItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class AstralBerryVinesBlock extends GrowingPlantHeadBlock implements BonemealableBlock, AstralBerryVines {
    private static final float CHANCE_OF_BERRIES_ON_GROWTH = 0.11F;

    public AstralBerryVinesBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false, CHANCE_OF_BERRIES_ON_GROWTH);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(BERRIES, false).setValue(TIP, true).setValue(GLOW, false));
    }

    @Override
    public BlockState getStateForPlacement(LevelAccessor level) {
        return this.defaultBlockState();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState placed = super.getStateForPlacement(context);
        BlockPos below = context.getClickedPos().relative(this.growthDirection);
        return placed.setValue(TIP, !context.getLevel().getBlockState(below).is(this.getBodyBlock()));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        BlockState updated = super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        if (facing == this.growthDirection) {
            updated = updated.setValue(TIP, !facingState.is(this.getBodyBlock()));
        }
        return updated;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);
        if (age >= MAX_AGE) {
            return;
        }

        BlockPos.MutableBlockPos cursor = pos.mutable();
        while (level.getBlockState(cursor.below()).is(this.getBodyBlock())) {
            cursor.move(Direction.DOWN);
        }
        BlockPos target = cursor.below();

        if (this.canGrowInto(level.getBlockState(target)) && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, target, state, random.nextDouble() < CHANCE_OF_BERRIES_ON_GROWTH)) {
            BlockState bodyState = this.getBodyBlock().defaultBlockState().setValue(BERRIES, random.nextFloat() < CHANCE_OF_BERRIES_ON_GROWTH).setValue(TIP, true);
            level.setBlockAndUpdate(target, bodyState);

            if (level.getBlockState(cursor).is(this.getBodyBlock())) {
                level.setBlock(cursor, level.getBlockState(cursor).setValue(TIP, false), 2);
            }
            level.setBlock(pos, state.setValue(TIP, false).setValue(AGE, age + 1), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, target, bodyState);
        }
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return 1;
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    @Override
    protected Block getBodyBlock() {
        return DFBlocks.ASTRAL_BERRY_VINES_PLANT.get();
    }

    @Override
    protected BlockState updateBodyAfterConvertedFromHead(BlockState head, BlockState body) {
        return head;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(DFItems.ASTRAL_BERRIES.get());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return AstralBerryVines.use(player, state, level, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BERRIES, TIP, GLOW);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        if (!level.getBlockState(pos).is(this)) {
            return;
        }

        AstralBerryVines.applyGlow(level.getBlockState(pos), level, pos);
        BlockPos.MutableBlockPos cursor = pos.mutable();
        while (level.getBlockState(cursor.below()).is(this.getBodyBlock())) {
            cursor.move(Direction.DOWN);
            AstralBerryVines.applyGlow(level.getBlockState(cursor), level, cursor);
        }

        level.scheduleTick(pos, this, 20);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        level.scheduleTick(pos, this, 20);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return !state.getValue(BERRIES);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.setBlock(pos, state.setValue(BERRIES, true), 2);
    }
}