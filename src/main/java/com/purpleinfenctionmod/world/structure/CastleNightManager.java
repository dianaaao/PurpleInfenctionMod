package com.purpleinfenctionmod.world.structure;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class CastleNightManager {

    // ---- НОВОЕ: ограничения, чтобы не лагало при большом числе замков ----

    // Сколько замков МАКСИМУМ может сменить состояние (показаться/скрыться) за один тик.
    // Раньше меняли ВСЕ замки сразу в один тик при смене дня/ночи - при большом
    // количестве загруженных замков это давало долгий фриз/зависание.
    private static final int MAX_TRANSITIONS_PER_TICK = 1;

    // Сколько тиков ждать чанки перед тем, как выбросить "зависшую" запись из очереди,
    // чтобы PENDING не рос бесконечно, если чанк почему-то никогда не догружается.
    private static final long PENDING_TIMEOUT_TICKS = 20L * 60L; // ~60 секунд

    private static long globalTickCounter = 0L;

    // -----------------------------------------------------------------------

    private static final List<PendingCastle> PENDING =
            new ArrayList<>();

    private static class PendingCastle {

        final BlockBox identityBox;
        final long seed;
        final long queuedAtTick; // НОВОЕ: когда поставили в очередь

        PendingCastle(BlockBox identityBox, long seed, long queuedAtTick) {
            this.identityBox = identityBox;
            this.seed = seed;
            this.queuedAtTick = queuedAtTick;
        }
    }

    /**
     * Called from NightOnlyJigsawStructure.postPlace().
     */
    public static synchronized void queueCastle(
            BlockBox identityBox,
            long seed
    ) {

        for (PendingCastle pending : PENDING) {
            if (pending.identityBox.equals(identityBox)) {
                return;
            }
        }

        PENDING.add(new PendingCastle(identityBox, seed, globalTickCounter));

        System.out.println(
                "[PurpleInfenctionMod] Queued castle " + identityBox
        );
    }

    /**
     * Runs on the actual ServerWorld.
     */
    public static void tick(ServerWorld world) {

        globalTickCounter++; // НОВОЕ

        processPending(world);

        long time = world.getTimeOfDay() % 24000L;

        boolean night = time >= 13000L && time < 23000L;

        CastleWorldState state = CastleWorldState.get(world);

        // НОВОЕ: считаем, сколько замков реально сменили состояние в этом тике,
        // и останавливаемся после лимита - остальные попробуем в следующий тик.
        int transitionsDone = 0;

        for (CastleWorldState.CastleData castle : state.getCastles()) {

            if (transitionsDone >= MAX_TRANSITIONS_PER_TICK) {
                break;
            }

            if (night && castle.isHidden()) {

                showCastle(world, castle);
                castle.setHidden(false);
                state.markDirty();
                transitionsDone++;

                System.out.println(
                        "[PurpleInfenctionMod] Castle appeared at night: "
                                + castle.getBox()
                );

            } else if (!night && !castle.isHidden()) {

                hideCastle(world, castle);
                castle.setHidden(true);
                state.markDirty();
                transitionsDone++;

                System.out.println(
                        "[PurpleInfenctionMod] Castle disappeared during day: "
                                + castle.getBox()
                );
            }
        }
    }

    private static void processPending(ServerWorld world) {

        if (PENDING.isEmpty()) {
            return;
        }

        synchronized (CastleNightManager.class) {

            for (PendingCastle pending : new ArrayList<>(PENDING)) {

                if (pending.seed != world.getSeed()) {
                    // НОВОЕ: раньше это оставляло запись в PENDING навсегда
                    // (continue без удаления). Теперь удаляем сразу - она
                    // никогда не станет валидной для этого мира.
                    PENDING.remove(pending);
                    continue;
                }

                // НОВОЕ: если ждём слишком долго - выбрасываем запись,
                // чтобы список не рос бесконечно и не проверялся каждый тик зря.
                if (globalTickCounter - pending.queuedAtTick > PENDING_TIMEOUT_TICKS) {
                    System.out.println(
                            "[PurpleInfenctionMod] Dropping castle queue entry, "
                                    + "chunks never fully loaded: " + pending.identityBox
                    );
                    PENDING.remove(pending);
                    continue;
                }

                // Don't touch the world until every chunk the box spans
                // is fully generated/loaded, otherwise reading blocks
                // here can force synchronous chunk gen and deadlock.
                if (!isBoxFullyLoaded(world, pending.identityBox)) {
                    continue; // leave it queued, try again next tick
                }

                CastleWorldState state = CastleWorldState.get(world);

                boolean exists = false;
                for (CastleWorldState.CastleData castle : state.getCastles()) {
                    if (castle.getBox().equals(pending.identityBox)) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {

                    state.captureCastle(pending.identityBox, world);

                    CastleWorldState.CastleData castle =
                            findCastle(state, pending.identityBox);

                    if (castle != null) {

                        long time = world.getTimeOfDay() % 24000L;
                        boolean night = time >= 13000L && time < 23000L;

                        if (!night) {
                            hideCastle(world, castle);
                            castle.setHidden(true);
                            state.markDirty();
                        }
                    }
                }

                PENDING.remove(pending);
            }
        }
    }

    /**
     * Checks that every chunk overlapping the box is fully generated,
     * not just present in memory. Prevents deadlocks from forcing
     * chunk generation synchronously while inside worldgen/tick code.
     */
    private static boolean isBoxFullyLoaded(ServerWorld world, BlockBox box) {

        int minChunkX = box.getMinX() >> 4;
        int maxChunkX = box.getMaxX() >> 4;
        int minChunkZ = box.getMinZ() >> 4;
        int maxChunkZ = box.getMaxZ() >> 4;

        for (int cx = minChunkX; cx <= maxChunkX; cx++) {
            for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {

                net.minecraft.world.chunk.Chunk chunk =
                        world.getChunkManager().getChunk(cx, cz, net.minecraft.world.chunk.ChunkStatus.FULL, false);

                if (chunk == null) {
                    return false;
                }
            }
        }

        return true;
    }

    private static CastleWorldState.CastleData findCastle(
            CastleWorldState state,
            BlockBox box
    ) {
        for (CastleWorldState.CastleData castle : state.getCastles()) {
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

        BlockBox box = castle.getBox();
        BlockPos.Mutable pos = new BlockPos.Mutable();

        for (int x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (int y = box.getMinY(); y <= box.getMaxY(); y++) {
                for (int z = box.getMinZ(); z <= box.getMaxZ(); z++) {

                    pos.set(x, y, z);

                    BlockEntity blockEntity = world.getBlockEntity(pos);

                    // Clear inventory contents first so nothing spills
                    // when the block is replaced with air below.
                    if (blockEntity instanceof Inventory inventory) {
                        inventory.clear();
                    }

                    if (blockEntity != null) {
                        blockEntity.markRemoved();
                    }

                    // ПРИМЕЧАНИЕ: в вашей версии Minecraft/Yarn нет флага
                    // SKIP_LIGHTING_UPDATES, поэтому оставляем исходные флаги.
                    // Основную нагрузку снимает лимит MAX_TRANSITIONS_PER_TICK
                    // выше - именно он не давал всем замкам меняться разом.
                    world.setBlockState(
                            pos,
                            Blocks.AIR.getDefaultState(),
                            Block.NOTIFY_LISTENERS | Block.FORCE_STATE
                    );
                }
            }
        }
    }

    private static void showCastle(
            ServerWorld world,
            CastleWorldState.CastleData castle
    ) {

        StructureTemplate template = castle.getTemplate(world);

        if (template == null) {
            System.out.println(
                    "[PurpleInfenctionMod] Missing template for castle "
                            + castle.getBox()
            );
            return;
        }

        BlockBox box = castle.getBox();
        BlockPos origin = new BlockPos(box.getMinX(), box.getMinY(), box.getMinZ());

        StructurePlacementData settings = new StructurePlacementData();

        template.place(
                world,
                origin,
                origin,
                settings,
                world.getRandom(),
                Block.NOTIFY_LISTENERS
        );
    }
}