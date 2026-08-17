package com.purpleinfenctionmod.client.model;

import com.purpleinfenctionmod.entity.SporeCreatureEntity;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SporeCreatureModel extends GeoModel<SporeCreatureEntity> {
    @Override
    public Identifier getModelResource(SporeCreatureEntity entity) {
        return new Identifier("purpleinfenctionmod", "geo/spore_creature.geo.json");
    }

    @Override
    public Identifier getTextureResource(SporeCreatureEntity entity) {
        return new Identifier("purpleinfenctionmod", "textures/entity/spore_creature.png");
    }

    @Override
    public Identifier getAnimationResource(SporeCreatureEntity entity) {
        return new Identifier("purpleinfenctionmod", "animations/spore_creature.animation.json");
    }
}
