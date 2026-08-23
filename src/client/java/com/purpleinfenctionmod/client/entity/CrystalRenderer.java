package com.purpleinfenctionmod.client.entity;

import com.purpleinfenctionmod.client.model.CrystalModel;
import com.purpleinfenctionmod.entity.CrystalEntity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CrystalRenderer extends GeoEntityRenderer<CrystalEntity> {
    public CrystalRenderer(EntityRendererFactory.Context context) {
        super(context, new CrystalModel());
    }
}
