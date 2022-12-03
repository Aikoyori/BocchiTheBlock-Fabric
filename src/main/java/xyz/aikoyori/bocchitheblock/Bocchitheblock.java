package xyz.aikoyori.bocchitheblock;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.aikoyori.bocchitheblock.blocks.BocchiBlock;
import xyz.aikoyori.bocchitheblock.blocks.BocchiBlockEntity;

public class Bocchitheblock implements ModInitializer {
    public static String MOD_ID = "bocchitheblock";
    public static BocchiBlock bocchiBlock = new BocchiBlock(FabricBlockSettings.of(Material.WOOD, MapColor.PINK).hardness(4.0f));
    public static BlockEntityType<BocchiBlockEntity> bocchiBlockEntity;
    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK,makeIdentifier("bocchiblock"),bocchiBlock);
        Registry.register(Registry.ITEM,makeIdentifier("bocchiblock"), new BlockItem(bocchiBlock, new FabricItemSettings().group(ItemGroup.MISC)));

        bocchiBlockEntity = Registry.register(Registry.BLOCK_ENTITY_TYPE,makeIdentifier("bocchiblockentity"),
                FabricBlockEntityTypeBuilder.create(BocchiBlockEntity::new,bocchiBlock).build());
    }

    public static Identifier makeIdentifier(String in)
    {
        return new Identifier(MOD_ID,in);
    }
}
