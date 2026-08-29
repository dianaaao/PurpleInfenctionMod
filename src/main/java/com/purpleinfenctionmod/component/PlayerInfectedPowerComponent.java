package com.purpleinfenctionmod.component;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class PlayerInfectedPowerComponent implements InfectedPowerComponent {

    private static final UUID BONUS_HEALTH_MODIFIER_ID =
            UUID.fromString("8e1a0f2e-6b2a-4c1a-9e3d-11f1f0a1c002");

    private final PlayerEntity player;

    private boolean active = false;

    private float bonusMaxHealth = 20.0f;
    private float lifestealPercent = 0.25f;
    private float absorptionPercent = 0.05f;
    private float maxAbsorption = 40.0f;

    public PlayerInfectedPowerComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;

        if (player.getWorld().isClient()) {
            return;
        }

        if (active) {
            applyHealthBonus();
        } else {
            removeHealthBonus();
        }
    }

    @Override
    public float getBonusMaxHealth() {
        return bonusMaxHealth;
    }

    @Override
    public void setBonusMaxHealth(float amount) {
        this.bonusMaxHealth = amount;

        if (!player.getWorld().isClient()) {
            applyHealthBonus();
        }
    }

    @Override
    public float getLifestealPercent() {
        return lifestealPercent;
    }

    @Override
    public void setLifestealPercent(float percent) {
        this.lifestealPercent = percent;
    }

    @Override
    public float getAbsorptionPercent() {
        return absorptionPercent;
    }

    @Override
    public void setAbsorptionPercent(float percent) {
        this.absorptionPercent = percent;
    }

    @Override
    public float getMaxAbsorption() {
        return maxAbsorption;
    }

    @Override
    public void setMaxAbsorption(float amount) {
        this.maxAbsorption = amount;
    }

    @Override
    public void onDealDamage(float damageDealt) {

        if (!active || damageDealt <= 0) {
            return;
        }

        // Player is missing health -> heal 25% of damage dealt
        if (player.getHealth() < player.getMaxHealth()) {

            float healAmount = damageDealt * lifestealPercent;

            if (healAmount > 0) {
                player.heal(healAmount);
            }

        } else {

            // Player is at full health -> gain absorption
            float absorptionGain = damageDealt * absorptionPercent;

            if (absorptionGain > 0) {

                float newAbsorption = Math.min(
                        player.getAbsorptionAmount() + absorptionGain,
                        maxAbsorption
                );

                player.setAbsorptionAmount(newAbsorption);
            }
        }
    }

    public void applyHealthBonus() {

        if (!active) {
            return;
        }

        EntityAttributeInstance maxHealth =
                player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

        if (maxHealth == null) {
            return;
        }

        // Don't add the modifier twice
        if (maxHealth.getModifier(BONUS_HEALTH_MODIFIER_ID) != null) {
            return;
        }

        maxHealth.addPersistentModifier(
                new EntityAttributeModifier(
                        BONUS_HEALTH_MODIFIER_ID,
                        "purpleinfenctionmod:infected_power_health",
                        bonusMaxHealth,
                        Operation.ADDITION
                )
        );
    }

    public void removeHealthBonus() {

        EntityAttributeInstance maxHealth =
                player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

        if (maxHealth == null) {
            return;
        }

        maxHealth.removeModifier(BONUS_HEALTH_MODIFIER_ID);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {

        if (tag.contains("InfectedPowerActive")) {
            active = tag.getBoolean("InfectedPowerActive");
        }

        if (tag.contains("InfectedPowerBonusHealth")) {
            bonusMaxHealth = tag.getFloat("InfectedPowerBonusHealth");
        }

        if (tag.contains("InfectedPowerLifesteal")) {
            lifestealPercent = tag.getFloat("InfectedPowerLifesteal");
        }

        if (tag.contains("InfectedPowerAbsorption")) {
            absorptionPercent = tag.getFloat("InfectedPowerAbsorption");
        }

        if (tag.contains("InfectedPowerMaxAbsorption")) {
            maxAbsorption = tag.getFloat("InfectedPowerMaxAbsorption");
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {

        tag.putBoolean("InfectedPowerActive", active);

        tag.putFloat(
                "InfectedPowerBonusHealth",
                bonusMaxHealth
        );

        tag.putFloat(
                "InfectedPowerLifesteal",
                lifestealPercent
        );

        tag.putFloat(
                "InfectedPowerAbsorption",
                absorptionPercent
        );

        tag.putFloat(
                "InfectedPowerMaxAbsorption",
                maxAbsorption
        );
    }
}