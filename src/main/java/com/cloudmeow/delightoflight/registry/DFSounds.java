package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class DFSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DelightoFlight.MOD_ID);

    public static final RegistryObject<SoundEvent> SHOCK = SOUNDS.register("entity.electric_current.discharge",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DelightoFlight.MOD_ID, "entity.electric_current.discharge")));
    public static final RegistryObject<SoundEvent> AEROLOPE_HURT = SOUNDS.register("entity.aerolope.hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DelightoFlight.MOD_ID, "entity.aerolope.hurt")));
    public static final RegistryObject<SoundEvent> AEROLOPE_AMBIENT = SOUNDS.register("entity.aerolope.ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DelightoFlight.MOD_ID, "entity.aerolope.ambient")));
    public static final RegistryObject<SoundEvent> AEROLOPE_DEATH = SOUNDS.register("entity.aerolope.death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DelightoFlight.MOD_ID, "entity.aerolope.death")));
    public static final Supplier<SoundEvent> MOON_SLASH = SOUNDS.register("entity.moon_slash.slash",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DelightoFlight.MOD_ID, "entity.moon_slash.slash")));

    public static final RegistryObject<SoundEvent> CLEAR_HORN = SOUNDS.register("item.clear_horn",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DelightoFlight.MOD_ID, "item.clear_horn")));
    public static final RegistryObject<SoundEvent> RAINY_HORN = SOUNDS.register("item.rainy_horn",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DelightoFlight.MOD_ID, "item.rainy_horn")));
    public static final RegistryObject<SoundEvent> THUNDER_HORN = SOUNDS.register("item.thunder_horn",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DelightoFlight.MOD_ID, "item.thunder_horn")));

    public static final RegistryObject<SoundEvent> COTTON_CANDY_MACHINE_WORK = SOUNDS.register("block.cotton_candy_machine.work",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DelightoFlight.MOD_ID, "block.cotton_candy_machine.work")));
    public static final Supplier<SoundEvent> COTTON_CANDY_MACHINE_PLACE = SOUNDS.register("block.cotton_candy_machine.place",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DelightoFlight.MOD_ID, "block.cotton_candy_machine.place")));
    public static final Supplier<SoundEvent> COTTON_CANDY_MACHINE_REMOVE = SOUNDS.register("block.cotton_candy_machine.remove",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DelightoFlight.MOD_ID, "block.cotton_candy_machine.remove")));

    public static final Supplier<SoundEvent> MOONLIGHT_TRANSFORMATION = SOUNDS.register("misc.moonlight_transformation.craft",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DelightoFlight.MOD_ID, "misc.moonlight_transformation.craft")));
}
