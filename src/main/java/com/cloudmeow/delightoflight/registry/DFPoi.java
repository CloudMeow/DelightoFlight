package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.block.CloudSilkBedBlock;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.stream.Collectors;

public class DFPoi {
    public static final DeferredRegister<PoiType> POI_TYPE = DeferredRegister.create(ForgeRegistries.POI_TYPES, DelightoFlight.MOD_ID);
    public static final RegistryObject<PoiType> CLOUD_BED = POI_TYPE.register("cloud_bed",
            () -> new PoiType(ImmutableSet.copyOf(DFBlocks.CLOUD_SILK_BED.get().getStateDefinition().getPossibleStates().stream()
                    .filter(state -> state.getValue(CloudSilkBedBlock.PART) == BedPart.HEAD)
                    .collect(Collectors.toSet())), 1, 2
            ));

    public static final RegistryObject<PoiType> BASKET = POI_TYPE.register("basket",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.BASKET.get().getStateDefinition().getPossibleStates()), 1, 2));

    public static final RegistryObject<PoiType> POT = POI_TYPE.register("pot",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.COOKING_POT.get().getStateDefinition().getPossibleStates()), 1, 2));
}