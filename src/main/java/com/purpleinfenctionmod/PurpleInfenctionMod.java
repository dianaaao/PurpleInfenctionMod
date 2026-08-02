package com.purpleinfenctionmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.purpleinfenctionmod.block.ModBlocks;
import com.purpleinfenctionmod.item.ModItems;
import com.purpleinfenctionmod.world.BiomeEffectHandler;

public class PurpleInfenctionMod implements ModInitializer {
	public static final String MOD_ID = "purpleinfenctionmod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		RegistryKey<PlacedFeature> INFECTED_CAVE_VINES_PLACED = RegistryKey.of(
                RegistryKeys.PLACED_FEATURE,
                new Identifier(MOD_ID, "infected_cave_vines")
        );
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		
		
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		BiomeEffectHandler.register();

		LOGGER.info("Hello Fabric world!");
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
