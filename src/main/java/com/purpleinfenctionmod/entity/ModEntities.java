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
				.build());

    public static final EntityType<RottingSporeFungusEntity> ROTTING_SPORE_FUNGUS = Registry.register(
	Registries.ENTITY_TYPE,
	new Identifier(PurpleInfenctionMod.MOD_ID, "rotting_spore_fungus"),
	FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, RottingSporeFungusEntity::new)
			.dimensions(EntityDimensions.changing(1.2f, 2.2f)) // подгони под реальный размер модели
			.build());

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

    public static final EntityType<SporeCreatureEntity> SPORE_CREATURE = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(PurpleInfenctionMod.MOD_ID, "spore_creature"),
        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SporeCreatureEntity::new)
                .dimensions(EntityDimensions.changing(0.5f, 0.6f))
                .build());


	public static final EntityType<CrystalEntity> OLD_FIRE_CRYSTAL = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(PurpleInfenctionMod.MOD_ID, "old_fire_crystal"),
        FabricEntityTypeBuilder.<CrystalEntity>create(SpawnGroup.MISC, CrystalEntity::new)
                .dimensions(EntityDimensions.fixed(4.0f, 6.0f))
                .build());
				
	public static final EntityType<BrokenFireCrystalEntity> BROKEN_FIRE_CRYSTAL = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(PurpleInfenctionMod.MOD_ID, "broken_fire_crystal"),
        FabricEntityTypeBuilder.<BrokenFireCrystalEntity>create(SpawnGroup.MISC, BrokenFireCrystalEntity::new)
                .dimensions(EntityDimensions.fixed(4.0f, 6.0f))
                .build());	

	public static final EntityType<MushroomPetEntity> MUSHROOM_PET = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(PurpleInfenctionMod.MOD_ID, "mushroom_pet"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, MushroomPetEntity::new)
                .dimensions(EntityDimensions.changing(0.5f, 0.6f))
                .build());	

	public static final EntityType<SporeProjectileEntity> SPORE_PROJECTILE = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(PurpleInfenctionMod.MOD_ID, "spore_projectile"),
        FabricEntityTypeBuilder.<SporeProjectileEntity>create(SpawnGroup.MISC, SporeProjectileEntity::new)
                .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                .trackRangeChunks(4)
                .trackedUpdateRate(10)
                .build());

    public static void register() {
        // регистрация выполняется через статические поля выше
    }
}