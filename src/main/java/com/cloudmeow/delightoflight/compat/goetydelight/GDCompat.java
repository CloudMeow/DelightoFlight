package com.cloudmeow.delightoflight.compat.goetydelight;

import com.cloudmeow.delightoflight.utility.DFUtilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.v_black_cat.goetydelight.block.CursedIngotPotBlockEntity;
import net.v_black_cat.goetydelight.block.ModBlocks;

import java.util.ArrayList;
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
        var cookBook = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        if (cookBook.isEmpty() || !cookBook.hasTag()) return;

        CompoundTag tag = cookBook.getTag();
        if (!tag.contains("Items", Tag.TAG_COMPOUND)) return;
        CompoundTag itemsTag = tag.getCompound("Items");
        ListTag itemsList = itemsTag.getList("Items", Tag.TAG_COMPOUND);

        List<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < itemsList.size(); i++) {
            itemStacks.add(ItemStack.of(itemsList.getCompound(i)));
        }

        int currentSlot = tag.getInt("Slot");
        if (currentSlot < 0 || currentSlot >= itemStacks.size()) return;

        ItemStack holdStack = livingEntity.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!holdStack.isEmpty()) {
            ItemStack leftover = ((CursedIngotPotBlockEntity)cooker).getInventory().insertItem(currentSlot, holdStack, false);
            livingEntity.setItemSlot(EquipmentSlot.MAINHAND, leftover);
            if (currentSlot  >= itemStacks.size() - 1) {
                tag.putInt("Slot", 0);
            } else {
                tag.putInt("Slot", ++currentSlot);
            }
        }
    }
}
