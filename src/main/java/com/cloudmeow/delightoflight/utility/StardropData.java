package com.cloudmeow.delightoflight.utility;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public final class StardropData {
    public static final String KEY_EATEN = "delighto_flight:stardrop_eater";
    public static final String KEY_COOLDOWN = "delighto_flight:stardrop_cooldown";
    public static final String KEY_RECOVERY = "delighto_flight:stardrop_recovery";

    private StardropData() {
    }

    public static boolean hasEaten(Player player) {
        return player.getPersistentData().getBoolean(KEY_EATEN);
    }

    public static void setEaten(Player player, boolean eaten) {
        player.getPersistentData().putBoolean(KEY_EATEN, eaten);
    }

    public static boolean isOnCooldown(Player player) {
        return player.getPersistentData().getBoolean(KEY_COOLDOWN);
    }

    public static void setCooldown(Player player, boolean cooldown) {
        player.getPersistentData().putBoolean(KEY_COOLDOWN, cooldown);
    }

    public static int getRecovery(Player player) {
        return player.getPersistentData().getInt(KEY_RECOVERY);
    }

    public static void setRecovery(Player player, int recovery) {
        player.getPersistentData().putInt(KEY_RECOVERY, recovery);
    }

    public static void copyTo(Player from, Player to) {
        CompoundTag source = from.getPersistentData();
        CompoundTag target = to.getPersistentData();
        target.putBoolean(KEY_EATEN, source.getBoolean(KEY_EATEN));
        target.putBoolean(KEY_COOLDOWN, source.getBoolean(KEY_COOLDOWN));
        target.putInt(KEY_RECOVERY, source.getInt(KEY_RECOVERY));
    }
}
