package com.cloudmeow.delightoflight.item;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class CloudBread extends ConsumableItem {
    private static final double RISE_SPEED = 0.08D;

    public CloudBread(Properties properties, boolean hasFoodEffectTooltip) {
        super(properties, hasFoodEffectTooltip);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        entity.setNoGravity(true);
        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.x * 0.95D, RISE_SPEED, motion.z * 0.95D);
        return false;
    }
}
