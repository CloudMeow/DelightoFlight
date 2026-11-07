package com.cloudmeow.delightoflight.item;

import com.cloudmeow.delightoflight.entity.PhantomMembraneBalloonEntity;
import com.cloudmeow.delightoflight.registry.DFEntityTypes;
import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class PhantomMembraneBalloon extends Item {
    public PhantomMembraneBalloon(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockState state = level.getBlockState(context.getClickedPos());
        boolean bamboo = state.is(ModBlocks.BAMBOO_BASKET.get());
        if (!bamboo && !state.is(ModBlocks.WOODEN_BASKET.get())) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        PhantomMembraneBalloonEntity balloon = new PhantomMembraneBalloonEntity(DFEntityTypes.PHANTOM_MEMBRANE_BALLOON.get(), level);
        balloon.setBambooBasket(bamboo);
        transferBasketContents(level, context.getClickedPos(), balloon);
        balloon.moveTo(context.getClickedPos().getX() + 0.5D, context.getClickedPos().getY(), context.getClickedPos().getZ() + 0.5D, context.getPlayer() != null ? context.getPlayer().getYRot() : 0.0F, 0.0F);
        level.removeBlock(context.getClickedPos(), false);
        level.addFreshEntity(balloon);

        if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild) {
            context.getItemInHand().shrink(1);
        }
        return InteractionResult.CONSUME;
    }

    private static void transferBasketContents(Level level, BlockPos pos, PhantomMembraneBalloonEntity balloon) {
        if (!(level.getBlockEntity(pos) instanceof Container container)) return;

        int glowBerries = 0;
        int netherStars = 0;
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.is(Items.GLOW_BERRIES)) {
                glowBerries += stack.getCount();
            } else if (stack.is(Items.NETHER_STAR)) {
                netherStars += stack.getCount();
            }
        }

        if (glowBerries > 0) {
            balloon.setStoredAstralBerries(new ItemStack(level.isNight() ? DFItems.ASTRAL_BERRIES.get() : Items.GLOW_BERRIES, glowBerries));
        }
        if (netherStars > 0) {
            balloon.setStoredStardrops(new ItemStack(level.isNight() ? DFItems.STARDROP.get() : Items.NETHER_STAR, netherStars));
        }

        container.clearContent();
    }
}
