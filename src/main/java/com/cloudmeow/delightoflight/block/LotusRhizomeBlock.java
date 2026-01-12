package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LotusRhizomeBlock extends GrowingPlantBodyBlock implements LiquidBlockContainer {
    public static final IntegerProperty RHIZOME_AGE = IntegerProperty.create("rhizome_age", 0, 1);
    public static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public LotusRhizomeBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, false);
        this.registerDefaultState(this.stateDefinition.any().setValue(RHIZOME_AGE, 0));
    }

    @Override
    protected boolean canAttachTo(BlockState state) {
        return state.is(DFBlocks.ROOTED_MUD.get()) || state.is(DFBlocks.LOTUS_RHIZOME.get());
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getSource(false);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter p_54766_, BlockPos p_54767_, BlockState p_54768_, Fluid p_54769_) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor p_54770_, BlockPos p_54771_, BlockState p_54772_, FluidState p_54773_) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(RHIZOME_AGE);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) DFBlocks.LOTUS_BUD.get();
    }

    @Override
    public boolean canSurvive(BlockState p_53876_, LevelReader p_53877_, BlockPos p_53878_) {
        return super.canSurvive(p_53876_, p_53877_, p_53878_);
    }
}
