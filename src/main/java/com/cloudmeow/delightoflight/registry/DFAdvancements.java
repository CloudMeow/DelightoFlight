package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.advancement.CutLotusFlowerNightTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DFAdvancements {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, DelightoFlight.MOD_ID);

    public static final Supplier<CutLotusFlowerNightTrigger> CUT_LOTUS_FLOWER_NIGHT = TRIGGERS.register("cut_lotus_flower_night", CutLotusFlowerNightTrigger::new);
}
