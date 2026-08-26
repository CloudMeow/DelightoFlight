package com.cloudmeow.delightoflight.network;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.block.entity.CottonCandyMachineBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class DFNetworking {
    private static final String PROTOCOL_VERSION = "1";

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
}