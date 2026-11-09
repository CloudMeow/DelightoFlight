package com.cloudmeow.delightoflight.entity;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
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

    private final NonNullList<ItemStack> storedProducts = NonNullList.create();
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

    public boolean isRising() {
        return this.tickCount > RISE_START_TICK;
    }

    public float getRiseAge(float partialTick) {
        return Math.max(0.0F, this.tickCount + partialTick - RISE_START_TICK - 1.0F);
    }

    public NonNullList<ItemStack> getStoredProducts() {
        return this.storedProducts;
    }

    public void addStoredProduct(ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        for (ItemStack stored : this.storedProducts) {
            if (ItemStack.isSameItemSameComponents(stored, stack)) {
                stored.grow(stack.getCount());
                return;
            }
        }

        this.storedProducts.add(stack.copy());
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
            for (ItemStack stack : this.storedProducts) {
                this.dropGlowingItem(stack.copy());
            }
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
        this.storedProducts.clear();

        if (compound.contains("Products")) {
            ListTag products = compound.getList("Products", Tag.TAG_COMPOUND);
            for (int i = 0; i < products.size(); i++) {
                CompoundTag entry = products.getCompound(i);
                this.addStoredProduct(ItemStack.parseOptional(this.level().registryAccess(), entry.getCompound("Item")));
            }
        }

        if (compound.contains("LaunchStartY")) {
            this.launchStartY = compound.getDouble("LaunchStartY");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putBoolean("BambooBasket", this.isBambooBasket());

        ListTag products = new ListTag();
        for (ItemStack stack : this.storedProducts) {
            if (stack.isEmpty()) {
                continue;
            }
            CompoundTag entry = new CompoundTag();
            entry.put("Item", stack.saveOptional(this.level().registryAccess()));
            products.add(entry);
        }
        compound.put("Products", products);

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
