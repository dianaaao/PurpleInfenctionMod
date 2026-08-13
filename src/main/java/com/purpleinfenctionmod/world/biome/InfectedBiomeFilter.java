package com.purpleinfenctionmod.world.biome;

public class InfectedBiomeFilter {

    private static final long MIN_RADIUS = 1000L;
    private static final long MAX_RADIUS = 3000L;

    private static final long MIN_RADIUS_SQUARED =
            MIN_RADIUS * MIN_RADIUS;

    private static final long MAX_RADIUS_SQUARED =
            MAX_RADIUS * MAX_RADIUS;

    public static boolean isWithinInfectedZone(int x, int z) {
        long distanceSquared =
                (long) x * x +
                (long) z * z;

        return distanceSquared >= MIN_RADIUS_SQUARED
            && distanceSquared <= MAX_RADIUS_SQUARED;
    }
}