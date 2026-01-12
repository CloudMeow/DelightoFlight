package com.cloudmeow.delightoflight.advancement;

import com.cloudmeow.delightoflight.registry.DFAdvancements;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class CutLotusFlowerNightTrigger extends SimpleCriterionTrigger<CutLotusFlowerNightTrigger.TriggerInstance> {
    @Override
    public Codec<TriggerInstance> codec() {
        return CutLotusFlowerNightTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, TriggerInstance::test);
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<CutLotusFlowerNightTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create((builder) -> {
            return builder.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(CutLotusFlowerNightTrigger.TriggerInstance::player)).apply(builder, CutLotusFlowerNightTrigger.TriggerInstance::new);
        });

        public static Criterion<TriggerInstance> simple() {
            return DFAdvancements.CUT_LOTUS_FLOWER_NIGHT.get().createCriterion(new CutLotusFlowerNightTrigger.TriggerInstance(Optional.empty()));
        }

        public boolean test() {
            return true;
        }
    }
}
