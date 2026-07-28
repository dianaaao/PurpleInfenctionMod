package com.purpleinfenctionmod.world;

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
                if (player.age % 20 == 0) {
                    if (world.getBiome(player.getBlockPos()).matchesKey(ModBiomes.INFECTED_KEY)) {
                        
                        // Проверяем предмет на голове
                        ItemStack headStack = player.getEquippedStack(EquipmentSlot.HEAD);
                        
                        // Если на игроке Алмазный Шлем — пропускаем наложение эффекта
                        if (headStack.isOf(Items.DIAMOND_HELMET)) {
                            continue;
                        }

                        player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.POISON,
                            60,
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