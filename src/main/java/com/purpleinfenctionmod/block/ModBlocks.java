package com.purpleinfenctionmod.block;

import com.purpleinfenctionmod.PurpleInfenctionMod;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
                .strength(3.0f, 3.0f)
        )
    );
    
    public static final Block INFECTED_GRASS = registerBlock(
        "infected_grass", 
        new Block(
            FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK)
                .strength(3.0f, 3.0f)
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
            .register(entries -> entries.add(INFECTED_GRASS));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
            .register(entries -> entries.add(INFECTED_DIRT));

        PurpleInfenctionMod.LOGGER.info("Registering Mod Blocks for " + PurpleInfenctionMod.MOD_ID);
    }


}