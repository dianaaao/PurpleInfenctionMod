package com.purpleinfenctionmod;

import com.purpleinfenctionmod.world.biome.InfectedRegion;
import com.purpleinfenctionmod.world.biome.ModSurfaceRules;
import com.purpleinfenctionmod.world.feature.ModFeatures;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class PurpleInfenctionModTerraBlender implements TerraBlenderApi {
    public static final String MOD_ID = "purpleinfenctionmod";

    @Override
    public void onTerraBlenderInitialized() {

        // Regions.register(new InfectedRegion(new Identifier(MOD_ID, "infected"), 1));


        SurfaceRuleManager.addSurfaceRules(
          SurfaceRuleManager.RuleCategory.OVERWORLD, 
          MOD_ID, 
          ModSurfaceRules.makeRules()
        );
    }
}