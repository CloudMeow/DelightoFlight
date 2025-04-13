package com.cloudmeow.delightoflight.event;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.client.model.AllayHatModel;
import com.cloudmeow.delightoflight.client.model.CookBookModel;
import com.cloudmeow.delightoflight.client.render.AllayHatLayer;
import com.cloudmeow.delightoflight.client.render.CloudSilkBedBlockEntityRenderer;
import com.cloudmeow.delightoflight.client.render.CookBookLayer;
import com.cloudmeow.delightoflight.client.render.ElectricCurrentRenderer;
import com.cloudmeow.delightoflight.gui.CookBookScreen;
import com.cloudmeow.delightoflight.registry.DFBlockEntities;
import com.cloudmeow.delightoflight.registry.DFEntityTypes;
import com.cloudmeow.delightoflight.registry.DFFluids;
import com.cloudmeow.delightoflight.registry.DFMenus;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.AllayRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = DelightoFlight.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)

public class ClientEvent {
    public static final ModelLayerLocation ALLAY_HAT = new ModelLayerLocation(AllayHatLayer.HAT_TEXTURE, "root");
        public static final ModelLayerLocation COOK_BOOK = new ModelLayerLocation(CookBookLayer.BOOK_TEXTURE, "root");

    @SubscribeEvent
    public static void entityRender(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(DFBlockEntities.CLOUD_SILK_BED.get(), CloudSilkBedBlockEntityRenderer::new);
        event.registerEntityRenderer(DFEntityTypes.ELECTRIC_CURRENT.get(), ElectricCurrentRenderer::new);
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
    public static void initializeClient(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return 0xffa020f0;
            }
            @Override
            public ResourceLocation getStillTexture() {
                return ResourceLocation.withDefaultNamespace("block/water_still");
            }
            @Override
            public ResourceLocation getFlowingTexture() {
                return ResourceLocation.withDefaultNamespace("block/water_flow");
            }
        }, DFFluids.CHARGED_ROSE_TEA_TYPE.value());
    }

    @SubscribeEvent
    public static void menuScreen(RegisterMenuScreensEvent event) {
        event.register(DFMenus.COOK_BOOK.get(), CookBookScreen::new);
    }
}
