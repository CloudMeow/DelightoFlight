package com.cloudmeow.delightoflight.mixin;

import com.cloudmeow.delightoflight.registry.DFEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "travel", at = @At("HEAD"))
    private void travel(Vec3 travelVector, CallbackInfo ci) {
        if (this.hasEffect(DFEffects.CLOUDWALKING.get()) && this.jumping) {
            Vec3 vel = this.getDeltaMovement();
            this.setDeltaMovement(vel.x, Math.max(vel.y, 0.05D), vel.z);
            this.resetFallDistance();
        }
    }
}
