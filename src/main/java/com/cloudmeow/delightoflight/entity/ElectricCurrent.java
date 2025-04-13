package com.cloudmeow.delightoflight.entity;

import com.cloudmeow.delightoflight.Config;
import com.cloudmeow.delightoflight.registry.DFEffects;
import com.cloudmeow.delightoflight.registry.DFEntityTypes;
import com.cloudmeow.delightoflight.registry.DFSounds;
import com.cloudmeow.delightoflight.utility.DFDamageTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class ElectricCurrent extends Projectile {
    private int soundCoolDown = 0;
    private int power = 0;

    public ElectricCurrent(EntityType<? extends ElectricCurrent> type, Level level) {
        super(type, level);
    }

    public ElectricCurrent(Level level, LivingEntity shooter, int power) {
        super(DFEntityTypes.ELECTRIC_CURRENT.get(), level);
        this.setOwner(shooter);
        this.power = power;
    }

    @Override
    public void tick() {
        super.tick();
        var owner = (LivingEntity)this.getOwner();
        if(owner != null) {
            this.setPos(owner.getEyePosition(1.0f).subtract(0, 0.8, 0));
            if(owner.getEffect(DFEffects.ARC.get()) == null) {
                this.discard();
            }
        }
        if(this.level() instanceof ServerLevel) {
            for (LivingEntity living : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(10))) {
                if (living != owner && !living.getUUID().equals(this.getUUID()) && living.distanceTo(this) - living.getBbWidth() / 2 < 7) {
                    living.hurt(living.damageSources().source(DFDamageTypes.SHOCK), Config.THUNDER_DAMAGE.get() + power);
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
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("power", this.power);
        super.addAdditionalSaveData(compound);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.power = compound.getInt("power");
        super.readAdditionalSaveData(compound);
    }

    @Override
    public boolean isInvisible() {
        return true;
    }
}
