package com.cloudmeow.delightoflight.event;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.entity.AerolopeEntity;
import com.cloudmeow.delightoflight.network.DFNetworking;
import com.cloudmeow.delightoflight.registry.DFEntityTypes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = DelightoFlight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvent {
    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
        event.put(DFEntityTypes.AEROLOPE.get(), AerolopeEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(DFNetworking::init);
    }
}
