package com.cloudmeow.delightoflight.compat.dungeonsdelight;

import com.cloudmeow.delightoflight.registry.DFDataComponents;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.yirmiri.dungeonsdelight.common.block.monster_pot.MonsterPotBlockEntity;
import net.yirmiri.dungeonsdelight.core.registry.DDBlocks;

import java.util.List;

public class DDCompat {
    public static Block getDDPot() {
        if (DFUtilities.dungeonsDelightLoad()) {
            return DDBlocks.MONSTER_POT.get();
        }
        return null;
    }

    public static boolean isMonsterPot(BlockEntity blockEntity) {
        return blockEntity instanceof MonsterPotBlockEntity;
    }

    public static void addIngredientForDDPot(BlockEntity cooker, LivingEntity livingEntity) {
        ItemStack holdStack = livingEntity.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack cookBook = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        ItemContainerContents contents = cookBook.get(DFDataComponents.COOK_BOOK);
        if (contents == null) return;
        List<ItemStack> itemStacks = contents.stream().toList();
        int slotCount = cookBook.get(DFDataComponents.CURRENT_SLOT);
        if (!holdStack.isEmpty()) {
            ItemStack leftover = ((MonsterPotBlockEntity)cooker).getInventory().insertItem(slotCount, holdStack, false);
            livingEntity.setItemSlot(EquipmentSlot.MAINHAND, leftover);
            if (slotCount >= itemStacks.size() - 1) {
                cookBook.set(DFDataComponents.CURRENT_SLOT, 0);
            } else {
                cookBook.set(DFDataComponents.CURRENT_SLOT, ++slotCount);
            }
        }
    }
}
