package com.purpleinfenctionmod.client.model;

import com.purpleinfenctionmod.entity.MushroomPetEntity;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MushroomPetModel extends GeoModel<MushroomPetEntity> {

    @Override
    public Identifier getModelResource(MushroomPetEntity entity) {
        return new Identifier("purpleinfenctionmod", "geo/mushroom_pet.geo.json");
    }

    @Override
    public Identifier getTextureResource(MushroomPetEntity entity) {
        return new Identifier("purpleinfenctionmod", "textures/entity/mushroom_pet_texture.png");
    }

    @Override
    public Identifier getAnimationResource(MushroomPetEntity entity) {
        return new Identifier("purpleinfenctionmod", "animations/mushroom_pet.animation.json");
    }
}
