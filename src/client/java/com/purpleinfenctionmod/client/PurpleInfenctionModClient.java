package com.purpleinfenctionmod.client;

import com.purpleinfenctionmod.block.ModBlocks;
import com.purpleinfenctionmod.client.entity.InfectedCreeperRenderer;
import com.purpleinfenctionmod.client.entity.InfectedSkeletonRenderer;
import com.purpleinfenctionmod.client.entity.InfectedZombieRenderer;
import com.purpleinfenctionmod.client.entity.MushroomMobRenderer;
import com.purpleinfenctionmod.client.model.RespiratorModel;
import com.purpleinfenctionmod.entity.ModEntities;
import com.purpleinfenctionmod.item.ModItems;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
// import net.minecraft.client.render.OverlayTexture;
// import net.minecraft.client.render.VertexConsumer;
// import net.minecraft.client.render.VertexConsumerProvider;
// import net.minecraft.client.render.entity.model.BipedEntityModel;
// import net.minecraft.client.util.math.MatrixStack;
// import net.minecraft.entity.EquipmentSlot;
// import net.minecraft.entity.LivingEntity;
// import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class PurpleInfenctionModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(
            ModBlocks.INFECTED_SMALL_MUSHROOM,
            RenderLayer.getCutout()
        );
        EntityRendererRegistry.register(
            ModEntities.MUSHROOM_MOB, 
            MushroomMobRenderer::new
        );

        EntityModelLayerRegistry.registerModelLayer(
            RespiratorModel.LAYER,
            RespiratorModel::getTexturedModelData
        );

        EntityRendererRegistry.register(ModEntities.INFECTED_ZOMBIE, InfectedZombieRenderer::new);
        EntityRendererRegistry.register(ModEntities.INFECTED_SKELETON, InfectedSkeletonRenderer::new);
        EntityRendererRegistry.register(ModEntities.INFECTED_CREEPER, InfectedCreeperRenderer::new);

        ArmorRenderer.register(
            (matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
                RespiratorModel model = new RespiratorModel(
                    MinecraftClient.getInstance().getEntityModelLoader().getModelPart(RespiratorModel.LAYER)
                );

                model.setAngles(contextModel.head.yaw, contextModel.head.pitch);

                ArmorRenderer.renderPart(
                    matrices,
                    vertexConsumers,
                    light,
                    stack,
                    model,
                    new Identifier("purpleinfenctionmod", "textures/models/armor/respirator.png")
                );
            },
            ModItems.RESPIRATOR
        );
    }
}
