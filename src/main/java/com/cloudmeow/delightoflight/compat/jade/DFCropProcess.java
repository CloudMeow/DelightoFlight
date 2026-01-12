package com.cloudmeow.delightoflight.compat.jade;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.block.CloudBerryBushBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.network.chat.Component;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum DFCropProcess implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        BlockState state = blockAccessor.getBlockState();
        Block block = state.getBlock();
        if (block instanceof CloudBerryBushBlock) {
            int age = state.getValue(CloudBerryBushBlock.AGE);
            int maxAge = 3;
            tooltip.add(Component.translatable("tooltip.jade.crop_growth",
            age == maxAge ?
                Component.translatable("tooltip.jade.crop_mature").withStyle(ChatFormatting.GREEN) :
                Component.literal(String.format("%.0f%%", (age / (float) maxAge) * 100F)).withStyle(ChatFormatting.WHITE)));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "crop_progress");
    }
}
