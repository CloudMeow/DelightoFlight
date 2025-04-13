package com.cloudmeow.delightoflight.item;

import com.cloudmeow.delightoflight.gui.CookBookMenu;
import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class CookBook extends Item implements MenuProvider {

    public CookBook(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack heldItem = player.getItemInHand(usedHand);
        if (!player.isCrouching()) {
            if (usedHand == InteractionHand.MAIN_HAND) {
                if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
                    NetworkHooks.openScreen(serverPlayer, this, buf -> {
                        buf.writeItem(heldItem);
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
        if (!stack.hasTag())
            return newInv;
        CompoundTag invNBT = stack.getOrCreateTagElement("Items");
        if (!invNBT.isEmpty())
            newInv.deserializeNBT(invNBT);

        return newInv;
    }
}