package xyz.aikoyori.bocchitheblock.entities.renderer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import xyz.aikoyori.bocchitheblock.entities.NijikaDoritosProjectile;

public class NijikaProjectileRenderer<T extends NijikaDoritosProjectile> extends EntityRenderer<T> {
    private final ItemRenderer itemRenderer;
    public NijikaProjectileRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public Identifier getTexture(NijikaDoritosProjectile entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.push();
        matrices.scale(0.5f,0.5f,0.5f);
        matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(90+entity.getThrownYaw()));
        this.itemRenderer.renderItem(entity.getStack(), ModelTransformation.Mode.FIXED,light, OverlayTexture.DEFAULT_UV,matrices,vertexConsumers,((Entity)entity).getId());
        matrices.pop();
    }
}
