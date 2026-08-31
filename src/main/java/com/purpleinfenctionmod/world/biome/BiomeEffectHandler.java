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
    private static int savety = 0;

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            for (ServerPlayerEntity player : world.getPlayers()) {
                if (player.age % 20 != 0) continue;
                savety=0;
                ModComponents.DECONTROLL.maybeGet(player).ifPresent(comp -> {
                    if (InfectionWorldState.get(world).isCrystalFixed()) {
                        comp.addStability(0.001f);
                        savety+=0.1;
                    }
                    boolean powerActive = ModComponents.INFECTED_POWER
                        .maybeGet(player)
                        .map(InfectedPowerComponent::isActive)
                        .orElse(false);

                    if (powerActive) {
                        comp.addStability(0.005f);
                        savety+=0.5;
                    
                    }
                    if (player.isOnFire()){
                        comp.addStability(0.05f);
                        savety+=0.5;
                        return;
                    }
                    if (world.getBiome(player.getBlockPos()).matchesKey(ModBiomes.INFECTED_KEY)) {
                        if (player.hasStatusEffect(ModEffects.DISINFECTANT_EFFECT)){
                            
                            comp.addStability(0.05f);
                            return;
                        }
                        ItemStack headStack = player.getEquippedStack(EquipmentSlot.HEAD);
                        ItemStack chestPlate = player.getEquippedStack(EquipmentSlot.CHEST);
                        ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
                        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
                        if (headStack.isOf(ModItems.CRYSTAL_HELMET)){
                            comp.addStability(0.01f);
                            savety+=0.1;
                        }
                        if (chestPlate.isOf(ModItems.CRYSTAL_BIB)){
                            comp.addStability(0.02f);
                            savety+=0.2;
                        }
                        if (leggings.isOf(ModItems.CRYSTAL_TROUSERS)){
                            comp.addStability(0.01f);
                            savety+=0.1;
                        }
                        if (boots.isOf(ModItems.CRYSTAL_BOOTS)){
                            comp.addStability(0.01f);
                            savety+=0.1;
                        }
                        if (headStack.isOf(ModItems.RESPIRATOR)) {
                            headStack.damage(1, player, p -> p.sendEquipmentBreakStatus(EquipmentSlot.HEAD));
                            if (!headStack.isOf(ModItems.RESPIRATOR)){
                                ItemStack brokenRespirator = new ItemStack(ModItems.BROKEN_RESPIRATOR);
                                player.equipStack(
                                    EquipmentSlot.HEAD,
                                    brokenRespirator
                                );
                            }
                            return;
                        }
                        if (headStack.isOf(ModItems.CRYSTAL_RESPIRATOR)) {
                            comp.addStability(0.01f);
                            if (player.age % 30 != 0) return;
                            headStack.damage(1, player, p -> p.sendEquipmentBreakStatus(EquipmentSlot.HEAD));
                            if (!headStack.isOf(ModItems.CRYSTAL_RESPIRATOR)){
                                ItemStack brokenRespirator = new ItemStack(ModItems.BROKEN_UPDATED_RESPIRATOR);
                                player.equipStack(
                                    EquipmentSlot.HEAD,
                                    brokenRespirator
                                );
                            }
                            return;
                        }
                        if (player.getHealth() <= player.getMaxHealth()/2) {
                            comp.reduceStability(0.01f);
                        } else {
                            player.damage(player.getDamageSources().magic(), 1.0f - savety);
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