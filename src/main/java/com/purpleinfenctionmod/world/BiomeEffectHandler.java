package com.purpleinfenctionmod.world;

import com.purpleinfenctionmod.block.ModItems;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;

public class BiomeEffectHandler {

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            for (ServerPlayerEntity player : world.getPlayers()) {
                // Проверка раз в 20 тиков (1 секунда)
                if (player.age % 60 == 0) {
                    if (world.getBiome(player.getBlockPos()).matchesKey(ModBiomes.INFECTED_KEY)) {
                        
                        // Проверяем предмет на голове
                        ItemStack headStack = player.getEquippedStack(EquipmentSlot.HEAD);
                        
                        // Если на игроке RESPIRATOR — пропускаем наложение эффекта
                        if (headStack.isOf(ModItems.RESPIRATOR)) {
                            headStack.damage(1, player, p ->
                            p.sendEquipmentBreakStatus(EquipmentSlot.HEAD));
                            continue;
                        }

                        player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.POISON,
                            70,
                            0,
                            false,
                            false
                        ));
                    }
                }
            }
        });
    }
}