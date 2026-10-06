package com.cloudmeow.delightoflight.item;

import com.cloudmeow.delightoflight.utility.DFUtilities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Stardrop extends Item {
    public Stardrop(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if (!level.isClientSide && entity instanceof Player player) {
            player.getPersistentData().putBoolean("delighto_flight:stardrop_eater", true);
            DFUtilities.applyStardropBonus(player);
        }
        return result;
    }
}
