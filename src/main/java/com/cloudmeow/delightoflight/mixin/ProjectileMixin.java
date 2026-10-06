package com.cloudmeow.delightoflight.mixin;

import com.cloudmeow.delightoflight.registry.DFEffects;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public abstract class ProjectileMixin extends Entity {
    public ProjectileMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "onHit", at = @At("HEAD"), cancellable = true)
    private void onHit(HitResult result, CallbackInfo ci) {
        if (!(result instanceof EntityHitResult entityHitResult)) return;
        Entity target = entityHitResult.getEntity();
        if (!(target instanceof LivingEntity living)) return;
        MobEffectInstance dim = living.getEffect(DFEffects.DIM);
        if (dim == null) return;
        if (!DFUtilities.hasGrindHerbs(living) && !this.level().isNight()) return;
        if (this.random.nextFloat() < 0.1F * (dim.getAmplifier() + 1)) {
            ci.cancel();
        }
    }
}
