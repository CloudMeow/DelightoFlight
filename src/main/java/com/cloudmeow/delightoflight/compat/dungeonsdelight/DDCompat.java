package com.cloudmeow.delightoflight.compat.dungeonsdelight;

import com.cloudmeow.delightoflight.utility.DFUtilities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.yirmiri.dungeonsdelight.common.block.entity.MonsterPotBlockEntity;
import net.yirmiri.dungeonsdelight.core.registry.DDBlocks;

public class DDCompat {
    public static Block getDDPot() {
        if (DFUtilities.dungeonsDelightLoad()) {
            return DDBlocks.MONSTER_POT.get();
        }
        return null;
    }

    public static boolean isMonsterPot(BlockEntity blockEntity) {
        return blockEntity instanceof MonsterPotBlockEntity;
    }

    public static void addIngredientForDDPot(BlockEntity cooker, LivingEntity livingEntity) {
        DFUtilities.insertCookBookIngredient(((MonsterPotBlockEntity) cooker).getInventory(), livingEntity);
    }
}
