package com.purpleinfenctionmod.client.model;

import com.purpleinfenctionmod.entity.RottingSporeFungusEntity;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.util.Identifier;

public class RottingSporeFungusModel extends GeoModel<RottingSporeFungusEntity> {
    @Override
    public Identifier getModelResource(RottingSporeFungusEntity entity) {
        return new Identifier("purpleinfenctionmod", "geo/spore_fungus.geo.json");
    }

    @Override
    public Identifier getTextureResource(RottingSporeFungusEntity entity) {
        return new Identifier("purpleinfenctionmod", "textures/entity/spore_fungus_texture.png");
    }

    @Override
    public Identifier getAnimationResource(RottingSporeFungusEntity entity) {
        return new Identifier("purpleinfenctionmod", "animations/spore_fungus.animation.json");
    }
}
