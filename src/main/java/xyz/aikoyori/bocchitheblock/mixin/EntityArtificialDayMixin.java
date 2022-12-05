package xyz.aikoyori.bocchitheblock.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.aikoyori.bocchitheblock.BocchiMod;
import xyz.aikoyori.bocchitheblock.blocks.KitaBlock;

@Mixin(MobEntity.class)
public abstract class EntityArtificialDayMixin extends LivingEntity {

    @Shadow protected abstract boolean isAffectedByDaylight();

    protected EntityArtificialDayMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "isAffectedByDaylight",at = @At("HEAD"),cancellable = true)
    void dayoverride(CallbackInfoReturnable<Boolean> cir){
        if(BocchiMod.KITA_BLOCK_EFFECT.get((MobEntity)(Object)this).get() && !(this.isWet() || this.inPowderSnow || this.wasInPowderSnow)){
            cir.setReturnValue(true);
        }
    }


    @Inject(method = "tickMovement",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/World;getProfiler()Lnet/minecraft/util/profiler/Profiler;",shift = At.Shift.AFTER))
    void whenthemobisnearthekitan(CallbackInfo ci){
        BocchiMod.KITA_BLOCK_EFFECT.get(this).set(false);
    }

}
