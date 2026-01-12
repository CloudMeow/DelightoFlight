package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface DFModelLayers {
    ModelLayerLocation AEROLOPE = new ModelLayerLocation(new ResourceLocation(DelightoFlight.MOD_ID, "aerolope"), "main");
}
