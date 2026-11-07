package com.cloudmeow.delightoflight;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
    public static ModConfigSpec CONFIG;
    public static final ModConfigSpec.IntValue THUNDER_DAMAGE;
    public static final ModConfigSpec.BooleanValue HURT_PASSIVE_MOBS;
    public static final ModConfigSpec.IntValue STARDROP_RECOVERY_TICKS;

    static {
        ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
        BUILDER.comment("Delight'o Flight Setting").push("Settings");

        THUNDER_DAMAGE = BUILDER
            .comment("The damage of current from arc effect.")
            .defineInRange("thunderDamage", 3, 0, Integer.MAX_VALUE);

        HURT_PASSIVE_MOBS = BUILDER
            .comment("Whether the current damages passive mobs")
            .define("hurtPassiveMobs", true);

        STARDROP_RECOVERY_TICKS = BUILDER
            .comment("Ticks of night-sky exposure required to recover the stardrop protection. Default 6000 (5 minutes).")
            .defineInRange("stardropRecoveryTicks", 6000, 0, Integer.MAX_VALUE);

        BUILDER.pop();
        CONFIG = BUILDER.build();
    }
}
