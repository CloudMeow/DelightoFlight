package com.cloudmeow.delightoflight.utility;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class DFTags {
    public static final TagKey<EntityType<?>> BIRD_FEED_USERS = modEntityTag("bird_feed_users");

    private static TagKey<EntityType<?>> modEntityTag(String path) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(DelightoFlight.MOD_ID, path));
    }
}
