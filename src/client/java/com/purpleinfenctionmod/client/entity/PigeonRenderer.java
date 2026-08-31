package com.purpleinfenctionmod.client.entity;

import com.purpleinfenctionmod.client.model.PigeonModel;
import com.purpleinfenctionmod.entity.PigeonEntity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PigeonRenderer extends GeoEntityRenderer<PigeonEntity> {
    public PigeonRenderer(EntityRendererFactory.Context context) {
        super(context, new PigeonModel());
    }
}
