package com.purpleinfenctionmod.world.feature;

import com.purpleinfenctionmod.PurpleInfenctionMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;

public class ModFeatures {
    public static final Feature<InfectedCaveVinesFeatureConfig> INFECTED_CAVE_VINES = new InfectedCaveVinesFeature(InfectedCaveVinesFeatureConfig.CODEC);

    public static void registerFeatures() {
        Registry.register(
            Registries.FEATURE, 
            new Identifier(PurpleInfenctionMod.MOD_ID, "infected_cave_vines"), 
            INFECTED_CAVE_VINES
        );
    }
}