package xyz.aikoyori.bocchitheblock.cca;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;

public interface BooleanComponent extends Component {
    boolean get();
    void set(boolean x);
}
