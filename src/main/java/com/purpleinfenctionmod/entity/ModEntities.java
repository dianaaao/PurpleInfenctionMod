package com.purpleinfenctionmod.entity;

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

    public static void register() {
        // регистрация выполняется через статические поля выше
    }
}