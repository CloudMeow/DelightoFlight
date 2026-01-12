package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.entity.AerolopeEntity;
import com.cloudmeow.delightoflight.entity.ElectricCurrent;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DFEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, DelightoFlight.MOD_ID);

    public static final Supplier<EntityType<ElectricCurrent>> ELECTRIC_CURRENT = ENTITY_TYPES.register("electric_current",
            () -> EntityType.Builder.<ElectricCurrent>of(ElectricCurrent::new, MobCategory.MISC).sized(0.1F, 0.1F).build("electric_current"));
    public static final Supplier<EntityType<AerolopeEntity>> AEROLOPE = ENTITY_TYPES.register("aerolope",
            () -> EntityType.Builder.<AerolopeEntity>of(AerolopeEntity::new, MobCategory.CREATURE).sized(0.9F, 1.6F).build("aerolope"));
}
