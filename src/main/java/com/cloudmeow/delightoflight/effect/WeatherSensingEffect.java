package com.cloudmeow.delightoflight.effect;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class WeatherSensingEffect extends MobEffect {
    private static final ResourceLocation SPEED = ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "speed");
    private static final ResourceLocation ATTACK_SPEED = ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "attack_speed");
    private static final ResourceLocation DAMAGE = ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "damage");

    public WeatherSensingEffect() {
        super(MobEffectCategory.BENEFICIAL, 2003199);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide) {
            ServerLevel world = (ServerLevel) entity.level();

            removeModifier(entity.getAttribute(Attributes.MOVEMENT_SPEED), SPEED);
            removeModifier(entity.getAttribute(Attributes.ATTACK_SPEED), ATTACK_SPEED);
            removeModifier(entity.getAttribute(Attributes.ATTACK_DAMAGE), DAMAGE);

            float movementAmount = 0.2F * (amplifier + 1);
            float attackSpeedAmount = 0.1F * (amplifier + 1);
            float attackDamageAmount = 3.0F * (amplifier + 1);

            if (world.isThundering()) {
                addModifier(entity.getAttribute(Attributes.ATTACK_DAMAGE), DAMAGE, attackDamageAmount, AttributeModifier.Operation.ADD_VALUE);
            } else if (world.isRaining()) {
                addModifier(entity.getAttribute(Attributes.MOVEMENT_SPEED), SPEED, movementAmount, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
            } else {
                addModifier(entity.getAttribute(Attributes.ATTACK_SPEED), ATTACK_SPEED, attackSpeedAmount, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            }
        }
        return true;
    }

    private void addModifier(AttributeInstance attribute, ResourceLocation resourceLocation, float value, AttributeModifier.Operation operation) {
        if (attribute != null) {
            attribute.addTransientModifier(new AttributeModifier(resourceLocation, value, operation));
        }
    }

    private void removeModifier(AttributeInstance attribute, ResourceLocation resourceLocation) {
        if (attribute != null) {
            attribute.removeModifier(resourceLocation);
        }
    }

    @Override
    public void removeAttributeModifiers(AttributeMap attributeMap) {
        removeModifier(attributeMap.getInstance(Attributes.MOVEMENT_SPEED), SPEED);
        removeModifier(attributeMap.getInstance(Attributes.ATTACK_SPEED), ATTACK_SPEED);
        removeModifier(attributeMap.getInstance(Attributes.ATTACK_DAMAGE), DAMAGE);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
