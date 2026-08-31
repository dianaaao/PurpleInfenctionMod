package com.purpleinfenctionmod.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

public class InfectedLookClient {

    private static BlockPos arenaTarget = null;

    public static void initialize() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null || arenaTarget == null) {
                return;
            }

            lookAtArena(client);
        });
    }

    public static void setArenaTarget(BlockPos pos) {
        arenaTarget = pos;
    }

    public static void clearArenaTarget() {
        arenaTarget = null;
    }

    private static void lookAtArena(MinecraftClient client) {

    if (client.player == null || arenaTarget == null) {
        return;
    }

    double targetX = arenaTarget.getX() + 0.5;
    double targetY = arenaTarget.getY() + 70;
    double targetZ = arenaTarget.getZ() + 0.5;

    double dx = targetX - client.player.getX();
    double dy = targetY - client.player.getEyeY();
    double dz = targetZ - client.player.getZ();

    double horizontalDistance =
            Math.sqrt(dx * dx + dz * dz);

    float targetYaw = (float) (
            Math.toDegrees(Math.atan2(dz, dx)) - 90.0
    );

    float targetPitch = (float) (
            -Math.toDegrees(
                    Math.atan2(dy, horizontalDistance)
            )
    );

    /*
     * How strongly the infection pulls the camera.
     *
     * 0.02 = very weak
     * 0.05 = noticeable
     * 0.10 = strong
     * 1.00 = instant lock
     */
    float strength = 0.05F;

    float currentYaw = client.player.getYaw();
    float currentPitch = client.player.getPitch();

    float yawDifference = wrapDegrees(targetYaw - currentYaw);

    float newYaw =
            currentYaw + yawDifference * strength;

    float newPitch =
            currentPitch
                    + (targetPitch - currentPitch) * strength;

    client.player.setYaw(newYaw);
    client.player.setPitch(newPitch);
}
private static float wrapDegrees(float degrees) {

    degrees %= 360.0F;

    if (degrees >= 180.0F) {
        degrees -= 360.0F;
    }

    if (degrees < -180.0F) {
        degrees += 360.0F;
    }

    return degrees;
}

}