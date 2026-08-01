package com.purpleinfenctionmod.client.entity;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import com.purpleinfenctionmod.client.model.MushroomMobModel;
import com.purpleinfenctionmod.entity.MushroomMobEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MushroomMobRenderer extends GeoEntityRenderer<MushroomMobEntity> {
    public MushroomMobRenderer(EntityRendererFactory.Context context) {
        super(context, new MushroomMobModel());
    }
}