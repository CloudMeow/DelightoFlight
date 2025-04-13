package com.cloudmeow.delightoflight;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Config
{
    public static ForgeConfigSpec CONFIG;
    public static final ForgeConfigSpec.IntValue THUNDER_DAMAGE;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        BUILDER.comment("Delight'o Flight Setting").push("Settings");;
        THUNDER_DAMAGE = BUILDER
            .comment("The damage of current from arc effect.")
            .defineInRange("thunderDamage", 3, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        CONFIG = BUILDER.build();
    }
}
