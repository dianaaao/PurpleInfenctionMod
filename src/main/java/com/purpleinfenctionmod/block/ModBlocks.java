package com.purpleinfenctionmod.block;

import com.purpleinfenctionmod.PurpleInfenctionMod;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BigDripleafBlock;
import net.minecraft.block.BigDripleafStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlowLichenBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.block.SmallDripleafBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public class ModBlocks {
    private static final java.util.Set<Block> INFECTED_BLOCKS = new java.util.HashSet<>();

    public static boolean isInfectedBlock(Block block) {
        return INFECTED_BLOCKS.contains(block);
    }

    public static final Block INFECTED_DIRT = registerBlock(
        "infected_dirt", 
        new Block(
            FabricBlockSettings.copyOf(Blocks.DIRT)
                .strength(0.5f, 2.5f)
        )
    );
    public static final Block FIRE_FLOWER = registerBlock(
        "fire_flower", 
        new Block(
            FabricBlockSettings.copyOf(Blocks.AZURE_BLUET)
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
            FabricBlockSettings.copyOf(Blocks.COBBLESTONE)
        )
    );
    public static final Block INFECTED_PLANKS = registerBlock(
        "infected_oak_planks",
        new Block(
            FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
        )
    );

    public static final Block INFECTED_OAK_LOG = registerBlock(
        "infected_oak_log",
        new PillarBlock(
            FabricBlockSettings.copyOf(Blocks.OAK_LOG)
        )
    );

    public static final Block INFECTED_IRON_ORE = registerBlock(
        "ores/infected_iron_ore",
        new Block(
            FabricBlockSettings.copyOf(Blocks.IRON_ORE)
            .strength(3.5f, 3.0f)
        )
    );
    public static final Block INFECTED_COAL_ORE = registerBlock(
        "ores/infected_coal_ore",
        new Block(
            FabricBlockSettings.copyOf(Blocks.COAL_ORE)
            .strength(3.5f, 3.0f)
        )
    );
    public static final Block INFECTED_COPPER_ORE = registerBlock(
        "ores/infected_copper_ore",
        new Block(
            FabricBlockSettings.copyOf(Blocks.COPPER_ORE)
            .strength(3.5f, 3.0f)
        )
    );
    public static final Block INFECTED_DIAMOND_ORE = registerBlock(
        "ores/infected_diamond_ore",
        new Block(
            FabricBlockSettings.copyOf(Blocks.DIAMOND_ORE)
            .strength(3.5f, 3.0f)
        )
    );
    public static final Block INFECTED_EMERALD_ORE = registerBlock(
        "ores/infected_emerald_ore",
        new Block(
            FabricBlockSettings.copyOf(Blocks.EMERALD_ORE)
            .strength(3.5f, 3.0f)
        )
    );
    public static final Block INFECTED_LAPIS_ORE = registerBlock(
        "ores/infected_lapis_ore",
        new Block(
            FabricBlockSettings.copyOf(Blocks.LAPIS_ORE)
            .strength(3.5f, 3.0f)
        )
    );
    public static final Block INFECTED_REDSTONE_ORE = registerBlock(
        "ores/infected_redstone_ore",
        new RedstoneOreBlock(
            FabricBlockSettings.copyOf(Blocks.REDSTONE_ORE)
            .strength(3.5f, 3.0f)
        )
    );
    public static final Block INFECTED_GOLD_ORE = registerBlock(
        "ores/infected_gold_ore",
        new Block(
            FabricBlockSettings.copyOf(Blocks.GOLD_ORE)
            .strength(3.5f, 3.0f)
        )
    );
    public static final Block INFECTED_DEEPSLATE_IRON_ORE = registerBlock(
        "ores/infected_deepslate_iron_ore",
        new Block(
            FabricBlockSettings.copyOf(Blocks.IRON_ORE)
            .strength(5.0f, 3.0f)
        )
    );
    public static final Block INFECTED_DEEPSLATE_COAL_ORE = registerBlock(
        "ores/infected_deepslate_coal_ore",
        new Block(
            FabricBlockSettings.copyOf(Blocks.COAL_ORE)
            .strength(5.0f, 3.0f)
        )
    );
    public static final Block INFECTED_DEEPSLATE_COPPER_ORE = registerBlock(
        "ores/infected_deepslate_copper_ore",
        new Block(
            FabricBlockSettings.copyOf(Blocks.COPPER_ORE)
            .strength(5.0f, 3.0f)
        )
    );
    public static final Block INFECTED_DEEPSLATE_DIAMOND_ORE = registerBlock(
        "ores/infected_deepslate_diamond_ore",
        new Block(
            FabricBlockSettings.copyOf(Blocks.DIAMOND_ORE)
            .strength(5.0f, 3.0f)
        )
    );
    public static final Block INFECTED_DEEPSLATE_EMERALD_ORE = registerBlock(
        "ores/infected_deepslate_emerald_ore",
        new Block(
            FabricBlockSettings.copyOf(Blocks.EMERALD_ORE)
            .strength(5.0f, 3.0f)
        )
    );
    public static final Block INFECTED_DEEPSLATE_LAPIS_ORE = registerBlock(
        "ores/infected_deepslate_lapis_ore",
        new Block(
            FabricBlockSettings.copyOf(Blocks.LAPIS_ORE)
            .strength(5.0f, 3.0f)
        )
    );
    public static final Block INFECTED_DEEPSLATE_REDSTONE_ORE = registerBlock(
        "ores/infected_deepslate_redstone_ore",
        new RedstoneOreBlock(
            FabricBlockSettings.copyOf(Blocks.REDSTONE_ORE)
            .strength(5.0f, 3.0f)
        )
    );
    public static final Block INFECTED_DEEPSLATE_GOLD_ORE = registerBlock(
        "ores/infected_deepslate_gold_ore",
        new Block(
            FabricBlockSettings.copyOf(Blocks.GOLD_ORE)
            .strength(5.0f, 3.0f)
        )
    );
    public static final Block INFECTED_STONE = registerBlock(
        "infected_stone",
        new Block(
            FabricBlockSettings.copyOf(Blocks.STONE)
        )
    );
    public static final Block INFECTED_DEEPSLATE = registerBlock(
        "infected_deepslate",
        new Block(
            FabricBlockSettings.copyOf(Blocks.DEEPSLATE)
        )
    );
    public static final InfectedCaveVinesBlock INFECTED_CAVE_VINES = Registry.register(
        Registries.BLOCK,
        PurpleInfenctionMod.id("infected_cave_vines"),
        new InfectedCaveVinesBlock(
                AbstractBlock.Settings.copy(Blocks.CAVE_VINES)
        )
);

    public static final InfectedCaveVinesPlantBlock INFECTED_CAVE_VINES_PLANT = Registry.register(
        Registries.BLOCK,
        PurpleInfenctionMod.id("infected_cave_vines_plant"),
        new InfectedCaveVinesPlantBlock(
                AbstractBlock.Settings.copy(Blocks.CAVE_VINES_PLANT)
        )
);
public static final Block INFECTED_SMALL_DRIPLEAF = registerBlock(
    "infected_small_dripleaf",
    new SmallDripleafBlock(
        FabricBlockSettings.copyOf(Blocks.SMALL_DRIPLEAF)
    )
);
        

    public static final Block INFECTED_OAK_LEAVES = registerBlock(
        "infected_oak_leaves",
        new LeavesBlock(
            FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)
        )
    );
 
    public static final Block INFECTED_MUSHROOM_STEM = registerBlock(
        "infected_mushroom_stem",
        new PillarBlock(
            FabricBlockSettings.copyOf(Blocks.MUSHROOM_STEM)
        )
    );

    public static final Block INFECTED_MUSHROOM_HAT = registerBlock(
        "infected_mushroom_hat",
        new MushroomBlock(
            FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK)
        )   
    );

    public static final Block INFECTED_BROWN_MUSHROOM = registerBlock(
        "infected_brown_mushroom_block",
        new MushroomBlock(
            FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK)
        )   
    );

    public static final Block INFECTED_SMALL_MUSHROOM = registerBlock(
        "infected_mushroom",
        new MushroomPlantBlock(
            FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM), null
        )   
    );
    public static final Block INFECTED_DRIPLEAF = registerBlock(
        "infected_dripleaf",
        new BigDripleafBlock(
            FabricBlockSettings.copyOf(Blocks.BIG_DRIPLEAF)
        )
    );

    public static final Block INFECTED_DRIPLEAF_STEM = registerBlock(
        "infected_dripleaf_stem",
        new BigDripleafStemBlock(
            FabricBlockSettings.copyOf(Blocks.BIG_DRIPLEAF_STEM)
        )
    );

    public static final Block INFECTED_GLOW_LICHEN = registerBlock(
        "infected_glow_lichen",
        new GlowLichenBlock(
            FabricBlockSettings.copyOf(Blocks.GLOW_LICHEN)
        )
    );


    private static Block registerBlock(String name, Block block){
        Registry.register(
            Registries.BLOCK,
            PurpleInfenctionMod.id(name),
            block
        );

        registerBlockItem(name, block);
        INFECTED_BLOCKS.add(block);
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
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(INFECTED_DIRT);
            entries.add(INFECTED_GRASS);
            entries.add(INFECTED_COBBLESTONE);
            entries.add(INFECTED_STONE);
            entries.add(INFECTED_OAK_LOG);
            entries.add(INFECTED_PLANKS);
            entries.add(INFECTED_OAK_LEAVES);
            entries.add(INFECTED_MUSHROOM_STEM);
            entries.add(INFECTED_MUSHROOM_HAT);
            entries.add(INFECTED_BROWN_MUSHROOM);
            entries.add(INFECTED_SMALL_MUSHROOM);
            entries.add(FIRE_FLOWER);
            entries.add(INFECTED_GLOW_LICHEN);
            entries.add(INFECTED_DRIPLEAF);
            entries.add(INFECTED_SMALL_DRIPLEAF);
            entries.add(INFECTED_COAL_ORE);
            entries.add(INFECTED_GOLD_ORE);
            entries.add(INFECTED_IRON_ORE);
            entries.add(INFECTED_DIAMOND_ORE);
            entries.add(INFECTED_COPPER_ORE);
            entries.add(INFECTED_LAPIS_ORE);
            entries.add(INFECTED_REDSTONE_ORE);
            entries.add(INFECTED_EMERALD_ORE);
            entries.add(INFECTED_DEEPSLATE_COAL_ORE);
            entries.add(INFECTED_DEEPSLATE_GOLD_ORE);
            entries.add(INFECTED_DEEPSLATE_IRON_ORE);
            entries.add(INFECTED_DEEPSLATE_DIAMOND_ORE);
            entries.add(INFECTED_DEEPSLATE_COPPER_ORE);
            entries.add(INFECTED_DEEPSLATE_LAPIS_ORE);
            entries.add(INFECTED_DEEPSLATE_REDSTONE_ORE);
            entries.add(INFECTED_DEEPSLATE_EMERALD_ORE);
        });
            
        PurpleInfenctionMod.LOGGER.info("Registering Mod Blocks for " + PurpleInfenctionMod.MOD_ID);
    }


}