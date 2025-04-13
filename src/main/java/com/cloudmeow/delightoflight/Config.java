package com.cloudmeow.delightoflight;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
    public static ModConfigSpec CONFIG;
    public static final ModConfigSpec.IntValue THUNDER_DAMAGE;

    static {
        ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        BUILDER.comment("Delight'o Flight Setting").push("Settings");;
        THUNDER_DAMAGE = BUILDER
            .comment("The damage of current from arc effect.")
            .defineInRange("thunderDamage", 3, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        CONFIG = BUILDER.build();
    }
}
