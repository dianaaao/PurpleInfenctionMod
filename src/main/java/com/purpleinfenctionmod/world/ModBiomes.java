package com.purpleinfenctionmod.world;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import com.purpleinfenctionmod.*;
public class ModBiomes {
    // Сам биом будет создан игрой динамически.
    public static final RegistryKey<Biome> INFECTED_KEY = RegistryKey.of(
        RegistryKeys.BIOME, 
        new Identifier(PurpleInfenctionMod.MOD_ID, "infected")
    );
    public static final RegistryKey<Biome> INFECTED_CAVE_KEY = RegistryKey.of(
        RegistryKeys.BIOME, 
        new Identifier(PurpleInfenctionMod.MOD_ID, "infected_caves")
    );

    public static void registerBiomes() {
        // Никаких Registry.register() здесь быть не должно.
    }
}