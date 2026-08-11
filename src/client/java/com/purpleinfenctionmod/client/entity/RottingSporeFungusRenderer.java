package com.purpleinfenctionmod.client.entity;

import com.purpleinfenctionmod.client.model.RottingSporeFungusModel;
import com.purpleinfenctionmod.entity.RottingSporeFungusEntity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RottingSporeFungusRenderer extends GeoEntityRenderer<RottingSporeFungusEntity> {
    public RottingSporeFungusRenderer(EntityRendererFactory.Context context) {
        super(context, new RottingSporeFungusModel());
    }
}
