package com.cloudmeow.delightoflight.block.entity;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.crafting.CottonCandyMachineRecipe;
import com.cloudmeow.delightoflight.network.DFNetworking;
import com.cloudmeow.delightoflight.registry.DFBlockEntities;
import com.cloudmeow.delightoflight.registry.DFRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;

import java.util.Optional;
import java.util.UUID;

public class CottonCandyMachineBlockEntity extends SyncedBlockEntity {
    public static final int INGREDIENT_SLOT = 3;

    private final ItemStackHandler inventory;

    private final RecipeManager.CachedCheck<RecipeWrapper, CottonCandyMachineRecipe> quickCheck;

    private boolean isSpinning;
    private int spinTime;
    private int spinTimeTotal;
    private UUID spinningPlayerId;
    private ItemStack currentOutput;
    private int currentColor;

    public CottonCandyMachineBlockEntity(BlockPos pos, BlockState state) {
        super(DFBlockEntities.COTTON_CANDY_MACHINE.get(), pos, state);
        this.currentOutput =ItemStack.EMPTY;
        this.inventory = createHandler();
        this.quickCheck = RecipeManager.createCheck(DFRecipeTypes.SPINNING.get());
    }

    public boolean isSpinning() {
        return isSpinning;
    }

    public ItemStack getCurrentOutput() {
        return currentOutput;
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public int getSpinTime() {
        return spinTime;
    }

    public int getSpinTimeTotal() {
        return spinTimeTotal;
    }

    public UUID getSpinningPlayerId() {
        return spinningPlayerId;
    }

    public void setSpinTime(int spinTime) {
        this.spinTime = spinTime;
    }

    public void syncSpinProgress() {
        if (level instanceof ServerLevel serverLevel && spinningPlayerId != null) {
            ServerPlayer serverPlayer = serverLevel.getServer().getPlayerList().getPlayer(spinningPlayerId);
            if (serverPlayer != null) {
                DFNetworking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new DFNetworking.SpinProgressMessage(worldPosition, spinTime));
            }
        }
    }

    public static void spinningTick(Level level, BlockPos pos, BlockState state, CottonCandyMachineBlockEntity machine) {
        if (!machine.isSpinning) return;
        if (!machine.isPlayerStillValid()) {
            machine.stopSpinning();
            return;
        }

        boolean didInventoryChange;

        Optional<CottonCandyMachineRecipe> recipe = machine.getMatchingRecipe(new RecipeWrapper(machine.inventory));
        if (recipe.isPresent()) {
            didInventoryChange = machine.processCooking(recipe.get(), machine);
        } else {
            machine.stopSpinning();
            return;
        }

        if (didInventoryChange) {
            machine.inventoryChanged();
        }

        machine.syncSpinProgress();
    }

    private boolean processCooking (CottonCandyMachineRecipe recipe, CottonCandyMachineBlockEntity machine) {
        if (level == null) return false;

        ++spinTime;
        spinTimeTotal = recipe.getSpinTime();
        if (spinTime < spinTimeTotal) {
            return false;
        }

        spinTime = 0;
        if (spinningPlayerId != null) {
            Player player = level.getPlayerByUUID(spinningPlayerId);
            if (player != null) {
                ItemStack held = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (held.is(Items.STICK)) {
                    held.shrink(1);
                    ItemStack output = recipe.getResultItem(level.registryAccess()).copy();
                    ItemEntity itemEntity = new ItemEntity((ServerLevel) level, machine.getBlockPos().getX() + 0.5, machine.getBlockPos().getY() + 0.8, machine.getBlockPos().getZ() + 0.5, output);
                    itemEntity.setDeltaMovement(player.getLookAngle().reverse().scale(0.4D));
                    level.addFreshEntity(itemEntity);
                    consumeIngredients();
                }
            }
        }
        return true;
    }

    private void consumeIngredients() {
        for (int i = 0; i < INGREDIENT_SLOT; i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                inventory.extractItem(i, 1, false);
            }
        }
    }

    public void startSpinning(Player player, CottonCandyMachineBlockEntity machine) {
        if (isSpinning) {
            if (spinningPlayerId != null && !spinningPlayerId.equals(player.getUUID())) {
                player.displayClientMessage(Component.translatable(DelightoFlight.MOD_ID + "." + "cotton_candy_machine.occupied"), true);
                return;
            }
        }

        Optional<CottonCandyMachineRecipe> recipe = getMatchingRecipe(new RecipeWrapper(machine.inventory));
        if (recipe.isEmpty()) return;

        isSpinning = true;
        spinTimeTotal = recipe.get().getSpinTime();
        spinningPlayerId = player.getUUID();
        if (level != null) {
            this.currentOutput = recipe.get().getResultItem(level.registryAccess()).copy();
        }
        this.currentColor = recipe.get().getColor();

        inventoryChanged();
    }

    public void stopSpinning() {
        isSpinning = false;
        spinTime = 0;
        spinTimeTotal = 0;
        spinningPlayerId = null;
        currentOutput = ItemStack.EMPTY;

        inventoryChanged();
    }

    private Optional<CottonCandyMachineRecipe> getMatchingRecipe(RecipeWrapper inventoryWrapper) {
        if (level == null) return Optional.empty();
        return this.hasInput() ? this.quickCheck.getRecipeFor(inventoryWrapper, this.level) : Optional.empty();
    }

    private boolean hasInput() {
        for(int i = 0; i < 3; ++i) {
            if (!this.inventory.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    private boolean isPlayerStillValid() {
        if (spinningPlayerId == null) return false;
        ServerPlayer player = level.getServer().getPlayerList().getPlayer(spinningPlayerId);
        if (player == null || !player.isAlive()) return false;
        if (!player.getMainHandItem().is(Items.STICK)) return false;

        BlockHitResult hit = (BlockHitResult) player.pick(5.0, 1.0F, false);
        if (hit.getType() != HitResult.Type.BLOCK) return false;
        return hit.getBlockPos().equals(worldPosition);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", inventory.serializeNBT());
        tag.putBoolean("Spinning", this.isSpinning);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("Inventory"));
        isSpinning = tag.getBoolean("Spinning");
        this.currentOutput = ItemStack.of(tag.getCompound("Output"));
        spinTimeTotal = tag.getInt("SpinTimeTotal");
        currentColor = tag.getInt("Color");
        if (tag.hasUUID("Spinner")) {
            spinningPlayerId = tag.getUUID("Spinner");
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return writeItems(new CompoundTag());
    }

    private CompoundTag writeItems(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Inventory", inventory.serializeNBT());
        compound.putBoolean("Spinning", this.isSpinning);
        compound.put("Output", this.currentOutput.serializeNBT());
        compound.putInt("SpinTimeTotal", this.spinTimeTotal);
        compound.putInt("Color", this.currentColor);
        if (this.spinningPlayerId != null) {
            compound.putUUID("Spinner", this.spinningPlayerId);
        }
        return compound;
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < INGREDIENT_SLOT; ++i) {
            drops.add(inventory.getStackInSlot(i));
        }
        return drops;
    }

    public boolean canAddItem(ItemStack addedStack) {
        if (addedStack.isEmpty()) return false;

        for (int i = 0; i < INGREDIENT_SLOT; ++i) {
            ItemStack slotStack = inventory.getStackInSlot(i);
            if (!slotStack.isEmpty() && ItemStack.isSameItem(slotStack, addedStack) && notFull(slotStack)) {
                return true;
            }
        }

        for (int i = 0; i < INGREDIENT_SLOT; ++i) {
            if (inventory.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public ItemStack addItem(ItemStack addedStack) {
        if (addedStack.isEmpty()) return addedStack;

        for (int i = 0; i < INGREDIENT_SLOT; ++i) {
            ItemStack slotStack = inventory.getStackInSlot(i);
            if (!slotStack.isEmpty() && ItemStack.isSameItem(slotStack, addedStack) && notFull(slotStack)) {
                return inventory.insertItem(i, addedStack, false);
            }
        }

        for (int i = 0; i < INGREDIENT_SLOT; ++i) {
            if (inventory.getStackInSlot(i).isEmpty()) {
                return inventory.insertItem(i, addedStack, false);
            }
        }

        return addedStack;
    }

    public boolean notFull(ItemStack stack) {
        return stack.getCount() < stack.getMaxStackSize();
    }

    public boolean isEmpty() {
        for (int i = 0; i < INGREDIENT_SLOT; ++i) {
            if (!isWhichSlotEmpty(i)) {
                return false;
            }
        }
        return true;
    }

    public ItemStack removeItem() {
        for (int i = INGREDIENT_SLOT - 1; i > 0; --i) {
            if (!isWhichSlotEmpty(i)) {
                return inventory.extractItem(i, getMaxStackSize(i), false);
            }
        }
        return inventory.extractItem(0, getMaxStackSize(0), false);
    }

    public boolean isWhichSlotEmpty(int i) {
        return inventory.getStackInSlot(i).isEmpty();
    }

    public int getMaxStackSize(int i) {
        return inventory.getSlotLimit(i);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(INGREDIENT_SLOT)
        {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                inventoryChanged();
            }
        };
    }
}
