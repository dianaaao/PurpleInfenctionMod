package com.purpleinfenctionmod.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

public interface InfectedPowerComponent extends ComponentV3, AutoSyncedComponent {

    boolean isActive();
    void setActive(boolean active);

    float getBonusMaxHealth();
    void setBonusMaxHealth(float amount);

    float getLifestealPercent();
    void setLifestealPercent(float percent);

    float getAbsorptionPercent();
    void setAbsorptionPercent(float percent);

    float getMaxAbsorption();
    void setMaxAbsorption(float amount);

    void onDealDamage(float damageDealt);
}