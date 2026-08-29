package com.purpleinfenctionmod.world;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

public class InfectionWorldState extends PersistentState {

    private boolean crystalFixed = false;

    public boolean isCrystalFixed() {
        return crystalFixed;
    }

    public void setCrystalFixed(boolean value) {
        this.crystalFixed = value;
        markDirty();
    }

    public static InfectionWorldState get(ServerWorld world) {
        PersistentStateManager manager = world.getPersistentStateManager();
        return manager.getOrCreate(
                InfectionWorldState::fromNbt,
                InfectionWorldState::new,
                "purpleinfenctionmod_infection"
        );
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putBoolean("crystalFixed", crystalFixed);
        return nbt;
    }

    public static InfectionWorldState fromNbt(NbtCompound nbt) {
        InfectionWorldState state = new InfectionWorldState();
        state.crystalFixed = nbt.getBoolean("crystalFixed");
        return state;
    }
}