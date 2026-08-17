package com.purpleinfenctionmod.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class InfectedLookNetworking {

    public static final Identifier CASTLE_TARGET =
            new Identifier(
                    "purpleinfenctionmod",
                    "castle_target"
            );

    public static final Identifier CLEAR_CASTLE_TARGET =
            new Identifier(
                    "purpleinfenctionmod",
                    "clear_castle_target"
            );

    public static void sendCastleTarget(
            ServerPlayerEntity player,
            BlockPos castlePos
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(castlePos);

        ServerPlayNetworking.send(
                player,
                CASTLE_TARGET,
                buf
        );
    }

    public static void clearCastleTarget(
            ServerPlayerEntity player
    ) {
        ServerPlayNetworking.send(
                player,
                CLEAR_CASTLE_TARGET,
                PacketByteBufs.empty()
        );
    }
}