package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.block.RichSoilFarmlandBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.MathUtils;

public class WeatherSoilFarmlandBlock extends RichSoilFarmlandBlock {
    public WeatherSoilFarmlandBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);
        Block aboveBlock = aboveState.getBlock();
        if (aboveState.is(ModTags.Blocks.UNAFFECTED_BY_RICH_SOIL) || aboveBlock instanceof TallFlowerBlock) {
            return;
        }
        if (aboveBlock instanceof BonemealableBlock) {
            BonemealableBlock growable = (BonemealableBlock)aboveBlock;
            if ((double) MathUtils.RAND.nextFloat() <= (Double) Configuration.RICH_SOIL_BOOST_CHANCE.get() && (growable.isValidBonemealTarget(level, abovePos, aboveState, false) || level.getBlockState(abovePos).is(DFBlocks.THUNDER_VINE.get())) && ForgeHooks.onCropsGrowPre(level, abovePos, aboveState, true)) {
                growable.performBonemeal(level, level.random, abovePos, aboveState);
                if (growable instanceof ThunderVineBlock) {
                    level.setBlockAndUpdate(pos, ModBlocks.RICH_SOIL_FARMLAND.get().defaultBlockState());
                }
                ForgeHooks.onCropsGrowPost(level, abovePos, aboveState);
            }
        }
    }

    @Override
    public boolean isFertile(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }
}
