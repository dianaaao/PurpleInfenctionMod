package com.purpleinfenctionmod.block;

import com.purpleinfenctionmod.PurpleInfenctionMod;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public class ModBlocks {

    public static final Block INFECTED_DIRT = registerBlock(
        "infected_dirt", 
        new Block(
            FabricBlockSettings.copyOf(Blocks.DIRT)
                .strength(0.5f, 2.5f)
        )
    );
    
    public static final Block INFECTED_GRASS = registerBlock(
        "infected_grass", 
        new InfectedGrassBlock(
            FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK)
                .strength(0.5f, 2.5f)
        )
    );

    public static final Block INFECTED_COBBLESTONE = registerBlock(
        "infected_cobblestone",
        new Block(
            FabricBlockSettings.copyOf(Blocks.COBBLESTONE).strength(2.0f, 62.9f)
        )
    );

    public static final Block INFECTED_OAK_LOG = registerBlock(
        "infected_oak_log",
        new PillarBlock(
            FabricBlockSettings.copyOf(Blocks.OAK_LOG).strength(2.0f, 62.9f)
        )
    );

    public static final Block INFECTED_STONE = registerBlock(
        "infected_stone",
        new Block(
            FabricBlockSettings.copyOf(Blocks.STONE).strength(2.0f, 62.9f)
        )
    );
        

    public static final Block INFECTED_OAK_LEAVES = registerBlock(
        "infected_oak_leaves",
        new LeavesBlock(
            FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).strength(1.0f, 5.0f)
        )
    );

    public static final Block INFECTED_MUSHROOM_STEM = registerBlock(
        "infected_mushroom_stem",
        new PillarBlock(
            FabricBlockSettings.copyOf(Blocks.MUSHROOM_STEM).strength(2.0f, 62.9f)
        )
    );

    public static final Block INFECTED_MUSHROOM_HAT = registerBlock(
        "infected_mushroom_hat",
        new MushroomBlock(
            FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK).strength(2.0f, 62.9f)
        )   
    );

    public static final Block INFECTED_BROWN_MUSHROOM = registerBlock(
        "infected_brown_mushroom_block",
        new MushroomBlock(
            FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK).strength(2.0f, 62.9f)
        )   
    );

    public static final Block INFECTED_SMALL_MUSHROOM = registerBlock(
        "infected_mushroom",
        new MushroomPlantBlock(
            FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM).strength(2.0f, 62.9f), null
        )   
    );

    private static Block registerBlock(String name, Block block){
        Registry.register(
            Registries.BLOCK,
            PurpleInfenctionMod.id(name),
            block
        );

        registerBlockItem(name, block);
        return block;
    }

    private static Item registerBlockItem(String name, Block block){

        return Registry.register(
            Registries.ITEM,
            PurpleInfenctionMod.id(name), 
            new BlockItem(
                block, 
                new Item.Settings()
            )
        );
        
    }

    public static void registerModBlocks() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
            .register(entries -> entries.add(INFECTED_DIRT));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
            .register(entries -> entries.add(INFECTED_GRASS));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
            .register(entries -> entries.add(INFECTED_COBBLESTONE));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
            .register(entries -> entries.add(INFECTED_STONE));          
        
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
            .register(entries -> entries.add(INFECTED_OAK_LOG));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
            .register(entries -> entries.add(INFECTED_OAK_LEAVES));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
            .register(entries -> entries.add(INFECTED_MUSHROOM_STEM));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
            .register(entries -> entries.add(INFECTED_MUSHROOM_HAT));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
            .register(entries -> entries.add(INFECTED_BROWN_MUSHROOM));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
            .register(entries -> entries.add(INFECTED_SMALL_MUSHROOM));

        

        PurpleInfenctionMod.LOGGER.info("Registering Mod Blocks for " + PurpleInfenctionMod.MOD_ID);
    }


}