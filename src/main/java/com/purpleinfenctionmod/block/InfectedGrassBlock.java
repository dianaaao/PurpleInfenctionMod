package com.purpleinfenctionmod.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public class InfectedGrassBlock extends GrassBlock {
    public InfectedGrassBlock(Settings settings) {
        super(settings);
    }

    // Проверка: достаточно ли света дерну, чтобы не отмирать
    private static boolean canStayAlive(BlockState state, WorldView world, BlockPos pos) {
        BlockPos posAbove = pos.up();
        BlockState stateAbove = world.getBlockState(posAbove);
        
        // Если сверху снег — дерн живет
        if (stateAbove.isOf(net.minecraft.block.Blocks.SNOW) && stateAbove.get(net.minecraft.block.SnowBlock.LAYERS) == 1) {
            return true;
        } 
        // Если сверху вода с уровнем > 0 — отмирает
        else if (stateAbove.getFluidState().getLevel() == 8) {
            return false;
        } 
        // Проверка уровня освещения (если перекрыто полным блоком)
        else {
            int opacity = ChunkLightProvider.getRealisticOpacity(world, state, pos, stateAbove, posAbove, net.minecraft.util.math.Direction.UP, stateAbove.getOpacity(world, posAbove));
            return opacity < world.getMaxLightLevel();
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Если света недостаточно (сверху поставили блок), превращаем в INFECTED_DIRT
        if (!canStayAlive(state, world, pos)) {
            world.setBlockState(pos, ModBlocks.INFECTED_DIRT.getDefaultState());
        }
    }
}