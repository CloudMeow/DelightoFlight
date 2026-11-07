package com.cloudmeow.delightoflight.item;

import com.cloudmeow.delightoflight.network.DFNetworking;
import com.cloudmeow.delightoflight.utility.StardropData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
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
        if (!level.isClientSide && entity instanceof ServerPlayer player) {
            StardropData.setEaten(player, true);
            DFNetworking.syncStardropState(player);
        }
        return result;
    }
}
