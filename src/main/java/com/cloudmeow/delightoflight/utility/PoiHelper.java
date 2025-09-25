package com.cloudmeow.delightoflight.utility;

import com.cloudmeow.delightoflight.registry.DFPoi;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.redstonegames.chefsdelight.villager.ModVillagers;

public class PoiHelper {
    public static Holder<PoiType> getChefPoi() {
        if (!DFUtilities.chefDelightLoad()) {
            return DFPoi.POT;
        }
        return ModVillagers.CHEF_POI;
    }
}