package com.purpleinfenctionmod.item;

import com.purpleinfenctionmod.PurpleInfenctionMod;
import com.purpleinfenctionmod.entity.ModEntities;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.PotionItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public class ModItems {

    public static final Item RESPIRATOR = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("respirator"),
        new ArmorItem(
            ModArmorMaterials.RESPIRATOR,
            ArmorItem.Type.HELMET,
            new Item.Settings().maxDamage(200)
        )
    );
    public static final Item CRYSTAL_SPLINTER = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("crystal_splinter"),
        new Item(
            new Item.Settings()
        )
    );
    public static final Item DISINFECTANT_POTION = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("disinfectant_potion"),
        new PotionItem(
            new Item.Settings()
        )
    );

    public static final Item MUSHROOM_MOB_SPAWN_EGG = registerItem("mushroom_mob_spawn_egg",
            new SpawnEggItem(ModEntities.MUSHROOM_MOB, 0x8B5FBF, 0xE1C8E6, new Item.Settings()));

    public static final Item INFECTED_ZOMBIE_SPAWN_EGG = registerItem("infected_zombie_spawn_egg",
            new SpawnEggItem(ModEntities.INFECTED_ZOMBIE, 0x5A3D7A, 0x2E7D32, new Item.Settings()));

    public static final Item INFECTED_SKELETON_SPAWN_EGG = registerItem("infected_skeleton_spawn_egg",
            new SpawnEggItem(ModEntities.INFECTED_SKELETON, 0xC0C0C0, 0x7B4FA3, new Item.Settings()));

    public static final Item INFECTED_CREEPER_SPAWN_EGG = registerItem("infected_creeper_spawn_egg",
            new SpawnEggItem(ModEntities.INFECTED_CREEPER, 0x2E7D32, 0x7B4FA3, new Item.Settings()));

    public static final Item ROTTING_SPORE_FUNGUS_SPAWN_EGG = registerItem("rotting_spore_fungus_spawn_egg",
            new SpawnEggItem(ModEntities.ROTTING_SPORE_FUNGUS, 0xB84D30, 0x3F2F2B, new Item.Settings()));


    private static Item registerItem(String name, Item item){
        return Registry.register(
            // добавляем предмет в реестр
            Registries.ITEM, // опеределяем в реестр("блокнотик" с инфой про предметы) айтомов, категория предметов
            PurpleInfenctionMod.id(name), // задем уникальное имя для предмета
            item // сам предмет
        );
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
            .register(entries -> entries.add(RESPIRATOR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
            .register(entries -> entries.add(CRYSTAL_SPLINTER));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
            .register(entries -> entries.add(DISINFECTANT_POTION));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(ModItems.MUSHROOM_MOB_SPAWN_EGG);
            entries.add(ModItems.ROTTING_SPORE_FUNGUS_SPAWN_EGG);
            entries.add(ModItems.INFECTED_ZOMBIE_SPAWN_EGG);
            entries.add(ModItems.INFECTED_SKELETON_SPAWN_EGG);
            entries.add(ModItems.INFECTED_CREEPER_SPAWN_EGG);
        });

        PurpleInfenctionMod.LOGGER.info("Registering Mod Items for " + PurpleInfenctionMod.MOD_ID);
    }
}
