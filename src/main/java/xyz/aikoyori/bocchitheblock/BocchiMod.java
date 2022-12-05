package xyz.aikoyori.bocchitheblock;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.aikoyori.bocchitheblock.blocks.BocchiBlock;
import xyz.aikoyori.bocchitheblock.blocks.BocchiBlockEntity;
import xyz.aikoyori.bocchitheblock.blocks.KitaBlock;
import xyz.aikoyori.bocchitheblock.blocks.KitaBlockEntity;
import xyz.aikoyori.bocchitheblock.cca.BooleanComponent;

public class BocchiMod implements ModInitializer {
    public static String MOD_ID = "bocchitheblock";
    public static BocchiBlock BOCCHI_BLOCK = new BocchiBlock(FabricBlockSettings.of(Material.WOOD, MapColor.PINK).hardness(4.0f));
    public static KitaBlock KITA_BLOCK = new KitaBlock(FabricBlockSettings.of(Material.WOOD, MapColor.RED).luminance(KitaBlock.createLightLevelFromKitanBlockState(15)).hardness(4.0f));
    public static BlockEntityType<BocchiBlockEntity> BOCCHI_BLOCK_ENTITY;
    public static BlockEntityType<KitaBlockEntity> KITA_BLOCK_ENTITY;

    public static final ComponentKey<BooleanComponent> KITA_BLOCK_EFFECT =
            ComponentRegistry.getOrCreate(makeIdentifier("kita_block_effect"), BooleanComponent.class);
    @Override
    public void onInitialize() {
        MixinExtrasBootstrap.init();
        Registry.register(Registry.BLOCK,makeIdentifier("bocchiblock"), BOCCHI_BLOCK);
        Registry.register(Registry.ITEM,makeIdentifier("bocchiblock"), new BlockItem(BOCCHI_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
        Registry.register(Registry.BLOCK,makeIdentifier("kitablock"), KITA_BLOCK);
        Registry.register(Registry.ITEM,makeIdentifier("kitablock"), new BlockItem(KITA_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));

        BOCCHI_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,makeIdentifier("bocchiblockentity"),
                FabricBlockEntityTypeBuilder.create(BocchiBlockEntity::new, BOCCHI_BLOCK).build());
        KITA_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,makeIdentifier("kitablockentity"),
                FabricBlockEntityTypeBuilder.create(KitaBlockEntity::new, KITA_BLOCK).build());
    }

    public static Identifier makeIdentifier(String in)
    {
        return new Identifier(MOD_ID,in);
    }
}
