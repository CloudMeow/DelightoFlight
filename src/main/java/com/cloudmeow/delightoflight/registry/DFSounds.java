package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DFSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DelightoFlight.MOD_ID);

    public static final RegistryObject<SoundEvent> SHOCK = SOUNDS.register("entity.electric_current.discharge",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DelightoFlight.MOD_ID, "entity.electric_current.discharge")));
}
