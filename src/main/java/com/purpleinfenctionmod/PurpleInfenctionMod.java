package com.purpleinfenctionmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.GeckoLib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import com.purpleinfenctionmod.block.ModBlocks;
import net.minecraft.registry.Registry;

import com.purpleinfenctionmod.effect.InfectedLookEffect;
import com.purpleinfenctionmod.effect.ModEffects;
import com.purpleinfenctionmod.effect.ModPotions;
import com.purpleinfenctionmod.entity.ModEntities;
import com.purpleinfenctionmod.entity.MushroomMobEntity;
import com.purpleinfenctionmod.entity.MushroomPetEntity;
import com.purpleinfenctionmod.entity.PoisonCloudManager;
import com.purpleinfenctionmod.entity.RottingSporeFungusEntity;
import com.purpleinfenctionmod.entity.SporeCreatureEntity;
import com.purpleinfenctionmod.entity.infected.InfectedCreeperEntity;
import com.purpleinfenctionmod.entity.infected.InfectedSkeletonEntity;
import com.purpleinfenctionmod.entity.infected.InfectedZombieEntity;
import com.purpleinfenctionmod.entity.infected.InfectionHandler;
import com.purpleinfenctionmod.item.ModItemGroups;
import com.purpleinfenctionmod.item.ModItems;
import com.purpleinfenctionmod.world.MushroomBreakHandler;
import com.purpleinfenctionmod.world.PlacedBlockDecayHandler;
import com.purpleinfenctionmod.world.biome.BiomeEffectHandler;
import com.purpleinfenctionmod.world.biome.RingBiomeSource;
import com.purpleinfenctionmod.world.feature.ModFeatures;
import com.purpleinfenctionmod.world.structure.CastleNightManager;
import com.purpleinfenctionmod.world.structure.ModStructures;


public class PurpleInfenctionMod implements ModInitializer {
	public static final String MOD_ID = "purpleinfenctionmod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		RegistryKey.of(
                RegistryKeys.PLACED_FEATURE,
                new Identifier(MOD_ID, "infected_cave_vines")
        );
		Registry.register(Registries.BIOME_SOURCE, new Identifier(MOD_ID, "ring_biome_source"), RingBiomeSource.CODEC);
		ModEffects.registerEffects();
        ModFeatures.registerFeatures();
		BrewingRecipeRegistry.registerItemRecipe(
			Items.POTION, // The base item placed in bottom slots (e.g., Water Bottle)
			ModBlocks.FIRE_FLOWER.asItem(), // The item put in top slot
			ModItems.DISINFECTANT_POTION // Your registered potion item
		);
		ModPotions.registerPotions();
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		GeckoLib.initialize();
		InfectionHandler.register();
		PlacedBlockDecayHandler.register();
		MushroomBreakHandler.register();
		PoisonCloudManager.register();
		
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		// BiomeEffectHandler.register();

		FabricDefaultAttributeRegistry.register(ModEntities.MUSHROOM_MOB, MushroomMobEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ROTTING_SPORE_FUNGUS, RottingSporeFungusEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.SPORE_CREATURE, SporeCreatureEntity.createAttributes());

		FabricDefaultAttributeRegistry.register(ModEntities.INFECTED_ZOMBIE, InfectedZombieEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.INFECTED_SKELETON, InfectedSkeletonEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.INFECTED_CREEPER, InfectedCreeperEntity.createAttributes());

		FabricDefaultAttributeRegistry.register(ModEntities.MUSHROOM_PET, MushroomPetEntity.createAttributes());

		ModStructures.registerStructures();
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			BiomeEffectHandler.register();
			LOGGER.info("BiomeEffectHandler registered after server start");
			var world = server.getOverworld();
			var biomeSource = world.getChunkManager().getChunkGenerator().getBiomeSource();
			PurpleInfenctionMod.LOGGER.info("[InfectedDiag] Real biome source class: " + biomeSource.getClass().getName());
		});
		ServerTickEvents.END_SERVER_TICK.register(server -> {

			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

				if (!player.hasStatusEffect(ModEffects.INFECTED_LOOK)) {

					InfectedLookEffect.removeTarget(player);
				}
			}
			
		});
		ServerTickEvents.END_WORLD_TICK.register(
				CastleNightManager::tick
		);
		LOGGER.info("Hello Fabric world!");
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
