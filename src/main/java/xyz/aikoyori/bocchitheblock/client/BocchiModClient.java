package xyz.aikoyori.bocchitheblock.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import xyz.aikoyori.bocchitheblock.BocchiMod;
import xyz.aikoyori.bocchitheblock.blocks.KitaBlockEntityRenderer;
import xyz.aikoyori.bocchitheblock.entities.renderer.NijikaProjectileRenderer;

@Environment(EnvType.CLIENT)
public class BocchiModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(BocchiMod.KITA_BLOCK_ENTITY, KitaBlockEntityRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BocchiMod.NIJIKA_BLOCK, RenderLayer.getCutout());
        EntityRendererRegistry.register(BocchiMod.NIJIKA_DORITOS_PROJECTILE, (context) ->
                new NijikaProjectileRenderer<>(context));
    }
}
