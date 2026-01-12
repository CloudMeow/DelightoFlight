package com.cloudmeow.delightoflight.compat.thirst;

import com.cloudmeow.delightoflight.registry.DFItems;
import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;

public class ThirstCompat {
    public static void init() {
        NeoForge.EVENT_BUS.register(ThirstCompat.class);
    }

    @SubscribeEvent
    public static void compat(RegisterThirstValueEvent event) {
        event.addFood(DFItems.CLOUD_BERRY.get(), 2, 2);
        event.addFood(DFItems.THUNDER_FRUIT.get(), 4, 3);
        event.addFood(DFItems.HAM_SALAD.get(), 4, 3);
        event.addFood(DFItems.THUNDER_FRUIT_STEW.get(), 4, 3);
        event.addFood(DFItems.RAIN_STEW.get(), 6, 6);
        event.addFood(DFItems.MUSHROOM_HOTPOT.get(), 4, 2);
        event.addFood(DFItems.CHICKEN_SOUP_WITH_LOTUS_SEEDS.get(), 4, 5);
        event.addFood(DFItems.LOTUS_ROOT_SOUP_WITH_PORK_RIBS.get(), 4, 5);
        event.addFood(DFItems.LOTUS_SEED_SWEET_PORRIDGE.get(), 2, 3);
        event.addFood(DFItems.CHARGED_ROSE_TEA.get(), 10, 14);
        event.addFood(DFItems.SPARKTRICITY_SODA.get(), 8, 10);
    }
}
