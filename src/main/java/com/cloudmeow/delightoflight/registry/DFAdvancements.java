package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.advancements.CutLotusFlowerNightTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class DFAdvancements {
    public static CutLotusFlowerNightTrigger CUT_LOTUS_FLOWER_NIGHT = new CutLotusFlowerNightTrigger();

    public static void register() {
        CriteriaTriggers.register(CUT_LOTUS_FLOWER_NIGHT);
    }
}
