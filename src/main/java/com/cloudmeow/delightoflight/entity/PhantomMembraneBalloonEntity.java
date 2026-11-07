package com.cloudmeow.delightoflight.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PhantomMembraneBalloonEntity extends Entity {
    private static final int RISE_START_TICK = 10;
    private static final double RISE_HEIGHT = 200.0D;

    private static final EntityDataAccessor<Boolean> BAMBOO_BASKET = SynchedEntityData.defineId(PhantomMembraneBalloonEntity.class, EntityDataSerializers.BOOLEAN);

    private ItemStack storedAstralBerries = ItemStack.EMPTY;
    private ItemStack storedStardrops = ItemStack.EMPTY;
    private double launchStartY = Double.NaN;

    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private float lerpYRot;
    private float lerpXRot;
    private int lerpSteps;

    public PhantomMembraneBalloonEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(BAMBOO_BASKET, false);
    }

    public boolean isBambooBasket() {
        return this.entityData.get(BAMBOO_BASKET);
    }

    public void setBambooBasket(boolean bamboo) {
        this.entityData.set(BAMBOO_BASKET, bamboo);
    }

    public ItemStack getStoredAstralBerries() {
        return this.storedAstralBerries;
    }

    public void setStoredAstralBerries(ItemStack stack) {
        this.storedAstralBerries = stack;
    }

    public ItemStack getStoredStardrops() {
        return this.storedStardrops;
    }

    public void setStoredStardrops(ItemStack stack) {
        this.storedStardrops = stack;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.lerpSteps > 0) {
                this.lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.lerpYRot, this.lerpXRot);
                this.lerpSteps--;
            }
            return;
        }
        if (this.tickCount <= RISE_START_TICK) {
            return;
        }

        if (Double.isNaN(this.launchStartY)) {
            this.launchStartY = this.getY();
        }

        int riseTicks = this.tickCount - RISE_START_TICK - 1;
        double speed = 0.05D + 0.01D * riseTicks;
        this.setPos(this.getX(), this.getY() + speed, this.getZ());

        if (this.getY() - this.launchStartY >= RISE_HEIGHT) {
            dropGlowingItem(this.storedAstralBerries);
            dropGlowingItem(this.storedStardrops);
            this.discard();
        }
    }

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYRot = yRot;
        this.lerpXRot = xRot;
        this.lerpSteps = steps;
    }

    private void dropGlowingItem(ItemStack stack) {
        if (stack.isEmpty()) return;
        ItemEntity item = this.spawnAtLocation(stack);
        if (item != null) {
            item.setGlowingTag(true);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.setBambooBasket(compound.getBoolean("BambooBasket"));
        this.storedAstralBerries = ItemStack.parseOptional(this.level().registryAccess(), compound.getCompound("AstralBerries"));
        this.storedStardrops = ItemStack.parseOptional(this.level().registryAccess(), compound.getCompound("Stardrops"));
        if (compound.contains("LaunchStartY")) {
            this.launchStartY = compound.getDouble("LaunchStartY");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putBoolean("BambooBasket", this.isBambooBasket());
        compound.put("AstralBerries", this.storedAstralBerries.saveOptional(this.level().registryAccess()));
        compound.put("Stardrops", this.storedStardrops.saveOptional(this.level().registryAccess()));
        if (!Double.isNaN(this.launchStartY)) {
            compound.putDouble("LaunchStartY", this.launchStartY);
        }
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }
}
