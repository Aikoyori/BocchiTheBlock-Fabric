package xyz.aikoyori.bocchitheblock.utils.damagesrcs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class DoritosDamageSource extends EntityDamageSource {
    @Nullable
    private final Entity attacker;

    public DoritosDamageSource(String name, Entity projectile, @Nullable Entity attacker, boolean isToADragon) {
        super(name, projectile);
        this.attacker = attacker;
        if(isToADragon) this.setExplosive();
    }

    @Override
    @Nullable
    public Entity getSource() {
        return this.source;
    }

    @Override
    @Nullable
    public Entity getAttacker() {
        return this.attacker;
    }


    @Override
    public Text getDeathMessage(LivingEntity entity) {
        Text text = this.attacker == null ? this.source.getDisplayName() : this.attacker.getDisplayName();
        ItemStack itemStack = this.attacker instanceof LivingEntity ? ((LivingEntity)this.attacker).getMainHandStack() : ItemStack.EMPTY;
        String string = "death.attack." + this.name;
        String string2 = string + ".item";
        if (!itemStack.isEmpty() && itemStack.hasCustomName()) {
            return Text.translatable(string2, entity.getDisplayName(), text, itemStack.toHoverableText());
        }
        return Text.translatable(string, entity.getDisplayName(), text);
    }
}
