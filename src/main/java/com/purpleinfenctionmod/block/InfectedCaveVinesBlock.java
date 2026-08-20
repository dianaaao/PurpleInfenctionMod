package com.purpleinfenctionmod.block;

import com.purpleinfenctionmod.item.ModItems;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CaveVines;
import net.minecraft.block.CaveVinesHeadBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
public class InfectedCaveVinesBlock extends CaveVinesHeadBlock {

    public InfectedCaveVinesBlock(AbstractBlock.Settings settings) {
        super(settings);
        }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!state.get(CaveVines.BERRIES)) {
            return ActionResult.PASS;
        }

        world.playSound(player, pos, SoundEvents.BLOCK_CAVE_VINES_PICK_BERRIES,
                SoundCategory.BLOCKS, 1.0f, 0.8f + world.random.nextFloat() * 0.4f);

        ItemStack stack = new ItemStack(ModItems.INFECTED_GLOW_BERRY); // your item field

        Block.dropStack(world, pos, stack);
        

        world.setBlockState(pos, state.with(CaveVines.BERRIES, false), 2);
        world.emitGameEvent(player, net.minecraft.world.event.GameEvent.BLOCK_CHANGE, pos);

        return ActionResult.success(world.isClient);
    }
    @Override
    protected Block getPlant() {
        return ModBlocks.INFECTED_CAVE_VINES_PLANT;
    }

    @Override
    protected BlockState copyState(BlockState from, BlockState to) {
        return to.with(
                CaveVines.BERRIES,
                from.get(CaveVines.BERRIES)
        );
    }
}