package io.github.techtastic.valkyries_and_fire.block.custom;

import com.github.alexthe666.iceandfire.entity.DragonType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.valkyrienskies.core.api.ships.Wing;
import org.valkyrienskies.mod.common.block.WingBlock;
import org.valkyrienskies.mod.common.util.VectorConversionsMCKt;

public class DragonWingBlock extends DirectionalBlock implements WingBlock {
    private final DragonType type;

    public DragonWingBlock(Properties properties, DragonType type) {
        super(properties);
        this.type = type;
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
}
