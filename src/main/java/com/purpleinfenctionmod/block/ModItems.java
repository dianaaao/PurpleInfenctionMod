package com.purpleinfenctionmod.block;

import com.purpleinfenctionmod.PurpleInfenctionMod;
import com.purpleinfenctionmod.item.ModArmorMaterials;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public class ModItems {

    // public static final Item MARTIAN_INGOT = registerItem(
    //     "martian_ingot", 
    //     new Item(new Item.Settings())
    // );

    // public static final Item GRAVITY_BOOTS = registerItem(
    //     "gravity_boots", 
    //     new ArmorItem(
    //         ModArmorMaterials.MARTIAN, // материал брони
    //         ArmorItem.Type.BOOTS, // тип брони
    //         new Item.Settings() // настройки предмета
    //     )
    // );
    public static final Item RESPIRATOR = Registry.register(
        Registries.ITEM,
        PurpleInfenctionMod.id("respirator"),
        new ArmorItem(
            ModArmorMaterials.RESPIRATOR,
            ArmorItem.Type.HELMET,
            new Item.Settings()
        )
    );


    private static Item registerItem(String name, Item item){
        return Registry.register(
            // добавляем предмет в реестр
            Registries.ITEM, // опеределяем в реестр("блокнотик" с инфой про предметы) айтомов, категория предметов
            PurpleInfenctionMod.id(name), // задем уникальное имя для предмета
            item // сам предмет
        );
    }

    public static void registerModItems() {
        // // добавляем предмет в группу предметов
        // ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
        // // список обьєктов в группе предметов (ингридиентам), добавляем наш предмет в список
        // .register(entries -> entries.add(MARTIAN_INGOT));

        // ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
        // // список обьєктов в группе предметов (ингридиентам), добавляем наш предмет в список
        // .register(entries -> entries.add(GRAVITY_BOOTS));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
        .register(entries -> entries.add(RESPIRATOR));

        PurpleInfenctionMod.LOGGER.info("Registering Mod Items for " + PurpleInfenctionMod.MOD_ID);
    }
}
