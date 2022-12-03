package xyz.aikoyori.bocchitheblock.helper;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import xyz.aikoyori.bocchitheblock.blocks.BocchiBlock;

public class BlockRotationCalculation {
    public static Direction rotateWithGlazedMagenta(BlockState glazedmagenta, BlockState bocchi)
    {
        switch(bocchi.get(BocchiBlock.FACING))
        {

            case DOWN -> {
                return glazedmagenta.get(Properties.HORIZONTAL_FACING).getOpposite();
            }
            case UP -> {

                return glazedmagenta.get(Properties.HORIZONTAL_FACING);
            }
            case NORTH -> {
                switch (glazedmagenta.get(Properties.HORIZONTAL_FACING)){

                    case NORTH -> {
                        return Direction.EAST;
                    }
                    case SOUTH -> {
                        return Direction.WEST;
                    }
                    case WEST -> {
                        return Direction.DOWN;
                    }
                    case EAST -> {
                        return Direction.UP;
                    }
                }
            }
            case SOUTH -> {
                switch (glazedmagenta.get(Properties.HORIZONTAL_FACING)){

                    case NORTH -> {
                        return Direction.EAST;
                    }
                    case SOUTH -> {
                        return Direction.WEST;
                    }
                    case WEST -> {
                        return Direction.UP;
                    }
                    case EAST -> {
                        return Direction.DOWN;
                    }
                }
            }
            case WEST -> {
                switch (glazedmagenta.get(Properties.HORIZONTAL_FACING)){

                    case NORTH -> {
                        return Direction.UP;
                    }
                    case SOUTH -> {
                        return Direction.DOWN;
                    }
                    case WEST -> {
                        return Direction.NORTH;
                    }
                    case EAST -> {
                        return Direction.SOUTH;
                    }

                }
            }
            case EAST -> {
                switch (glazedmagenta.get(Properties.HORIZONTAL_FACING)){

                    case NORTH -> {
                        return Direction.DOWN;
                    }
                    case SOUTH -> {
                        return Direction.UP;
                    }
                    case WEST -> {
                        return Direction.NORTH;
                    }
                    case EAST -> {
                        return Direction.SOUTH;
                    }
                }
            }
        }
        return bocchi.get(BocchiBlock.FACING);
    }
}
