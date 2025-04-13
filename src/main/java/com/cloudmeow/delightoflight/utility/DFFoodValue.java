package com.cloudmeow.delightoflight.utility;

import com.cloudmeow.delightoflight.registry.DFEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class DFFoodValue {
    public static final FoodProperties CLOUD_BERRY = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.3f).build();
    public static final FoodProperties CLOUD_BERRY_STEAMED_BUN = (new FoodProperties.Builder())
            .nutrition(6).saturationMod(0.6f).build();
    public static final FoodProperties CLOUD_BERRY_PANCAKE = (new FoodProperties.Builder())
            .nutrition(9).saturationMod(0.8f).build();
    public static final FoodProperties THUNDER_FRUIT = (new FoodProperties.Builder())
            .nutrition(3).saturationMod(0.3f)
            .effect(() -> new MobEffectInstance(DFEffects.ARC.get(), 600, 0), 1.0F).build();
    public static final FoodProperties ORLEANS_PHANTOM_WING = (new FoodProperties.Builder())
            .nutrition(14).saturationMod(0.6f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 6000, 0), 1.0F).build();
    public static final FoodProperties CLOUD_BERRY_CHEESE = (new FoodProperties.Builder())
            .nutrition(12).saturationMod(0.7f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3600, 0), 1.0F).build();
    public static final FoodProperties HAM_SALAD = (new FoodProperties.Builder())
            .nutrition(11).saturationMod(0.6f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.ARC.get(), 1200, 0), 1.0F).build();
    public static final FoodProperties THUNDER_FRUIT_STEW = (new FoodProperties.Builder())
            .nutrition(13).saturationMod(0.8f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 6000, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.ARC.get(), 1200, 0), 1.0F).build();

    public static final FoodProperties CHARGED_ROSE_TEA = (new FoodProperties.Builder())
            .alwaysEat().effect(() -> new MobEffectInstance(DFEffects.ARC.get(), 600, 1), 1.0F).build();
}
