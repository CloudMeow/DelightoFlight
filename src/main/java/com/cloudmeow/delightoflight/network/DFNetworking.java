package com.cloudmeow.delightoflight.network;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.block.entity.CottonCandyMachineBlockEntity;
import com.cloudmeow.delightoflight.client.HeartAnimationHandler;
import com.cloudmeow.delightoflight.utility.StardropData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.UUID;
import java.util.function.Supplier;

public class DFNetworking {
    private static final String PROTOCOL_VERSION = "3";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(DelightoFlight.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        int id = 0;
        INSTANCE.registerMessage(id++, StartSpinningMessage.class,
                StartSpinningMessage::encode,
                StartSpinningMessage::new,
                StartSpinningMessage::handle
        );
        INSTANCE.registerMessage(id++, StopSpinningMessage.class,
                StopSpinningMessage::encode,
                StopSpinningMessage::new,
                StopSpinningMessage::handle
        );
        INSTANCE.registerMessage(id++, SpinProgressMessage.class,
                SpinProgressMessage::encode,
                SpinProgressMessage::new,
                SpinProgressMessage::handle
        );
        INSTANCE.registerMessage(id++, SyncStardropMessage.class,
                SyncStardropMessage::encode,
                SyncStardropMessage::new,
                SyncStardropMessage::handle
        );
        INSTANCE.registerMessage(id++, HeartAnimationMessage.class,
                HeartAnimationMessage::encode,
                HeartAnimationMessage::new,
                HeartAnimationMessage::handle
        );
    }

    public static void syncStardropState(ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player),
                new SyncStardropMessage(player.getUUID(), StardropData.hasEaten(player), StardropData.isOnCooldown(player)));
    }

    public static void sendHeartAnimation(ServerPlayer player, ItemStack stack) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new HeartAnimationMessage(stack));
    }

    public static class StartSpinningMessage {
        private final BlockPos pos;

        public StartSpinningMessage(BlockPos pos) {
            this.pos = pos;
        }

        public StartSpinningMessage(FriendlyByteBuf buf) {
            this.pos = buf.readBlockPos();
        }

        public void encode(FriendlyByteBuf buf) {
            buf.writeBlockPos(pos);
        }

        public static void handle(StartSpinningMessage msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player == null) return;
                BlockEntity be = player.serverLevel().getBlockEntity(msg.pos);
                if (be instanceof CottonCandyMachineBlockEntity machine) {
                    machine.startSpinning(player, machine);
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }

    public static class StopSpinningMessage {
        private final BlockPos pos;

        public StopSpinningMessage(BlockPos pos) {
            this.pos = pos;
        }

        public StopSpinningMessage(FriendlyByteBuf buf) {
            this.pos = buf.readBlockPos();
        }

        public void encode(FriendlyByteBuf buf) {
            buf.writeBlockPos(pos);
        }

        public static void handle(StopSpinningMessage msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player == null) return;
                BlockEntity be = player.serverLevel().getBlockEntity(msg.pos);
                if (be instanceof CottonCandyMachineBlockEntity machine) {
                    machine.stopSpinning();
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }

    public static class SpinProgressMessage {
        private final BlockPos pos;
        private final int spinTime;

        public SpinProgressMessage(BlockPos pos, int spinTime) {
            this.pos = pos;
            this.spinTime = spinTime;
        }

        public SpinProgressMessage(FriendlyByteBuf buf) {
            this.pos = buf.readBlockPos();
            this.spinTime = buf.readInt();
        }

        public void encode(FriendlyByteBuf buf) {
            buf.writeBlockPos(pos);
            buf.writeInt(spinTime);
        }

        public static void handle(SpinProgressMessage msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ClientLevel level = Minecraft.getInstance().level;
                if (level != null) {
                    BlockEntity be = level.getBlockEntity(msg.pos);
                    if (be instanceof CottonCandyMachineBlockEntity machine) {
                        machine.setSpinTime(msg.spinTime);
                    }
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }

    public static class SyncStardropMessage {
        private final UUID playerUUID;
        private final boolean eaten;
        private final boolean cooldown;

        public SyncStardropMessage(UUID playerUUID, boolean eaten, boolean cooldown) {
            this.playerUUID = playerUUID;
            this.eaten = eaten;
            this.cooldown = cooldown;
        }

        public SyncStardropMessage(FriendlyByteBuf buf) {
            this.playerUUID = buf.readUUID();
            this.eaten = buf.readBoolean();
            this.cooldown = buf.readBoolean();
        }

        public void encode(FriendlyByteBuf buf) {
            buf.writeUUID(playerUUID);
            buf.writeBoolean(eaten);
            buf.writeBoolean(cooldown);
        }

        public static void handle(SyncStardropMessage msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ClientStardropData.setEaten(msg.playerUUID, msg.eaten);
                ClientStardropData.setCooldown(msg.playerUUID, msg.cooldown);
            });
            ctx.get().setPacketHandled(true);
        }
    }

    public static class HeartAnimationMessage {
        private final ItemStack stack;

        public HeartAnimationMessage(ItemStack stack) {
            this.stack = stack;
        }

        public HeartAnimationMessage(FriendlyByteBuf buf) {
            this.stack = buf.readItem();
        }

        public void encode(FriendlyByteBuf buf) {
            buf.writeItem(stack);
        }

        public static void handle(HeartAnimationMessage msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                    () -> () -> HeartAnimationHandler.handle(msg.stack)));
            ctx.get().setPacketHandled(true);
        }
    }
}