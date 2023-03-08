package xyz.aikoyori.bocchitheblock.entities;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import xyz.aikoyori.bocchitheblock.BocchiMod;
import xyz.aikoyori.bocchitheblock.blocks.BocchiBlock;
import xyz.aikoyori.bocchitheblock.blocks.NijikaBlock;
import xyz.aikoyori.bocchitheblock.utils.BocUtils;

import java.util.Optional;
import java.util.UUID;

public class NijikaDoritosProjectile extends ThrownEntity {

    private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(NijikaDoritosProjectile.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private static final TrackedData<Float> LIFE = DataTracker.registerData(NijikaDoritosProjectile.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Boolean> DOES_PIERCE = DataTracker.registerData(NijikaDoritosProjectile.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Float> THROWN_YAW = DataTracker.registerData(NijikaDoritosProjectile.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> DAMAGE = DataTracker.registerData(NijikaDoritosProjectile.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Optional<UUID>> ENTITY_TRACKING = DataTracker.registerData(NijikaDoritosProjectile.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    public NijikaDoritosProjectile(EntityType<? extends NijikaDoritosProjectile> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);

    }
    public NijikaDoritosProjectile(World world) {
        this(BocchiMod.NIJIKA_DORITOS_PROJECTILE,world);
    }
    public NijikaDoritosProjectile(World world, Vec3d pos) {
        this(BocchiMod.NIJIKA_DORITOS_PROJECTILE,world);
        this.setPos(pos.getX(),pos.getY(),pos.getZ());
    }


    @Override
    public void tick() {
        addLife();
        //world.addParticle(ParticleTypes.FLAME,this.getX(),this.getY(),this.getZ(),0,0,0);
        if(getLife()>150)
        {
            this.discard();
        }
        if(getEntityTrackedUUID().isPresent() && !world.isClient())
        {
            ServerWorld svw = (ServerWorld) world;
            if(svw.getEntity(getEntityTrackedUUID().get()) != null)
            {
                Entity ent = svw.getEntity(getEntityTrackedUUID().get());
                if(ent.isAlive())
                {
                    Vec3d entpos = ent.getPos().add(new Vec3d(0,ent.getHeight()/2f,0));
                    this.setThrownYaw(BocUtils.getDegreesToRotateFromTwoVector3(entpos,this.getPos()));
                    Vec3d velocityto = entpos.add(this.getPos().multiply(-1)).normalize().multiply(1.5);
                    //this.setCustomName(Text.literal(""+velocityto));
                    //this.setCustomNameVisible(true);
                    if(entpos.add(this.getPos().multiply(-1)).length() > ent.getBoundingBox().getXLength()*1.5 ||
                            entpos.add(this.getPos().multiply(-1)).length() > ent.getBoundingBox().getZLength()*1.5
                    ) this.setVelocity(velocityto);
                }
                else{

                }

            }

        }
        super.tick();

    }
    public float getThrownYaw() {
        return this.getDataTracker().get(THROWN_YAW);
    }
    public void setThrownYaw(float thrownYaw) {
        this.getDataTracker().set(THROWN_YAW,thrownYaw);
    }
    public boolean setIsPiecing() {
        return this.getDataTracker().get(DOES_PIERCE);
    }
    public void isPiecing(boolean thrownYaw) {
        this.getDataTracker().set(DOES_PIERCE,thrownYaw);
    }
    public float getLife() {
        return this.getDataTracker().get(LIFE);
    }
    public void setLife(float newLife) {
        this.getDataTracker().set(LIFE,newLife);
    }
    public void addLife() {
        this.setLife((float) (getLife()+Math.max(1,this.getVelocity().length())));
    }


    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(LIFE,0f);
        this.dataTracker.startTracking(THROWN_YAW,0f);
        this.dataTracker.startTracking(DAMAGE,2f);
        this.dataTracker.startTracking(DOES_PIERCE,false);
        this.dataTracker.startTracking(ITEM,ItemStack.EMPTY);
        this.dataTracker.startTracking(ENTITY_TRACKING,Optional.empty());
    }
    Item getDefaultItem(){
        return BocchiMod.NIJIKA_DORITOS;
    }
    public void setItem(ItemStack item) {
        if (!item.isOf(this.getDefaultItem()) || item.hasNbt()) {
            this.getDataTracker().set(ITEM, Util.make(item.copy(), stack -> stack.setCount(1)));
        }
    }

    public float getDamage() {
        return this.getDataTracker().get(DAMAGE);
    }

    public void setDamage(float sett) {
        this.getDataTracker().set(DAMAGE,sett);
    }

    public ItemStack getStack() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemStack;
    }
    protected ItemStack getItem() {
        return this.getDataTracker().get(ITEM);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setLife(nbt.getFloat("DoritoLife"));
        setThrownYaw(nbt.getFloat("ThrownYaw"));
        setDamage(nbt.getFloat("damage"));

           try
            {
                setEntityTrackedUUID(nbt.getUuid("EntityTracking"));
            }
           catch(Exception ignored){

           }
        ItemStack itemStack = ItemStack.fromNbt(nbt.getCompound("Item"));
        this.setItem(itemStack);
    }

    protected ItemStack asItemStack() {
        return new ItemStack(BocchiMod.NIJIKA_DORITOS);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putFloat("DoritoLife",getLife());
        nbt.putFloat("ThrownYaw",getThrownYaw());
        nbt.putFloat("damage",getDamage());
        if(getEntityTrackedUUID().isPresent())
        {

            nbt.putUuid("EntityTracking",getEntityTrackedUUID().get());
        }
        else{
            nbt.putUuid("EntityTracking",new UUID(0,0));
        }
        ItemStack itemStack = this.getItem();
        if (!itemStack.isEmpty()) {
            nbt.put("Item", itemStack.writeNbt(new NbtCompound()));
        }
    }

    public Optional<UUID> getEntityTrackedUUID(){
        return (this.getDataTracker().get(ENTITY_TRACKING).isPresent()?this.getDataTracker().get(ENTITY_TRACKING):Optional.empty());
    }
    public void setEntityTrackedUUID(UUID uuidset){
        this.getDataTracker().set(ENTITY_TRACKING, Optional.of(uuidset));
    }

    protected SoundEvent getHitSound() {
        return BocchiMod.SILENT_SOUND_EVENT;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity en = entityHitResult.getEntity();
        if(entityHitResult.getEntity() != null)
        {
            boolean isEnder = en instanceof EnderDragonEntity || en instanceof EnderDragonPart;
            boolean damaged = en.damage(BocchiMod.doritosDamage(this,getOwner(),isEnder),getDamage());
            //world.createExplosion(this,this.getX(),this.getY(),this.getZ(),5, Explosion.DestructionType.NONE);
            if(damaged)
            {
                //this.setDamage(this.getDamage()*1.1f);
                if( entityHitResult.getEntity() instanceof LivingEntity liv)
                    liv.takeKnockback(0.1,this.getX()-liv.getX(),this.getZ()-liv.getZ());
                    //liv.takeKnockback(0.25f/this.getVelocity().length(),this.getX()-liv.getX(),this.getZ()-liv.getZ());
            }
            this.discard();
        }
    }


    @Override
    public boolean isSubmergedInWater() {
        return false;
    }

    @Override
    public boolean isTouchingWater() {
        return false;
    }
}
