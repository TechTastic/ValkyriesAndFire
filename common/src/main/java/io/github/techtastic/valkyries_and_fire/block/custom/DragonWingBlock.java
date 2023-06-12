package io.github.techtastic.valkyries_and_fire.block.custom;

import com.github.alexthe666.iceandfire.entity.DragonType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.valkyrienskies.core.api.ships.Wing;
import org.valkyrienskies.mod.common.block.WingBlock;
import org.valkyrienskies.mod.common.util.VectorConversionsMCKt;

public class DragonWingBlock extends DirectionalBlock implements WingBlock {
    private final DragonType type;
    private final DirectionProperty FACING = BlockStateProperties.FACING;
    private final BooleanProperty NORTH = BlockStateProperties.NORTH;
    private final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    private final BooleanProperty EAST = BlockStateProperties.EAST;
    private final BooleanProperty WEST = BlockStateProperties.WEST;
    private final BooleanProperty UP = BlockStateProperties.UP;
    private final BooleanProperty DOWN = BlockStateProperties.DOWN;

    public DragonWingBlock(Properties properties, DragonType type) {
        super(properties);
        this.type = type;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        Direction face = blockPlaceContext.getClickedFace();

        return getNewState(this.defaultBlockState().setValue(FACING, face), blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos());
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        level.setBlockAndUpdate(blockPos, getNewState(blockState, level, blockPos));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    @Nullable
    @Override
    public Wing getWing(@Nullable Level level, @Nullable BlockPos pos, @NotNull BlockState state) {
        if (level == null || pos == null) return null;
        Biome biome = level.getBiome(pos).value();

        double weatherMult = weatherMultiplier(level, biome);
        float temp = biome.getBaseTemperature();
        double wingPower = 150.0;
        double wingDrag = 30.0;
        Double wingBreakingForce = null;
        double wingCamberAttackAngleBias = Math.toRadians(10.0);
        Vector3d wingNormal = VectorConversionsMCKt.toJOMLD(state.getValue(BlockStateProperties.FACING).getNormal());

        if (type.equals(DragonType.FIRE))
            return new Wing(
                wingNormal,
                wingPower * temp,
                wingDrag * (1/temp),
                wingBreakingForce,
                wingCamberAttackAngleBias
            );
        else if (type.equals(DragonType.ICE))
            return new Wing(
                    wingNormal,
                    wingPower * (1/temp),
                    wingDrag * temp,
                    wingBreakingForce,
                    wingCamberAttackAngleBias
            );
        else if (type.equals(DragonType.LIGHTNING))
            return new Wing(
                    wingNormal,
                    wingPower * weatherMult,
                    wingDrag * (1/weatherMult),
                    wingBreakingForce,
                    wingCamberAttackAngleBias
            );
        else
            return null;
    }

    private float weatherMultiplier(Level level, Biome biome) {
        float mult = biome.getDownfall();
        if (level.isRaining())
            mult += 1;
        if (level.isThundering())
            mult += 2;
        return mult;
    }

    private BlockState getNewState(BlockState state, Level level, BlockPos pos) {
        return state
                .setValue(NORTH, (state.getValue(FACING) != Direction.UP || state.getValue(FACING) != Direction.UP) &&
                        (level.getBlockState(pos.north()).getBlock() instanceof DragonWingBlock))
                .setValue(SOUTH, (state.getValue(FACING) != Direction.UP || state.getValue(FACING) != Direction.UP) &&
                        (level.getBlockState(pos.south()).getBlock() instanceof DragonWingBlock))
                .setValue(EAST, (state.getValue(FACING) != Direction.UP || state.getValue(FACING) != Direction.UP) &&
                        (level.getBlockState(pos.east()).getBlock() instanceof DragonWingBlock))
                .setValue(WEST, (state.getValue(FACING) != Direction.UP || state.getValue(FACING) != Direction.UP) &&
                        (level.getBlockState(pos.west()).getBlock() instanceof DragonWingBlock))
                .setValue(UP, (state.getValue(FACING) != Direction.NORTH || state.getValue(FACING) != Direction.SOUTH ||
                        state.getValue(FACING) != Direction.EAST || state.getValue(FACING) != Direction.WEST) &&
                        (level.getBlockState(pos.above()).getBlock() instanceof DragonWingBlock) &&
                        level.getBlockState(pos.above()).getValue(FACING) == state.getValue(FACING))
                .setValue(DOWN, (state.getValue(FACING) != Direction.NORTH || state.getValue(FACING) != Direction.SOUTH ||
                        state.getValue(FACING) != Direction.EAST || state.getValue(FACING) != Direction.WEST) &&
                        (level.getBlockState(pos.below()).getBlock() instanceof DragonWingBlock) &&
                        level.getBlockState(pos.below()).getValue(FACING) == state.getValue(FACING));
    }
}
