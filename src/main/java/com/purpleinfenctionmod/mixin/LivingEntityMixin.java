package com.purpleinfenctionmod.mixin;

import com.purpleinfenctionmod.component.ModComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(
            method = "damage",
            at = @At("TAIL")
    )
    private void purpleinfenctionmod$onDamage(
            DamageSource source,
            float amount,
            CallbackInfoReturnable<Boolean> cir
    ) {

        if (!cir.getReturnValue()) {
            return;
        }

        Entity attacker = source.getAttacker();

        if (!(attacker instanceof PlayerEntity player)) {
            return;
        }

        if (player.getWorld().isClient()) {
            return;
        }

        // Don't trigger on self-damage.
        if (attacker == (Object) this) {
            return;
        }

        ModComponents.INFECTED_POWER
                .get(player)
                .onDealDamage(amount);
    }
}