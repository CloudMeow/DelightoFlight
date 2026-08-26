package com.cloudmeow.delightoflight.network.payload;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SpinProgressPayload(BlockPos pos, int spinTime) implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "spin_progress");
    public static final Type<SpinProgressPayload> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, SpinProgressPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            SpinProgressPayload::pos,
            ByteBufCodecs.VAR_INT,
            SpinProgressPayload::spinTime,
            SpinProgressPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
