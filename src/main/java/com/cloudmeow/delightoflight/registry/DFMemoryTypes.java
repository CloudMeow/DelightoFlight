package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Supplier;

public class DFMemoryTypes {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORIES = DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, DelightoFlight.MOD_ID);

    public static final Supplier<MemoryModuleType<Boolean>> IS_CHEF = MEMORIES.register("is_chef",
            ()-> new MemoryModuleType<>(Optional.empty()));
    public static final Supplier<MemoryModuleType<GlobalPos>> BASKET = MEMORIES.register("basket",
            ()-> new MemoryModuleType<>(Optional.empty()));
    public static final Supplier<MemoryModuleType<GlobalPos>> POT = MEMORIES.register("pot",
            ()-> new MemoryModuleType<>(Optional.empty()));
}