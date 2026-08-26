package com.cloudmeow.delightoflight.network;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.block.entity.CottonCandyMachineBlockEntity;
import com.cloudmeow.delightoflight.network.payload.SpinProgressPayload;
import com.cloudmeow.delightoflight.network.payload.StartSpinningPayload;
import com.cloudmeow.delightoflight.network.payload.StopSpinningPayload;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = DelightoFlight.MOD_ID)
public class DFNetworking {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(StartSpinningPayload.TYPE, StartSpinningPayload.STREAM_CODEC, StartSpinningPayloadHandler::handleSpinCottonCandy);
        registrar.playToServer(StopSpinningPayload.TYPE, StopSpinningPayload.STREAM_CODEC, StopSpinningPayloadHandler::handleSpinCottonCandy);
        registrar.playToClient(SpinProgressPayload.TYPE, SpinProgressPayload.STREAM_CODEC, SpinProgressPayloadHandler::handleSpinProgress);
    }

    public static class StartSpinningPayloadHandler {
        public static void handleSpinCottonCandy(StartSpinningPayload payload, IPayloadContext context) {
            ServerPlayer player = (ServerPlayer) context.player();
            BlockEntity be = player.serverLevel().getBlockEntity(payload.pos());
            if (be instanceof CottonCandyMachineBlockEntity machine) {
                machine.startSpinning(player, machine);
            }
        }
    }

    public static class StopSpinningPayloadHandler {
        public static void handleSpinCottonCandy(StopSpinningPayload payload, IPayloadContext context) {
            ServerPlayer player = (ServerPlayer) context.player();
            BlockEntity be = player.serverLevel().getBlockEntity(payload.pos());
            if (be instanceof CottonCandyMachineBlockEntity machine) {
                machine.stopSpinning();
            }
        }
    }

    public static class SpinProgressPayloadHandler {
        public static void handleSpinProgress(SpinProgressPayload payload, IPayloadContext context) {
            if (context.player().level() instanceof ClientLevel clientLevel && clientLevel.getBlockEntity(payload.pos()) instanceof CottonCandyMachineBlockEntity machine) {
                machine.setSpinTime(payload.spinTime());
            }
        }
    }
}
