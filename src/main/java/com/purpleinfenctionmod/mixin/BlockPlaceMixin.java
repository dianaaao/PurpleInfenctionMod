package com.purpleinfenctionmod.mixin;

import com.purpleinfenctionmod.component.InfectedPowerComponent;
import com.purpleinfenctionmod.component.ModComponents;
import com.purpleinfenctionmod.world.PlacedBlockDecayHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockPlaceMixin {

    @Inject(method = "place", at = @At("RETURN"))
    private void purpleinfenctionmod$onPlace(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (!cir.getReturnValue().isAccepted()) return;
        PlayerEntity player = context.getPlayer();
        if (player == null) return;
        if (!(context.getWorld() instanceof ServerWorld serverWorld)) return;
        boolean powerActive = ModComponents.INFECTED_POWER
            .maybeGet(player)
            .map(InfectedPowerComponent::isActive)
            .orElse(false);
        if (powerActive) return;
        BlockPos pos = context.getBlockPos();
        PlacedBlockDecayHandler.trackPlacedBlock(serverWorld, pos);
    }
}