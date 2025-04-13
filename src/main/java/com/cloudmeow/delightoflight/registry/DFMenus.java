package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.gui.CookBookMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DFMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, DelightoFlight.MOD_ID);

    public static final Supplier<MenuType<CookBookMenu>> COOK_BOOK = MENUS
            .register("cook_book", () -> IForgeMenuType.create(CookBookMenu::new));
}