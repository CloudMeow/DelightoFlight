package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.block.CloudSilkBedBlock;
import com.cloudmeow.delightoflight.compat.dungeonsdelight.DDCompat;
import com.cloudmeow.delightoflight.compat.twilightdelight.TDCompat;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.Collections;
import java.util.stream.Collectors;

public class DFPoi {
    public static final DeferredRegister<PoiType> POI_TYPE = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, DelightoFlight.MOD_ID);
    public static final DeferredRegister<PoiType> POT_POI_TYPE = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, DelightoFlight.MOD_ID);

    public static final Holder<PoiType> CLOUD_BED = POI_TYPE.register("cloud_bed",
            () -> new PoiType(ImmutableSet.copyOf(DFBlocks.CLOUD_SILK_BED.get().getStateDefinition().getPossibleStates().stream()
                    .filter(state -> state.getValue(CloudSilkBedBlock.PART) == BedPart.HEAD)
                    .collect(Collectors.toSet())), 1, 2
            ));

    public static final Holder<PoiType> BASKET = POI_TYPE.register("basket",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.BASKET.get().getStateDefinition().getPossibleStates()), 1, 2));

    public static final Holder<PoiType> POT = POT_POI_TYPE.register("pot",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.COOKING_POT.get().getStateDefinition().getPossibleStates()), 1, 2));

    public static final Holder<PoiType> MORE_POT = POI_TYPE.register("more_pot",
            () -> new PoiType(ImmutableSet.<BlockState>builder()
                    .addAll(DFUtilities.twilightDelightLoad() ? TDCompat.getTDPot().getStateDefinition().getPossibleStates() : Collections.emptySet())
                    .addAll(DFUtilities.dungeonsDelightLoad() ? DDCompat.getDDPot().getStateDefinition().getPossibleStates() : Collections.emptySet())
                    .build(), 1, 2));
}