package com.purpleinfenctionmod.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class DisinfectantEffect extends StatusEffect {

    public DisinfectantEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xC15A32);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}