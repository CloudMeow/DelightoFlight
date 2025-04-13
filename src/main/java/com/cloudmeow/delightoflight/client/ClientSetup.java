package com.cloudmeow.delightoflight.client;

import com.cloudmeow.delightoflight.gui.CookBookScreen;
import com.cloudmeow.delightoflight.registry.DFMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup
{
    public static void screenInit(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(DFMenus.COOK_BOOK.get(), CookBookScreen::new));
    }
}