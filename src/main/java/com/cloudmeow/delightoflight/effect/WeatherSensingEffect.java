package com.cloudmeow.delightoflight.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class WeatherSensingEffect extends MobEffect {
    private static final UUID SPEED_UUID = UUID.fromString("a7f8c2f8-4c7b-4c30-91f6-8718d1d3f3fa");
    private static final UUID ATTACK_SPEED_UUID = UUID.fromString("9d004f6b-204f-4d15-b84c-3882f64e9a5c");
    private static final UUID DAMAGE_UUID = UUID.fromString("b1c2d3e4-f5f6-47a8-b9c0-d1e2f3040506");

    public WeatherSensingEffect() {
        super(MobEffectCategory.BENEFICIAL, 2003199);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level().isClientSide) {
            ServerLevel world = (ServerLevel) livingEntity.level();

            removeModifier(livingEntity.getAttribute(Attributes.MOVEMENT_SPEED), SPEED_UUID);
            removeModifier(livingEntity.getAttribute(Attributes.ATTACK_SPEED), ATTACK_SPEED_UUID);
            removeModifier(livingEntity.getAttribute(Attributes.ATTACK_DAMAGE), DAMAGE_UUID);

            float movementAmount = 0.2F * (amplifier + 1);
            float attackSpeedAmount = 0.1F * (amplifier + 1);
            float attackDamageAmount = 3.0F * (amplifier + 1);

            if (world.isThundering()) {
                this.addModifier(livingEntity.getAttribute(Attributes.ATTACK_DAMAGE), DAMAGE_UUID, "damage", attackDamageAmount, AttributeModifier.Operation.ADDITION);
            } else if (world.isRaining()) {
                this.addModifier(livingEntity.getAttribute(Attributes.MOVEMENT_SPEED), SPEED_UUID, "speed", movementAmount, AttributeModifier.Operation.MULTIPLY_BASE);
            } else {
                this.addModifier(livingEntity.getAttribute(Attributes.ATTACK_SPEED), ATTACK_SPEED_UUID, "attack_speed", attackSpeedAmount, AttributeModifier.Operation.MULTIPLY_TOTAL);
            }
        }
    }

    private void addModifier(AttributeInstance attribute, UUID uuid, String string,float value, AttributeModifier.Operation operation) {
        if (attribute != null) {
            attribute.addTransientModifier(new AttributeModifier(uuid, string, value, operation));
        }
    }

    private void removeModifier(AttributeInstance attribute, UUID uuid) {
        if (attribute != null) {
            attribute.removeModifier(uuid);
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity livingEntity, AttributeMap attributeMap, int p_19471_) {
        removeModifier(attributeMap.getInstance(Attributes.MOVEMENT_SPEED), SPEED_UUID);
        removeModifier(attributeMap.getInstance(Attributes.ATTACK_SPEED), ATTACK_SPEED_UUID);
        removeModifier(attributeMap.getInstance(Attributes.ATTACK_DAMAGE), DAMAGE_UUID);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
