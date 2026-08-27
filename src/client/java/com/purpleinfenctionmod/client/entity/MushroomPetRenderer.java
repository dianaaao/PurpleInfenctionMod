package com.purpleinfenctionmod.client.entity;

import com.purpleinfenctionmod.client.model.MushroomPetModel;
import com.purpleinfenctionmod.entity.MushroomPetEntity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MushroomPetRenderer extends GeoEntityRenderer<MushroomPetEntity> {
    public MushroomPetRenderer(EntityRendererFactory.Context context) {
        super(context, new MushroomPetModel());
    }
}
