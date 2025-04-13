package com.cloudmeow.delightoflight.mixin;

import com.cloudmeow.delightoflight.block.CloudSilkBedBlock;
import com.cloudmeow.delightoflight.registry.DFPoi;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AcquirePoi;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {
    @Shadow
    protected abstract void resetSpecialPrices();

    public VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }
    @Unique private boolean delightoFlight$feelComfortable = false;

    @Inject(method = "registerBrainGoals", at = @At("RETURN"))
    protected void registerBrainGoals(Brain<Villager> brain, CallbackInfo ci){
        brain.addActivity(Activity.CORE, ImmutableList.of(Pair.of(6, AcquirePoi.create((x) -> {
            assert DFPoi.CLOUD_BED.getKey() != null;
            return x.is(DFPoi.CLOUD_BED.getKey());}, MemoryModuleType.HOME, false, Optional.of((byte)14)))
        ));
    }

    @Inject(method = "startSleeping", at = @At("HEAD"))
    protected void startSleeping(BlockPos pos, CallbackInfo ci) {
        Block bed = this.level().getBlockState(pos).getBlock();
        if (bed instanceof CloudSilkBedBlock) {
            if(!this.delightoFlight$feelComfortable) {
                for (MerchantOffer merchantoffer : this.getOffers()) {
                    merchantoffer.addToSpecialPriceDiff(-2);
                    this.delightoFlight$feelComfortable = true;
                }
            }
        }
    }

    @Inject(method = "stopTrading", at = @At("TAIL"))
    protected void stopTrading(CallbackInfo ci){
        if (this.delightoFlight$feelComfortable) {
            for (MerchantOffer merchantoffer : this.getOffers()) {
                merchantoffer.addToSpecialPriceDiff(-2);
            }
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionalSaveData(CompoundTag tag, CallbackInfo ci){
        tag.putBoolean("Comfortable", this.delightoFlight$feelComfortable);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionalSaveData(CompoundTag tag, CallbackInfo ci){
        this.delightoFlight$feelComfortable = tag.getBoolean("Comfortable");
    }

    @Inject(method = "updateTrades", at = @At("TAIL"))
    protected void updateTrades(CallbackInfo ci){
        this.resetSpecialPrices();
        if (this.delightoFlight$feelComfortable) {
            for (MerchantOffer merchantoffer : this.getOffers()) {
                merchantoffer.addToSpecialPriceDiff(-2);
            }
        }
    }
}
