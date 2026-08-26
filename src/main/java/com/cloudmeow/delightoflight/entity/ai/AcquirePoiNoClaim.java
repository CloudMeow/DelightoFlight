package com.cloudmeow.delightoflight.entity.ai;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AcquirePoi;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.pathfinder.Path;
import org.apache.commons.lang3.mutable.MutableLong;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AcquirePoiNoClaim {
    public static OneShot<PathfinderMob> create(Predicate<Holder<PoiType>> predicate, MemoryModuleType<GlobalPos> memory) {
        MutableLong nextAttempt = new MutableLong(0L);
        return BehaviorBuilder.create(ctx -> ctx.group(ctx.absent(memory)).apply(ctx, mem -> (level, entity, gameTime) -> {
            if (gameTime < nextAttempt.getValue()) {
                return false;
            }
            nextAttempt.setValue(gameTime + 20L + level.getRandom().nextInt(20));
            PoiManager poimanager = level.getPoiManager();
            Set<Pair<Holder<PoiType>, BlockPos>> pois = poimanager
                    .findAllClosestFirstWithType(predicate, p -> true, entity.blockPosition(), 48, PoiManager.Occupancy.ANY)
                    .limit(5L)
                    .collect(Collectors.toSet());
            Path path = AcquirePoi.findPathToPois(entity, pois);
            if (path != null && path.canReach()) {
                mem.set(GlobalPos.of(level.dimension(), path.getTarget()));
                return true;
            }
            return true;
        }));
    }
}
