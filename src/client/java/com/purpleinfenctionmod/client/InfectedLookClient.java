package com.purpleinfenctionmod.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

public class InfectedLookClient {

    private static BlockPos castleTarget = null;

    public static void initialize() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null || castleTarget == null) {
                return;
            }

            lookAtCastle(client);
        });
    }

    public static void setCastleTarget(BlockPos pos) {
        castleTarget = pos;
    }

    public static void clearCastleTarget() {
        castleTarget = null;
    }

    private static void lookAtCastle(MinecraftClient client) {

    if (client.player == null || castleTarget == null) {
        return;
    }

    double targetX = castleTarget.getX() + 0.5;
    double targetY = castleTarget.getY() + 70;
    double targetZ = castleTarget.getZ() + 0.5;

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