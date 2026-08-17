package com.purpleinfenctionmod.client.entity;

import com.purpleinfenctionmod.client.model.SporeCreatureModel;
import com.purpleinfenctionmod.entity.SporeCreatureEntity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SporeCreatureRenderer extends GeoEntityRenderer<SporeCreatureEntity> {
    public SporeCreatureRenderer(EntityRendererFactory.Context context) {
        super(context, new SporeCreatureModel());
    }
}
