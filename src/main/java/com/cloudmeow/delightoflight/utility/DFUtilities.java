package com.cloudmeow.delightoflight.utility;

import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fml.ModList;

public class DFUtilities {
    public static boolean checkChefHatExist(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == DFItems.MAGIC_CHEF_HAT.get();
    }

    public static boolean checkCookBookExist(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == DFItems.COOK_BOOK.get();
    }

    public static boolean getConnectedBlock(BlockGetter blockGetter, BlockPos pos, Block block, Direction direction, Block needBlock, int howLong) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();

        BlockState blockstate;

        for(int x = 1; x < howLong; ++x) {
            blockpos$mutableblockpos.move(direction);
            blockstate = blockGetter.getBlockState(blockpos$mutableblockpos);
            if(blockstate.is(needBlock)){
                return true;
            } else if(!blockstate.is(block)){
                return false;
            }
        }
        return false;
    }

    public static boolean isSameBlock(BlockGetter blockGetter, BlockPos pos, Block block, Direction direction, int howLong) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();

        BlockState blockstate;

        for(int x = 1; x < howLong; ++x) {
            blockpos$mutableblockpos.move(direction);
            blockstate = blockGetter.getBlockState(blockpos$mutableblockpos);
            if(!blockstate.is(block)){
                return false;
            }
        }
        return true;
    }

    public static boolean chefDelightLoad() {
        return ModList.get().isLoaded("chefsdelight");
    }

    public static boolean twilightDelightLoad() {
        return ModList.get().isLoaded("twilightdelight");
    }

    public static boolean goetyDelightLoad() {
        return ModList.get().isLoaded("goetydelight");
    }

    public static boolean dungeonsDelightLoad() {
        return ModList.get().isLoaded("dungeonsdelight");
    }

    public static boolean thirstLoad() {
        return ModList.get().isLoaded("thirst");
    }

    public static boolean isFullMoon(ServerLevel world) {
        return world.isNight() && world.getMoonPhase() == 0;
    }

    public static boolean isConductive(LivingEntity owner) {
        ServerLevel world = (ServerLevel) owner.level();
        BlockPos pos = owner.blockPosition();
        FluidState fluidState = world.getFluidState(pos);
        return world.isRainingAt(pos) || fluidState.getType().isSame(Fluids.WATER);
    }
}
