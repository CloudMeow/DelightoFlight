package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.crafting.RawMoonCakeFillingRecipe;
import com.cloudmeow.delightoflight.registry.DFRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

public class RawMoonCakeSkinBlock extends AbstractMoonCakeBlock {

    public RawMoonCakeSkinBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, 0));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        ItemStack stack = player.getItemInHand(hand);
        BlockPos masterPos = getMasterPos(level, pos, state);
        if (masterPos == null) {
            return InteractionResult.PASS;
        }

        BlockState masterState = level.getBlockState(masterPos);

        SimpleContainer container = new SimpleContainer(1);
        container.setItem(0, stack);
        Optional<RawMoonCakeFillingRecipe> recipe = level.getRecipeManager().getRecipeFor(DFRecipeTypes.RAW_MOON_CAKE_FILLING.get(), container, level);
        if (recipe.isPresent()) {
            Block result = recipe.get().getResultBlock();
            if (result != null) {
                BlockState resultState = result.defaultBlockState();
                if (resultState.getBlock() instanceof AbstractMoonCakeBlock) {
                    setAllParts(level, masterPos, resultState);
                } else {
                    removeAll(level, masterPos);
                    level.setBlock(masterPos, resultState, 3);
                }
                playPlaceSound(level, masterPos, masterState, player);
                stack.shrink(1);
                return InteractionResult.SUCCESS;
            }
        }

        player.displayClientMessage(Component.translatable("delighto_flight.raw_moon_cake_skin.invalid"), true);
        return InteractionResult.SUCCESS;
    }
}
