package com.purpleinfenctionmod.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CaveVines;
import net.minecraft.block.CaveVinesHeadBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class InfectedCaveVinesFeature extends Feature<InfectedCaveVinesFeatureConfig> {

    public InfectedCaveVinesFeature(Codec<InfectedCaveVinesFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<InfectedCaveVinesFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();
        InfectedCaveVinesFeatureConfig config = context.getConfig();

        // Проверяем, что над нами есть твёрдый блок (потолок пещеры)
        if (!world.isAir(pos) || !world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos.up(), Direction.DOWN)) {
            return false;
        }

        // Генерируем случайную длину лианы от minLength до maxLength из конфигурации
        int length = random.nextBetween(config.minLength(), config.maxLength());

        BlockPos.Mutable mutablePos = pos.mutableCopy();
        for (int i = 0; i < length; i++) {
            if (world.isAir(mutablePos)) {
                boolean isTip = (i == length - 1) || !world.isAir(mutablePos.down());
                
                // Вычисляем, будут ли на этом сегменте светящиеся ягоды
                boolean hasBerries = random.nextFloat() < config.berryChance();

                BlockState state;
                if (isTip) {
                    state = Blocks.CAVE_VINES.getDefaultState()
                            .with(CaveVinesHeadBlock.BERRIES, hasBerries);
                } else {
                    state = Blocks.CAVE_VINES_PLANT.getDefaultState()
                            .with(CaveVines.BERRIES, hasBerries);
                }

                world.setBlockState(mutablePos, state, 2);

                if (isTip) {
                    break;
                }
            } else {
                break;
            }
            mutablePos.move(Direction.DOWN);
        }

        return true;
    }
}