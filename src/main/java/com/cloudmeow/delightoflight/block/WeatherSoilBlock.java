package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFBlocks;
import com.cloudmeow.delightoflight.utility.DFTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.block.RichSoilBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.MathUtils;

public class WeatherSoilBlock extends RichSoilBlock {
    public WeatherSoilBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        return toolAction.equals(ToolActions.HOE_TILL) && context.getLevel().getBlockState(context.getClickedPos().above()).isAir() ? ((Block) DFBlocks.WEATHER_SOIL_FARMLAND.get()).defaultBlockState() : null;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        if (!level.isClientSide) {
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);
            Block aboveBlock = aboveState.getBlock();
            if (aboveState.is(ModTags.UNAFFECTED_BY_RICH_SOIL)) {
                return;
            }

            if (aboveState.is(DFTags.MUSHROOM)) {
                level.setBlockAndUpdate(pos.above(), ((Block) DFBlocks.CLOUDSHROOM_COLONY.get()).defaultBlockState());
                return;
            }

            if ((Double) Configuration.RICH_SOIL_BOOST_CHANCE.get() == 0.0) {
                return;
            }

            if (aboveBlock instanceof BonemealableBlock) {
                BonemealableBlock growable = (BonemealableBlock)aboveBlock;
                if ((double) MathUtils.RAND.nextFloat() <= (Double)Configuration.RICH_SOIL_BOOST_CHANCE.get() && growable.isValidBonemealTarget(level, pos.above(), aboveState, false) && ForgeHooks.onCropsGrowPre(level, pos.above(), aboveState, true)) {
                    growable.performBonemeal(level, level.random, pos.above(), aboveState);
                    level.levelEvent(2005, pos.above(), 0);
                    ForgeHooks.onCropsGrowPost(level, pos.above(), aboveState);
                }
            }
        }

    }
}
