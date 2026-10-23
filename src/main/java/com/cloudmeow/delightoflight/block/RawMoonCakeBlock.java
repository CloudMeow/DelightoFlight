package com.cloudmeow.delightoflight.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.Supplier;

public class RawMoonCakeBlock extends AbstractMoonCakeBlock {
    public static final IntegerProperty FILLING = IntegerProperty.create("filling", 0, 1);
    public static final IntegerProperty COOKING = IntegerProperty.create("cooking", 0, 3);

    private final Supplier<Block> cookedBlock;

    public RawMoonCakeBlock(Properties properties, Supplier<Block> cookedBlock) {
        super(properties);
        this.cookedBlock = cookedBlock;
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, 0).setValue(FILLING, 0).setValue(COOKING, 0));
    }

    public Block getCookedBlock() {
        return cookedBlock.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FILLING, COOKING);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockPos masterPos = getMasterPos(level, pos, state);
        if (masterPos == null) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        BlockState masterState = level.getBlockState(masterPos);
        if (masterState.getValue(FILLING) != 0) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!stack.is(ModItems.WHEAT_DOUGH.get())) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable("delighto_flight.raw_gaint_rabbish_moon_cake.dough_required"), true);
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!level.isClientSide) {
            setAllParts(level, masterPos, masterState.setValue(FILLING, 1));
            playPlaceSound(level, masterPos, masterState, player);
            stack.shrink(1);
            level.scheduleTick(masterPos, this, 20);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(PART) != 0) {
            return;
        }
        if (state.getValue(FILLING) == 0) {
            return;
        }

        boolean hasMagma = true;
        for (int part = 0; part < 4; part++) {
            if (!level.getBlockState(partPos(pos, part).below()).is(Blocks.MAGMA_BLOCK)) {
                hasMagma = false;
                break;
            }
        }
        int stage = state.getValue(COOKING);

        if (!hasMagma) {
            if (stage > 0) {
                setAllParts(level, pos, state.setValue(COOKING, 0));
            }
            level.scheduleTick(pos, this, 20);
            return;
        }

        if (stage >= 3) {
            Block cooked = getCookedBlock();
            BlockState cookedState = cooked.defaultBlockState();
            if (cookedState.getBlock() instanceof AbstractMoonCakeBlock) {
                setAllParts(level, pos, cookedState);
            } else {
                removeAll(level, pos);
                level.setBlock(pos, cookedState, 3);
            }
            return;
        }

        setAllParts(level, pos, state.setValue(COOKING, stage + 1));
        level.scheduleTick(pos, this, 300);
    }
}
