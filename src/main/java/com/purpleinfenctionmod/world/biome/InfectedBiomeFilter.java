package com.purpleinfenctionmod.world.biome;

public class InfectedBiomeFilter {

    private static final double CENTER_X = 0.0;
    private static final double CENTER_Z = 0.0;

    private static final double INNER_RADIUS = 1000.0;
    private static final double OUTER_RADIUS = 3000.0;

    private static final double BORDER_ROUGHNESS = 20.0;

    public static boolean isWithinInfectedZone(double blockX, double blockZ) {
        double dx = blockX - CENTER_X;
        double dz = blockZ - CENTER_Z;

        double distance = Math.sqrt(dx * dx + dz * dz);

        double angle = Math.atan2(dz, dx);

        double borderOffset =
            Math.sin(angle * 8.0) * BORDER_ROUGHNESS +
            Math.sin(angle * 17.0) * (BORDER_ROUGHNESS * 0.4);

        double innerRadius = INNER_RADIUS + borderOffset;
        double outerRadius = OUTER_RADIUS + borderOffset;

        return distance >= innerRadius
            && distance <= outerRadius;
    }
}