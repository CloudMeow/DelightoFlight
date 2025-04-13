package com.cloudmeow.delightoflight.mixin;

import com.cloudmeow.delightoflight.entity.ai.ChefHatCheck;
import com.cloudmeow.delightoflight.entity.ai.GetItemFromContainer;
import com.cloudmeow.delightoflight.entity.ai.PutItemIntoPot;
import com.cloudmeow.delightoflight.entity.ai.SetTargetFromBlockMemory;
import com.cloudmeow.delightoflight.registry.DFMemoryTypes;
import com.cloudmeow.delightoflight.registry.DFPoi;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.allay.AllayAi;
import net.minecraft.world.entity.schedule.Activity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AllayAi.class)
public class AllayAiMixin {
    @Unique
    private static void delightoFlight$initWorkActivity(Brain<Allay> brain) {
        brain.addActivityWithConditions(Activity.WORK, ImmutableList.of(Pair.of(0, ValidateNearbyPoi.create((x) -> x.is(DFPoi.BASKET.getKey()), DFMemoryTypes.BASKET.get())), Pair.of(0, ValidateNearbyPoi.create((x) -> x.is(DFPoi.POT.getKey()), DFMemoryTypes.POT.get())), Pair.of(1, SetTargetFromBlockMemory.create(DFMemoryTypes.BASKET.get(), 1.2F, 1, 20, 1200)), Pair.of(2, new GetItemFromContainer<>()), Pair.of(3, SetTargetFromBlockMemory.create(DFMemoryTypes.POT.get(), 1.2F, 1, 20, 1200)), Pair.of(4, new PutItemIntoPot<>()), Pair.of(5, new DoNothing(100, 200))), ImmutableSet.of(Pair.of(DFMemoryTypes.IS_CHEF.get(), MemoryStatus.VALUE_PRESENT)));
    }

    @Unique
    private static void delightoFlight$initMoreCoreActivity(Brain<Allay> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
            new ChefHatCheck<>(),
            AcquirePoi.create((x) -> {
                assert DFPoi.BASKET.getKey() != null;
                return x.is(DFPoi.BASKET.getKey());}, DFMemoryTypes.BASKET.get(), false, Optional.of((byte)14)),
            AcquirePoi.create((x) -> {
                assert DFPoi.POT.getKey() != null;
                return x.is(DFPoi.POT.getKey());}, DFMemoryTypes.POT.get(), false, Optional.of((byte)14))
            ));
    }

    @Inject(method = "makeBrain", at = @At("HEAD"))
    private static void makeBrain(Brain<Allay> brain, CallbackInfoReturnable<Brain<?>> cir) {
        delightoFlight$initWorkActivity(brain);
        delightoFlight$initMoreCoreActivity(brain);
    }

    @Inject(method = "updateActivity", at = @At("HEAD"), cancellable = true)
    private static void updateActivity(Allay allay, CallbackInfo ci) {
        allay.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.WORK, Activity.IDLE));
        ci.cancel();
    }
}
