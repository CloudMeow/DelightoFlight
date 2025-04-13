package com.cloudmeow.delightoflight.entity.ai;

import com.cloudmeow.delightoflight.registry.DFMemoryTypes;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PutItemIntoPot<E extends LivingEntity> extends Behavior<E> {
    public PutItemIntoPot() {
        super(ImmutableMap.of(DFMemoryTypes.POT.get(), MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel p_22538_, E livingEntity) {
        Optional<GlobalPos> optional = livingEntity.getBrain().getMemory(DFMemoryTypes.POT.get());
        return optional.filter(globalPos -> livingEntity.blockPosition().distManhattan(globalPos.pos()) <= 1).isPresent() && DFUtilities.checkChefHatExist(livingEntity);
    }

    @Override
    protected boolean canStillUse(ServerLevel p_22545_, E livingEntity, long p_22547_) {
        return livingEntity.getBrain().getMemory(DFMemoryTypes.POT.get()).isPresent() && DFUtilities.checkChefHatExist(livingEntity);
    }

    @Override
    protected void tick(ServerLevel level, E livingEntity, long p_22553_) {
        Optional<GlobalPos> optional = livingEntity.getBrain().getMemory(DFMemoryTypes.POT.get());
        if (optional.isPresent()) {
            BlockEntity entity = level.getBlockEntity(optional.get().pos());
            if (entity instanceof CookingPotBlockEntity cooker) {
                addIngredient(cooker, livingEntity);
            }
        }
    }

    public void addIngredient(CookingPotBlockEntity cooker, E livingEntity) {
        ItemStack cookBook = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
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
            ItemStack leftover = cooker.getInventory().insertItem(currentSlot, holdStack, false);
            livingEntity.setItemSlot(EquipmentSlot.MAINHAND, leftover);
            if (currentSlot  >= itemStacks.size() - 1) {
                tag.putInt("Slot", 0);
            } else {
                tag.putInt("Slot", ++currentSlot);
            }
        }
    }
}