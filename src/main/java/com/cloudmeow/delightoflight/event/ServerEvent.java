package com.cloudmeow.delightoflight.event;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.registry.DFBlocks;
import com.cloudmeow.delightoflight.registry.DFEffects;
import com.cloudmeow.delightoflight.registry.DFItems;
import com.cloudmeow.delightoflight.utility.DFUtilities;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.List;

@EventBusSubscriber(modid = DelightoFlight.MOD_ID)
public class ServerEvent {
    @SubscribeEvent
    public static void onEffectRemove(MobEffectEvent.Remove event) {
        if (event.getEffectInstance() == null) {
            return;
        }
        if (event.getEffectInstance().getEffect() == DFEffects.ARC) {
            CompoundTag entityData = event.getEntity().getPersistentData();
            entityData.putBoolean("discharge", false);
        }
    }

    @SubscribeEvent
    public static void onStruckByLightning(LivingDamageEvent.Post event) {
        if(event.getEntity() instanceof Player player) {
            if(event.getSource().is(DamageTypes.LIGHTNING_BOLT)) {
                for (int i = 0; i < player.getInventory().items.size(); i++) {
                    ItemStack stack = player.getInventory().items.get(i);
                    if (stack.getItem() == Items.PITCHER_POD) {
                        stack.shrink(1);
                        if (!player.getInventory().add(DFItems.THUNDER_FRUIT_SEED.get().getDefaultInstance())) {
                            player.drop(DFItems.THUNDER_FRUIT_SEED.get().getDefaultInstance(), false);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onGiveAllayItem(PlayerInteractEvent.EntityInteract event){
        Player player = event.getEntity();
        Entity target = event.getTarget();
        ItemStack heldStack = event.getItemStack();
        if (target instanceof Allay allay) {
            if(player.isCrouching()) {
                if(!allay.hasItemInSlot(EquipmentSlot.HEAD)) {
                    if (heldStack.getItem() == DFItems.MAGIC_CHEF_HAT.get()) {
                        if (!player.level().isClientSide()) {
                            allay.setItemSlot(EquipmentSlot.HEAD, heldStack.copy());
                            allay.level().playSound(null, allay.blockPosition(), SoundEvents.WOOL_PLACE, SoundSource.PLAYERS, 0.8F, 0.8F);
                            if (!player.isCreative()) {
                                heldStack.shrink(1);
                            }
                        }
                        event.setCanceled(true);
                    }
                } else if (DFUtilities.checkChefHatExist(allay)) {
                    if (!DFUtilities.checkCookBookExist(allay)) {
                        if(heldStack.getItem() == Items.SHEARS) {
                            allay.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                            allay.level().playSound(null, allay.blockPosition(), SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 0.8F, 0.8F);
                            heldStack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                            if (!player.level().isClientSide()) {
                                BlockPos pos = allay.blockPosition();
                                ItemEntity itemEntity = new ItemEntity((ServerLevel) player.level(), pos.getX(), pos.getY(), pos.getZ(), DFItems.MAGIC_CHEF_HAT.get().getDefaultInstance());
                                player.level().addFreshEntity(itemEntity);
                            }
                            event.setCanceled(true);
                        } else if (heldStack.getItem() == DFItems.COOK_BOOK.get()) {
                            allay.setItemSlot(EquipmentSlot.CHEST, heldStack);
                            allay.level().playSound(null, allay.blockPosition(), SoundEvents.WOOL_PLACE, SoundSource.PLAYERS, 0.8F, 0.8F);
                            heldStack.shrink(1);
                            event.setCanceled(true);
                        }
                    } else if (DFUtilities.checkCookBookExist(allay)) {
                        if (heldStack.getItem() == Items.BOOK) {
                            if (!player.level().isClientSide()) {
                                BlockPos pos = allay.blockPosition();
                                ItemEntity itemEntity = new ItemEntity((ServerLevel) player.level(), pos.getX(), pos.getY(), pos.getZ(), allay.getItemBySlot(EquipmentSlot.CHEST));
                                player.level().addFreshEntity(itemEntity);
                                allay.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
                                allay.level().playSound(null, allay.blockPosition(), SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
                            }
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onFeedCopperThunderFruit(PlayerInteractEvent.EntityInteract event){
        Entity target = event.getTarget();
        ItemStack heldStack = event.getItemStack();
        if (heldStack.getItem() == DFItems.COPPER_THUNDER_FRUIT.get()) {
            if (target instanceof Creeper || target instanceof Villager) {
                if (!event.getLevel().isClientSide()) {
                    ServerLevel serverWorld = (ServerLevel) event.getLevel();
                    BlockPos targetPos = target.blockPosition();
                    if (serverWorld.canSeeSky(targetPos)) {
                        LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(serverWorld);
                        if (lightningbolt != null) {
                            lightningbolt.moveTo(Vec3.atBottomCenterOf(targetPos));
                            serverWorld.addFreshEntity(lightningbolt);
                        }
                        heldStack.shrink(1);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.BUTCHER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(4).add((trader, rand) -> {
                return new MerchantOffer(new ItemCost(Items.EMERALD, 10), new ItemStack(DFItems.COOK_BOOK.get(), 1), 6, 6, 0.05F);
            });
        }
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof LightningBolt bolt && event.getLevel() instanceof ServerLevel level) {
            BlockPos pos = bolt.blockPosition();
            int radius = 3;
            for (int x = -radius; x <= radius; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos nearbyPos = pos.offset(x, y, z);
                        BlockState state = level.getBlockState(nearbyPos);
                        if (state.is(ModBlocks.RICH_SOIL_FARMLAND.get())) {
                            level.setBlockAndUpdate(nearbyPos, DFBlocks.WEATHER_SOIL_FARMLAND.get().defaultBlockState());
                        }
                        if (state.is(ModBlocks.RICH_SOIL.get())) {
                            level.setBlockAndUpdate(nearbyPos, DFBlocks.WEATHER_SOIL.get().defaultBlockState());
                        }
                    }
                }
            }
        }
    }
}