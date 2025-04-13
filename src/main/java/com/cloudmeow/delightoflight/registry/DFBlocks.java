package com.cloudmeow.delightoflight.registry;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DFBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DelightoFlight.MOD_ID);

    public static final RegistryObject<Block> CLOUD = BLOCKS.register("cloud",
            ()-> new CloudBlock(Block.Properties.copy(Blocks.ORANGE_WOOL).strength(0.2f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> CLOUD_SILK_BLOCK = BLOCKS.register("cloud_silk_block",
            ()-> new Block(Block.Properties.copy(Blocks.ORANGE_WOOL).strength(0.2f).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> CLOUD_SILK_BED = BLOCKS.register("cloud_silk_bed", () -> {
        return new CloudSilkBedBlock(Block.Properties.copy(Blocks.BLUE_BED));
    });
    public static final RegistryObject<Block> CLOUD_BERRY_BUSH = BLOCKS.register("cloud_berry_bush",
            ()-> new CloudBerryBushBlock(Block.Properties.copy(Blocks.SWEET_BERRY_BUSH)));
    public static final RegistryObject<Block> THUNDER_VINE = BLOCKS.register("thunder_vine",
            ()-> new ThunderVineBlock(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> CLOUD_BERRY_PANCAKE_TOWER = BLOCKS.register("cloud_berry_pancake_tower",
            ()-> new CloudBerryPancakeTowerBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));
    public static final RegistryObject<Block> THUNDER_FRUIT_STEW_BLOCK = BLOCKS.register("thunder_fruit_stew_block",
            ()-> new ThunderFruitStewBlock(BlockBehaviour.Properties.of().strength(0.5F, 6.0F).sound(SoundType.LANTERN)));
}
