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
    public static final FoodProperties RAIN_STEW = (new FoodProperties.Builder())
            .nutrition(9).saturationMod(0.8f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 6000, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 3600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.WEATHER_SENSING.get(), 1200, 0), 1.0F).build();
    public static final FoodProperties MUSHROOM_HOTPOT = (new FoodProperties.Builder())
            .nutrition(8).saturationMod(1.1f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 100, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 100, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 1200, 0), 1.0F)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 6000, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.ARC.get(), 600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.WEATHER_SENSING.get(), 3600, 0), 1.0F).build();
    public static final FoodProperties CLOUD_BREAD = (new FoodProperties.Builder())
            .nutrition(5).saturationMod(0.7f)
            .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 200, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.WEATHER_SENSING.get(), 600, 0), 1.0F).build();
    public static final FoodProperties LOTUS_LEAF_RICE = (new FoodProperties.Builder())
            .nutrition(14).saturationMod(0.8f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 6000, 0), 1.0F).build();
    public static final FoodProperties CHICKEN_SOUP_WITH_LOTUS_SEEDS = (new FoodProperties.Builder())
            .nutrition(10).saturationMod(0.8f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 6000, 0), 1.0F).build();
    public static final FoodProperties LOTUS_ROOT_SALAD = (new FoodProperties.Builder())
            .nutrition(6).saturationMod(0.5f).build();
    public static final FoodProperties LOTUS_ROOT_SOUP_WITH_PORK_RIBS = (new FoodProperties.Builder())
            .nutrition(11).saturationMod(0.9f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 6000, 0), 1.0F).build();
    public static final FoodProperties LOTUS_SEED_CAKE = (new FoodProperties.Builder())
            .nutrition(9).saturationMod(0.6f).build();
    public static final FoodProperties LOTUS_SEED_SWEET_PORRIDGE = (new FoodProperties.Builder())
            .nutrition(8).saturationMod(0.8f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 3600, 0), 1.0F).build();
    public static final FoodProperties STUFFED_LOTUS_ROOT = (new FoodProperties.Builder())
            .nutrition(10).saturationMod(0.7f).build();
    public static final FoodProperties LOTUS_ROOT = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.5f).build();
    public static final FoodProperties LOTUS_ROOT_SLICE = (new FoodProperties.Builder())
            .nutrition(1).saturationMod(0.5f).build();

    public static final FoodProperties CHARGED_ROSE_TEA = (new FoodProperties.Builder())
            .alwaysEat().effect(() -> new MobEffectInstance(DFEffects.ARC.get(), 600, 1), 1.0F).build();
    public static final FoodProperties SPARKTRICITY_SODA = (new FoodProperties.Builder())
            .alwaysEat()
            .effect(() -> new MobEffectInstance(DFEffects.ARC.get(), 200, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.WEATHER_SENSING.get(), 600, 0), 1.0F).build();
}
