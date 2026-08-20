// package com.purpleinfenctionmod.world.feature;

// import com.purpleinfenctionmod.block.ModBlocks;
// import net.minecraft.block.BlockState;
// import net.minecraft.block.GlowLichenBlock;
// import net.minecraft.util.math.BlockPos;
// import net.minecraft.util.math.Direction;
// import net.minecraft.world.gen.feature.Feature;
// import net.minecraft.world.gen.feature.util.FeatureContext;
// import net.minecraft.world.gen.feature.GlowLichenFeatureConfig;

// public class InfectedGlowLichenFeature extends Feature<GlowLichenFeatureConfig> {

//     public InfectedGlowLichenFeature() {
//         super(GlowLichenFeatureConfig.CODEC);
//     }

//     @Override
//     public boolean generate(FeatureContext<GlowLichenFeatureConfig> context) {
//         GlowLichenFeatureConfig config = context.getConfig();

//         BlockPos origin = context.getOrigin();
//         var world = context.getWorld();
//         var random = context.getRandom();

//         boolean placed = false;

//         for (int i = 0; i < config.searchRange; i++) {
//             int x = origin.getX() + random.nextInt(16) - 8;
//             int y = origin.getY() + random.nextInt(16) - 8;
//             int z = origin.getZ() + random.nextInt(16) - 8;

//             BlockPos pos = new BlockPos(x, y, z);

//             if (!world.getBlockState(pos).isAir()) {
//                 continue;
//             }

//             for (Direction direction : config.directions) {
//                 BlockPos supportPos = pos.offset(direction);
//                 BlockState supportState = world.getBlockState(supportPos);

//                 if (!config.canGrowOn(supportState.getBlock())) {
//                     continue;
//                 }

//                 if (random.nextFloat() > config.spreadChance) {
//                     continue;
//                 }

//                 BlockState lichenState =
//                     ModBlocks.INFECTED_GLOW_LICHEN.getDefaultState();

//                 lichenState = lichenState.with(
//                     GlowLichenBlock.FACING_PROPERTIES.get(direction.getOpposite()),
//                     true
//                 );

//                 if (world.setBlockState(pos, lichenState)) {
//                     placed = true;
//                     break;
//                 }
//             }
//         }

//         return placed;
//     }
// }