package com.purpleinfenctionmod.client.model;

import com.purpleinfenctionmod.entity.BrokenFireCrystalEntity;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BrokenCrystalModel extends GeoModel<BrokenFireCrystalEntity> {

    @Override
    public Identifier getModelResource(BrokenFireCrystalEntity entity) {
        return new Identifier("purpleinfenctionmod", "geo/broken_fire_crystal.geo.json");
    }

    @Override
    public Identifier getTextureResource(BrokenFireCrystalEntity entity) {
        return new Identifier("purpleinfenctionmod", "textures/entity/broken_fire_crystal_texture.png");
    }

    @Override
    public Identifier getAnimationResource(BrokenFireCrystalEntity entity) {
        return new Identifier("purpleinfenctionmod", "animations/broken_fire_crystal.animation.json");
    }
}
