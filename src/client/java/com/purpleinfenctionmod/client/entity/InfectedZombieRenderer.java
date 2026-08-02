package com.purpleinfenctionmod.client.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

public class InfectedZombieRenderer extends ZombieEntityRenderer {
    private static final Identifier TEXTURE =
            new Identifier("purpleinfenctionmod", "textures/entity/zombie/infected_zombie.png");

    public InfectedZombieRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(ZombieEntity entity) {
        return TEXTURE;
    }
}