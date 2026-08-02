package com.purpleinfenctionmod.entity;

import com.purpleinfenctionmod.PurpleInfenctionMod;
import com.purpleinfenctionmod.entity.infected.InfectedCreeperEntity;
import com.purpleinfenctionmod.entity.infected.InfectedSkeletonEntity;
import com.purpleinfenctionmod.entity.infected.InfectedZombieEntity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<MushroomMobEntity> MUSHROOM_MOB = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier("purpleinfenctionmod", "mushroom_mob"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MushroomMobEntity::new)
                    .dimensions(EntityDimensions.changing(0.6f, 1.95f))
                    .build()
    );

    public static final EntityType<InfectedZombieEntity> INFECTED_ZOMBIE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(PurpleInfenctionMod.MOD_ID, "infected_zombie"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, InfectedZombieEntity::new)
                    .dimensions(EntityDimensions.changing(0.6f, 1.95f))
                    .build());

    public static final EntityType<InfectedSkeletonEntity> INFECTED_SKELETON = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(PurpleInfenctionMod.MOD_ID, "infected_skeleton"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, InfectedSkeletonEntity::new)
                    .dimensions(EntityDimensions.changing(0.6f, 1.99f))
                    .build());

    public static final EntityType<InfectedCreeperEntity> INFECTED_CREEPER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(PurpleInfenctionMod.MOD_ID, "infected_creeper"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, InfectedCreeperEntity::new)
                    .dimensions(EntityDimensions.changing(0.6f, 1.7f))
                    .build());

    public static void register() {
        // регистрация выполняется через статические поля выше
    }
}