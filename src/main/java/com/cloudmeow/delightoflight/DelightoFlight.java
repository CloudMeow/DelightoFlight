package com.cloudmeow.delightoflight;

import com.cloudmeow.delightoflight.compat.thirst.ThirstCompat;
import com.cloudmeow.delightoflight.registry.*;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(DelightoFlight.MOD_ID)
public class DelightoFlight
{
    public static final String MOD_ID = "delighto_flight";

    public DelightoFlight(IEventBus modEventBus, ModContainer modContainer)
    {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.CONFIG);

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
        DFDataComponents.DATA_COMPONENTS.register(modEventBus);
        DFMenus.MENUS.register(modEventBus);
        DFSensorType.SENSOR_TYPE.register(modEventBus);
        DFFeatures.FEATURES.register(modEventBus);
        DFAdvancements.TRIGGERS.register(modEventBus);
        DFRecipeTypes.RECIPE_TYPES.register(modEventBus);
        DFRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);

        if (!DFUtilities.chefDelightLoad()) {
            DFPoi.POT_POI_TYPE.register(modEventBus);
        }
        if (DFUtilities.thirstLoad()) {
            ThirstCompat.init();
        }
    }
}
