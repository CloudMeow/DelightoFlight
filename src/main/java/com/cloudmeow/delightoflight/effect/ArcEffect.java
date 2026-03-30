package com.cloudmeow.delightoflight.effect;

import com.cloudmeow.delightoflight.entity.ElectricCurrent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ArcEffect extends MobEffect {
    public ArcEffect() {
        super(MobEffectCategory.BENEFICIAL, 14591935);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide && entity.getHealth() > 0) {
            boolean hasArc = false;
            for (ElectricCurrent arc : entity.level().getEntitiesOfClass(ElectricCurrent.class, entity.getBoundingBox().inflate(20))) {
                if (arc.getOwner() != null && arc.getOwner().getUUID().equals(entity.getUUID())) {
                    hasArc = true;
                    break;
                }
            }
            if (!hasArc) {
                ElectricCurrent electricCurrent = new ElectricCurrent(entity);
                electricCurrent.setPos(entity.position());
                electricCurrent.setOwner(entity);
                entity.level().addFreshEntity(electricCurrent);
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}