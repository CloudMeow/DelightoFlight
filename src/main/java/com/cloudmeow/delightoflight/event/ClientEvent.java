package com.cloudmeow.delightoflight.event;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.client.model.AerolopeModel;
import com.cloudmeow.delightoflight.client.model.AllayHatModel;
import com.cloudmeow.delightoflight.client.model.CookBookModel;
import com.cloudmeow.delightoflight.client.render.*;
import com.cloudmeow.delightoflight.entity.AerolopeEntity;
import com.cloudmeow.delightoflight.registry.DFBlockEntities;
import com.cloudmeow.delightoflight.registry.DFEntityTypes;
import com.cloudmeow.delightoflight.registry.DFItems;
import com.cloudmeow.delightoflight.registry.DFModelLayers;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.AllayRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DelightoFlight.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvent {
    public static final ModelLayerLocation ALLAY_HAT = new ModelLayerLocation(AllayHatLayer.HAT_TEXTURE, "root");
    public static final ModelLayerLocation COOK_BOOK = new ModelLayerLocation(CookBookLayer.BOOK_TEXTURE, "root");

    @SubscribeEvent
    public static void entityRender(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(DFBlockEntities.CLOUD_SILK_BED.get(), CloudSilkBedBlockEntityRenderer::new);
        event.registerEntityRenderer(DFEntityTypes.ELECTRIC_CURRENT.get(), ElectricCurrentRenderer::new);
        event.registerEntityRenderer(DFEntityTypes.AEROLOPE.get(), AerolopeRenderer::new);
    }

    @SubscribeEvent
    public static void layerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ALLAY_HAT, AllayHatModel::createBodyLayer);
        event.registerLayerDefinition(COOK_BOOK, CookBookModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void addLayer(EntityRenderersEvent.AddLayers event) {
        AllayRenderer renderer = event.getRenderer(EntityType.ALLAY);
        renderer.addLayer(new AllayHatLayer(renderer, event.getEntityModels()));
        renderer.addLayer(new CookBookLayer(renderer, event.getEntityModels()));
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemProperties.register(
                DFItems.CLEAR_HORN.get(),
                new ResourceLocation("ctooting"),
                (ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed) -> {
                    if (entity != null && entity.isUsingItem() && entity.getUseItem() == stack) {
                        return 1.0F;
                    }
                    return 0.0F;
                }
        );
        ItemProperties.register(
                DFItems.RAINY_HORN.get(),
                new ResourceLocation("rtooting"),
                (ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed) -> {
                    if (entity != null && entity.isUsingItem() && entity.getUseItem() == stack) {
                        return 1.0F;
                    }
                    return 0.0F;
                }
        );
        ItemProperties.register(
                DFItems.THUNDER_HORN.get(),
                new ResourceLocation("ttooting"),
                (ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed) -> {
                    if (entity != null && entity.isUsingItem() && entity.getUseItem() == stack) {
                        return 1.0F;
                    }
                    return 0.0F;
                }
        );
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DFModelLayers.AEROLOPE, AerolopeModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(
                DFEntityTypes.AEROLOPE.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AerolopeEntity::checkAerolopeSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
