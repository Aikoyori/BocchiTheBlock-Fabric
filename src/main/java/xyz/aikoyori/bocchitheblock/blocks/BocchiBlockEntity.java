package xyz.aikoyori.bocchitheblock.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.aikoyori.bocchitheblock.BocchiMod;
import xyz.aikoyori.bocchitheblock.helper.BlockRotationCalculation;

import static net.minecraft.block.Block.NOTIFY_ALL;

public class BocchiBlockEntity extends BlockEntity {
    public int age;
    public BocchiBlockEntity(BlockPos pos, BlockState state) {
        super(BocchiMod.BOCCHI_BLOCK_ENTITY, pos, state);
        age = 0;
    }
    public static void tick(World world, BlockPos pos, BlockState state, BocchiBlockEntity be) {
        be.age++;
        boolean bl = world.isReceivingRedstonePower(pos);
        //System.out.println("ME IS CHECKING");
        if (bl != state.get(BocchiBlock.POWERED)) {
            if(bl)
            {
                //System.out.println(state);
                state = state.with(BocchiBlock.PANICKING,!state.get(BocchiBlock.PANICKING));
                world.setBlockState(pos,state);
            }
            world.setBlockState(pos,state.with(BocchiBlock.POWERED,bl),NOTIFY_ALL);

        }

        if(state.get(BocchiBlock.PANICKING) && world.isPlayerInRange(pos.getX(),pos.getY(),pos.getZ(),64) &&  be.age % 4 == 0)
        {

            BlockPos pos2 = pos.add(state.get(BocchiBlock.FACING).getOffsetX(),state.get(BocchiBlock.FACING).getOffsetY(),state.get(BocchiBlock.FACING).getOffsetZ());
            BlockPos pos3 = pos2.add(state.get(BocchiBlock.FACING).getOffsetX(),state.get(BocchiBlock.FACING).getOffsetY(),state.get(BocchiBlock.FACING).getOffsetZ());
            if(world.getBlockState(pos2).getMaterial().isReplaceable())
            {
                if(pos2.getY() < world.getTopY() && pos2.getY() >= world.getBottomY())
                {

                    world.removeBlockEntity(pos);
                    world.removeBlock(pos,false);
                    world.setBlockState(pos2,state);
                }
            }
            else if(world.getBlockState(pos2).getBlock() == Blocks.MAGMA_BLOCK) {
                world.setBlockState(pos, state.with(BocchiBlock.FACING, state.get(BocchiBlock.FACING).getOpposite()));
            }
            else if(world.getBlockState(pos2).getBlock() == Blocks.MAGENTA_GLAZED_TERRACOTTA) {
                BlockState teracottaState = world.getBlockState(pos2);

                world.setBlockState(pos, state.with(BocchiBlock.FACING, BlockRotationCalculation.rotateWithGlazedMagenta(teracottaState,state)));
            }
            else if(world.getBlockState(pos2).getBlock() == BocchiMod.BOCCHI_BLOCK && !world.getBlockState(pos3).getMaterial().isReplaceable()) {
                BlockState bbs = world.getBlockState(pos2);
                if (bbs.get(BocchiBlock.FACING) == state.get(BocchiBlock.FACING).getOpposite())
                    world.setBlockState(pos, state.with(BocchiBlock.FACING, bbs.get(BocchiBlock.FACING)));
            }
        }
    }
}
