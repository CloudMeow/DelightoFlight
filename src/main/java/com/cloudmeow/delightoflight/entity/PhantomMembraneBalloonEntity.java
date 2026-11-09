package com.cloudmeow.delightoflight.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class PhantomMembraneBalloonEntity extends Entity {
    private static final int RISE_START_TICK = 10;
    private static final double RISE_HEIGHT = 200.0D;

    private static final EntityDataAccessor<Boolean> BAMBOO_BASKET = SynchedEntityData.defineId(PhantomMembraneBalloonEntity.class, EntityDataSerializers.BOOLEAN);

    private final List<ItemStack> storedProducts = new ArrayList<>();
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
    protected void defineSynchedData() {
        this.entityData.define(BAMBOO_BASKET, false);
    }

    public boolean isBambooBasket() {
        return this.entityData.get(BAMBOO_BASKET);
    }

    public void setBambooBasket(boolean bamboo) {
        this.entityData.set(BAMBOO_BASKET, bamboo);
    }

    public void addStoredProduct(ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        for (ItemStack stored : this.storedProducts) {
            if (ItemStack.isSameItemSameTags(stored, stack)) {
                stored.grow(stack.getCount());
                return;
            }
        }

        this.storedProducts.add(stack.copy());
    }

    public List<ItemStack> getStoredProducts() {
        return this.storedProducts;
    }

    public float getRiseAge(float partialTick) {
        return Math.max(0.0F, (float) this.tickCount + partialTick - (float) RISE_START_TICK);
    }

    public boolean isRising() {
        return this.tickCount > RISE_START_TICK;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.lerpSteps > 0) {
                double delta = 1.0D / (double) this.lerpSteps;
                double x = Mth.lerp(delta, this.getX(), this.lerpX);
                double y = Mth.lerp(delta, this.getY(), this.lerpY);
                double z = Mth.lerp(delta, this.getZ(), this.lerpZ);
                float yRot = Mth.rotLerp((float) delta, this.getYRot(), this.lerpYRot);
                float xRot = Mth.lerp((float) delta, this.getXRot(), this.lerpXRot);
                this.setPos(x, y, z);
                this.setRot(yRot, xRot);
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
            for (ItemStack stack : this.storedProducts) {
                this.dropGlowingItem(stack);
            }
            this.discard();
        }
    }

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps, boolean flag) {
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
        this.storedProducts.clear();
        if (compound.contains("Products")) {
            ListTag list = compound.getList("Products", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                ItemStack stack = ItemStack.of(list.getCompound(i));
                if (!stack.isEmpty()) {
                    this.storedProducts.add(stack);
                }
            }
        }
        if (compound.contains("LaunchStartY")) {
            this.launchStartY = compound.getDouble("LaunchStartY");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putBoolean("BambooBasket", this.isBambooBasket());
        ListTag list = new ListTag();
        for (ItemStack stack : this.storedProducts) {
            if (stack.isEmpty()) {
                continue;
            }
            CompoundTag itemTag = new CompoundTag();
            stack.save(itemTag);
            list.add(itemTag);
        }
        compound.put("Products", list);
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
