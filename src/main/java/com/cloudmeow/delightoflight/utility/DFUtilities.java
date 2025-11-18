package com.cloudmeow.delightoflight.utility;

import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.ModList;

public class DFUtilities {
    public static boolean checkChefHatExist(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == DFItems.MAGIC_CHEF_HAT.get();
    }

    public static boolean checkCookBookExist(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == DFItems.COOK_BOOK.get();
    }

    public static boolean chefDelightLoad() {
        return ModList.get().isLoaded("chefsdelight");
    }

    public static boolean twilightDelightLoad() {
        return ModList.get().isLoaded("twilightdelight");
    }

    public static boolean goetyDelightLoad() {
        return ModList.get().isLoaded("goetydelight");
    }

    public static boolean dungeonsDelightLoad() {
        return ModList.get().isLoaded("dungeonsdelight");
    }
}
