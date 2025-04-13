package com.cloudmeow.delightoflight.block;

import com.cloudmeow.delightoflight.block.entity.CloudSilkBedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import org.jetbrains.annotations.NotNull;

public class CloudSilkBedBlock extends BedBlock {
    public CloudSilkBedBlock(Properties properties) {
        super(DyeColor.BLUE, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, BedPart.FOOT).setValue(OCCUPIED, false));
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new CloudSilkBedBlockEntity(blockPos, blockState);
    }
}
