package com.cloudmeow.delightoflight.mixin;

import com.cloudmeow.delightoflight.registry.DFPoi;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;

@Mixin(VillagerGoalPackages.class)
public class VillagerGoalPackagesMixin {
    @ModifyArg(
            method = "getRestPackage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/behavior/ValidateNearbyPoi;create(Ljava/util/function/Predicate;Lnet/minecraft/world/entity/ai/memory/MemoryModuleType;)Lnet/minecraft/world/entity/ai/behavior/BehaviorControl;"
            ),
            index = 0
    )
    private static Predicate<Holder<PoiType>> delightoFlight$acceptCloudBed(Predicate<Holder<PoiType>> original) {
        return original.or(poi -> poi.is(DFPoi.CLOUD_BED.getKey()));
    }
}
