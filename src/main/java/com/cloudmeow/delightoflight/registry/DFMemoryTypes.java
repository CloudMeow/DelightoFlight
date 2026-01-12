package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Optional;

public class DFMemoryTypes {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORIES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, DelightoFlight.MOD_ID);

    public static final RegistryObject<MemoryModuleType<Boolean>> IS_CHEF = MEMORIES.register("is_chef",
            ()-> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<GlobalPos>> BASKET = MEMORIES.register("basket",
            ()-> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<GlobalPos>> POT = MEMORIES.register("pot",
            ()-> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<GlobalPos>> MORE_POT = MEMORIES.register("more_pot",
            ()-> new MemoryModuleType<>(Optional.empty()));

    public static final RegistryObject<MemoryModuleType<Integer>> GROW_HORN_TICKS = MEMORIES.register("grow_horn_ticks",
            ()-> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<Integer>> SHAKE_HEAD_TICKS = MEMORIES.register("shake_head_ticks",
            ()-> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<List<Goat>>> NEAREST_GOAT = MEMORIES.register("nearest_goat",
            ()-> new MemoryModuleType<>(Optional.empty()));
}