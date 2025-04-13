package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.effect.ArcEffect;
import com.cloudmeow.delightoflight.effect.TurbulenceEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DFEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DelightoFlight.MOD_ID);

    public static final RegistryObject<MobEffect> TURBULENCE = EFFECTS.register("turbulence", TurbulenceEffect::new);
    public static final RegistryObject<MobEffect> ARC = EFFECTS.register("arc", ArcEffect::new);
}
