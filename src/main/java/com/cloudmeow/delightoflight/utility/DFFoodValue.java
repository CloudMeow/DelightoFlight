package com.cloudmeow.delightoflight.utility;

import com.cloudmeow.delightoflight.registry.DFEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class DFFoodValue {
    public static final FoodProperties CLOUD_BERRY = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.3f).build();
    public static final FoodProperties CLOUD_BERRY_STEAMED_BUN = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.6f).build();
    public static final FoodProperties CLOUD_BERRY_PANCAKE = (new FoodProperties.Builder())
            .nutrition(9).saturationModifier(0.8f).build();
    public static final FoodProperties THUNDER_FRUIT = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.3f)
            .effect(() -> new MobEffectInstance(DFEffects.ARC, 600, 0), 1.0F).build();
    public static final FoodProperties ORLEANS_PHANTOM_WING = (new FoodProperties.Builder())
            .nutrition(14).saturationModifier(0.6f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0F).build();
    public static final FoodProperties CLOUD_BERRY_CHEESE = (new FoodProperties.Builder())
            .nutrition(12).saturationModifier(0.7f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0F).build();
    public static final FoodProperties HAM_SALAD = (new FoodProperties.Builder())
            .nutrition(11).saturationModifier(0.6f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.ARC, 1200, 0), 1.0F).build();
    public static final FoodProperties THUNDER_FRUIT_STEW = (new FoodProperties.Builder())
            .nutrition(13).saturationModifier(0.8f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.ARC, 1200, 0), 1.0F).build();
    public static final FoodProperties RAIN_STEW = (new FoodProperties.Builder())
            .nutrition(9).saturationModifier(0.8f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 3600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.WEATHER_SENSING, 1200, 0), 1.0F).build();
    public static final FoodProperties MUSHROOM_HOTPOT = (new FoodProperties.Builder())
            .nutrition(10).saturationModifier(1.1f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 100, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 100, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 1200, 0), 1.0F)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.ARC, 600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.WEATHER_SENSING, 3600, 0), 1.0F).build();
    public static final FoodProperties CLOUD_BREAD = (new FoodProperties.Builder())
            .nutrition(5).saturationModifier(0.7f)
            .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 200, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.WEATHER_SENSING, 600, 0), 1.0F).build();
    public static final FoodProperties LOTUS_LEAF_RICE = (new FoodProperties.Builder())
            .nutrition(13).saturationModifier(0.8f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000, 0), 1.0F).build();
    public static final FoodProperties CHICKEN_SOUP_WITH_LOTUS_SEEDS = (new FoodProperties.Builder())
            .nutrition(10).saturationModifier(0.8f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0F).build();
    public static final FoodProperties LOTUS_ROOT_SALAD = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.5f).build();
    public static final FoodProperties LOTUS_ROOT_SOUP_WITH_PORK_RIBS = (new FoodProperties.Builder())
            .nutrition(11).saturationModifier(0.9f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0F).build();
    public static final FoodProperties LOTUS_SEED_CAKE = (new FoodProperties.Builder())
            .nutrition(9).saturationModifier(0.6f).build();
    public static final FoodProperties LOTUS_SEED_SWEET_PORRIDGE = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.8f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 1200, 0), 1.0F).build();
    public static final FoodProperties STUFFED_LOTUS_ROOT = (new FoodProperties.Builder())
            .nutrition(10).saturationModifier(0.7f).build();
    public static final FoodProperties LOTUS_ROOT = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.5f).build();
    public static final FoodProperties LOTUS_ROOT_SLICE = (new FoodProperties.Builder())
            .nutrition(1).saturationModifier(0.5f).build();
    public static final FoodProperties MOON_RABBISH = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.7f).build();
    public static final FoodProperties STARDROP = (new FoodProperties.Builder())
            .nutrition(10).saturationModifier(1.0f).alwaysEdible().build();
    public static final FoodProperties ASTRAL_BERRY = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.3f).build();
    public static final FoodProperties MOONSHADE_NECTAR_BREAD = (new FoodProperties.Builder())
            .nutrition(11).saturationModifier(0.6f)
            .effect(() -> new MobEffectInstance(DFEffects.DIM, 4800, 2), 1.0F).build();
    public static final FoodProperties MOON_PALACE_PLATTER = (new FoodProperties.Builder())
            .nutrition(14).saturationModifier(0.6f)
            .effect(() -> new MobEffectInstance(DFEffects.GRIND_HERBS, 2400, 0), 1.0F)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0F).build();
    public static final FoodProperties GAINT_RABBISH_MOON_CAKE_SLICE = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.3f)
            .effect(() -> new MobEffectInstance(DFEffects.GRIND_HERBS, 2400, 0), 1.0F).alwaysEdible().build();
    public static final FoodProperties CELESTIAL_PUDDING_SLICE = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.5f).fast()
            .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.DIM, 2400, 5), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1.0F).build();
    public static final FoodProperties WAXING_CRESCENT_SILVERMELON_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.3f)
            .effect(() -> new MobEffectInstance(DFEffects.WAXING_CRESCENT, 600, 0), 1.0F).build();
    public static final FoodProperties WANING_CRESCENT_SILVERMELON_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.3f)
            .effect(() -> new MobEffectInstance(DFEffects.WANING_CRESCENT, 600, 0), 1.0F).build();
    public static final FoodProperties WANING_CRESCENT_JELLY_SLICE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.3f)
            .effect(() -> new MobEffectInstance(DFEffects.WANING_CRESCENT, 3600, 0), 1.0F).build();
    public static final FoodProperties WANING_CRESCENT_PASTA = (new FoodProperties.Builder())
            .nutrition(12).saturationModifier(0.9f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.WANING_CRESCENT, 6000, 0), 1.0F).build();
    public static final FoodProperties WAXING_CRESCENT_CAKE = (new FoodProperties.Builder())
            .nutrition(12).saturationModifier(0.7f)
            .effect(() -> new MobEffectInstance(DFEffects.WAXING_CRESCENT, 3600, 0), 1.0F).build();
    public static final FoodProperties WAXING_CRESCENT_EGG_SOUP = (new FoodProperties.Builder())
            .nutrition(12).saturationModifier(0.6f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.WAXING_CRESCENT, 6000, 0), 1.0F).build();

    public static final FoodProperties APPLE_COTTON_CANDY = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.4f)
            .effect(() -> new MobEffectInstance(DFEffects.CLOUDWALKING, 200, 0), 1.0F).alwaysEdible().build();
    public static final FoodProperties CHORUS_FRUIT_COTTON_CANDY = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.4f)
            .effect(() -> new MobEffectInstance(DFEffects.CLOUDWALKING, 200, 0), 1.0F).alwaysEdible().build();
    public static final FoodProperties CLOUD_BERRY_COTTON_CANDY = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.4f)
            .effect(() -> new MobEffectInstance(DFEffects.CLOUDWALKING, 200, 0), 1.0F).alwaysEdible().build();
    public static final FoodProperties ENCHANTED_GOLDEN_APPLE_COTTON_CANDY = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.4f)
            .effect(() -> new MobEffectInstance(DFEffects.CLOUDWALKING, 600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1.0F).alwaysEdible().build();
    public static final FoodProperties GLOW_BERRY_COTTON_CANDY = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.4f)
            .effect(() -> new MobEffectInstance(DFEffects.CLOUDWALKING, 200, 0), 1.0F).alwaysEdible().build();
    public static final FoodProperties GOLDEN_APPLE_COTTON_CANDY = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.4f)
            .effect(() -> new MobEffectInstance(DFEffects.CLOUDWALKING, 400, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F).alwaysEdible().build();
    public static final FoodProperties MELON_COTTON_CANDY = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.4f)
            .effect(() -> new MobEffectInstance(DFEffects.CLOUDWALKING, 200, 0), 1.0F).alwaysEdible().build();
    public static final FoodProperties SWEET_BERRY_COTTON_CANDY = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.4f)
            .effect(() -> new MobEffectInstance(DFEffects.CLOUDWALKING, 200, 0), 1.0F).alwaysEdible().build();
    public static final FoodProperties THUNDER_FRUIT_COTTON_CANDY = (new FoodProperties.Builder())
            .nutrition(5).saturationModifier(0.4f)
            .effect(() -> new MobEffectInstance(DFEffects.CLOUDWALKING, 600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.ARC, 1200, 0), 1.0F).alwaysEdible().build();
    public static final FoodProperties ASTRAL_BERRY_COTTON_CANDY = (new FoodProperties.Builder())
            .nutrition(5).saturationModifier(0.4f)
            .effect(() -> new MobEffectInstance(DFEffects.CLOUDWALKING, 600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 600, 0), 1.0F).alwaysEdible().build();

    public static final FoodProperties MOONSHADE_NECTAR = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.6f)
            .effect(() -> new MobEffectInstance(DFEffects.DIM, 4800, 0), 1.0F).build();
    public static final FoodProperties CHARGED_ROSE_TEA = (new FoodProperties.Builder())
            .alwaysEdible().effect(() -> new MobEffectInstance(DFEffects.ARC, 600, 1), 1.0F).build();
    public static final FoodProperties SPARKTRICITY_SODA = (new FoodProperties.Builder())
            .alwaysEdible()
            .effect(() -> new MobEffectInstance(DFEffects.ARC, 200, 0), 1.0F)
            .effect(() -> new MobEffectInstance(DFEffects.WEATHER_SENSING, 600, 0), 1.0F).build();
    public static final FoodProperties RABBISH_JUICE = (new FoodProperties.Builder())
            .alwaysEdible()
            .effect(() -> new MobEffectInstance(DFEffects.GRIND_HERBS, 2400, 0), 1.0F).build();
    public static final FoodProperties ASTRAL_BERRY_SMOOTHIE = (new FoodProperties.Builder())
            .nutrition(5).saturationModifier(0.6f)
            .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 0), 1.0F).build();
}
