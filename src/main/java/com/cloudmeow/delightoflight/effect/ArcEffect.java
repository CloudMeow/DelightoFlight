package com.cloudmeow.delightoflight.effect;

import com.cloudmeow.delightoflight.entity.ElectricCurrentEntity;
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
            for (ElectricCurrentEntity arc : entity.level().getEntitiesOfClass(ElectricCurrentEntity.class, entity.getBoundingBox().inflate(20))) {
                if (arc.getOwner() != null && arc.getOwner().getUUID().equals(entity.getUUID())) {
                    hasArc = true;
                    break;
                }
            }
            if (!hasArc) {
                ElectricCurrentEntity electricCurrentEntity = new ElectricCurrentEntity(entity);
                electricCurrentEntity.setPos(entity.position());
                electricCurrentEntity.setOwner(entity);
                entity.level().addFreshEntity(electricCurrentEntity);
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}