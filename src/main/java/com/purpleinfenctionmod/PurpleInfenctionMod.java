package com.purpleinfenctionmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.GeckoLib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.purpleinfenctionmod.block.ModBlocks;
import com.purpleinfenctionmod.entity.ModEntities;
import com.purpleinfenctionmod.entity.MushroomMobEntity;
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
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		GeckoLib.initialize();
		
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		BiomeEffectHandler.register();

		FabricDefaultAttributeRegistry.register(ModEntities.MUSHROOM_MOB, MushroomMobEntity.createAttributes());

		LOGGER.info("Hello Fabric world!");
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
