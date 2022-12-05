package xyz.aikoyori.bocchitheblock.blocks;

import com.google.common.primitives.Doubles;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.aikoyori.bocchitheblock.BocchiMod;

import java.util.ArrayList;
import java.util.Random;

public class KitaBlockEntity extends BlockEntity {


    private Random random = new Random();
    private Box aoe;
    private static int uptickid = 0;
    private int id;
    private static int AOE_DIST = 8;
    public KitaBlockEntity(BlockPos pos, BlockState state) {
        super(BocchiMod.KITA_BLOCK_ENTITY, pos, state);
        aoe = new Box(pos.add(-AOE_DIST,-AOE_DIST,-AOE_DIST),pos.add(AOE_DIST,AOE_DIST,AOE_DIST));
        id = uptickid++;
    }


    public static void tick(World world1, BlockPos pos, BlockState state1, KitaBlockEntity blockEntity) {
        if(blockEntity.isRemoved()) return;
        if(state1.getBlock() == BocchiMod.KITA_BLOCK && state1.get(KitaBlock.KITAN))
        {
            world1.addParticle(ParticleTypes.END_ROD,true,
                    pos.getX()+0.5+blockEntity.random.nextDouble(-0.2,0.2),
                    pos.getY()+0.5+blockEntity.random.nextDouble(-0.2,0.2),
                    pos.getZ()+0.5+blockEntity.random.nextDouble(-0.2,0.2),
                    blockEntity.random.nextDouble(-.1,.1),
                    blockEntity.random.nextDouble(-.1,.1),
                    blockEntity.random.nextDouble(-.1,.1));
            world1.addParticle(ParticleTypes.HAPPY_VILLAGER,true,
                    pos.getX()+0.5+blockEntity.random.nextDouble(-0.7,0.7),
                    pos.getY()+0.5+blockEntity.random.nextDouble(-0.7,0.7),
                    pos.getZ()+0.5+blockEntity.random.nextDouble(-0.7,0.7),
                    blockEntity.random.nextDouble(-1,1),
                    blockEntity.random.nextDouble(-1,1),
                    blockEntity.random.nextDouble(-1,1));
            var entities = world1.getEntitiesByClass(LivingEntity.class, blockEntity.aoe.expand(16), entity -> { return true;});
            //entities.sort((entity1, entity2) -> Doubles.compare(entity1.squaredDistanceTo(new Vec3d(pos.getX(),pos.getY(),pos.getZ())), entity2.squaredDistanceTo(new Vec3d(pos.getX(),pos.getY(),pos.getZ()))));
            for(LivingEntity le: entities)
            {
                if(blockEntity.aoe.contains(le.getPos()))
                {
                    BocchiMod.KITA_BLOCK_EFFECT.get(le).set(true);
                }
                else
                {
                    BocchiMod.KITA_BLOCK_EFFECT.get(le).set(BocchiMod.KITA_BLOCK_EFFECT.get(le).get());
                }
            }
        }

    }
}
