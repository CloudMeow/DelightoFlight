package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.item.CookBook;
import com.cloudmeow.delightoflight.utility.DFFoodValue;
import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.item.BirdFeed;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;

public class DFItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DelightoFlight.MOD_ID);

    public static Item.Properties bowlFoodItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(Items.BOWL).stacksTo(16);
    }

    public static Item.Properties drinkItem() {
        return new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16);
    }

    public static final RegistryObject<Item> CLOUD_SILK = ITEMS.register("cloud_silk",
            ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PHANTOM_WING = ITEMS.register("phantom_wing",
            ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> THUNDER_FRUIT = ITEMS.register("thunder_fruit",
            ()-> new Item(new Item.Properties().food(DFFoodValue.THUNDER_FRUIT)));
    public static final RegistryObject<Item> CLOUD_BERRY_STEAMED_BUN = ITEMS.register("cloud_berry_steamed_bun",
            ()-> new Item(new Item.Properties().food(DFFoodValue.CLOUD_BERRY_STEAMED_BUN)));
    public static final RegistryObject<Item> CLOUD_BERRY_PANCAKE = ITEMS.register("cloud_berry_pancake",
            ()-> new Item(new Item.Properties().food(DFFoodValue.CLOUD_BERRY_PANCAKE)));
    public static final RegistryObject<Item> MAGIC_CHEF_HAT = ITEMS.register("magic_chef_hat",
            ()-> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> COPPER_THUNDER_FRUIT = ITEMS.register("copper_thunder_fruit",
            ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BIRD_FEED = ITEMS.register("bird_feed",
            ()-> new BirdFeed(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> CLOUD_BERRY = ITEMS.register("cloud_berries",
            ()-> new ItemNameBlockItem(DFBlocks.CLOUD_BERRY_BUSH.get(), new Item.Properties().food(DFFoodValue.CLOUD_BERRY)));
    public static final RegistryObject<Item> THUNDER_FRUIT_SEED = ITEMS.register("thunder_fruit_seeds",
            ()-> new ItemNameBlockItem(DFBlocks.THUNDER_VINE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ORLEANS_PHANTOM_WING = ITEMS.register("new_orleans_phantom_wing",
            ()-> new ConsumableItem(bowlFoodItem(DFFoodValue.ORLEANS_PHANTOM_WING), true));
    public static final RegistryObject<Item> CLOUD_BERRY_CHEESE = ITEMS.register("cheese_cloud_berry_jam",
            ()-> new ConsumableItem(bowlFoodItem(DFFoodValue.CLOUD_BERRY_CHEESE), true));
    public static final RegistryObject<Item> HAM_SALAD = ITEMS.register("ham_salad",
            ()-> new ConsumableItem(bowlFoodItem(DFFoodValue.HAM_SALAD), true));
    public static final RegistryObject<Item> THUNDER_FRUIT_STEW = ITEMS.register("thunder_fruit_stew",
            ()-> new ConsumableItem(bowlFoodItem(DFFoodValue.THUNDER_FRUIT_STEW), true));
    public static final RegistryObject<Item> RAIN_STEW = ITEMS.register("rain_stew",
            ()-> new ConsumableItem(bowlFoodItem(DFFoodValue.RAIN_STEW), true));
    public static final RegistryObject<Item> MUSHROOM_HOTPOT = ITEMS.register("mushroom_hotpot",
            ()-> new ConsumableItem(bowlFoodItem(DFFoodValue.MUSHROOM_HOTPOT), true));
    public static final RegistryObject<Item> CLOUD_BREAD = ITEMS.register("cloud_bread",
            ()-> new ConsumableItem(new Item.Properties().food(DFFoodValue.CLOUD_BREAD), true));
    public static final RegistryObject<Item> CLOUD = ITEMS.register("cloud",
            ()-> new BlockItem(DFBlocks.CLOUD.get(), new Item.Properties()));
    public static final RegistryObject<Item> CLOUD_SILK_BLOCK = ITEMS.register("cloud_silk_block",
            ()-> new BlockItem(DFBlocks.CLOUD_SILK_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CLOUD_SILK_BED = ITEMS.register("cloud_silk_bed",
            ()-> new BlockItem(DFBlocks.CLOUD_SILK_BED.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CLOUD_BERRY_PANCAKE_TOWER = ITEMS.register("cloud_berry_pancake_tower",
            ()-> new BlockItem(DFBlocks.CLOUD_BERRY_PANCAKE_TOWER.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> THUNDER_FRUIT_STEW_BLOCK = ITEMS.register("thunder_fruit_stew_block",
            ()-> new BlockItem(DFBlocks.THUNDER_FRUIT_STEW_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MUSHROOM_HOTPOT_BLOCK = ITEMS.register("mushroom_hotpot_block",
            ()-> new BlockItem(DFBlocks.MUSHROOM_HOTPOT_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WEATHER_SOIL_FARMLAND = ITEMS.register("weather_soil_farmland",
            ()-> new BlockItem(DFBlocks.WEATHER_SOIL_FARMLAND.get(), new Item.Properties()));
    public static final RegistryObject<Item> WEATHER_SOIL = ITEMS.register("weather_soil",
            ()-> new BlockItem(DFBlocks.WEATHER_SOIL.get(), new Item.Properties()));
    public static final RegistryObject<Item> CLEAR_CLOUDSHROOM = ITEMS.register("clear_cloudshroom",
            ()-> new BlockItem(DFBlocks.CLEAR_CLOUDSHROOM.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAINY_CLOUDSHROOM = ITEMS.register("rainy_cloudshroom",
            ()-> new BlockItem(DFBlocks.RAINY_CLOUDSHROOM.get(), new Item.Properties()));
    public static final RegistryObject<Item> THUNDER_CLOUDSHROOM = ITEMS.register("thunder_cloudshroom",
            ()-> new BlockItem(DFBlocks.THUNDER_CLOUDSHROOM.get(), new Item.Properties()));
    public static final RegistryObject<Item> CLOUDSHROOM_COLONY = ITEMS.register("cloudshroom_colony",
            ()-> new BlockItem(DFBlocks.CLOUDSHROOM_COLONY.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHARGED_ROSE_TEA = ITEMS.register("charged_rose_tea",
            ()-> new DrinkableItem(drinkItem().food(DFFoodValue.CHARGED_ROSE_TEA), true, false));
    public static final RegistryObject<Item> SPARKTRICITY_SODA = ITEMS.register("sparktricity_soda",
            ()-> new DrinkableItem(drinkItem().food(DFFoodValue.SPARKTRICITY_SODA), true, false));
    public static final RegistryObject<Item> COOK_BOOK = ITEMS.register("cook_book",
            ()-> new CookBook(new Item.Properties().stacksTo(1)));
}
