package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFBlocks;
import com.cloudmeow.delightoflight.registry.DFItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class AstralBerryVinesPlantBlock extends GrowingPlantBodyBlock implements BonemealableBlock, AstralBerryVines {
    public static final MapCodec<AstralBerryVinesPlantBlock> CODEC = simpleCodec(AstralBerryVinesPlantBlock::new);

    @Override
    public MapCodec<AstralBerryVinesPlantBlock> codec() {
        return CODEC;
    }

    public AstralBerryVinesPlantBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false);
        this.registerDefaultState(this.stateDefinition.any().setValue(BERRIES, false).setValue(TIP, true).setValue(GLOW, false));
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) DFBlocks.ASTRAL_BERRY_VINES.get();
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        BlockState updated = super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        if (facing == this.growthDirection) {
            updated = updated.setValue(TIP, !facingState.is(this.getBodyBlock()));
        }
        return updated;
    }

    @Override
    protected BlockState updateHeadAfterConvertedFromBody(BlockState body, BlockState head) {
        return body;
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(DFItems.ASTRAL_BERRIES.get());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return AstralBerryVines.use(player, state, level, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BERRIES, TIP, GLOW);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
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
