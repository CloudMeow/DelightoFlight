package com.cloudmeow.delightoflight.block.entity;

import com.cloudmeow.delightoflight.registry.DFBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CloudSilkBedBlockEntity extends BlockEntity {
    public CloudSilkBedBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(DFBlockEntities.CLOUD_SILK_BED.get(), blockPos, blockState);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
