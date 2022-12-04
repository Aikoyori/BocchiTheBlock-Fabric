package xyz.aikoyori.bocchitheblock.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.enums.Instrument;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.aikoyori.bocchitheblock.Bocchitheblock;

import static net.minecraft.block.enums.Instrument.GUITAR;

@Mixin(Instrument.class)
public class InstrumentInjection {
    @Inject(method = "fromBlockState",at=@At("HEAD"),cancellable = true)
    private static void itsInstrumentinTime(BlockState state, CallbackInfoReturnable<Instrument> cir)
    {
        if(state.getBlock() == Bocchitheblock.bocchiBlock)
        {
            cir.setReturnValue(GUITAR);
        }
    }
}
