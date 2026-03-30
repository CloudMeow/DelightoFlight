package com.cloudmeow.delightoflight.entity.ai.aerolope;

import com.cloudmeow.delightoflight.entity.AerolopeEntity;
import com.cloudmeow.delightoflight.registry.DFEntityTypes;
import com.cloudmeow.delightoflight.registry.DFItems;
import com.cloudmeow.delightoflight.registry.DFMemoryTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class AerolopeAi {
    private static final UniformInt TIME_BETWEEN_RAMS = UniformInt.of(400, 800);
    private static final TargetingConditions RAM_TARGET_CONDITIONS = TargetingConditions.forCombat().selector((entity) -> {
        return !entity.getType().equals(DFEntityTypes.AEROLOPE.get()) && entity.level().getWorldBorder().isWithinBounds(entity.getBoundingBox());
    });

    public static void initMemories(AerolopeEntity aerolope, RandomSource source) {
        aerolope.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, TIME_BETWEEN_RAMS.sample(source));
    }

    public static Brain<?> makeBrain(Brain<AerolopeEntity> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initRamActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(Brain<AerolopeEntity> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(new Swim(1.0F), new AnimalPanic(2.0F), new LookAtTargetSink(45, 90), new MoveToTargetSink(), new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS), new CountDownCooldownTicks(MemoryModuleType.RAM_COOLDOWN_TICKS), new CountDownCooldownTicks(DFMemoryTypes.GROW_HORN_TICKS.get()), new CountDownCooldownTicks(DFMemoryTypes.SHAKE_HEAD_TICKS.get())));
    }

    private static void initIdleActivity(Brain<AerolopeEntity> brain) {
        brain.addActivityWithConditions(Activity.IDLE, ImmutableList.of(Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))), Pair.of(0, new AnimalMakeLove(DFEntityTypes.AEROLOPE.get(), 1.0F)), Pair.of(1, new FollowTemptation((p_149446_) -> {
            return 1.25F;
        })), Pair.of(3, new RunOne<>(ImmutableList.of(Pair.of(RandomStroll.stroll(1.0F), 2), Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2), Pair.of(new DoNothing(30, 60), 1))))), ImmutableSet.of(Pair.of(MemoryModuleType.RAM_TARGET, MemoryStatus.VALUE_ABSENT)));
    }

    private static void initRamActivity(Brain<AerolopeEntity> brain) {
        brain.addActivityWithConditions(Activity.RAM, ImmutableList.of(Pair.of(0, new RamGoat<>(TIME_BETWEEN_RAMS, RAM_TARGET_CONDITIONS, 3.0F, (aerolope) -> {
            return aerolope.isBaby() ? 1.5D : 4.0D;
        }, SoundEvents.GOAT_RAM_IMPACT)), Pair.of(1, new PrepareRamNearestGoat<>((p_218770_) -> {
            return TIME_BETWEEN_RAMS.getMinValue();
        }, 2, 12, 1.25F, RAM_TARGET_CONDITIONS, 20, (p_218768_) -> {
            return SoundEvents.GOAT_PREPARE_RAM;
        }))), ImmutableSet.of(Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT), Pair.of(DFMemoryTypes.NEAREST_GOAT.get(), MemoryStatus.VALUE_PRESENT)));
    }

    public static void updateActivity(AerolopeEntity aerolope) {
        aerolope.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.RAM, Activity.IDLE));
    }

    public static Ingredient getTemptations() {
        return Ingredient.of(DFItems.CLEAR_CLOUDSHROOM.get(), DFItems.RAINY_CLOUDSHROOM.get(), DFItems.THUNDER_CLOUDSHROOM.get(), DFItems.CLOUD_BERRY.get(), Items.WHEAT);
    }
}
