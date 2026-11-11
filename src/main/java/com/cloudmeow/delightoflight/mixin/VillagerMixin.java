package com.cloudmeow.delightoflight.mixin;

import com.cloudmeow.delightoflight.block.CloudSilkBedBlock;
import com.cloudmeow.delightoflight.registry.DFPoi;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AcquirePoi;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
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

    @Inject(method = "releasePoi", at = @At("HEAD"), cancellable = true)
    protected void releaseCloudBedPoi(MemoryModuleType<GlobalPos> moduleType, CallbackInfo ci) {
        if (moduleType != MemoryModuleType.HOME || !(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        this.getBrain().getMemory(MemoryModuleType.HOME).ifPresent(globalPos -> {
            ServerLevel poiLevel = serverLevel.getServer().getLevel(globalPos.dimension());
            if (poiLevel == null) {
                return;
            }

            PoiManager poiManager = poiLevel.getPoiManager();
            Optional<Holder<PoiType>> poiType = poiManager.getType(globalPos.pos());
            if (poiType.isPresent() && poiType.get().is(DFPoi.CLOUD_BED.getKey())) {
                poiManager.release(globalPos.pos());
                DebugPackets.sendPoiTicketCountPacket(poiLevel, globalPos.pos());
                ci.cancel();
            }
        });
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
