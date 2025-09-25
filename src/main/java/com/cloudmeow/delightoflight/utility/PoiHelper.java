package com.cloudmeow.delightoflight.utility;

import com.cloudmeow.delightoflight.registry.DFPoi;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.RegistryObject;
import net.redstonegames.chefsdelight.villager.ModVillagers;

public class PoiHelper {
    public static RegistryObject<PoiType> getChefPoi() {
        if (!DFUtilities.chefDelightLoad()) {
            return DFPoi.POT;
        }
        return ModVillagers.CHEF_POI;
    }
}