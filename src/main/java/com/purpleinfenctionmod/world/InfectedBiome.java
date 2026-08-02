package com.purpleinfenctionmod.world;

// import net.minecraft.sound.BiomeMoodSound;
// import net.minecraft.world.biome.Biome;
// import net.minecraft.world.biome.BiomeEffects;
// import net.minecraft.world.biome.SpawnSettings;
// public class InfectedBiome {

//     public static Biome create() {
//         // 1. Настройка мобов (пусто — никто не спавнится)
//         SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();  
//         // GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();

//         return new Biome.Builder()
//             .precipitation(true) // Без дождя
//             .temperature(0.1f)    // Жара / Пустынный климат
//             .downfall(0.0f)
//             .effects(new BiomeEffects.Builder()
//                 .waterColor(0x3f76e4)
//                 .waterFogColor(0x050533)
//                 .fogColor(0xc0d8e0)     
//                 .skyColor(0x778899)     
//                 .moodSound(BiomeMoodSound.CAVE)
//                 .build())
//             .spawnSettings(spawnSettings.build())
//            // .generationSettings(generationSettings.build())
//             .build();
//     }
    
// }