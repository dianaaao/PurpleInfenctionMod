package com.purpleinfenctionmod.item;
import net.minecraft.item.Item;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;

public class CrystalSwordAndSaber {
    public static class ModSwordItem extends SwordItem {
            public ModSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
                super(toolMaterial, attackDamage, attackSpeed, settings);
            }

            @Override
            public boolean hasGlint(ItemStack stack) {
                return false; // Запрещает фиолетовый глянец зачарований
            }
        }


    public static final Item CRYSTAL_SWORD = new ModSwordItem(
        ToolMaterials.DIAMOND, 5, -2.4f, new Item.Settings()
    );

    public static final Item CRYSTAL_SABER = new ModSwordItem(
        ToolMaterials.NETHERITE, 12, -2.0f, new Item.Settings()
    );

    // 3. Фабричные методы для получения предметов с зачарованиями
    public static ItemStack getEnchantedSword() {
        ItemStack stack = new ItemStack(ModItems.CRYSTAL_SWORD);
        stack.addEnchantment(Enchantments.SHARPNESS, 3);
        stack.addEnchantment(Enchantments.UNBREAKING, 2);
        stack.addEnchantment(Enchantments.LOOTING, 1);
        return stack;
    }

    public static ItemStack getEnchantedSaber() {
        ItemStack stack = new ItemStack(ModItems.CRYSTAL_SABER);
        stack.addEnchantment(Enchantments.FIRE_ASPECT, 2);
        stack.addEnchantment(Enchantments.SWEEPING, 3);
        stack.addEnchantment(Enchantments.UNBREAKING, 3);
        stack.addEnchantment(Enchantments.SHARPNESS, 5); 
        stack.addEnchantment(Enchantments.LOOTING, 2); 
        return stack;
    }
}
