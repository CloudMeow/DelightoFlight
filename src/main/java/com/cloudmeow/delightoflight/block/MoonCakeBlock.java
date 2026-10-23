package com.cloudmeow.delightoflight.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import vectorwing.farmersdelight.common.registry.ModSounds;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.function.Supplier;

public class MoonCakeBlock extends AbstractMoonCakeBlock {
    public static final IntegerProperty SERVINGS = IntegerProperty.create("servings", 0, 3);
    private static final int MAX_SERVINGS = 3;

    public final Supplier<Item> servingItem;

    public MoonCakeBlock(Properties properties, Supplier<Item> servingItem) {
        super(properties);
        this.servingItem = servingItem;
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, 0).setValue(SERVINGS, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SERVINGS);
    }

    @Override
    protected void removeOtherParts(Level level, BlockPos pos, BlockState state) {}

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!stack.is(ModTags.Items.KNIVES)) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable("delighto_flight.gaint_rabbish_moon_cake.knife_required"), true);
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }

        int servings = state.getValue(SERVINGS);
        Block.popResource(level, pos, new ItemStack(servingItem.get()));
        if (servings >= MAX_SERVINGS) {
            level.removeBlock(pos, false);
        } else {
            level.setBlock(pos, state.setValue(SERVINGS, servings + 1), 3);
        }
        level.playSound(null, pos, ModSounds.BLOCK_FOOD_SLICE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected void onBrokenByPlayer(Level level, BlockPos pos, BlockState state, Player player) {
        int part = state.getValue(PART);
        BlockPos origin = pos.offset(-(part & 1), 0, -(part >> 1));
        for (int i = 0; i < 4; i++) {
            BlockState quarter = level.getBlockState(partPos(origin, i));
            if (quarter.getBlock() != this || quarter.getValue(SERVINGS) != 0) {
                return;
            }
        }
        if (!player.isCreative()) {
            Block.popResource(level, pos, new ItemStack(this));
        }
        for (int i = 0; i < 4; i++) {
            level.setBlock(partPos(origin, i), Blocks.AIR.defaultBlockState(), 35);
        }
    }
}
