package com.cloudmeow.delightoflight.compat.goetydelight;

import com.cloudmeow.delightoflight.registry.DFDataComponents;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.v_black_cat.goetydelight.block.CursedIngotPotBlockEntity;
import net.v_black_cat.goetydelight.init.ModBlocks;

import java.util.List;

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
        if (!(cooker instanceof CursedIngotPotBlockEntity pot)) return;

        ItemStack holdStack = livingEntity.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack cookBook = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        ItemContainerContents contents = cookBook.get(DFDataComponents.COOK_BOOK);
        if (contents == null) return;

        List<ItemStack> itemStacks = contents.stream().toList();
        Integer slot = cookBook.get(DFDataComponents.CURRENT_SLOT);
        int slotCount = slot != null ? slot : 0;
        if (!holdStack.isEmpty()) {
            ItemStack leftover = pot.getInventory().insertItem(slotCount, holdStack, false);
            livingEntity.setItemSlot(EquipmentSlot.MAINHAND, leftover);
            if (slotCount >= itemStacks.size() - 1) {
                cookBook.set(DFDataComponents.CURRENT_SLOT, 0);
            } else {
                cookBook.set(DFDataComponents.CURRENT_SLOT, slotCount + 1);
            }
        }
    }
}
