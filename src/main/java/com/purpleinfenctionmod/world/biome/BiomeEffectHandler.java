package com.purpleinfenctionmod.world.biome;

import org.slf4j.LoggerFactory;

import com.purpleinfenctionmod.component.InfectedPowerComponent;
import com.purpleinfenctionmod.component.ModComponents;
import com.purpleinfenctionmod.effect.ModEffects;
import com.purpleinfenctionmod.item.ModItems;
import com.purpleinfenctionmod.world.InfectionWorldState;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

import org.slf4j.Logger;
public class BiomeEffectHandler {
	public static final String MOD_ID = "purpleinfenctionmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            for (ServerPlayerEntity player : world.getPlayers()) {
                if (player.age % 20 != 0) continue;
                
                ModComponents.DECONTROLL.maybeGet(player).ifPresent(comp -> {
                    if (InfectionWorldState.get(world).isCrystalFixed()) {
                        comp.addStability(0.001f);
                    }
                    boolean powerActive = ModComponents.INFECTED_POWER
                        .maybeGet(player)
                        .map(InfectedPowerComponent::isActive)
                        .orElse(false);

                    if (powerActive) {
                        comp.addStability(0.05f);
                        return;
                    }
                    if (player.isOnFire()){
                        comp.addStability(0.05f);
                        return;
                    }
                    if (world.getBiome(player.getBlockPos()).matchesKey(ModBiomes.INFECTED_KEY)) {
                        if (player.hasStatusEffect(ModEffects.DISINFECTANT_EFFECT)){
                            
                            comp.addStability(0.05f);
                            return;
                        }
                        ItemStack headStack = player.getEquippedStack(EquipmentSlot.HEAD);
                        if (headStack.isOf(ModItems.RESPIRATOR)) {
                            headStack.damage(1, player, p -> p.sendEquipmentBreakStatus(EquipmentSlot.HEAD));
                            return;
                        }
                        if (headStack.isOf(ModItems.CRYSTAL_RESPIRATOR)) {
                            comp.addStability(0.01f);
                            if (player.age % 30 != 0) return;
                            headStack.damage(1, player, p -> p.sendEquipmentBreakStatus(EquipmentSlot.HEAD));
                            return;
                        }
                        if (player.getHealth() <= player.getMaxHealth()/2) {
                            comp.reduceStability(0.01f);
                        } else {
                            player.damage(player.getDamageSources().magic(), 1.0f);
                        }
                    } else {
                        if (player.hasStatusEffect(ModEffects.DISINFECTANT_EFFECT)){
                            comp.addStability(0.1f);
                        }else{
                            comp.addStability(0.01f);
                        }
                    }
                });
                
            }
        });
    }
}