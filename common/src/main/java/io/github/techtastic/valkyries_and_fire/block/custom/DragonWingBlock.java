package io.github.techtastic.valkyries_and_fire.block.custom;

import com.github.alexthe666.iceandfire.entity.DragonType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.valkyrienskies.core.api.ships.Wing;
import org.valkyrienskies.mod.common.block.WingBlock;

public class DragonWingBlock extends DirectionalBlock implements WingBlock {
    private DragonType type;

    override fun getWing(level: Level?, pos: BlockPos?, state: BlockState): Wing? {
        if (level == null || pos == null) return null
        val biome = level.getBiome(pos).value()

        val weatherMult = weatherMultiplier(level, biome)
        val temp = biome.baseTemperature
        val wingPower = 150.0
        val wingDrag = 30.0
        val wingBreakingForce = null
        val wingCamberAttackAngleBias = Math.toRadians(10.0)

        return when (type) {
            DragonType.FIRE -> Wing(
                    state.getValue(BlockStateProperties.FACING).normal.toJOMLD(),
                    wingPower * temp,
                    wingDrag * (1/temp),
                    wingBreakingForce,
                    wingCamberAttackAngleBias
            )
            DragonType.ICE -> Wing(
                    state.getValue(BlockStateProperties.FACING).normal.toJOMLD(),
                    wingPower * (1/temp),
                    wingDrag * temp,
                    wingBreakingForce,
                    wingCamberAttackAngleBias
            )
            DragonType.LIGHTNING -> Wing(
                    state.getValue(BlockStateProperties.FACING).normal.toJOMLD(),
                    wingPower * weatherMult,
                    wingDrag * (1/weatherMult),
                    wingBreakingForce,
                    wingCamberAttackAngleBias
            )
            else -> null
        }
    }

    public DragonWingBlock(Properties properties, DragonType type) {
        super(properties);
        this.type = type;
    }

    private fun weatherMultiplier(level: Level, biome: Biome): Float {
        var mult = biome.downfall
        if (level.isRaining)
            mult += 1
        if (level.isThundering)
            mult += 2
        return mult
    }

    @Nullable
    @Override
    public Wing getWing(@Nullable Level level, @Nullable BlockPos blockPos, @NotNull BlockState blockState) {
        return null;
    }
}
