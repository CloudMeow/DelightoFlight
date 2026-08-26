package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.block.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DFBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, DelightoFlight.MOD_ID);

    public static final Supplier<Block> CLOUD = BLOCKS.register("cloud",
            ()-> new CloudBlock(Block.Properties.ofFullCopy(Blocks.ORANGE_WOOL).strength(0.2f).sound(SoundType.WOOL).noOcclusion()));
    public static final Supplier<Block> CLOUD_SILK_BLOCK = BLOCKS.register("cloud_silk_block",
            ()-> new Block(Block.Properties.ofFullCopy(Blocks.ORANGE_WOOL).strength(0.2f).sound(SoundType.WOOL)));
    public static final Supplier<Block> CLOUD_BERRY_BAG = BLOCKS.register("cloud_berry_bag",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.WHITE_WOOL)));
    public static final Supplier<Block> THUNDER_FRUIT_CRATE = BLOCKS.register("thunder_fruit_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> CLEAR_CLOUDSHROOM = BLOCKS.register("clear_cloudshroom",
            ()-> new CloudshroomBlock(Block.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM)));
    public static final Supplier<Block> RAINY_CLOUDSHROOM = BLOCKS.register("rainy_cloudshroom",
            ()-> new CloudshroomBlock(Block.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM)));
    public static final Supplier<Block> THUNDER_CLOUDSHROOM = BLOCKS.register("thunder_cloudshroom",
            ()-> new CloudshroomBlock(Block.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM)));
    public static final Supplier<Block> CLOUD_SILK_BED = BLOCKS.register("cloud_silk_bed", () -> {
        return new CloudSilkBedBlock(Block.Properties.ofFullCopy(Blocks.BLUE_BED));
    });
    public static final Supplier<Block> CLOUD_BERRY_BUSH = BLOCKS.register("cloud_berry_bush",
            ()-> new CloudBerryBushBlock(Block.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));
    public static final Supplier<Block> THUNDER_VINE = BLOCKS.register("thunder_vine",
            ()-> new ThunderVineBlock(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final Supplier<Block> CLOUD_BERRY_PANCAKE_TOWER = BLOCKS.register("cloud_berry_pancake_tower",
            ()-> new CloudBerryPancakeTowerBlock(Block.Properties.ofFullCopy(Blocks.WHITE_WOOL)));
    public static final Supplier<Block> THUNDER_FRUIT_STEW_BLOCK = BLOCKS.register("thunder_fruit_stew_block",
            ()-> new ThunderFruitStewBlock(BlockBehaviour.Properties.of().strength(0.5F, 6.0F).sound(SoundType.LANTERN)));
    public static final Supplier<Block> MUSHROOM_HOTPOT_BLOCK = BLOCKS.register("mushroom_hotpot_block",
            ()-> new MushroomHotpotBlock(BlockBehaviour.Properties.of().strength(0.5F, 3.0F).sound(SoundType.COPPER)));
    public static final Supplier<Block> LOTUS_LEAF_RICE_BLOCK = BLOCKS.register("lotus_leaf_rice_block",
            ()-> new LotusLeafRiceBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOD)));
    public static final Supplier<Block> WEATHER_SOIL_FARMLAND = BLOCKS.register("weather_soil_farmland",
            ()-> new WeatherSoilFarmlandBlock(Block.Properties.ofFullCopy(Blocks.FARMLAND)));
    public static final Supplier<Block> WEATHER_SOIL = BLOCKS.register("weather_soil",
            ()-> new WeatherSoilBlock(Block.Properties.ofFullCopy(Blocks.FARMLAND)));
    public static final Supplier<Block> CLOUDSHROOM_COLONY = BLOCKS.register("cloudshroom_colony",
            ()-> new CloudshroomColonyBlock(Block.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM)));
    public static final Supplier<Block> ROOTED_MUD = BLOCKS.register("rooted_mud",
            ()-> new RootedMudBlock(Block.Properties.ofFullCopy(Blocks.MUD).sound(SoundType.MUD).randomTicks()));
    public static final Supplier<Block> LOTUS_RHIZOME = BLOCKS.register("lotus_rhizome",
            ()-> new LotusRhizomeBlock(Block.Properties.ofFullCopy(Blocks.BIG_DRIPLEAF_STEM).sound(SoundType.BIG_DRIPLEAF)));
    public static final Supplier<Block> LOTUS_BUD = BLOCKS.register("lotus_bud",
            ()-> new LotusBudBlock(Block.Properties.ofFullCopy(Blocks.BIG_DRIPLEAF_STEM).sound(SoundType.BIG_DRIPLEAF)));
    public static final Supplier<Block> LOTUS_FLOWER = BLOCKS.register("lotus_flower",
            ()-> new LotusFlowerBlock(Block.Properties.ofFullCopy(Blocks.BIG_DRIPLEAF_STEM).sound(SoundType.BIG_DRIPLEAF)));
    public static final Supplier<Block> DECORATIVE_LOTUS_FLOWER = BLOCKS.register("decorative_lotus_flower",
            ()-> new WaterlilyBlock(Block.Properties.ofFullCopy(Blocks.BIG_DRIPLEAF_STEM).instabreak().sound(SoundType.LILY_PAD).noOcclusion()));
    public static final Supplier<Block> WILD_LOTUS = BLOCKS.register("wild_lotus",
            ()-> new WildLotusBlock(Block.Properties.ofFullCopy(Blocks.TALL_GRASS)));
    public static final Supplier<Block> COTTON_CANDY_MACHINE = BLOCKS.register("cotton_candy_machine",
            ()-> new CottonCandyMachineBlock(Block.Properties.ofFullCopy(Blocks.BRICKS).noOcclusion()));
}
