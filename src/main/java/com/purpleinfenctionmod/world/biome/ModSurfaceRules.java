package com.purpleinfenctionmod.world.biome;

import com.purpleinfenctionmod.block.ModBlocks;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.util.math.VerticalSurfaceType;

public class ModSurfaceRules {

    private static final MaterialRules.MaterialRule INFECTED_GRASS =
        MaterialRules.block(ModBlocks.INFECTED_GRASS.getDefaultState());

    // Anything at/above this Y is treated as "surface" and skipped.
    private static final int CAVE_CEILING_Y = 60;

    public static MaterialRules.MaterialRule makeRules() {

        MaterialRules.MaterialCondition caveFloor =
            MaterialRules.stoneDepth(
                0,
                false,
                0,
                VerticalSurfaceType.FLOOR
            );

        MaterialRules.MaterialCondition underground =
            MaterialRules.not(
                MaterialRules.aboveY(YOffset.fixed(CAVE_CEILING_Y), 0)
            );

        return MaterialRules.condition(
            MaterialRules.biome(ModBiomes.INFECTED_KEY),
            MaterialRules.condition(
                caveFloor,
                MaterialRules.condition(
                    underground,
                    INFECTED_GRASS
                )
            )
        );
    }
}