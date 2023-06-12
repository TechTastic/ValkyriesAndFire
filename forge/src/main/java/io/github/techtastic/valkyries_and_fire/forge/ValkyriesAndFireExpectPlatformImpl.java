package io.github.techtastic.valkyries_and_fire.forge;

import io.github.techtastic.valkyries_and_fire.ValkyriesAndFireExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class ValkyriesAndFireExpectPlatformImpl {
    /**
     * This is our actual method to {@link ValkyriesAndFireExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
