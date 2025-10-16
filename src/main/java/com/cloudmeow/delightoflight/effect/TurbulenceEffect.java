package com.cloudmeow.delightoflight.effect;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TurbulenceEffect extends MobEffect {
    public TurbulenceEffect() {
        super(MobEffectCategory.BENEFICIAL, 16776960);
        this.addAttributeModifier(Attributes.FLYING_SPEED, ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "turbulence_modifier"), 1.0F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "turbulence_modifier"), 1.0F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }
}
