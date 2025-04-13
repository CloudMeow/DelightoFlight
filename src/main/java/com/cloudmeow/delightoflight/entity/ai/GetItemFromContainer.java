package com.cloudmeow.delightoflight.entity.ai;

import com.cloudmeow.delightoflight.registry.DFMemoryTypes;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GetItemFromContainer<E extends LivingEntity> extends Behavior<E> {
    public GetItemFromContainer() {
        super(ImmutableMap.of(DFMemoryTypes.BASKET.get(), MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel p_22538_, E livingEntity) {
        Optional<GlobalPos> optional = livingEntity.getBrain().getMemory(DFMemoryTypes.BASKET.get());
        return optional.filter(globalPos -> livingEntity.blockPosition().distManhattan(globalPos.pos()) <= 1).isPresent() && DFUtilities.checkCookBookExist(livingEntity);
    }

    @Override
    protected boolean canStillUse(ServerLevel p_22545_, E livingEntity, long p_22547_) {
        return livingEntity.getBrain().getMemory(DFMemoryTypes.BASKET.get()).isPresent() && DFUtilities.checkCookBookExist(livingEntity);
    }

    @Override
    protected void tick(ServerLevel level, E livingEntity, long p_22553_) {
        Optional<GlobalPos> optional = livingEntity.getBrain().getMemory(DFMemoryTypes.BASKET.get());
        if (optional.isPresent()) {
            BlockEntity entity = level.getBlockEntity(optional.get().pos());
            if (entity instanceof Container container) {
                getIngredient(container, livingEntity);
            }
        }
    }

    public void getIngredient(Container container, E livingEntity) {
        ItemStack cookBook = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        if (cookBook.isEmpty() || !cookBook.hasTag()) return;
        CompoundTag tag = cookBook.getTag();


        if (!tag.contains("Items", Tag.TAG_COMPOUND)) return;
        CompoundTag itemsTag = tag.getCompound("Items");
        ListTag list = itemsTag.getList("Items", Tag.TAG_COMPOUND);

        if (list.isEmpty()) return;

        List<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            itemStacks.add(ItemStack.of(list.getCompound(i)));
        }

        int slotCount = tag.getInt("Slot");
        if (slotCount < 0 || slotCount >= itemStacks.size()) return;

        ItemStack ingredient = itemStacks.get(slotCount);
        if (livingEntity.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
            for (int i = 0; i < container.getContainerSize(); i++) {
                ItemStack stack = container.getItem(i);
                if (ItemStack.isSameItem(ingredient, stack)) {
                    stack.shrink(1);
                    livingEntity.setItemInHand(InteractionHand.MAIN_HAND, ingredient.copy());
                    break;
                }
            }
        }
    }
}
