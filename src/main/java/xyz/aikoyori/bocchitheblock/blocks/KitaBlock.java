package xyz.aikoyori.bocchitheblock.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
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
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import xyz.aikoyori.bocchitheblock.BocchiMod;

import java.util.function.ToIntFunction;

public class KitaBlock extends BlockWithEntity {

    public static final BooleanProperty KITAN = BooleanProperty.of("kitan");
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    public KitaBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.SOUTH).with(KITAN, false).with(POWERED, false));

    }
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(Properties.FACING, ctx.getPlayerLookDirection().getOpposite());
    }
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.FACING);
        stateManager.add(KITAN);
        stateManager.add(POWERED);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        world.setBlockState(pos,state.with(KITAN,!state.get(KITAN)));
        return ActionResult.SUCCESS;
        //return super.onUse(state, world, pos, player, hand, hit);
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state){
        return new KitaBlockEntity(pos,state);
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
                state = state.with(KITAN,!state.get(KITAN));
                world.setBlockState(pos,state);
            }
            world.setBlockState(pos,state.with(POWERED,bl),NOTIFY_ALL);

        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BocchiMod.KITA_BLOCK_ENTITY,(world1, pos, state1, blockEntity) -> {
            KitaBlockEntity.tick(world1,pos,state1,blockEntity);
        });
        //return super.getTicker(world, state, type);
    }

    public static ToIntFunction<BlockState> createLightLevelFromKitanBlockState(int litLevel) {
        return state -> state.get(KITAN) != false ? litLevel : 0;
    }

}
