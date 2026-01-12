package com.cloudmeow.delightoflight.entity.ai.aerolope;

import com.cloudmeow.delightoflight.registry.DFMemoryTypes;
import com.google.common.collect.ImmutableSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.phys.AABB;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class GoatSensor extends Sensor<LivingEntity> {
    @Override
    protected void doTick(ServerLevel level, LivingEntity entity) {
        AABB aabb = entity.getBoundingBox().inflate((double)this.radiusXZ(), (double)this.radiusY(), (double)this.radiusXZ());
        List<Goat> list = level.getEntitiesOfClass(Goat.class, aabb, (goat) -> {
            return goat != entity && goat.isAlive();
        });
        list.sort(Comparator.comparingDouble(entity::distanceToSqr));
        Brain<?> brain = entity.getBrain();
        if (list.isEmpty()) {
            brain.eraseMemory(DFMemoryTypes.NEAREST_GOAT.get());
        } else {
            brain.setMemory(DFMemoryTypes.NEAREST_GOAT.get(), list);
        }
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(DFMemoryTypes.NEAREST_GOAT.get());
    }

    protected int radiusXZ() {
        return 16;
    }

    protected int radiusY() {
        return 16;
    }
}
