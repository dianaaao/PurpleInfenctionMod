package com.purpleinfenctionmod;

import com.purpleinfenctionmod.world.biome.ModSurfaceRules;

import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class PurpleInfenctionModTerraBlender implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {

        SurfaceRuleManager.addSurfaceRules(
                SurfaceRuleManager.RuleCategory.OVERWORLD,
                PurpleInfenctionMod.MOD_ID,
                ModSurfaceRules.makeRules()
        );
    }
}