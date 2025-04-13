package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DFCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DelightoFlight.MOD_ID);
    public static final Supplier<CreativeModeTab> TAB = TABS.register(DelightoFlight.MOD_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.delighto_flight"))
            .icon(() -> new ItemStack(DFItems.CLOUD_SILK.get()))
            .displayItems((parameters, output) -> {
                output.accept(DFItems.COOK_BOOK.get());
                output.accept(DFItems.CLOUD_SILK.get());
                output.accept(DFItems.PHANTOM_WING.get());
                output.accept(DFItems.MAGIC_CHEF_HAT.get());
                output.accept(DFItems.BIRD_FEED.get());
                output.accept(DFItems.THUNDER_FRUIT_SEED.get());
                output.accept(DFItems.COPPER_THUNDER_FRUIT.get());
                output.accept(DFItems.THUNDER_FRUIT.get());
                output.accept(DFItems.CLOUD_BERRY.get());
                output.accept(DFItems.CLOUD_BERRY_STEAMED_BUN.get());
                output.accept(DFItems.CLOUD_BERRY_PANCAKE_TOWER.get());
                output.accept(DFItems.CLOUD_BERRY_PANCAKE.get());
                output.accept(DFItems.CLOUD_BERRY_CHEESE.get());
                output.accept(DFItems.ORLEANS_PHANTOM_WING.get());
                output.accept(DFItems.HAM_SALAD.get());
                output.accept(DFItems.THUNDER_FRUIT_STEW_BLOCK.get());
                output.accept(DFItems.THUNDER_FRUIT_STEW.get());
                output.accept(DFItems.CHARGED_ROSE_TEA.get());
                output.accept(DFItems.CLOUD.get());
                output.accept(DFItems.CLOUD_SILK_BLOCK.get());
                output.accept(DFItems.CLOUD_SILK_BED.get());
            })
            .build());
}
