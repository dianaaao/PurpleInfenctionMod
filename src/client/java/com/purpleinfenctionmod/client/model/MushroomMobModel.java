package com.purpleinfenctionmod.client.model;

import net.minecraft.util.Identifier;
import com.purpleinfenctionmod.entity.MushroomMobEntity;
import software.bernie.geckolib.model.GeoModel;

public class MushroomMobModel extends GeoModel<MushroomMobEntity> {

    @Override
    public Identifier getModelResource(MushroomMobEntity entity) {
        return new Identifier("purpleinfenctionmod", "geo/mushroom_mob.geo.json");
    }

    @Override
    public Identifier getTextureResource(MushroomMobEntity entity) {
        return new Identifier("purpleinfenctionmod", "textures/entity/mushroom_mob_texture.png");
    }

    @Override
    public Identifier getAnimationResource(MushroomMobEntity entity) {
        return new Identifier("purpleinfenctionmod", "animations/mushroom_mob.animation.json");
    }
}