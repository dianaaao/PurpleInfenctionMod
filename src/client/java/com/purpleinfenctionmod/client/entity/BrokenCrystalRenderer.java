package com.purpleinfenctionmod.client.entity;

import com.purpleinfenctionmod.client.model.BrokenCrystalModel;
import com.purpleinfenctionmod.entity.BrokenFireCrystalEntity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BrokenCrystalRenderer extends GeoEntityRenderer<BrokenFireCrystalEntity> {
    public BrokenCrystalRenderer(EntityRendererFactory.Context context) {
        super(context, new BrokenCrystalModel());
    }
}
