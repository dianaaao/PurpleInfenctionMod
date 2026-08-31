package com.purpleinfenctionmod.effect;

import com.purpleinfenctionmod.PurpleInfenctionMod;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModPotions {

//     public static final Potion DISINFECTANT_POTION = Registry.register(
//             Registries.POTION,
//             PurpleInfenctionMod.id("disinfectant"),
//             new Potion(
//                     new StatusEffectInstance(
//                             ModEffects.DISINFECTANT_EFFECT,
//                             20 * 60 * 4, // 60 seconds
//                             0        // amplifier
//                     )
//             )
//     );

    public static void registerPotions() {
        PurpleInfenctionMod.LOGGER.info("Registering potions");
    }
}