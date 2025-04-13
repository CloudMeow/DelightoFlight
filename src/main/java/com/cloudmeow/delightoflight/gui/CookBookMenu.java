package com.cloudmeow.delightoflight.gui;

import com.cloudmeow.delightoflight.item.CookBook;
import com.cloudmeow.delightoflight.registry.DFDataComponents;
import com.cloudmeow.delightoflight.registry.DFMenus;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;

public class CookBookMenu extends AbstractContainerMenu {
    public ItemStackHandler recipeInventory;
    public Inventory playerInventory;
    public ItemStack containerItem;
    public Player player;

    public CookBookMenu(int id, Inventory inv, RegistryFriendlyByteBuf extraData) {
        super(DFMenus.COOK_BOOK.get(), id);
        init(inv, createOnClient(extraData));
    }

    public CookBookMenu(MenuType<?> type, int id, Inventory inv, ItemStack stack) {
        super(type, id);
        init(inv, stack);
    }

    protected void init(Inventory inv, ItemStack stack) {
        this.player = inv.player;
        this.playerInventory = inv;
        this.containerItem = stack;

        initAndReadInventory(stack);
        addInventorySlot();
        addCookBookSlots();
        broadcastChanges();
    }

    public static CookBookMenu create(int id, Inventory inv, ItemStack stack) {
        return new CookBookMenu(DFMenus.COOK_BOOK.get(), id, inv, stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    protected ItemStack createOnClient(RegistryFriendlyByteBuf extraData) {
        return ItemStack.STREAM_CODEC.decode(extraData);
    }

    protected void initAndReadInventory(ItemStack itemStack) {
        recipeInventory = CookBook.getContentItems(itemStack);
        itemStack.set(DFDataComponents.CURRENT_SLOT, 0);
    }

    protected void addInventorySlot() {
        for (int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot)
            this.addSlot(new Slot(playerInventory, hotbarSlot, 8 + hotbarSlot * 18, 84 + 58));
        for (int row = 0; row < 3; ++row)
            for (int col = 0; col < 9; ++col)
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + col, 8 + (col * 18),
                        84 + (row * 18)));
    }

    protected void addCookBookSlots() {
        for (int row = 0; row < 2; ++row) {
            for (int column = 0; column < 3; ++column) {
                this.addSlot(new SlotItemHandler(recipeInventory, (row * 3) + column,
                        62 + (column * 18),
                        27 + (row * 18)));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        if (index < 36) {
            ItemStack stackToInsert = playerInventory.getItem(index);
            for (int i = 0; i < recipeInventory.getSlots(); i++) {
                ItemStack stack = recipeInventory.getStackInSlot(i);
                if (ItemStack.isSameItemSameComponents(stack, stackToInsert))
                    continue;
                if (stack.isEmpty()) {
                    ItemStack copy = stackToInsert.copy();
                    copy.setCount(1);
                    recipeInventory.insertItem(i, copy, false);
                    getSlot(i + 36).setChanged();
                    break;
                }
            }
        } else {
            recipeInventory.extractItem(index - 36, 1, false);
            getSlot(index).setChanged();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId < 36) {
            super.clicked(slotId, dragType, clickTypeIn, player);
            return;
        }
        if (clickTypeIn == ClickType.THROW)
            return;

        ItemStack held = getCarried();
        int slot = slotId - 36;
        if (clickTypeIn == ClickType.CLONE) {
            if (player.isCreative() && held.isEmpty()) {
                ItemStack stackInSlot = recipeInventory.getStackInSlot(slot)
                        .copy();
                stackInSlot.setCount(stackInSlot.getOrDefault(DataComponents.MAX_STACK_SIZE, 64));
                setCarried(stackInSlot);
                return;
            }
            return;
        }

        ItemStack insert;
        if (held.isEmpty()) {
            insert = ItemStack.EMPTY;
        } else {
            insert = held.copy();
            insert.setCount(1);
        }
        recipeInventory.setStackInSlot(slot, insert);
        getSlot(slotId).setChanged();
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        saveData(containerItem);
    }

    protected void saveData(ItemStack contentHolder) {
        for (int i = 0; i < recipeInventory.getSlots(); i++) {
            if (!recipeInventory.getStackInSlot(i).isEmpty()) {
                contentHolder.set(DFDataComponents.COOK_BOOK, containerContentsFromHandler(recipeInventory));
                return;
            }
        }
        contentHolder.remove(DFDataComponents.COOK_BOOK);
    }

    public static ItemContainerContents containerContentsFromHandler(ItemStackHandler handler) {
        List<ItemStack> stacks = new ArrayList<>(handler.getSlots());
        for (int i = 0; i < handler.getSlots(); i++) {
            stacks.add(handler.getStackInSlot(i).copy());
        }
        return ItemContainerContents.fromItems(stacks);
    }
}