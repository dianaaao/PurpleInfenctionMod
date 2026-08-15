package com.purpleinfenctionmod.world.structure;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

public class CastleWorldState extends PersistentState {

    private boolean generated = false;

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated() {
        generated = true;
        markDirty();
    }
    private static final java.util.Set<Long> RESERVED_WORLDS =
        java.util.Collections.synchronizedSet(new java.util.HashSet<>());
    public static boolean tryReserve(long seed) {
        return RESERVED_WORLDS.add(seed);
    }
    public static void releaseReservation(long seed) {
        RESERVED_WORLDS.remove(seed);
    }
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putBoolean("generated", generated);
        return nbt;
    }

    public static CastleWorldState fromNbt(NbtCompound nbt) {
        CastleWorldState state = new CastleWorldState();
        state.generated = nbt.getBoolean("generated");
        return state;
    }

    public static CastleWorldState get(ServerWorld world) {
        PersistentStateManager manager = world.getPersistentStateManager();

        return manager.getOrCreate(
                CastleWorldState::fromNbt,
                CastleWorldState::new,
                "purpleinfenctionmod_castle"
        );
    }
}