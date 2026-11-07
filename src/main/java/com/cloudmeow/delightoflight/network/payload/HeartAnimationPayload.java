package com.cloudmeow.delightoflight.network.payload;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record HeartAnimationPayload(ItemStack stack) implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "heart_animation");
    public static final Type<HeartAnimationPayload> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, HeartAnimationPayload> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            HeartAnimationPayload::stack,
            HeartAnimationPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
