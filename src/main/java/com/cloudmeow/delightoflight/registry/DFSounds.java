package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DFSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, DelightoFlight.MOD_ID);

    public static final Supplier<SoundEvent> SHOCK = SOUNDS.register("entity.electric_current.discharge",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "entity.electric_current.discharge")));
    public static final Supplier<SoundEvent> AEROLOPE_HURT = SOUNDS.register("entity.aerolope.hurt",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "entity.aerolope.hurt")));
    public static final Supplier<SoundEvent> AEROLOPE_AMBIENT = SOUNDS.register("entity.aerolope.ambient",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "entity.aerolope.ambient")));
    public static final Supplier<SoundEvent> AEROLOPE_DEATH = SOUNDS.register("entity.aerolope.death",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "entity.aerolope.death")));

    public static final Supplier<SoundEvent> CLEAR_HORN = SOUNDS.register("item.clear_horn",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "item.clear_horn")));
    public static final Supplier<SoundEvent> RAINY_HORN = SOUNDS.register("item.rainy_horn",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "item.rainy_horn")));
    public static final Supplier<SoundEvent> THUNDER_HORN = SOUNDS.register("item.thunder_horn",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "item.thunder_horn")));
}
