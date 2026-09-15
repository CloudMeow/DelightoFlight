package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DFEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DelightoFlight.MOD_ID);

    public static final RegistryObject<MobEffect> TURBULENCE = EFFECTS.register("turbulence", TurbulenceEffect::new);
    public static final RegistryObject<MobEffect> ARC = EFFECTS.register("arc", ArcEffect::new);
    public static final RegistryObject<MobEffect> WEATHER_SENSING = EFFECTS.register("weather_sensing", WeatherSensingEffect::new);
    public static final RegistryObject<MobEffect> CLOUDWALKING = EFFECTS.register("cloudwalking", CloudwalkingEffect::new);
    public static final RegistryObject<MobEffect> WAXING_CRESCENT = EFFECTS.register("waxing_crescent", WaxingCrescentEffect::new);
    public static final RegistryObject<MobEffect> WANING_CRESCENT = EFFECTS.register("waning_crescent", WaningCrescentEffect::new);
    public static final RegistryObject<MobEffect> FULL_MOON = EFFECTS.register("full_moon", FullMoonEffect::new);
}
