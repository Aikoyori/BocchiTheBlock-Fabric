package xyz.aikoyori.bocchitheblock.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.aikoyori.bocchitheblock.BocchiMod;
import xyz.aikoyori.bocchitheblock.gui.NijikaBlockGUI;

public class NijikaBlock extends BlockWithEntity {

    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty ACTIVATED = BooleanProperty.of("activated");
    public static final BooleanProperty POWERED = Properties.POWERED;
    public NijikaBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(ACTIVATED,false));
    }
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(FACING);
        stateManager.add(ACTIVATED);
        stateManager.add(POWERED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(Properties.FACING, ctx.getPlayerLookDirection().getOpposite());
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        /*
        world.setBlockState(pos,state.with(ACTIVATED,!state.get(ACTIVATED)));
        return ActionResult.SUCCESS;*/
        //return super.onUse(state, world, pos, player, hand, hit);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof NijikaBlockEntity neb) {
            if(world.isClient())
            {
                ClientPlayerEntity cpe = (ClientPlayerEntity) player;
                cpe.client.setScreen(new NijikaBlockGUI(neb));
            }
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state){
        return new NijikaBlockEntity(pos,state);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BocchiMod.NIJIKA_BLOCK_ENTITY,(world1, pos, state1, be)->{
            NijikaBlockEntity.tick(world1, pos, state1, be);
        });
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
                state = state.with(ACTIVATED,!state.get(ACTIVATED));
                world.setBlockState(pos,state);
            }
            world.setBlockState(pos,state.with(POWERED,bl),NOTIFY_ALL);

        }
    }
}
