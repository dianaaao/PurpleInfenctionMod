package com.purpleinfenctionmod.item;

import com.purpleinfenctionmod.PurpleInfenctionMod;
import com.purpleinfenctionmod.block.ModBlocks;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItemGroups {
    public static final ItemGroup PURPLE_INFECTION = Registry.register(
        Registries.ITEM_GROUP,
        PurpleInfenctionMod.id("purple_infection"),
        FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.purpleinfenctionmod.purple_infection"))
            .icon(() -> new ItemStack(ModBlocks.INFECTED_SMALL_MUSHROOM))
            .entries((displayContext, entries) -> {

                // ===== ПРЕДМЕТЫ =====

                entries.add(ModItems.RESPIRATOR);
                entries.add(ModItems.CRYSTAL_RESPIRATOR);
                entries.add(ModItems.CRYSTAL_UPGRADE_TEMPLATE);
                entries.add(ModItems.CRYSTAL_SPLINTER);
                entries.add(ModItems.INFECTED_GLOW_BERRY);
                entries.add(ModItems.DISINFECTANT_POTION);
                entries.add(ModItems.INFECTED_BOWL);
                entries.add(ModItems.INFECTED_STEW);
                entries.add(ModItems.RESPIRATOR_FIX);
                entries.add(ModItems.FRAGMENT_OF_OLD_CRYSTAL);

                
                entries.add(ModItems.CRYSTAL_HELMET);
                entries.add(ModItems.CRYSTAL_BIB);
                entries.add(ModItems.CRYSTAL_TROUSERS);
                entries.add(ModItems.CRYSTAL_BOOTS);
                entries.add(CrystalSwordAndSaber.getEnchantedSword());
                entries.add(CrystalSwordAndSaber.getEnchantedSaber());
                entries.add(ModItems.CRYSTAL_MAGIC_STAFF);
                
                // ===== БЛОКИ =====
                
                entries.add(ModBlocks.INFECTED_DIRT);
                entries.add(ModBlocks.INFECTED_GRASS);
                entries.add(ModBlocks.INFECTED_COBBLESTONE);
                entries.add(ModBlocks.INFECTED_STONE);
                entries.add(ModBlocks.INFECTED_OAK_LOG);
                entries.add(ModBlocks.INFECTED_PLANKS);
                entries.add(ModBlocks.INFECTED_OAK_LEAVES);
                entries.add(ModBlocks.INFECTED_MUSHROOM_STEM);
                entries.add(ModBlocks.INFECTED_MUSHROOM_HAT);
                entries.add(ModBlocks.INFECTED_BROWN_MUSHROOM);
                entries.add(ModBlocks.INFECTED_SMALL_MUSHROOM);
                entries.add(ModBlocks.FIRE_FLOWER);
                entries.add(ModBlocks.INFECTED_GLOW_LICHEN);
                entries.add(ModBlocks.INFECTED_DRIPLEAF);
                entries.add(ModBlocks.INFECTED_SMALL_DRIPLEAF);

                

                // ===== СПАВН-ЯЙЦА =====

                entries.add(ModItems.MUSHROOM_MOB_SPAWN_EGG);
                entries.add(ModItems.ROTTING_SPORE_FUNGUS_SPAWN_EGG);
                entries.add(ModItems.INFECTED_ZOMBIE_SPAWN_EGG);
                entries.add(ModItems.INFECTED_SKELETON_SPAWN_EGG);
                entries.add(ModItems.INFECTED_CREEPER_SPAWN_EGG);
                entries.add(ModItems.INFECTED_VEX_SPAWN_EGG);
                entries.add(ModItems.PIGEON_SPAWN_EGG);
            })
            .build()
    );

    public static void registerItemGroups() {
        PurpleInfenctionMod.LOGGER.info(
            "Registering Item Groups for " + PurpleInfenctionMod.MOD_ID
        );
    }
}
