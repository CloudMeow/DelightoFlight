package com.cloudmeow.delightoflight.entity;

import com.cloudmeow.delightoflight.Config;
import com.cloudmeow.delightoflight.registry.DFEffects;
import com.cloudmeow.delightoflight.registry.DFEntityTypes;
import com.cloudmeow.delightoflight.registry.DFSounds;
import com.cloudmeow.delightoflight.utility.DFDamageTypes;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class ElectricCurrentEntity extends Projectile {
    private int soundCoolDown = 0;

    public ElectricCurrentEntity(EntityType<? extends ElectricCurrentEntity> type, Level level) {
        super(type, level);
    }

    public ElectricCurrentEntity(LivingEntity owner) {
        super(DFEntityTypes.ELECTRIC_CURRENT.get(), owner.level());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) return;
        var owner = (LivingEntity)this.getOwner();
        if(owner != null) {
            this.setPos(owner.getEyePosition(1.0f).subtract(0, 0.8, 0));
            if(owner.getEffect(DFEffects.ARC) != null) {
                if(this.level() instanceof ServerLevel) {
                    for (LivingEntity living : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(10))) {
                        if (canBeHurt(living, owner)) {
                            living.hurt(living.damageSources().source(DFDamageTypes.SHOCK), Config.THUNDER_DAMAGE.get() + owner.getEffect(DFEffects.ARC).getAmplifier());
                            if (soundCoolDown <= 0) {
                                this.level().playSound(null, this.blockPosition(), DFSounds.SHOCK.get(), SoundSource.PLAYERS, 0.8F, 0.8F);
                                soundCoolDown = 20;
                            }
                        }
                    }
                }
                if (soundCoolDown > 0) {
                    soundCoolDown--;
                }
            } else {
                this.discard();
            }
        }else {
            this.discard();
        }
    }

    public boolean canBeHurt(LivingEntity living, LivingEntity owner) {
        return (DFUtilities.isConductive(owner) || canBeHurt(living));
    }

    public boolean canBeHurt(LivingEntity living) {
        return (Config.HURT_PASSIVE_MOBS.get() || !living.getType().getCategory().isFriendly()) && !living.getUUID().equals(this.getUUID()) && living.distanceTo(this) - living.getBbWidth() / 2 < 7;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isInvisible() {
        return true;
    }
}
