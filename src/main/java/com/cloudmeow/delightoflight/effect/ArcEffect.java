package com.cloudmeow.delightoflight.effect;

import com.cloudmeow.delightoflight.entity.ElectricCurrent;
import com.cloudmeow.delightoflight.registry.DFEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class ArcEffect extends MobEffect {
    public ArcEffect() {
        super(MobEffectCategory.BENEFICIAL, 14591935);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide && entity.getHealth() > 0) {
            CompoundTag entityData = entity.getPersistentData();
            if (!entityData.getBoolean("discharge")) {
                ElectricCurrent electricCurrent = new ElectricCurrent(entity.level(), entity, amplifier);
                electricCurrent.setPos(entity.position());
                entity.level().addFreshEntity(electricCurrent);
                entityData.putBoolean("discharge", true);
            }
            int duration = Objects.requireNonNull(entity.getEffect(DFEffects.ARC.get())).getDuration();
            if(duration == 1) {
                entityData.putBoolean("discharge", false);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
