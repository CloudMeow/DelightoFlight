package com.cloudmeow.delightoflight.entity.ai;

import com.cloudmeow.delightoflight.registry.DFMemoryTypes;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class ChefHatCheck<E extends LivingEntity> extends Behavior<E> {
    public ChefHatCheck() {
        super(ImmutableMap.of(DFMemoryTypes.IS_CHEF.get(), MemoryStatus.REGISTERED));
    }

    @Override
    protected void start(ServerLevel world, E entity, long gameTime) {
        boolean isChef = DFUtilities.checkChefHatExist(entity);
        Brain<?> brain = entity.getBrain();
        if (isChef) {
            brain.setMemory(DFMemoryTypes.IS_CHEF.get(), true);
        } else {
            brain.eraseMemory(DFMemoryTypes.IS_CHEF.get());
        }
    }
}
