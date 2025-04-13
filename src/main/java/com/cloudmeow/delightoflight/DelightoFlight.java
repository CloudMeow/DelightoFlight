package com.cloudmeow.delightoflight;

import com.cloudmeow.delightoflight.client.ClientSetup;
import com.cloudmeow.delightoflight.registry.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(DelightoFlight.MOD_ID)
public class DelightoFlight
{
    public static final String MOD_ID = "delighto_flight";

    public DelightoFlight()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(ClientSetup::screenInit);
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.CONFIG);

        DFItems.ITEMS.register(modEventBus);
        DFBlocks.BLOCKS.register(modEventBus);
        DFFluids.FLUIDS.register(modEventBus);
        DFFluids.FLUID_TYPES.register(modEventBus);
        DFEntityTypes.ENTITY_TYPES.register(modEventBus);
        DFPoi.POI_TYPE.register(modEventBus);
        DFSounds.SOUNDS.register(modEventBus);
        DFEffects.EFFECTS.register(modEventBus);
        DFBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        DFMemoryTypes.MEMORIES.register(modEventBus);
        DFCreativeModeTabs.TABS.register(modEventBus);
        DFLootModifiers.LOOT_MODIFIERS.register(modEventBus);
        DFMenus.MENUS.register(modEventBus);
    }
}