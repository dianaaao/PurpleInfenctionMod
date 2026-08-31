package com.purpleinfenctionmod.client.model;

import com.purpleinfenctionmod.entity.PigeonEntity;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PigeonModel extends GeoModel<PigeonEntity> {

    @Override
    public Identifier getModelResource(PigeonEntity entity) {
        return new Identifier("purpleinfenctionmod", "geo/pigeon.geo.json");
    }

    @Override
    public Identifier getTextureResource(PigeonEntity entity) {
        return new Identifier("purpleinfenctionmod", "textures/entity/pigeon_texture.png");
    }

    @Override
    public Identifier getAnimationResource(PigeonEntity entity) {
        return new Identifier("purpleinfenctionmod", "animations/pigeon.animation.json");
    }
}
