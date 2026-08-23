package com.purpleinfenctionmod.client.model;

import com.purpleinfenctionmod.entity.CrystalEntity;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CrystalModel extends GeoModel<CrystalEntity> {

    @Override
    public Identifier getModelResource(CrystalEntity entity) {
        return new Identifier("purpleinfenctionmod", "geo/old_fire_crystal.geo.json");
    }

    @Override
    public Identifier getTextureResource(CrystalEntity entity) {
        return new Identifier("purpleinfenctionmod", "textures/entity/old_fire_crystal_texture.png");
    }

    @Override
    public Identifier getAnimationResource(CrystalEntity entity) {
        return new Identifier("purpleinfenctionmod", "animations/old_fire_crystal.animation.json");
    }
}
