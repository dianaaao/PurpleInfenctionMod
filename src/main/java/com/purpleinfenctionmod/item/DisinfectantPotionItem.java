package com.purpleinfenctionmod.item;

import com.purpleinfenctionmod.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.world.World;

public class DisinfectantPotionItem extends PotionItem {

    public DisinfectantPotionItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {

        if (!world.isClient) {
            user.addStatusEffect(
                    new StatusEffectInstance(
                            ModEffects.DISINFECTANT_EFFECT,
                            20 * 60 * 4,
                            0
                    )
            );
        }

        if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
            stack.decrement(1);

            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
        }

        return stack;
    }
}