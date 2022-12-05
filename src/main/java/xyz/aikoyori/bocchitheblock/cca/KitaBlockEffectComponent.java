package xyz.aikoyori.bocchitheblock.cca;

import net.minecraft.nbt.NbtCompound;

public class KitaBlockEffectComponent implements BooleanComponent{
    private boolean value = false;

    public KitaBlockEffectComponent() {
    }

    @Override
    public boolean get() {
        return value;
    }

    @Override
    public void set(boolean x) {
        value=x;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.value = tag.getBoolean("isKitanned");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("isKitanned",value);
    }
}
