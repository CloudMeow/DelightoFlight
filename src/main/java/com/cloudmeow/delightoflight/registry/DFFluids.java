package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.fluid.BasicFluidType;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DFFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, DelightoFlight.MOD_ID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, DelightoFlight.MOD_ID);

    public static final RegistryObject<FluidType> CHARGED_ROSE_TEA_TYPE = FLUID_TYPES.register("charged_rose_tea_type",
            () -> new BasicFluidType(0xffa020f0));
    public static final RegistryObject<FlowingFluid> CHARGED_ROSE_TEA = FLUIDS.register("charged_rose_tea",
            () -> new ForgeFlowingFluid.Source(DFFluids.CHARGED_ROSE_TEA_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_CHARGED_ROSE_TEA = FLUIDS.register("flowing_charged_rose_tea",
            () -> new ForgeFlowingFluid.Flowing(DFFluids.CHARGED_ROSE_TEA_PROPERTIES));

    public static final ForgeFlowingFluid.Properties CHARGED_ROSE_TEA_PROPERTIES = new ForgeFlowingFluid.Properties(CHARGED_ROSE_TEA_TYPE, CHARGED_ROSE_TEA, FLOWING_CHARGED_ROSE_TEA);
}