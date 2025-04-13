package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class DFMemoryTypes {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORIES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, DelightoFlight.MOD_ID);

    public static final RegistryObject<MemoryModuleType<Boolean>> IS_CHEF = MEMORIES.register("is_chef",
            ()-> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<GlobalPos>> BASKET = MEMORIES.register("basket",
            ()-> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<GlobalPos>> POT = MEMORIES.register("pot",
            ()-> new MemoryModuleType<>(Optional.empty()));
}