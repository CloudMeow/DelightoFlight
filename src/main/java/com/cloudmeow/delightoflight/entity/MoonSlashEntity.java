package com.cloudmeow.delightoflight.entity;

import com.cloudmeow.delightoflight.registry.DFEffects;
import com.cloudmeow.delightoflight.utility.DFDamageTypes;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class MoonSlashEntity extends Entity {
    private static final EntityDataAccessor<Integer> SHAPE = SynchedEntityData.defineId(MoonSlashEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> GRIND_HERBS = SynchedEntityData.defineId(MoonSlashEntity.class, EntityDataSerializers.BOOLEAN);

    private Vec3 lookDirection = new Vec3(0.0, 0.0, 1.0);
    private UUID attackerId;

    public MoonSlashEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(SHAPE, 0);
        this.entityData.define(GRIND_HERBS, false);
    }

    public boolean isFullMoon() {
        return this.entityData.get(SHAPE) == 0;
    }

    public boolean isRight() {
        return this.entityData.get(SHAPE) == 1;
    }

    public int getShape() {
        return this.entityData.get(SHAPE);
    }

    public void setShape(int shape) {
        this.entityData.set(SHAPE, shape);
    }

    public boolean hasGrindHerbs() {
        return this.entityData.get(GRIND_HERBS);
    }

    public void setGrindHerbs(boolean grindHerbs) {
        this.entityData.set(GRIND_HERBS, grindHerbs);
    }

    public void setLookDirection(Vec3 lookDirection) {
        this.lookDirection = lookDirection;
    }

    public void setAttacker(Player player) {
        this.attackerId = player.getUUID();
        this.setGrindHerbs(DFUtilities.hasGrindHerbs(player));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount == 8) {
            applyDamage();
        }
        if (this.tickCount >= 12) {
            this.discard();
        }
    }

    private void applyDamage() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        Player attacker = attackerId == null ? null : serverLevel.getServer().getPlayerList().getPlayer(attackerId);
        int shape = getShape();
        float radius = (shape == 0) ? 3.0F : 2.0F;

        Vec3 hLook = new Vec3(lookDirection.x, 0.0, lookDirection.z).normalize();
        for (LivingEntity target : serverLevel.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(radius))) {
            if (attacker == null || target.getUUID().equals(attacker.getUUID())) continue;
            Vec3 to = target.position().subtract(this.getX(), this.getY(), this.getZ());
            double dist = Math.sqrt(to.x * to.x + to.z * to.z);
            if (dist > radius) continue;
            if (shape != 0) {
                double cross = hLook.x * to.z - hLook.z * to.x;
                if (shape == 1) {
                    if (cross < 0) continue;
                } else {
                    if (cross > 0) continue;
                }
            }
            target.hurt(getDamageSource(attacker), getEffectAmplifier(attacker));
        }
    }

    public DamageSource getDamageSource(Player attacker) {
        return DFUtilities.hasGrindHerbs(attacker) ? attacker.damageSources().source(DFDamageTypes.MOONLIGHT_ATTACK) : attacker.damageSources().playerAttack(attacker);
    }

    public float getEffectAmplifier(Player attacker) {
        return isFullMoon() ? 4.0F * (attacker.getEffect(DFEffects.FULL_MOON.get()).getAmplifier() + 1) : 3.0F * (isRight() ? (attacker.getEffect(DFEffects.WAXING_CRESCENT.get()).getAmplifier() + 1) : (attacker.getEffect(DFEffects.WANING_CRESCENT.get()).getAmplifier() + 1));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
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
