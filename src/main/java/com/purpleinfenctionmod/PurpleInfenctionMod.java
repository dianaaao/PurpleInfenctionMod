package com.purpleinfenctionmod;

import com.purpleinfenctionmod.block.ModBlocks;
import com.purpleinfenctionmod.component.ModComponents;
import com.purpleinfenctionmod.component.PlayerInfectedPowerComponent;
import com.purpleinfenctionmod.effect.InfectedLookEffect;
import com.purpleinfenctionmod.effect.ModEffects;
import com.purpleinfenctionmod.effect.ModPotions;
import com.purpleinfenctionmod.entity.ModEntities;
import com.purpleinfenctionmod.entity.MushroomMobEntity;
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
import com.purpleinfenctionmod.world.biome.ModBiomeModifiers;
import com.purpleinfenctionmod.world.biome.RingBiomeSource;
import com.purpleinfenctionmod.world.feature.ModFeatures;
import com.purpleinfenctionmod.world.structure.CastleNightManager;
import com.purpleinfenctionmod.world.structure.ModStructures;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.bernie.geckolib.GeckoLib;

public class PurpleInfenctionMod implements ModInitializer {

    public static final String MOD_ID = "purpleinfenctionmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        GeckoLib.initialize();


        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
        ModItemGroups.registerItemGroups();

        ModEffects.registerEffects();
        ModPotions.registerPotions();

        ModFeatures.registerFeatures();
        ModStructures.registerStructures();
        ModBiomeModifiers.register();


        Registry.register(
                Registries.BIOME_SOURCE,
                id("ring_biome_source"),
                RingBiomeSource.CODEC
        );


        RegistryKey.of(
                RegistryKeys.PLACED_FEATURE,
                id("infected_cave_vines")
        );


        BrewingRecipeRegistry.registerItemRecipe(
                Items.POTION,
                ModBlocks.FIRE_FLOWER.asItem(),
                ModItems.DISINFECTANT_POTION
        );


        registerEntityAttributes();

        InfectionHandler.register();
        PlacedBlockDecayHandler.register();
        MushroomBreakHandler.register();
        PoisonCloudManager.register();

        registerServerEvents();


        LOGGER.info("Purple Infection Mod initialized.");
    }


    private static void registerEntityAttributes() {

        FabricDefaultAttributeRegistry.register(
                ModEntities.MUSHROOM_MOB,
                MushroomMobEntity.createAttributes()
        );

        FabricDefaultAttributeRegistry.register(
                ModEntities.ROTTING_SPORE_FUNGUS,
                RottingSporeFungusEntity.createAttributes()
        );

        FabricDefaultAttributeRegistry.register(
                ModEntities.SPORE_CREATURE,
                SporeCreatureEntity.createAttributes()
        );

        FabricDefaultAttributeRegistry.register(
                ModEntities.INFECTED_ZOMBIE,
                InfectedZombieEntity.createAttributes()
        );

        FabricDefaultAttributeRegistry.register(
                ModEntities.INFECTED_SKELETON,
                InfectedSkeletonEntity.createAttributes()
        );

        FabricDefaultAttributeRegistry.register(
                ModEntities.INFECTED_CREEPER,
                InfectedCreeperEntity.createAttributes()
        );
    }


    private static void registerServerEvents() {

        /*
         * Biome-related server initialization
         */
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {

            BiomeEffectHandler.register();

            LOGGER.info("BiomeEffectHandler registered.");

            var world = server.getOverworld();

            var biomeSource =
                    world.getChunkManager()
                            .getChunkGenerator()
                            .getBiomeSource();

            LOGGER.info(
                    "[InfectedDiag] Real biome source class: {}",
                    biomeSource.getClass().getName()
            );
        });


        /*
         * Player infection-look effect
         */
        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayerEntity player :
                    server.getPlayerManager().getPlayerList()) {

                if (!player.hasStatusEffect(ModEffects.INFECTED_LOOK)) {
                    InfectedLookEffect.removeTarget(player);
                }
            }
        });


        /*
         * Castle night system
         */
        ServerTickEvents.END_WORLD_TICK.register(
                CastleNightManager::tick
        );


        /*
         * Player component initialization
         */
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {

            if (entity instanceof PlayerEntity player) {

                PlayerInfectedPowerComponent component =
                        (PlayerInfectedPowerComponent)
                                ModComponents.INFECTED_POWER.get(player);

                component.applyHealthBonus();
            }
        });
    }


    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}