package com.purpleinfenctionmod.world;

import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;

public class InfectedBiome {

    public static Biome create() {
        // 1. Настройка мобов (пусто — никто не спавнится)
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        // 2. Настройка генерации (без деревьев и травы)
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(null, null);

        return new Biome.Builder()
            .precipitation(false) // Без дождя
            .temperature(0.0f)    // Жара / Пустынный климат
            .downfall(0.0f)
            .effects(new BiomeEffects.Builder()
                .waterColor(0x3f76e4)
                .waterFogColor(0x050533)
                .fogColor(0xc0d8e0)       // Цвет тумана
                .skyColor(0x778899)       // Серый цвет неба
                .moodSound(BiomeMoodSound.CAVE)
                .build())
            .spawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
}