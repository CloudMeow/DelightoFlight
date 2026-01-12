package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFBlocks;
import com.cloudmeow.delightoflight.registry.DFItems;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolActions;

public class RootedMudBlock extends Block implements BonemealableBlock {
    public RootedMudBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos pos, BlockState state, boolean isClient) {
        if (levelReader.getBlockState(pos.above()).getFluidState().getType().isSame(Fluids.WATER)) {
            return DFUtilities.getConnectedBlock(levelReader, pos, state.getBlock(), Direction.DOWN, Blocks.MUD, 3);
        }
        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos pos, BlockState state) {
        growLotusRoot(level, pos);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty() && (stack.getItem() instanceof HoeItem || stack.is(ItemTags.HOES) || stack.getItem().canPerformAction(stack, ToolActions.HOE_DIG))) {
            popResource(level, pos, new ItemStack(DFItems.LOTUS_ROOT.get(), 1));
            level.playSound(null, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            level.setBlockAndUpdate(pos, Blocks.MUD.defaultBlockState());
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            stack.hurtAndBreak(1, player, (s) -> {
                s.broadcastBreakEvent(hand);
            });
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, result);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        if (isValidBonemealTarget(level, pos, state, false)) {
            growLotusRoot(level, pos);
        }
        if (hasWaterAbove(level, pos) && !level.getBlockState(pos.above()).is(DFBlocks.LOTUS_RHIZOME.get())) {
            growLotusBud(level, pos);
        }
    }

    public void growLotusRoot(ServerLevel level, BlockPos pos) {
        boolean canGrow = DFUtilities.isFullMoon(level) || level.random.nextInt(3) == 0;
        if (canGrow) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
            for(int x = 1; x < 3; ++x) {
                blockpos$mutableblockpos.move(Direction.DOWN);
                BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
                if(blockstate.is(Blocks.MUD)){
                    level.setBlockAndUpdate(blockpos$mutableblockpos, DFBlocks.ROOTED_MUD.get().defaultBlockState());
                    break;
                }
            }
        }
    }

    public void growLotusBud(ServerLevel level, BlockPos pos) {
        level.setBlockAndUpdate(pos.above(), DFBlocks.LOTUS_BUD.get().defaultBlockState());
    }

    public boolean hasWaterAbove(LevelReader level, BlockPos pos) {
        return DFUtilities.isSameBlock(level, pos, Blocks.WATER, Direction.UP, 4);
    }
}
