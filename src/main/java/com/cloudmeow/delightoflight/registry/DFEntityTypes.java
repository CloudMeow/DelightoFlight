package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.entity.ElectricCurrent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DFEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DelightoFlight.MOD_ID);

    public static final RegistryObject<EntityType<ElectricCurrent>> ELECTRIC_CURRENT = ENTITY_TYPES.register("electric_current",
            () -> EntityType.Builder.<ElectricCurrent>of(ElectricCurrent::new, MobCategory.MISC).sized(0.1F, 0.1F).build("electric_current"));
}
