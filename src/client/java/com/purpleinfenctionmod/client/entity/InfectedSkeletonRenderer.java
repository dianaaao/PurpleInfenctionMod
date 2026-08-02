package com.purpleinfenctionmod.client.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;

public class InfectedSkeletonRenderer extends SkeletonEntityRenderer {
    private static final Identifier TEXTURE =
            new Identifier("purpleinfenctionmod", "textures/entity/skeleton/infected_skeleton.png");

    public InfectedSkeletonRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(AbstractSkeletonEntity entity) {
        return TEXTURE;
    }
}