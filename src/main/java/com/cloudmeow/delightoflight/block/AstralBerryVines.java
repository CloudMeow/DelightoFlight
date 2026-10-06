package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.function.ToIntFunction;

public interface AstralBerryVines {
    VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    BooleanProperty BERRIES = BlockStateProperties.BERRIES;
    BooleanProperty TIP = BooleanProperty.create("tip");
    BooleanProperty GLOW = BooleanProperty.create("glow");

    static InteractionResult use(@Nullable Entity entity, BlockState state, Level level, BlockPos pos) {
        if (state.getValue(BERRIES)) {
            Block.popResource(level, pos, new ItemStack(DFItems.ASTRAL_BERRIES.get(), 1));
            float f = Mth.randomBetween(level.random, 0.8F, 1.2F);
            level.playSound(null, pos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, f);
            BlockState blockstate = state.setValue(BERRIES, false).setValue(GLOW, false);
            level.setBlock(pos, blockstate, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockstate));
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    static void applyGlow(BlockState state, ServerLevel level, BlockPos pos) {
        boolean glow = state.getValue(BERRIES) && level.isNight();
        if (state.getValue(GLOW) != glow) {
            level.setBlock(pos, state.setValue(GLOW, glow), 2);
        }
    }

    static boolean hasAstralBerries(BlockState state) {
        return state.hasProperty(BERRIES) && state.getValue(BERRIES);
    }

    static ToIntFunction<BlockState> emission(int light) {
        return state -> state.getValue(BERRIES) && state.getValue(GLOW) ? light : 0;
    }
}
