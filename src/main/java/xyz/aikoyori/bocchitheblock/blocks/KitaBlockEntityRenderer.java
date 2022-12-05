package xyz.aikoyori.bocchitheblock.blocks;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class KitaBlockEntityRenderer implements BlockEntityRenderer<KitaBlockEntity> {
    public KitaBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}
    @Override
    public void render(KitaBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        matrices.pop();
    }
}
