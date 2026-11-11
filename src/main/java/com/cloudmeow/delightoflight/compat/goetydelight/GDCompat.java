package com.cloudmeow.delightoflight.compat.goetydelight;

import com.cloudmeow.delightoflight.utility.DFUtilities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.v_black_cat.goetydelight.block.CursedIngotPotBlockEntity;
import net.v_black_cat.goetydelight.init.ModBlocks;

public class GDCompat {
    public static Block getGDPot(){
        if (DFUtilities.goetyDelightLoad()) {
            return ModBlocks.CURSED_INGOT_POT.get();
        }
        return null;
    }

    public static boolean isCursedPot(BlockEntity blockEntity) {
        return blockEntity instanceof CursedIngotPotBlockEntity;
    }

    public static void addIngredientForGDPot(BlockEntity cooker, LivingEntity livingEntity) {
        if (cooker instanceof CursedIngotPotBlockEntity pot) {
            DFUtilities.insertCookBookIngredient(pot.getInventory(), livingEntity);
        }
    }
}
