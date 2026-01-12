package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.world.feature.WildLotusFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DFFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, DelightoFlight.MOD_ID);

    public static final Supplier<Feature<RandomPatchConfiguration>> WILD_RICE = FEATURES.register("wild_lotus", () -> new WildLotusFeature(RandomPatchConfiguration.CODEC));
}
