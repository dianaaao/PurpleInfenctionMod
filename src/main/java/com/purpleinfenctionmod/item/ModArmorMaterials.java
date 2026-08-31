package com.purpleinfenctionmod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
// import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
// import net.minecraft.util.registry.Registry;

import java.util.EnumMap;
// import java.util.function.Supplier;



public class ModArmorMaterials {
    public static final ArmorMaterial RESPIRATOR = new ArmorMaterial() {
        private static final EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.HELMET, 11);
            map.put(ArmorItem.Type.CHESTPLATE, 16);
            map.put(ArmorItem.Type.LEGGINGS, 15);
            map.put(ArmorItem.Type.BOOTS, 13);
        });
        
        @Override
        public int getDurability(ArmorItem.Type type) {
            return BASE_DURABILITY.get(type) * 15;
        }

        @Override
        public int getProtection(ArmorItem.Type type) {
            return switch (type) {
                case HELMET -> 2;
                case CHESTPLATE -> 5;
                case LEGGINGS -> 4;
                case BOOTS -> 2;
            };
        }

        @Override
        public int getEnchantability() {
            return 9;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(ModItems.RESPIRATOR_FIX);
        }

        @Override
        public String getName() {
            return "purpleinfenctionmod:respirator";
        }

        @Override
        public float getToughness() {
            return 0.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0F;
        }
        
    };

    public static final ArmorMaterial CRYSTAL_RESPIRATOR = new ArmorMaterial() {
        private static final EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.HELMET, 11);
            map.put(ArmorItem.Type.CHESTPLATE, 16);
            map.put(ArmorItem.Type.LEGGINGS, 15);
            map.put(ArmorItem.Type.BOOTS, 13);
        });

        @Override
        public int getDurability(ArmorItem.Type type) {
            return BASE_DURABILITY.get(type) * 15;
        }

        @Override
        public int getProtection(ArmorItem.Type type) {
            return switch (type) {
                case HELMET -> 3;
                case CHESTPLATE -> 8;
                case LEGGINGS -> 6;
                case BOOTS -> 4;
            };
        }

        @Override
        public int getEnchantability() {
            return 13;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(ModItems.CRYSTAL_SPLINTER);
        }

        @Override
        public String getName() {
            return "purpleinfenctionmod:crystal_respirator";
        }

        @Override
        public float getToughness() {
            return 0.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0F;
        }
    };

    public static final ArmorMaterial CRYSTAL = new ArmorMaterial() {

        private static final EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY =
                Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                    map.put(ArmorItem.Type.HELMET, 11);
                    map.put(ArmorItem.Type.CHESTPLATE, 16);
                    map.put(ArmorItem.Type.LEGGINGS, 15);
                    map.put(ArmorItem.Type.BOOTS, 13);
                });

        @Override
        public int getDurability(ArmorItem.Type type) {
            return BASE_DURABILITY.get(type) * 25;
        }

        @Override
        public int getProtection(ArmorItem.Type type) {
            return switch (type) {
                case HELMET -> 3;
                case CHESTPLATE -> 8;
                case LEGGINGS -> 6;
                case BOOTS -> 3;
            };
        }

        @Override
        public int getEnchantability() {
            return 15;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(ModItems.CRYSTAL_SPLINTER);
        }

        @Override
        public String getName() {
            return "purpleinfenctionmod:crystal";
        }

        @Override
        public float getToughness() {
            return 2.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.1F;
        }
    };
    public static final ArmorMaterial BROKEN_RESPIRATOR = new ArmorMaterial() {

        @Override
        public int getDurability(ArmorItem.Type type) {
            return 1;
        }

        @Override
        public int getProtection(ArmorItem.Type type) {
            return 0;
        }

        @Override
        public int getEnchantability() {
            return 0;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_CHAIN;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }

        @Override
        public String getName() {
            return "purpleinfenctionmod:broken";
        }

        @Override
        public float getToughness() {
            return 0.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0F;
        }
    };
}
