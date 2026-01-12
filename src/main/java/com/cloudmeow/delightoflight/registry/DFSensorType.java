package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.entity.ai.aerolope.AerolopeAi;
import com.cloudmeow.delightoflight.entity.ai.aerolope.GoatSensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DFSensorType {
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPE = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, DelightoFlight.MOD_ID);

    public static final RegistryObject<SensorType<TemptingSensor>> AEROLOPE_TEMPTATIONS = SENSOR_TYPE.register("aerolope_temptations", () -> {
        return new SensorType<>(() -> new TemptingSensor(AerolopeAi.getTemptations()));
    });
    public static final RegistryObject<SensorType<GoatSensor>> NEAREST_GOATS = SENSOR_TYPE.register("nearest_goats", () -> {
        return new SensorType<>(GoatSensor::new);
    });
}
