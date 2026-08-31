package com.purpleinfenctionmod.item;
import net.minecraft.item.Item;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

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

    // НОВОЕ: убраны CRYSTAL_SWORD и CRYSTAL_SABER, которые тут создавались
    // через "new ModSwordItem(...)" БЕЗ Registry.register(). Это были
    // предметы-дубли, не привязанные ни к какому Identifier - "мёртвые"
    // объекты, никогда не появляющиеся в игре нормально, при этом с ДРУГИМИ
    // характеристиками (урон меча 5 вместо 3), чем реал ьно зарегистрированные
    // версии в ModItems.java. Единственный источникправды по этим предметам -
    // ModItems.CRYSTAL_SWORD и ModItems.CRYSTAL_SABER.

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