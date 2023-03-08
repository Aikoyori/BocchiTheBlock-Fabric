package xyz.aikoyori.bocchitheblock.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.aikoyori.bocchitheblock.BocchiMod;
import xyz.aikoyori.bocchitheblock.entities.NijikaDoritosProjectile;
import xyz.aikoyori.bocchitheblock.utils.BocUtils;

import java.util.List;
import java.util.Random;

public class NijikaBlockEntity extends BlockEntity {

    private Random random;
    private int age;
    private int lastreloadage;
    private boolean reloaded;
    public NijikaBlockEntity(BlockPos pos, BlockState state) {
        super(BocchiMod.NIJIKA_BLOCK_ENTITY, pos, state);
        random = new Random();
        age = 0;
        lastreloadage = 0;
        reloaded = false;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        age = nbt.getInt("age");
        lastreloadage = nbt.getInt("lastreloadage");
        reloaded = nbt.getBoolean("reloaded");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("age",age);
        nbt.putInt("lastreloadage",lastreloadage);
        nbt.putBoolean("reloaded",reloaded);
    }

    public static void tick(World world1, BlockPos pos, BlockState state1, NijikaBlockEntity be) {
        be.markDirty();
        if(state1.get(NijikaBlock.ACTIVATED)) be.age++;
        if(be.age- be.lastreloadage >= 60)
        {
            be.reloaded = true;
        }
        //LivingEntity en = world1.getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT,null,pos.getX(),pos.getY(),pos.getZ());
        List<MobEntity> enl = world1.getEntitiesByClass(MobEntity.class,new Box(pos.add(-32,-32,-32),pos.add(32,32,32)), livingEntity -> {
            return !livingEntity.isDead();});
        //if(enl.size()>0 && be.reloaded)
        Vec3d center = Vec3d.ofCenter(pos);
        if(enl.size()>0 && state1.get(NijikaBlock.ACTIVATED) && be.reloaded)
        {
            //for(MobEntity en: enl)
            {
                Entity en = BocUtils.getClosestEntity(enl,center.getX(),center.getY(),center.getZ());
                if(en != null)
                {
                    NijikaDoritosProjectile dorito = new NijikaDoritosProjectile(world1);
                    Vec3d pos2 = Vec3d.ofCenter(pos);
                    Vec3d velfromlol = new Vec3d(en.getX()-pos2.getX(),(en.getY()+(en.getHeight()/3f))-pos2.getY(),en.getZ()-pos2.getZ());

                    //dorito.setVelocity(velfromlol.multiply(1/5.0f));
                    float rot = (float) (MathHelper.atan2(velfromlol.getZ(),velfromlol.getX()) * 180/MathHelper.PI) + 90;
                    //dorito.setCustomName(Text.literal(""+rot));
                    //dorito.setCustomNameVisible(true);
                    dorito.setThrownYaw(rot);
                    dorito.setEntityTrackedUUID(en.getUuid());
                    dorito.setPosition(pos2.add(velfromlol.normalize().multiply(1.1)));
                    dorito.setDamage(2);
                    world1.spawnEntity(dorito);
                    be.reloaded = false;
                    be.lastreloadage = be.age;
                }
            }
        }


    }

}
