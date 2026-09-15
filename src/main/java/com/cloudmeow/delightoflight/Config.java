package com.cloudmeow.delightoflight;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
    public static ModConfigSpec CONFIG;
    public static final ModConfigSpec.IntValue THUNDER_DAMAGE;
    public static final ModConfigSpec.BooleanValue HURT_PASSIVE_MOBS;

    static {
        ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
        BUILDER.comment("Delight'o Flight Setting").push("Settings");

        THUNDER_DAMAGE = BUILDER
            .comment("The damage of current from arc effect.")
            .defineInRange("thunderDamage", 3, 0, Integer.MAX_VALUE);

        HURT_PASSIVE_MOBS = BUILDER
            .comment("Whether the current damages passive mobs")
            .define("hurtPassiveMobs", true);

        BUILDER.pop();
        CONFIG = BUILDER.build();
    }
}
