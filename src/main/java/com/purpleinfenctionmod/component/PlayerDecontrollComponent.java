package com.purpleinfenctionmod.component;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerDecontrollComponent implements DecontrollComponent {
    private final PlayerEntity player;
    private float stability = 1.0f;

    public PlayerDecontrollComponent(PlayerEntity player) { this.player = player; }

    @Override public float getStability() { return stability; }

    @Override
    public void setStability(float value) {
        float old = stability;
        stability = Math.max(0f, Math.min(1f, value));
        if (!player.getWorld().isClient() && old != stability) {
            ModComponents.DECONTROLL.sync(player);
        }
    }

    @Override public void reduceStability(float amount) { 
        if (stability - amount < 0){
            setStability(0.0f);
        }else{
            setStability(stability - amount); 
        }
    }
    @Override public void addStability(float amount) {
        if (stability + amount > 1){
            setStability(1.0f);
        }else{
            setStability(stability + amount); 
        }}

    @Override
    public void readFromNbt(NbtCompound tag) {
        if (tag.contains("DecontrollStability")) stability = tag.getFloat("DecontrollStability");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putFloat("DecontrollStability", stability);
    }
}