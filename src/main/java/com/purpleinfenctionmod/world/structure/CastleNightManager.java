package com.purpleinfenctionmod.world.structure;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class CastleNightManager {

    private static final List<PendingCastle> PENDING =
            new ArrayList<>();

    // NEW:
private static class PendingCastle {

    final BlockBox identityBox;
    final List<BlockBox> pieceBoxes;
    final long seed;

    PendingCastle(BlockBox identityBox, List<BlockBox> pieceBoxes, long seed) {
        this.identityBox = identityBox;
        this.pieceBoxes = pieceBoxes;
        this.seed = seed;
    }
}

    /**
     * Called from NightOnlyJigsawStructure.postPlace().
     *
     * This is safe even when the world is a ChunkRegion.
     */
    // NEW:
public static synchronized void queueCastle(
        BlockBox identityBox,
        List<BlockBox> pieceBoxes,
        long seed
) {

    for (PendingCastle pending : PENDING) {

        if (pending.identityBox.equals(identityBox)) {
            return;
        }
    }

    PENDING.add(
            new PendingCastle(identityBox, pieceBoxes, seed)
    );

    System.out.println(
            "[PurpleInfenctionMod] Queued castle "
                    + identityBox
    );
}

    /**
     * Runs on the actual ServerWorld.
     */
    public static void tick(ServerWorld world) {

        processPending(world);

        long time =
                world.getTimeOfDay() % 24000L;

        boolean night =
                time >= 13000L &&
                time < 23000L;

        CastleWorldState state =
                CastleWorldState.get(world);

        for (CastleWorldState.CastleData castle :
                state.getCastles()) {

            if (night && castle.isHidden()) {

                showCastle(
                        world,
                        castle
                );

                castle.setHidden(false);

                state.markDirty();

                System.out.println(
                        "[PurpleInfenctionMod] "
                                + "Castle appeared at night: "
                                + castle.getBox()
                );
            }

            else if (!night && !castle.isHidden()) {

                hideCastle(
                        world,
                        castle
                );

                castle.setHidden(true);

                state.markDirty();

                System.out.println(
                        "[PurpleInfenctionMod] "
                                + "Castle disappeared during day: "
                                + castle.getBox()
                );
            }
        }
    }

    private static void processPending(
            ServerWorld world
    ) {

        if (PENDING.isEmpty()) {
            return;
        }

        synchronized (CastleNightManager.class) {

            for (PendingCastle pending :
                    new ArrayList<>(PENDING)) {

                /*
                 * Only process castles belonging
                 * to this world seed.
                 */
                if (pending.seed != world.getSeed()) {
                    continue;
                }

                CastleWorldState state =
                        CastleWorldState.get(world);

                boolean exists = false;

                // NEW:
for (CastleWorldState.CastleData castle :
        state.getCastles()) {

    if (castle.getBox().equals(
            pending.identityBox
    )) {

        exists = true;
        break;
    }
}

if (!exists) {

    state.addCastle(
            pending.identityBox,
            pending.pieceBoxes,
            world
    );

    CastleWorldState.CastleData castle =
            findCastle(
                    state,
                    pending.identityBox
            );

                    if (castle != null) {

                        /*
                         * If currently daytime,
                         * immediately hide it.
                         */
                        long time =
                                world.getTimeOfDay()
                                        % 24000L;

                        boolean night =
                                time >= 13000L &&
                                time < 23000L;

                        if (!night) {

                            hideCastle(
                                    world,
                                    castle
                            );

                            castle.setHidden(true);

                            state.markDirty();
                        }
                    }
                }

                PENDING.remove(pending);
            }
        }
    }

    private static CastleWorldState.CastleData findCastle(
            CastleWorldState state,
            BlockBox box
    ) {

        for (CastleWorldState.CastleData castle :
                state.getCastles()) {

            if (castle.getBox().equals(box)) {
                return castle;
            }
        }

        return null;
    }

    private static void hideCastle(
            ServerWorld world,
            CastleWorldState.CastleData castle
    ) {

        for (CastleWorldState.SavedBlock block :
                castle.getBlocks()) {

            BlockPos pos =
                    block.getPos();

            /*
             * Remove block entity first.
             */
            BlockEntity blockEntity =
                    world.getBlockEntity(pos);

            if (blockEntity != null) {
                blockEntity.markRemoved();
            }

            // NEW:
        world.setBlockState(
                pos,
                Blocks.AIR.getDefaultState(),
                Block.NOTIFY_LISTENERS | Block.FORCE_STATE
        );
        }
    }

    private static void showCastle(
            ServerWorld world,
            CastleWorldState.CastleData castle
    ) {

        for (CastleWorldState.SavedBlock block :
                castle.getBlocks()) {

            BlockPos pos =
                    block.getPos();

            // NEW:
                world.setBlockState(
                        pos,
                        block.getState(),
                        Block.NOTIFY_LISTENERS | Block.FORCE_STATE
                );

            /*
             * Restore block entity NBT.
             */
            if (block.hasBlockEntity()) {

                BlockEntity blockEntity =
                        world.getBlockEntity(pos);

                if (blockEntity != null) {

                    blockEntity.readNbt(
                        block.getBlockEntityNbt()
                        );

                    blockEntity.markDirty();
                }
            }
        }
    }
}