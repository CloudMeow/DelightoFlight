package com.cloudmeow.delightoflight.advancements;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class CutLotusFlowerNightTrigger extends SimpleCriterionTrigger<CutLotusFlowerNightTrigger.TriggerInstance> {
    private static final ResourceLocation ID = new ResourceLocation(DelightoFlight.MOD_ID, "cut_lotus_flower_night");

    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, TriggerInstance::test);
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext conditionsParser) {
        return new TriggerInstance(player);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance
    {
        public TriggerInstance(ContextAwarePredicate player) {
            super(CutLotusFlowerNightTrigger.ID, player);
        }

        public static TriggerInstance simple() {
            return new TriggerInstance(ContextAwarePredicate.ANY);
        }

        public boolean test() {
            return true;
        }
    }
}
