package com.purpleinfenctionmod.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

public interface DecontrollComponent extends ComponentV3, AutoSyncedComponent {
    float getStability();
    void setStability(float value);
    void reduceStability(float amount);
    void addStability(float amount);
}