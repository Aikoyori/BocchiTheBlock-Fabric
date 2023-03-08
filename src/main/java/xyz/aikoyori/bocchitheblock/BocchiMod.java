package xyz.aikoyori.bocchitheblock;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import xyz.aikoyori.bocchitheblock.blocks.*;
import xyz.aikoyori.bocchitheblock.cca.BooleanComponent;
import xyz.aikoyori.bocchitheblock.entities.NijikaDoritosProjectile;
import xyz.aikoyori.bocchitheblock.items.NijikaDoritosItem;
import xyz.aikoyori.bocchitheblock.utils.damagesrcs.DoritosDamageSource;

public class BocchiMod implements ModInitializer {
    public static String MOD_ID = "bocchitheblock";

    public static BocchiBlock BOCCHI_BLOCK = new BocchiBlock(FabricBlockSettings.of(Material.STONE, MapColor.PINK).hardness(4.0f));
    public static KitaBlock KITA_BLOCK = new KitaBlock(FabricBlockSettings.of(Material.STONE, MapColor.RED).luminance(KitaBlock.createLightLevelFromKitanBlockState(15)).hardness(4.0f));
    public static NijikaBlock NIJIKA_BLOCK = new NijikaBlock(FabricBlockSettings.of(Material.STONE, MapColor.YELLOW).hardness(4.0f));
    public static ItemGroup BOCCHI_GROUP = FabricItemGroupBuilder.build(makeIdentifier("bocchimodtab"),() -> {return new ItemStack(Item.fromBlock(BOCCHI_BLOCK));});

    public static NijikaDoritosItem NIJIKA_DORITOS = new NijikaDoritosItem(new FabricItemSettings().food(NijikaDoritosItem.DORITOS).group(BOCCHI_GROUP));
    public static BlockEntityType<BocchiBlockEntity> BOCCHI_BLOCK_ENTITY;
    public static BlockEntityType<KitaBlockEntity> KITA_BLOCK_ENTITY;
    public static BlockEntityType<NijikaBlockEntity> NIJIKA_BLOCK_ENTITY;
    public static final EntityType<NijikaDoritosProjectile> NIJIKA_DORITOS_PROJECTILE = Registry.register(
            Registry.ENTITY_TYPE,makeIdentifier("nijika_doritos"),
            FabricEntityTypeBuilder.<NijikaDoritosProjectile>create(SpawnGroup.MISC,NijikaDoritosProjectile::new).dimensions(EntityDimensions.fixed(0.25f,0.25f)).trackRangeBlocks(64).trackedUpdateRate(10).build()
    );
    public static final ComponentKey<BooleanComponent> KITA_BLOCK_EFFECT =
            ComponentRegistry.getOrCreate(makeIdentifier("kita_block_effect"), BooleanComponent.class);

    public static final Identifier SILENT_ID = makeIdentifier("silent");
    public static SoundEvent SILENT_SOUND_EVENT = new SoundEvent(SILENT_ID);
    @Override
    public void onInitialize() {
        MixinExtrasBootstrap.init();
        Registry.register(Registry.BLOCK,makeIdentifier("bocchiblock"), BOCCHI_BLOCK);
        Registry.register(Registry.ITEM,makeIdentifier("bocchiblock"), new BlockItem(BOCCHI_BLOCK, new FabricItemSettings().group(BOCCHI_GROUP)));
        Registry.register(Registry.BLOCK,makeIdentifier("kitablock"), KITA_BLOCK);
        Registry.register(Registry.ITEM,makeIdentifier("kitablock"), new BlockItem(KITA_BLOCK, new FabricItemSettings().group(BOCCHI_GROUP)));
        Registry.register(Registry.BLOCK,makeIdentifier("nijikablock"), NIJIKA_BLOCK);
        Registry.register(Registry.ITEM,makeIdentifier("nijikablock"), new BlockItem(NIJIKA_BLOCK, new FabricItemSettings().group(BOCCHI_GROUP)));

        Registry.register(Registry.ITEM,makeIdentifier("nijika_doritos"),NIJIKA_DORITOS);


        BOCCHI_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,makeIdentifier("bocchiblockentity"),
                FabricBlockEntityTypeBuilder.create(BocchiBlockEntity::new, BOCCHI_BLOCK).build());
        KITA_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,makeIdentifier("kitablockentity"),
                FabricBlockEntityTypeBuilder.create(KitaBlockEntity::new, KITA_BLOCK).build());
        NIJIKA_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,makeIdentifier("nijikablockentity"),
                FabricBlockEntityTypeBuilder.create(NijikaBlockEntity::new, NIJIKA_BLOCK).build());
        Registry.register(Registry.SOUND_EVENT, SILENT_ID, SILENT_SOUND_EVENT);
    }
    public static DamageSource doritosDamage(Entity proj, @Nullable Entity attacker, boolean isDragon)
    {
        return new DoritosDamageSource("doritos",proj,attacker,isDragon);
    }
    public static Identifier makeIdentifier(String in)
    {
        return new Identifier(MOD_ID,in);
    }
}
