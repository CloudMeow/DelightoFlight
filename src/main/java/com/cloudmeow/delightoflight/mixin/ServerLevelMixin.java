package com.cloudmeow.delightoflight.mixin;

import com.cloudmeow.delightoflight.registry.DFBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.List;
import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level {
    protected ServerLevelMixin(WritableLevelData p_270739_, ResourceKey<Level> p_270683_, RegistryAccess p_270200_, Holder<DimensionType> p_270240_, Supplier<ProfilerFiller> p_270692_, boolean p_270904_, boolean p_270470_, long p_270248_, int p_270466_) {
        super(p_270739_, p_270683_, p_270200_, p_270240_, p_270692_, p_270904_, p_270470_, p_270248_, p_270466_);
    }

    @Shadow
    public abstract List<ServerPlayer> players();

    @Inject(method = "wakeUpAllPlayers", at = @At("HEAD"))
    private void wakeUpAllPlayers(CallbackInfo ci) {
        for (ServerPlayer serverPlayer : players()) {
            serverPlayer.getSleepingPos().ifPresent(pos -> {
                BlockState blockstate = this.getBlockState(pos);
                if(blockstate.getBlock() == DFBlocks.CLOUD_SILK_BED.get()) {
                    if (serverPlayer.getEffect(ModEffects.COMFORT.get()) != null) {
                        serverPlayer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 6000, 0));
                    }
                    if (serverPlayer.getEffect(ModEffects.NOURISHMENT.get()) != null) {
                        serverPlayer.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 0));
                    }
                }
            });
        }
    }
}
