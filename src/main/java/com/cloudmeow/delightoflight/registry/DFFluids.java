package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.fluid.BasicFluidType;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class DFFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, DelightoFlight.MOD_ID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, DelightoFlight.MOD_ID);

    public static final Holder<FluidType> CHARGED_ROSE_TEA_TYPE = FLUID_TYPES.register("charged_rose_tea_type",
            BasicFluidType::new);
    public static final Supplier<FlowingFluid> CHARGED_ROSE_TEA = FLUIDS.register("charged_rose_tea",
            () -> new BaseFlowingFluid.Source(DFFluids.CHARGED_ROSE_TEA_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_CHARGED_ROSE_TEA = FLUIDS.register("flowing_charged_rose_tea",
            () -> new BaseFlowingFluid.Flowing(DFFluids.CHARGED_ROSE_TEA_PROPERTIES));

    public static final BaseFlowingFluid.Properties CHARGED_ROSE_TEA_PROPERTIES = new BaseFlowingFluid.Properties((Supplier<? extends FluidType>) CHARGED_ROSE_TEA_TYPE, CHARGED_ROSE_TEA, FLOWING_CHARGED_ROSE_TEA);
}
