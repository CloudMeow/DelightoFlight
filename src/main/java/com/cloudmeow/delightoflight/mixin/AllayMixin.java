package com.cloudmeow.delightoflight.mixin;

import com.cloudmeow.delightoflight.registry.DFMemoryTypes;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.allay.Allay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Allay.class)
public abstract class AllayMixin {
    @Final
    @Shadow
    protected static final ImmutableList<SensorType<? extends Sensor<? super Allay>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, SensorType.NEAREST_ITEMS);

    @Final
    @Shadow
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.PATH, MemoryModuleType.LOOK_TARGET, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.HURT_BY, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.LIKED_PLAYER, MemoryModuleType.LIKED_NOTEBLOCK_POSITION, MemoryModuleType.LIKED_NOTEBLOCK_COOLDOWN_TICKS, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, MemoryModuleType.IS_PANICKING);

    @Shadow public abstract Brain<Allay> getBrain();

    @Inject(method = "brainProvider", at = @At("RETURN"), cancellable = true)
    private void injectBrainProvider(CallbackInfoReturnable<Brain.Provider<Allay>> cir) {
        ImmutableList<MemoryModuleType<?>> newMemoryTypes = new ImmutableList.Builder<MemoryModuleType<?>>()
                .addAll(MEMORY_TYPES)
                .add(DFMemoryTypes.IS_CHEF.get())
                .add(DFMemoryTypes.BASKET.get())
                .add(DFMemoryTypes.POT.get())
                .build();
        Brain.Provider<Allay> newProvider = Brain.provider(newMemoryTypes, SENSOR_TYPES);
        cir.setReturnValue(newProvider);
    }
}
