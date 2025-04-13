package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.effect.ArcEffect;
import com.cloudmeow.delightoflight.effect.TurbulenceEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DFEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, DelightoFlight.MOD_ID);

    public static final Holder<MobEffect> TURBULENCE = EFFECTS.register("turbulence", TurbulenceEffect::new);
    public static final Holder<MobEffect> ARC = EFFECTS.register("arc", ArcEffect::new);
}
