package com.cloudmeow.delightoflight.utility;

import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class DFUtilities {
    public static boolean checkChefHatExist(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == DFItems.MAGIC_CHEF_HAT.get();
    }

    public static boolean checkCookBookExist(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == DFItems.COOK_BOOK.get();
    }
}
