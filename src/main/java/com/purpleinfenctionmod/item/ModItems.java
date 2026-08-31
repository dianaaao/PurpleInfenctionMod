package com.purpleinfenctionmod.item;

import com.purpleinfenctionmod.PurpleInfenctionMod;
import com.purpleinfenctionmod.entity.ModEntities;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

// import net.minecraft.item.BowlFoodItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import com.purpleinfenctionmod.effect.ModEffects;

public class ModItems {

    public static final Item RESPIRATOR = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("respirator"),
        new ArmorItem(
            ModArmorMaterials.RESPIRATOR,
            ArmorItem.Type.HELMET,
            new Item.Settings().maxDamage(300)
        )
    );
    public static Item CRYSTAL_MAGIC_STAFF = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("crystal_magic_staff"),
        new CrystalMagicStaffItem(
                new Item.Settings().maxDamage(300)
        )
    );
    // public static final Item UPGRADED_RESPIRATOR = Registry.register(
    //     Registries.ITEM,
    //     PurpleInfenctionMod.id("upgraded_respirator"),
    //     new ArmorItem(
    //         ModArmorMaterials.RESPIRATOR,
    //         ArmorItem.Type.HELMET,
    //         new Item.Settings().maxDamage(450)
    //     )
    // );

    public static final Item CRYSTAL_RESPIRATOR = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("crystal_respirator"),
        new ArmorItem(
            ModArmorMaterials.CRYSTAL_RESPIRATOR,
            ArmorItem.Type.HELMET,
            new Item.Settings().maxDamage(1000)
        )
    );
    public static final Item CRYSTAL_SPLINTER = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("crystal_splinter"),
        new Item(
            new Item.Settings()
        )
    );
    public static final Item CRYSTAL_UPGRADE_TEMPLATE = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("crystal_upgrade_template"),
        new Item(
            new Item.Settings()
        )
    );
    public static final Item DISINFECTANT_POTION = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("disinfectant_potion"),
        new DisinfectantPotionItem(
                new Item.Settings().maxCount(16)
        )
    );
    public static final Item INFECTED_GLOW_BERRY = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("infected_glow_berry"),
        new InfectedGlowBerryItem(
                new Item.Settings().maxCount(64)
        )
    );
    
    public static final Item INFECTED_BOWL = registerItem(
        "infected_bowl",
        new Item(new Item.Settings())
    );
    public static final Item RESPIRATOR_FIX = registerItem(
        "respirator_fix",
        new Item(new Item.Settings())
    );
        
    public static final Item INFECTED_STEW = registerItem(
        "infected_stew",
        new Item(
            new Item.Settings()
                .maxCount(1)
                .food(
                    new FoodComponent.Builder()
                        .hunger(6)
                        .saturationModifier(0.6f)
                        .statusEffect(
                            new StatusEffectInstance(
                                ModEffects.INFECTED_LOOK,
                                1000,
                                0
                            ),
                            0.3f
                        )
                        .build()
                )
                .recipeRemainder(Items.BOWL)
        )
    );

    public static final Item FRAGMENT_OF_OLD_CRYSTAL = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("fragment_of_old_crystal"),
        new Item(
            new Item.Settings()
        )
    );

    public static Item CRYSTAL_BIB = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("crystal_bib"),
        new ArmorItem(
                ModArmorMaterials.CRYSTAL,
                ArmorItem.Type.CHESTPLATE,
                new Item.Settings().maxDamage(300)
        )
    );

    public static Item CRYSTAL_BOOTS = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("crystal_boots"),
        new ArmorItem(
                ModArmorMaterials.CRYSTAL,
                ArmorItem.Type.BOOTS,
                new Item.Settings().maxDamage(300)
        )
    );

    public static Item CRYSTAL_HELMET = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("crystal_helmet"),
        new ArmorItem(
                ModArmorMaterials.CRYSTAL,
                ArmorItem.Type.HELMET,
                new Item.Settings().maxDamage(300)
        )
    );

    public static Item CRYSTAL_TROUSERS = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("crystal_trousers"),
        new ArmorItem(
                ModArmorMaterials.CRYSTAL,
                ArmorItem.Type.LEGGINGS,
                new Item.Settings().maxDamage(300)
        )
    );

    public static final Item CRYSTAL_SABER = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("crystal_saber"),
        new CrystalSwordAndSaber.ModSwordItem(
            ToolMaterials.NETHERITE,
            12,
            -2.0F,
            new Item.Settings()
        )
    );

    // public static Item CRYSTAL_MAGIC_STAFF = Registry.register(
    //     Registries.ITEM,
    //     PurpleInfenctionMod.id("crystal_magic_staff"),
    //     new Item(
    //             new Item.Settings().maxDamage(300)
    //     )
    // );

    public static final Item CRYSTAL_SWORD = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("crystal_sword"),
        new CrystalSwordAndSaber.ModSwordItem(
            ToolMaterials.DIAMOND,
            3,
            -2.4F,
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

    public static final Item INFECTED_VEX_SPAWN_EGG = registerItem("infected_vex_spawn_egg",
        new SpawnEggItem(ModEntities.INFECTED_VEX, 0x0022FF, 0xBF00FF, new Item.Settings()));

    public static final Item PIGEON_SPAWN_EGG = registerItem("pigeon_spawn_egg",
        new SpawnEggItem(ModEntities.PIGEON, 0x305B56, 0x7A8694, new Item.Settings()));

    private static Item registerItem(String name, Item item){
        return Registry.register(
            // добавляем предмет в реестр
            Registries.ITEM, // опеределяем в реестр("блокнотик" с инфой про предметы) айтомов, категория предметов
            PurpleInfenctionMod.id(name), // задем уникальное имя для предмета
            item // сам предмет
        );
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(ModItems.MUSHROOM_MOB_SPAWN_EGG);
            entries.add(ModItems.ROTTING_SPORE_FUNGUS_SPAWN_EGG);
            entries.add(ModItems.INFECTED_ZOMBIE_SPAWN_EGG);
            entries.add(ModItems.INFECTED_SKELETON_SPAWN_EGG);
            entries.add(ModItems.INFECTED_CREEPER_SPAWN_EGG);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
            .register(entries -> {
                entries.add(INFECTED_BOWL);
                entries.add(INFECTED_STEW);
                entries.add(INFECTED_GLOW_BERRY);
                entries.add(DISINFECTANT_POTION);            
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(RESPIRATOR);
            entries.add(CRYSTAL_RESPIRATOR);

            entries.add(CRYSTAL_HELMET);
            entries.add(CRYSTAL_BIB);
            entries.add(CRYSTAL_TROUSERS);
            entries.add(CRYSTAL_BOOTS);
            entries.add(CRYSTAL_SWORD);
            entries.add(CRYSTAL_SABER);
            entries.add(CRYSTAL_MAGIC_STAFF);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(CRYSTAL_UPGRADE_TEMPLATE);
            entries.add(RESPIRATOR_FIX);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
            .register(entries -> entries.add(CRYSTAL_SPLINTER));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
            .register(entries -> entries.add(FRAGMENT_OF_OLD_CRYSTAL));


        PurpleInfenctionMod.LOGGER.info("Registering Mod Items for " + PurpleInfenctionMod.MOD_ID);
    }
}
