package com.cloudmeow.delightoflight.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TurbulenceEffect extends MobEffect {
    public TurbulenceEffect() {
        super(MobEffectCategory.BENEFICIAL, 16776960);
        this.addAttributeModifier(Attributes.FLYING_SPEED, "a7f8c2f8-4c7b-4c30-91f6-8718d1d3f3fa", 0.4F, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "9d004f6b-204f-4d15-b84c-3882f64e9a5c", 0.4F, AttributeModifier.Operation.ADDITION);
    }
}
