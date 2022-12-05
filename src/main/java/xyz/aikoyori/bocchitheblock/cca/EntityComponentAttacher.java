package xyz.aikoyori.bocchitheblock.cca;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.minecraft.entity.LivingEntity;
import xyz.aikoyori.bocchitheblock.BocchiMod;

public class EntityComponentAttacher implements EntityComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, BocchiMod.KITA_BLOCK_EFFECT, livingEntity -> {
            return new KitaBlockEffectComponent();
        });
    }
}
