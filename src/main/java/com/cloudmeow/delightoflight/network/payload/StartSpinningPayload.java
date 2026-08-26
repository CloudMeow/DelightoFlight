package com.cloudmeow.delightoflight.network.payload;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record StartSpinningPayload(BlockPos pos) implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "start_spinning");
    public static final Type<StartSpinningPayload> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, StartSpinningPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            StartSpinningPayload::pos,
            StartSpinningPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
