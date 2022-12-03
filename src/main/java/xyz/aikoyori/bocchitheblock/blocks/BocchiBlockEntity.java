package xyz.aikoyori.bocchitheblock.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.aikoyori.bocchitheblock.Bocchitheblock;
import xyz.aikoyori.bocchitheblock.helper.BlockRotationCalculation;

public class BocchiBlockEntity extends BlockEntity {
    public int age;
    public BocchiBlockEntity(BlockPos pos, BlockState state) {
        super(Bocchitheblock.bocchiBlockEntity, pos, state);
        age = 0;
    }
    public static void tick(World world, BlockPos pos, BlockState state, BocchiBlockEntity be) {
        be.age++;
        if(state.get(BocchiBlock.PANICKING) && world.isPlayerInRange(pos.getX(),pos.getY(),pos.getZ(),8) &&  be.age % 3 == 0)
        {

            BlockPos pos2 = pos.add(state.get(BocchiBlock.FACING).getOffsetX(),state.get(BocchiBlock.FACING).getOffsetY(),state.get(BocchiBlock.FACING).getOffsetZ());
            if(world.getBlockState(pos2).getBlock() == Blocks.AIR)
            {
                world.setBlockState(pos2,state);
                world.removeBlockEntity(pos);
                world.removeBlock(pos,false);
            }
            else if(world.getBlockState(pos2).getBlock() == Blocks.MAGMA_BLOCK) {
                world.setBlockState(pos, state.with(BocchiBlock.FACING, state.get(BocchiBlock.FACING).getOpposite()));
            }
            else if(world.getBlockState(pos2).getBlock() == Blocks.MAGENTA_GLAZED_TERRACOTTA) {
                BlockState teracottaState = world.getBlockState(pos2);

                world.setBlockState(pos, state.with(BocchiBlock.FACING, BlockRotationCalculation.rotateWithGlazedMagenta(teracottaState,state)));
            }
        }
    }
}
