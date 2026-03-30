package com.cloudmeow.delightoflight.utility;

import com.cloudmeow.delightoflight.DelightoFlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class DFTags {
    public static final TagKey<EntityType<?>> BIRD_FEED_USERS = modEntityTag("bird_feed_users");

    public static final TagKey<Block> MUSHROOM = modBlockTag("mushroom");

    public static final TagKey<Item> AEROLOPE_FOOD = modItemTag("aerolope_food");

    private static TagKey<EntityType<?>> modEntityTag(String path) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, path));
    }

    private static TagKey<Block> modBlockTag(String path) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, path));
    }

    private static TagKey<Item> modItemTag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, path));
    }
}
