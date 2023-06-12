package io.github.techtastic.valkyries_and_fire.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import io.github.techtastic.valkyries_and_fire.ValkyriesAndFireMod;

@Mod(ValkyriesAndFireMod.MOD_ID)
public class ValkyriesAndFireModForge {
    public ValkyriesAndFireModForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(ValkyriesAndFireMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ValkyriesAndFireMod.init();
    }
}
