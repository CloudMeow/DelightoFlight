package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class DFAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, DelightoFlight.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> MOONLIGHT_PROGRESS = ATTACHMENT_TYPES.register("moonlight_progress",
            () -> AttachmentType.builder(() -> 0).build());

    public static final Supplier<AttachmentType<Boolean>> STARDROP_EATER = ATTACHMENT_TYPES.register("stardrop_eater",
            () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).copyOnDeath().sync(ByteBufCodecs.BOOL).build());

    public static final Supplier<AttachmentType<Boolean>> STARDROP_COOLDOWN = ATTACHMENT_TYPES.register("stardrop_cooldown",
            () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).copyOnDeath().sync(ByteBufCodecs.BOOL).build());

    public static final Supplier<AttachmentType<Integer>> STARDROP_RECOVERY = ATTACHMENT_TYPES.register("stardrop_recovery",
            () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
}
