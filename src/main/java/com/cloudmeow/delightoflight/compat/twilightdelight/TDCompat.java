package com.cloudmeow.delightoflight.compat.twilightdelight;

import com.cloudmeow.delightoflight.utility.DFUtilities;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.world.level.block.Block;

public class TDCompat {
    public static Block getTDPot(){
        if (DFUtilities.twilightDelightLoad()) {
            return TDBlocks.FIERY_POT.get();
        }
        return null;
    }
}
