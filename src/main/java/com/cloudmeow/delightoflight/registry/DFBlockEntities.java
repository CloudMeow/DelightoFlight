package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.block.entity.CloudSilkBedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DFBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DelightoFlight.MOD_ID);

    public static final RegistryObject<BlockEntityType<CloudSilkBedBlockEntity>> CLOUD_SILK_BED = BLOCK_ENTITIES.register(
            "cloud_silk_bed", ()-> BlockEntityType.Builder.of(CloudSilkBedBlockEntity::new, DFBlocks.CLOUD_SILK_BED.get()).build(null));
}