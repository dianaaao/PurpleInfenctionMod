package com.purpleinfenctionmod;

import com.purpleinfenctionmod.world.InfectedRegion;
import com.purpleinfenctionmod.world.ModSurfaceRules;

import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class PurpleInfenctionModTerraBlender implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new InfectedRegion(new Identifier(PurpleInfenctionMod.MOD_ID, "infected"), 10));
        
        SurfaceRuleManager.addSurfaceRules(
          SurfaceRuleManager.RuleCategory.OVERWORLD, 
          "purpleinfenctionmod", 
          ModSurfaceRules.makeRules()
        );
    }
}
