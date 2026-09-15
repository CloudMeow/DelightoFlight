package com.cloudmeow.delightoflight.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class MoonSlashEntity extends Entity {
    private static final EntityDataAccessor<Integer> SHAPE = SynchedEntityData.defineId(MoonSlashEntity.class, EntityDataSerializers.INT);

    private Vec3 lookDirection = new Vec3(0.0, 0.0, 1.0);
    private UUID attackerId;

    public MoonSlashEntity(EntityType<?> type, Level level) {
        super(type, level);
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

    public void setLookDirection(Vec3 lookDirection) {
        this.lookDirection = lookDirection;
    }

    public void setAttacker(Player player) {
        this.attackerId = player.getUUID();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(SHAPE, 0);
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
            target.hurt(attacker.damageSources().playerAttack(attacker), 4.0F);
        }
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
