package com.cloudmeow.delightoflight.datagen.sub;

import com.cloudmeow.delightoflight.registry.DFBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class DFBlockLootSubProvider extends BlockLootSubProvider {
    public DFBlockLootSubProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(DFBlocks.CLOUD.get());
        this.dropSelf(DFBlocks.CLOUD_SILK_BLOCK.get());
        this.dropSelf(DFBlocks.CLOUD_SILK_BED.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return DFBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
