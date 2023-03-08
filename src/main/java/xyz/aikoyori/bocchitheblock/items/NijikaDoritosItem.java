package xyz.aikoyori.bocchitheblock.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.aikoyori.bocchitheblock.entities.NijikaDoritosProjectile;

public class NijikaDoritosItem extends Item {
    public static final FoodComponent DORITOS = new FoodComponent.Builder().hunger(1).saturationModifier(1.5f).build();
    public NijikaDoritosItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFood() {
        return true;
    }

    @Nullable
    @Override
    public FoodComponent getFoodComponent() {
        return DORITOS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if(!user.isSneaking())
        {

            if (!world.isClient) {
                NijikaDoritosProjectile dorito = new NijikaDoritosProjectile(world,user.getPos().add(0,user.getEyeHeight(user.getPose()),0).add(user.getRotationVector().multiply(0.75)));
                dorito.setVelocity(user.getRotationVector().multiply(2));
                dorito.setThrownYaw(user.getYaw());
                world.spawnEntity(dorito);
            }
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!user.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return TypedActionResult.success(itemStack, world.isClient());

        }
        return super.use(world, user, hand);
    }

}
