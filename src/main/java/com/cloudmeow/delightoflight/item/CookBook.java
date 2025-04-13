package com.cloudmeow.delightoflight.item;

import com.cloudmeow.delightoflight.gui.CookBookMenu;
import com.cloudmeow.delightoflight.registry.DFDataComponents;
import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CookBook extends Item implements MenuProvider {

    public CookBook(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack heldItem = player.getItemInHand(usedHand);
        if (!player.isCrouching()) {
            if (usedHand == InteractionHand.MAIN_HAND) {
                if (!level.isClientSide && player instanceof ServerPlayer) {
                    player.openMenu(this, buf -> {
                        ItemStack.STREAM_CODEC.encode(buf, heldItem);
                    });
                }
                return InteractionResultHolder.success(heldItem);
            }
            return InteractionResultHolder.pass(heldItem);
        }
        return InteractionResultHolder.pass(heldItem);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("delighto_flight." + "container.cook_book", new Object[0]);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        ItemStack heldItem = player.getMainHandItem();
        return CookBookMenu.create(containerId, playerInventory, heldItem);
    }

    public static ItemStackHandler getContentItems(ItemStack stack) {
        ItemStackHandler newInv = new ItemStackHandler(6);
        if (DFItems.COOK_BOOK.get() != stack.getItem())
            throw new IllegalArgumentException("Cannot get ingredients from book");
        if (!stack.has(DFDataComponents.COOK_BOOK))
            return newInv;
        fillItemStackHandler(stack.get(DFDataComponents.COOK_BOOK), newInv);

        return newInv;
    }

    public static void fillItemStackHandler(ItemContainerContents contents, ItemStackHandler inv) {
        List<ItemStack> itemStacks = contents.stream().toList();

        for (int i = 0; i < itemStacks.size(); i++) {
            inv.setStackInSlot(i, itemStacks.get(i));
        }
    }
}