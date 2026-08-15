package com.purpleinfenctionmod.world.biome;

public class InfectedBiomeFilter {

    private static final int CENTER_X = 0;
    private static final int CENTER_Z = 0;

    private static final long INNER_RADIUS_SQ = 1_000_000L;
    private static final long OUTER_RADIUS_SQ = 9_000_000L;

    public static boolean isWithinInfectedZone(int blockX, int blockZ) {
        long dx = (long) blockX - CENTER_X;
        long dz = (long) blockZ - CENTER_Z;

        long distanceSquared = dx * dx + dz * dz;

        return distanceSquared >= INNER_RADIUS_SQ
            && distanceSquared <= OUTER_RADIUS_SQ;
    }
}