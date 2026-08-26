package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.block.entity.CloudSilkBedBlockEntity;
import com.cloudmeow.delightoflight.block.entity.CloudshroomColonyBlockEntity;
import com.cloudmeow.delightoflight.block.entity.CottonCandyMachineBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DFBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, DelightoFlight.MOD_ID);

    public static final Supplier<BlockEntityType<CloudSilkBedBlockEntity>> CLOUD_SILK_BED = BLOCK_ENTITIES.register(
            "cloud_silk_bed", ()-> BlockEntityType.Builder.of(CloudSilkBedBlockEntity::new, DFBlocks.CLOUD_SILK_BED.get()).build(null));
    public static final Supplier<BlockEntityType<CloudshroomColonyBlockEntity>> CLOUDSHROOM_COLONY = BLOCK_ENTITIES.register(
            "cloudshroom_colony", ()-> BlockEntityType.Builder.of(CloudshroomColonyBlockEntity::new, DFBlocks.CLOUDSHROOM_COLONY.get()).build(null));
    public static final Supplier<BlockEntityType<CottonCandyMachineBlockEntity>> COTTON_CANDY_MACHINE = BLOCK_ENTITIES.register(
            "cotton_candy_machine", ()-> BlockEntityType.Builder.of(CottonCandyMachineBlockEntity::new, DFBlocks.COTTON_CANDY_MACHINE.get()).build(null));
}