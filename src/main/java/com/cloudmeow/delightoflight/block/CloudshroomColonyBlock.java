package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.block.entity.CloudshroomColonyBlockEntity;
import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;

public class CloudshroomColonyBlock extends MushroomColonyBlock implements EntityBlock {
    public static final IntegerProperty WEATHER_AGE = IntegerProperty.create("weather", 0, 2);

    public CloudshroomColonyBlock(Properties properties) {
        super(properties, Items.BROWN_MUSHROOM.builtInRegistryHolder());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        int age = (Integer)state.getValue(COLONY_AGE);
        ItemStack heldStack = player.getItemInHand(hand);
        if (age > 0 && heldStack.is(Tags.Items.SHEARS)) {
            popResource(level, pos, new ItemStack((level.isThundering() ? DFItems.THUNDER_CLOUDSHROOM.get() : level.isRaining() ? DFItems.RAINY_CLOUDSHROOM.get() : DFItems.CLEAR_CLOUDSHROOM.get()), 1));
            level.playSound((Player)null, pos, SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlock(pos, (BlockState)state.setValue(COLONY_AGE, age - 1), 2);
            if (!level.isClientSide) {
                heldStack.hurtAndBreak(1, player, (playerIn) -> {
                    playerIn.broadcastBreakEvent(hand);
                });
            }

            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(DFItems.CLEAR_CLOUDSHROOM.get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLONY_AGE, WEATHER_AGE);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (!level.isClientSide()) {
            return (level1, pos, state1, blockEntity) -> this.tick(state1, level1, pos);
        }
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CloudshroomColonyBlockEntity(blockPos, blockState);
    }

    public void tick(BlockState state, Level level, BlockPos pos) {
        if (level.isThundering()) {
            level.setBlock(pos, (BlockState)state.setValue(WEATHER_AGE, 2), 2);
        } else if (level.isRaining()) {
            level.setBlock(pos, (BlockState)state.setValue(WEATHER_AGE, 1), 2);
        } else {
            level.setBlock(pos, (BlockState)state.setValue(WEATHER_AGE, 0), 2);
        }
    }
}
