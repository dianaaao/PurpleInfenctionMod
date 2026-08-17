package com.purpleinfenctionmod.world.biome;

import com.purpleinfenctionmod.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ModSurfaceRules {
    private static final MaterialRules.MaterialRule INFECTED_GRASS =
        MaterialRules.block(ModBlocks.INFECTED_GRASS.getDefaultState());

private static final MaterialRules.MaterialRule INFECTED_DIRT =
        MaterialRules.block(ModBlocks.INFECTED_DIRT.getDefaultState());

private static final MaterialRules.MaterialRule GRAVEL =
        MaterialRules.block(Blocks.GRAVEL.getDefaultState());

public static MaterialRules.MaterialRule makeRules() {

    MaterialRules.MaterialCondition topBlock =
            MaterialRules.stoneDepth(0, false, 0, VerticalSurfaceType.FLOOR);

    MaterialRules.MaterialCondition dirtLayer =
            MaterialRules.stoneDepth(1, true, 4, VerticalSurfaceType.FLOOR);

    MaterialRules.MaterialCondition isAboveWater =
            MaterialRules.water(-1, 0);

    MaterialRules.MaterialRule landRule = MaterialRules.sequence(
            MaterialRules.condition(topBlock, INFECTED_GRASS),
            MaterialRules.condition(dirtLayer, INFECTED_DIRT)
    );

    MaterialRules.MaterialRule underwaterRule = MaterialRules.sequence(
            MaterialRules.condition(topBlock, GRAVEL)
    );

    return MaterialRules.condition(
            MaterialRules.biome(ModBiomes.INFECTED_KEY),
            MaterialRules.sequence(
                    MaterialRules.condition(isAboveWater, landRule),
                    underwaterRule
            )
    );
}
    
}