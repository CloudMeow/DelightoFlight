package com.cloudmeow.delightoflight.entity.ai;

import com.cloudmeow.delightoflight.registry.DFMemoryTypes;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.entity.BlockEntity;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

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
        Optional<GlobalPos> optional = livingEntity.getBrain().getMemory(DFMemoryTypes.POT.get());
        return livingEntity.getBrain().getMemory(DFMemoryTypes.POT.get()).isPresent() && DFUtilities.checkChefHatExist(livingEntity) && optional.filter(globalPos -> livingEntity.blockPosition().distManhattan(globalPos.pos()) <= 1).isPresent();
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
        DFUtilities.insertCookBookIngredient(cooker.getInventory(), livingEntity);
    }
}