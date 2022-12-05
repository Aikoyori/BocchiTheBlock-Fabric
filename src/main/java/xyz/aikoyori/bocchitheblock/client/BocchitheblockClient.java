package xyz.aikoyori.bocchitheblock.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import xyz.aikoyori.bocchitheblock.BocchiMod;
import xyz.aikoyori.bocchitheblock.blocks.KitaBlockEntityRenderer;

@Environment(EnvType.CLIENT)
public class BocchitheblockClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(BocchiMod.KITA_BLOCK_ENTITY, KitaBlockEntityRenderer::new);
    }
}
