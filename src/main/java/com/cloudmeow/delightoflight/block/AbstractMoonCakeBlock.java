package com.cloudmeow.delightoflight.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractMoonCakeBlock extends Block {
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 3);

    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(4.0, 0.0, 4.0, 16.0, 10.0, 16.0),
            Block.box(0.0, 0.0, 4.0, 12.0, 10.0, 16.0),
            Block.box(4.0, 0.0, 0.0, 16.0, 10.0, 12.0),
            Block.box(0.0, 0.0, 0.0, 12.0, 10.0, 12.0)
    };

    protected AbstractMoonCakeBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        for (int dx = 0; dx <= 1; dx++) {
            for (int dz = 0; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) {
                    continue;
                }
                if (!level.getBlockState(pos.offset(dx, 0, dz)).canBeReplaced()) {
                    return null;
                }
            }
        }
        return this.defaultBlockState();
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide) {
            setAllParts(level, pos, state);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(PART)];
    }

    public static BlockPos getMasterPos(BlockGetter level, BlockPos pos, BlockState state) {
        int part = state.getValue(PART);
        if (part == 0) {
            return pos;
        }
        BlockPos masterPos = pos.offset(-(part & 1), 0, -(part >> 1));
        BlockState masterState = level.getBlockState(masterPos);
        if (masterState.getBlock() == state.getBlock() && masterState.getValue(PART) == 0) {
            return masterPos;
        }
        return null;
    }

    public static BlockPos partPos(BlockPos masterPos, int part) {
        return masterPos.offset(part & 1, 0, part >> 1);
    }

    public static void setAllParts(Level level, BlockPos masterPos, BlockState masterState) {
        for (int part = 0; part < 4; part++) {
            level.setBlock(partPos(masterPos, part), masterState.setValue(PART, part), 3);
        }
    }

    protected static void removeParts(Level level, BlockPos masterPos) {
        for (int part = 1; part < 4; part++) {
            BlockPos pos = partPos(masterPos, part);
            if (level.getBlockState(pos).getBlock() instanceof AbstractMoonCakeBlock) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 35);
            }
        }
    }

    protected static void removeAll(Level level, BlockPos masterPos) {
        level.setBlock(masterPos, Blocks.AIR.defaultBlockState(), 35);
        removeParts(level, masterPos);
    }

    protected static void playPlaceSound(Level level, BlockPos pos, BlockState state, Player player) {
        SoundType soundType = state.getSoundType(level, pos, player);
        level.playSound(null, pos, soundType.getPlaceSound(), SoundSource.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!level.isClientSide && newState.isAir()) {
            removeOtherParts(level, pos, state);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    protected void removeOtherParts(Level level, BlockPos pos, BlockState state) {
        if (state.getValue(PART) == 0) {
            removeParts(level, pos);
        } else {
            BlockPos masterPos = getMasterPos(level, pos, state);
            if (masterPos != null) {
                level.setBlock(masterPos, Blocks.AIR.defaultBlockState(), 35);
            }
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            onBrokenByPlayer(level, pos, state, player);
        }
    }

    protected void onBrokenByPlayer(Level level, BlockPos pos, BlockState state, Player player) {
        if (state.getValue(PART) == 0) {
            return;
        }
        BlockPos masterPos = getMasterPos(level, pos, state);
        if (masterPos == null) {
            return;
        }
        BlockState masterState = level.getBlockState(masterPos);
        if (!player.isCreative()) {
            Block.dropResources(masterState, level, masterPos, level.getBlockEntity(masterPos), player, player.getMainHandItem());
        }
        removeAll(level, masterPos);
    }
}
