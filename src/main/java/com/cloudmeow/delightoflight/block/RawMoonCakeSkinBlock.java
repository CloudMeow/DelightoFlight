package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.crafting.RawMoonCakeFillingRecipe;
import com.cloudmeow.delightoflight.registry.DFRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
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
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }

        BlockPos masterPos = getMasterPos(level, pos, state);
        if (masterPos == null) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        BlockState masterState = level.getBlockState(masterPos);

        Optional<RecipeHolder<RawMoonCakeFillingRecipe>> recipe = level.getRecipeManager().getRecipeFor(DFRecipeTypes.RAW_MOON_CAKE_FILLING.get(), new SingleRecipeInput(stack), level);
        if (recipe.isPresent()) {
            Block result = recipe.get().value().getResultBlock();
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
                return ItemInteractionResult.SUCCESS;
            }
        }

        player.displayClientMessage(Component.translatable("delighto_flight.raw_moon_cake_skin.invalid"), true);
        return ItemInteractionResult.SUCCESS;
    }
}
