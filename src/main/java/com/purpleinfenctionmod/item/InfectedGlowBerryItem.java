package com.purpleinfenctionmod.item;

import com.purpleinfenctionmod.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.CaveVines;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class InfectedGlowBerryItem extends Item {

    public InfectedGlowBerryItem(Settings settings) {
        super(settings);
    }
@Override
public ActionResult useOnBlock(ItemUsageContext context) {
    if (context.getSide() != Direction.DOWN) {
        return ActionResult.PASS;
    }

    var world = context.getWorld();
    var vinePos = context.getBlockPos().down();
    var player = context.getPlayer();

    BlockState current = world.getBlockState(vinePos);
    if (!current.isAir() && !current.isReplaceable()) {
        return ActionResult.PASS;
    }

    BlockState vineState = ModBlocks.INFECTED_CAVE_VINES
            .getDefaultState()
            .with(CaveVines.BERRIES, true);

    if (!vineState.canPlaceAt(world, vinePos)) {
        return ActionResult.PASS;
    }

    if (!world.isClient) {
        world.setBlockState(vinePos, vineState);
        if (player == null || !player.isCreative()) {
            context.getStack().decrement(1);
        }
    }

    return ActionResult.success(world.isClient);
}
}