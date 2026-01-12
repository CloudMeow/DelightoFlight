package com.cloudmeow.delightoflight.entity;

import com.cloudmeow.delightoflight.entity.ai.aerolope.AerolopeAi;
import com.cloudmeow.delightoflight.registry.*;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AerolopeEntity extends Animal {
    public AnimationState shakeHeadAnimationState = new AnimationState();
    private ItemStack mushroom;
    private static final EntityDataAccessor<Integer> DELAY = SynchedEntityData.defineId(AerolopeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SHAKE = SynchedEntityData.defineId(AerolopeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HAS_LEFT_HORN = SynchedEntityData.defineId(AerolopeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HAS_RIGHT_HORN = SynchedEntityData.defineId(AerolopeEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final ImmutableList<SensorType<? extends Sensor<? super AerolopeEntity>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_ITEMS,
            SensorType.NEAREST_ADULT,
            SensorType.HURT_BY,
            DFSensorType.AEROLOPE_TEMPTATIONS.get(),
            DFSensorType.NEAREST_GOATS.get());
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ATE_RECENTLY,
            MemoryModuleType.BREED_TARGET,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ADULT,
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.RAM_COOLDOWN_TICKS,
            MemoryModuleType.RAM_TARGET,
            MemoryModuleType.IS_PANICKING,
            DFMemoryTypes.SHAKE_HEAD_TICKS.get(),
            DFMemoryTypes.GROW_HORN_TICKS.get());

    public static boolean checkAerolopeSpawnRules(EntityType<AerolopeEntity> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        BlockState stateBelow = level.getBlockState(pos.below());
        return stateBelow.is(BlockTags.GOATS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    public AerolopeEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.mushroom = DFItems.CLEAR_CLOUDSHROOM.get().getDefaultInstance();
        this.getNavigation().setCanFloat(true);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
    }

    @Nullable
    @Override
    public AerolopeEntity getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
        return DFEntityTypes.AEROLOPE.get().create(level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.MAX_HEALTH, 16)
                .add(Attributes.ATTACK_DAMAGE, 6)
                .add(Attributes.FOLLOW_RANGE, 6);
    }

    @Override
    protected void ageBoundaryReached() {
        if (this.isBaby()) {
            this.removeHorns();
        } else {
            this.addHorns();
        }
    }

    @Override
    protected Brain.Provider<AerolopeEntity> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return AerolopeAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public Brain<AerolopeEntity> getBrain() {
        return (Brain<AerolopeEntity>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("aerolopeBrain");
        this.getBrain().tick((ServerLevel)this.level(), this);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("aerolopeActivityUpdate");
        AerolopeAi.updateActivity(this);
        this.level().getProfiler().pop();
        if (this.isShaking()) {
            this.getNavigation().stop();
            this.getLookControl().setLookAt(this.getX(), this.getEyeY(), this.getZ());
        }
        super.customServerAiStep();
    }

    @Override
    public void tick() {
        if (this.getBrain().getMemory(DFMemoryTypes.SHAKE_HEAD_TICKS.get()).isEmpty()) {
            shakeHead(false);
        }
        if (hasLeftHorn() && hasRightHorn()) {
            this.getBrain().setMemory(DFMemoryTypes.GROW_HORN_TICKS.get(), -1);
        }
        if (!this.isBaby() && this.getBrain().getMemory(DFMemoryTypes.GROW_HORN_TICKS.get()).isEmpty() && (!hasLeftHorn() || !hasRightHorn())) {
            growHorn();
        }
        if (!this.level().isClientSide) {
            int delayTime = this.entityData.get(DELAY);
            if (delayTime > 0) {
                this.entityData.set(DELAY, delayTime - 1);
            }
            if (this.entityData.get(DELAY) == 0) {
                dropHorn(getMushroom());
                this.entityData.set(DELAY, -1);
            }
        }
        super.tick();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!this.level().isClientSide) {
            boolean hasHorn = this.hasLeftHorn() || this.hasRightHorn();
            if (hasHorn && level().getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE) && !this.entityData.get(SHAKE)) {
                if (isWeatherMushroom(stack)) {
                    this.entityData.set(DELAY, 20);
                    this.mushroom = stack.copy();
                    shakeHead(true);
                    resetShakeCooldown();
                    if (!player.isCreative()) stack.shrink(1);
                    this.shakeHeadAnimationState.start(this.tickCount);
                    player.level().playSound(null, player.blockPosition(), SoundEvents.GOAT_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    public void resetShakeCooldown() {
        this.getBrain().setMemory(DFMemoryTypes.SHAKE_HEAD_TICKS.get(), 40);
        this.getBrain().eraseMemory(MemoryModuleType.RAM_TARGET);
    }

    public boolean isWeatherMushroom(ItemStack stack) {
        return stack.is(DFItems.CLEAR_CLOUDSHROOM.get()) || stack.is(DFItems.RAINY_CLOUDSHROOM.get()) || stack.is(DFItems.THUNDER_CLOUDSHROOM.get()) || stack.is(DFItems.COPPER_THUNDER_FRUIT.get());
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.WHEAT);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (damageSource.getEntity() instanceof Goat) {
            return false;
        }
        return super.hurt(damageSource, amount);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_149387_) {
        return DFSounds.AEROLOPE_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return DFSounds.AEROLOPE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return DFSounds.AEROLOPE_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.GOAT_STEP, 0.15F, 1.0F);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficultyInstance, MobSpawnType type, @Nullable SpawnGroupData data) {
        RandomSource randomsource = accessor.getRandom();
        AerolopeAi.initMemories(this, randomsource);
        this.entityData.set(DELAY, -1);
        this.ageBoundaryReached();
        return super.finalizeSpawn(accessor, difficultyInstance, type, data);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Mushroom", this.mushroom.save(this.registryAccess()));
        tag.putInt("Delay", this.getShakeHeadDelay());
        tag.putBoolean("Shaking", this.isShaking());
        tag.putBoolean("HasLeftHorn", this.hasLeftHorn());
        tag.putBoolean("HasRightHorn", this.hasRightHorn());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.mushroom = ItemStack.parse(this.registryAccess(), tag.getCompound("Mushroom")).orElse(DFItems.CLEAR_CLOUDSHROOM.get().getDefaultInstance());
        this.entityData.set(DELAY, tag.getInt("Delay"));
        this.entityData.set(SHAKE, tag.getBoolean("Shaking"));
        this.entityData.set(DATA_HAS_LEFT_HORN, tag.getBoolean("HasLeftHorn"));
        this.entityData.set(DATA_HAS_RIGHT_HORN, tag.getBoolean("HasRightHorn"));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (SHAKE.equals(accessor)) {
            if (isShaking()) {
                this.shakeHeadAnimationState.start(this.tickCount);
            };
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DELAY, -1);
        builder.define(SHAKE, false);
        builder.define(DATA_HAS_LEFT_HORN, true);
        builder.define(DATA_HAS_RIGHT_HORN, true);
    }

    public ItemStack getMushroom() {
        return this.mushroom;
    }

    public int getShakeHeadDelay() {
        return this.entityData.get(DELAY);
    }

    public void shakeHead(boolean state) {
        this.entityData.set(SHAKE, state);
    }

    public boolean isShaking() {
        return this.entityData.get(SHAKE);
    }

    public boolean hasLeftHorn() {
        return this.entityData.get(DATA_HAS_LEFT_HORN);
    }

    public boolean hasRightHorn() {
        return this.entityData.get(DATA_HAS_RIGHT_HORN);
    }

    public void addHorns() {
        this.entityData.set(DATA_HAS_LEFT_HORN, true);
        this.entityData.set(DATA_HAS_RIGHT_HORN, true);
    }

    public void removeHorns() {
        this.entityData.set(DATA_HAS_LEFT_HORN, false);
        this.entityData.set(DATA_HAS_RIGHT_HORN, false);
    }

    public boolean dropHorn(ItemStack mushroom) {
        boolean flag = this.hasLeftHorn();
        boolean flag1 = this.hasRightHorn();
        if (!flag && !flag1) {
            return false;
        } else {
            EntityDataAccessor<Boolean> entitydataaccessor;
            if (!flag) {
                entitydataaccessor = DATA_HAS_RIGHT_HORN;
            } else if (!flag1) {
                entitydataaccessor = DATA_HAS_LEFT_HORN;
            } else {
                entitydataaccessor = this.random.nextBoolean() ? DATA_HAS_LEFT_HORN : DATA_HAS_RIGHT_HORN;
            }

            this.entityData.set(entitydataaccessor, false);
            Vec3 vec3 = this.position();
            ItemStack itemstack = this.createHorn(mushroom);
            double d0 = (double) Mth.randomBetween(this.random, -0.2F, 0.2F);
            double d1 = (double)Mth.randomBetween(this.random, 0.3F, 0.7F);
            double d2 = (double)Mth.randomBetween(this.random, -0.2F, 0.2F);
            ItemEntity itementity = new ItemEntity(this.level(), vec3.x(), vec3.y(), vec3.z(), itemstack, d0, d1, d2);
            this.level().addFreshEntity(itementity);
            this.getBrain().setMemory(DFMemoryTypes.GROW_HORN_TICKS.get(), 24000);
            return true;
        }
    }

    public ItemStack createHorn(ItemStack mushroom) {
        if (mushroom.is(DFItems.CLEAR_CLOUDSHROOM.get())) {
            return DFItems.CLEAR_HORN.get().getDefaultInstance();
        } else if (mushroom.is(DFItems.RAINY_CLOUDSHROOM.get())) {
            return DFItems.RAINY_HORN.get().getDefaultInstance();
        } else {
            return DFItems.THUNDER_HORN.get().getDefaultInstance();
        }
    }

    public void growHorn() {
        if (!hasLeftHorn()) {
            this.entityData.set(DATA_HAS_LEFT_HORN, true);
        } else {
            this.entityData.set(DATA_HAS_RIGHT_HORN, true);
        }
        this.getBrain().setMemory(DFMemoryTypes.GROW_HORN_TICKS.get(), 24000);
    }
}
