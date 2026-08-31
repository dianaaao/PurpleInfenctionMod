package com.purpleinfenctionmod.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class InfectedLookNetworking {

    public static final Identifier ARENA_TARGET =
            new Identifier(
                    "purpleinfenctionmod",
                    "arena_target"
            );

    public static final Identifier CLEAR_ARENA_TARGET =
            new Identifier(
                    "purpleinfenctionmod",
                    "clear_arena_target"
            );

    public static void sendArenaTarget(
            ServerPlayerEntity player,
            BlockPos arenaPos
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(arenaPos);

        ServerPlayNetworking.send(
                player,
                ARENA_TARGET,
                buf
        );
    }

    public static void clearArenaTarget(
            ServerPlayerEntity player
    ) {
        ServerPlayNetworking.send(
                player,
                CLEAR_ARENA_TARGET,
                PacketByteBufs.empty()
        );
    }
}