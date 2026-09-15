package com.cloudmeow.delightoflight;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Config
{
    public static ForgeConfigSpec CONFIG;
    public static final ForgeConfigSpec.IntValue THUNDER_DAMAGE;
    public static final ForgeConfigSpec.BooleanValue HURT_PASSIVE_MOBS;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
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
