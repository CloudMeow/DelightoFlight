package com.cloudmeow.delightoflight.mixin;

import com.cloudmeow.delightoflight.registry.DFPoi;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerGoalPackages.class)
public class VillagerGoalPackagesMixin {
    @Inject(method = "getRestPackage", at = @At("RETURN"), cancellable = true)
    private static void getRestPackage(VillagerProfession p_24593_, float p_24594_, CallbackInfoReturnable<ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Villager>>>> cir){
        cir.setReturnValue(ImmutableList.of(Pair.of(2, SetWalkTargetFromBlockMemory.create(MemoryModuleType.HOME, p_24594_, 1, 150, 1200)), Pair.of(3, ValidateNearbyPoi.create((p_217495_) -> {
            assert DFPoi.CLOUD_BED.getKey() != null;
            return p_217495_.is(DFPoi.CLOUD_BED.getKey()) || p_217495_.is(PoiTypes.HOME);
        }, MemoryModuleType.HOME)), Pair.of(3, new SleepInBed()), Pair.of(5, new RunOne<>(ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_ABSENT), ImmutableList.of(Pair.of(SetClosestHomeAsWalkTarget.create(p_24594_), 1), Pair.of(InsideBrownianWalk.create(p_24594_), 4), Pair.of(GoToClosestVillage.create(p_24594_, 4), 2), Pair.of(new DoNothing(20, 40), 2)))), VillagerGoalPackages.getMinimalLookBehavior(), Pair.of(99, UpdateActivityFromSchedule.create())));
    }
}
