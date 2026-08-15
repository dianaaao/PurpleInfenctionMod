package com.purpleinfenctionmod.effect;

import com.purpleinfenctionmod.PurpleInfenctionMod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {

    public static final StatusEffect DISINFECTANT_EFFECT = Registry.register(
            Registries.STATUS_EFFECT,
            new Identifier(PurpleInfenctionMod.MOD_ID, "disinfectant_effect"),
            new DisinfectantEffect()
    );

    public static void registerEffects() {
        PurpleInfenctionMod.LOGGER.info("Registering custom effects for " + PurpleInfenctionMod.MOD_ID);
    }
}