package com.purpleinfenctionmod.entity.infected;

import com.purpleinfenctionmod.entity.ModEntities;
import com.purpleinfenctionmod.world.biome.ModBiomes;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class InfectionHandler {

    private static final Map<ServerWorld, Integer> tickCounters = new IdentityHashMap<>();
    private static final int CHECK_INTERVAL = 20;

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> tryInfect(world, entity));

        ServerTickEvents.END_WORLD_TICK.register(world -> {
            int count = tickCounters.getOrDefault(world, 0) + 1;
            if (count < CHECK_INTERVAL) {
                tickCounters.put(world, count);
                return;
            }
            tickCounters.put(world, 0);
            checkAndInfectEntities(world);
        });
    }

    private static void checkAndInfectEntities(ServerWorld world) {
        List<Entity> candidates = new ArrayList<>();
        for (Entity entity : world.iterateEntities()) {
            if (isEligible(entity)) candidates.add(entity);
        }
        for (Entity entity : candidates) {
            tryInfect(world, entity);
        }
    }

    private static boolean isEligible(Entity entity) {
        if (entity.getClass() == ZombieEntity.class) {
            return !((ZombieEntity) entity).isBaby();
        }
        if (entity.getClass() == SkeletonEntity.class) {
            return true;
        }
        return entity.getClass() == CreeperEntity.class;
    }

    private static void tryInfect(net.minecraft.world.World world, Entity entity) {
        if (!(world instanceof ServerWorld serverWorld)) return;
        if (!isEligible(entity)) return;
        if (!isInInfectedBiome(entity)) return;

        if (entity.getClass() == ZombieEntity.class) {
            convertAndInfect(serverWorld, (MobEntity) entity, ModEntities.INFECTED_ZOMBIE);
        } else if (entity.getClass() == SkeletonEntity.class) {
            convertAndInfect(serverWorld, (MobEntity) entity, ModEntities.INFECTED_SKELETON);
        } else if (entity.getClass() == CreeperEntity.class) {
            convertAndInfect(serverWorld, (MobEntity) entity, ModEntities.INFECTED_CREEPER);
        }
    }

    private static boolean isInInfectedBiome(Entity entity) {
        return entity.getWorld().getBiome(entity.getBlockPos()).matchesKey(ModBiomes.INFECTED_KEY);
    }

    private static <T extends MobEntity> void convertAndInfect(ServerWorld world, MobEntity original, EntityType<T> newType) {
        float currentHealth = original.getHealth();
        float maxHealth = original.getMaxHealth();

        T replacement = original.convertTo(newType, true);
        if (replacement == null) return;

        float newMaxHealth = (float) replacement.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);
        float ratio = maxHealth > 0 ? currentHealth / maxHealth : 1.0f;
        replacement.setHealth(Math.max(1.0f, newMaxHealth * ratio));
    }
}