package com.cloudmeow.delightoflight.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class AstralBerryBagBlock extends Block {
    public static final MapCodec<AstralBerryBagBlock> CODEC = simpleCodec(AstralBerryBagBlock::new);
    public static final BooleanProperty GLOW = BooleanProperty.create("glow");

    public AstralBerryBagBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(GLOW, false));
    }

    @Override
    public MapCodec<AstralBerryBagBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GLOW);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        level.scheduleTick(pos, this, 20);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        boolean glow = level.isNight();
        if (state.getValue(GLOW) != glow) {
            level.setBlock(pos, state.setValue(GLOW, glow), 2);
        }
        level.scheduleTick(pos, this, 20);
    }
}
