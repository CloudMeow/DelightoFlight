package com.cloudmeow.delightoflight.network;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public final class ClientStardropData {
    private static final Set<UUID> STAR_DROP_EATERS = new HashSet<>();
    private static final Set<UUID> STAR_DROP_COOLDOWNS = new HashSet<>();

    private ClientStardropData() {
    }

    public static void setEaten(UUID playerUUID, boolean eaten) {
        if (eaten) {
            STAR_DROP_EATERS.add(playerUUID);
        } else {
            STAR_DROP_EATERS.remove(playerUUID);
        }
    }

    public static boolean hasEaten(UUID playerUUID) {
        return playerUUID != null && STAR_DROP_EATERS.contains(playerUUID);
    }

    public static void setCooldown(UUID playerUUID, boolean cooldown) {
        if (cooldown) {
            STAR_DROP_COOLDOWNS.add(playerUUID);
        } else {
            STAR_DROP_COOLDOWNS.remove(playerUUID);
        }
    }

    public static boolean isOnCooldown(UUID playerUUID) {
        return playerUUID != null && STAR_DROP_COOLDOWNS.contains(playerUUID);
    }

    public static void clear() {
        STAR_DROP_EATERS.clear();
        STAR_DROP_COOLDOWNS.clear();
    }
}
