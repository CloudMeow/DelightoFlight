package com.cloudmeow.delightoflight.entity.ai.aerolope;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.function.ToDoubleFunction;

public class RamGoat<E extends LivingEntity> extends Behavior<E> {
    private final UniformInt getTimeBetweenRams;
    private final TargetingConditions ramTargeting;
    private final float speed;
    private final ToDoubleFunction<E> getKnockbackForce;
    private Vec3 ramDirection;
    private final SoundEvent getImpactSound;

    public RamGoat(UniformInt cooldown, TargetingConditions conditions, float speed, ToDoubleFunction<E> force, SoundEvent soundEvent) {
        super(ImmutableMap.of(MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT, MemoryModuleType.RAM_TARGET, MemoryStatus.VALUE_PRESENT), 200);
        this.getTimeBetweenRams = cooldown;
        this.ramTargeting = conditions;
        this.speed = speed;
        this.getKnockbackForce = force;
        this.getImpactSound = soundEvent;
        this.ramDirection = Vec3.ZERO;
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, E entity) {
        return entity.getBrain().hasMemoryValue(MemoryModuleType.RAM_TARGET);
    }

    protected boolean canStillUse(ServerLevel serverLevel, E entity, long p_217354_) {
        return entity.getBrain().hasMemoryValue(MemoryModuleType.RAM_TARGET);
    }

    protected void start(ServerLevel serverLevel, E entity, long p_217361_) {
        BlockPos blockpos = entity.blockPosition();
        Brain<?> brain = entity.getBrain();
        Vec3 vec3 = brain.getMemory(MemoryModuleType.RAM_TARGET).get();
        this.ramDirection = (new Vec3((double)blockpos.getX() - vec3.x(), 0.0D, (double)blockpos.getZ() - vec3.z())).normalize();
        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(vec3, this.speed, 0));
    }

    protected void tick(ServerLevel level, E entity, long p_217368_) {
        List<LivingEntity> list = level.getNearbyEntities(LivingEntity.class, this.ramTargeting, entity, entity.getBoundingBox());
        Brain<?> brain = entity.getBrain();
        if (!list.isEmpty()) {
            LivingEntity livingentity = list.get(0);
            livingentity.hurt(level.damageSources().noAggroMobAttack(entity), (float)entity.getAttributeValue(Attributes.ATTACK_DAMAGE));
            int i = entity.hasEffect(MobEffects.MOVEMENT_SPEED) ? entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
            int j = entity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? entity.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
            float f = 0.25F * (float)(i - j);
            float f1 = Mth.clamp(entity.getSpeed() * 1.65F, 0.2F, 3.0F) + f;
            float f2 = livingentity.isDamageSourceBlocked(level.damageSources().mobAttack(entity)) ? 0.5F : 1.0F;
            livingentity.knockback((double)(f2 * f1) * this.getKnockbackForce.applyAsDouble(entity), this.ramDirection.x(), this.ramDirection.z());
            this.finishRam(level, entity);
            level.playSound((Player)null, entity, this.getImpactSound, SoundSource.NEUTRAL, 1.0F, 1.0F);
        } else {
            Optional<WalkTarget> optional = brain.getMemory(MemoryModuleType.WALK_TARGET);
            Optional<Vec3> optional1 = brain.getMemory(MemoryModuleType.RAM_TARGET);
            boolean flag1 = optional.isEmpty() || optional1.isEmpty() || optional.get().getTarget().currentPosition().closerThan(optional1.get(), 0.25D);
            if (flag1) {
                this.finishRam(level, entity);
            }
        }
    }

    protected void finishRam(ServerLevel level, E entity) {
        level.broadcastEntityEvent(entity, (byte)59);
        entity.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, this.getTimeBetweenRams.sample(level.random));
        entity.getBrain().eraseMemory(MemoryModuleType.RAM_TARGET);
    }
}
