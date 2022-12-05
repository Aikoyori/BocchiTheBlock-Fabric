package xyz.aikoyori.bocchitheblock.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import xyz.aikoyori.bocchitheblock.BocchiMod;

public class BocchiBlock extends BlockWithEntity {


    public static final BooleanProperty PANICKING = BooleanProperty.of("panicking");
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");

    public static final DirectionProperty FACING = Properties.FACING;

    public BocchiBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.SOUTH).with(PANICKING, false).with(POWERED, false));

    }
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        if(state.get(PANICKING))
        {
            return 2;
        }
        else
        {
            return 1;
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (world.isClient) {
            return;
        }
        boolean bl = world.isReceivingRedstonePower(pos);
        //System.out.println("ME IS CHECKING");
        if (bl != state.get(POWERED)) {
            if(bl)
            {
                //System.out.println(state);
                state = state.with(PANICKING,!state.get(PANICKING));
                world.setBlockState(pos,state);
            }
            world.setBlockState(pos,state.with(POWERED,bl),NOTIFY_ALL);

        }
    }
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.FACING);
        stateManager.add(PANICKING);
        stateManager.add(POWERED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(Properties.FACING, ctx.getPlayerLookDirection().getOpposite());
    }



    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BocchiMod.BOCCHI_BLOCK_ENTITY,(world1, pos, state1, be)->{
            BocchiBlockEntity.tick(world1, pos, state1, be);
        });
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        world.setBlockState(pos,state.with(PANICKING,!state.get(PANICKING)));
        return ActionResult.SUCCESS;
        //return super.onUse(state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getGameEventListener(ServerWorld world, T blockEntity) {
        return super.getGameEventListener(world, blockEntity);
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos,BlockState state){
        return new BocchiBlockEntity(pos,state);
    }
}
