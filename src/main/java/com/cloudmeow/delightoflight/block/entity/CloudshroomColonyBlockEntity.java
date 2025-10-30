package com.cloudmeow.delightoflight.block.entity;

import com.cloudmeow.delightoflight.registry.DFBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CloudshroomColonyBlockEntity extends BlockEntity {
    public CloudshroomColonyBlockEntity(BlockPos pos, BlockState blockState) {
        super(DFBlockEntities.CLOUDSHROOM_COLONY.get(), pos, blockState);
    }
}