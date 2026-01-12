package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface DFModelLayers {
    ModelLayerLocation AEROLOPE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "aerolope"), "main");
}
