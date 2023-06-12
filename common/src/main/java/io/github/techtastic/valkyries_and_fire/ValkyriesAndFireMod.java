package io.github.techtastic.valkyries_and_fire;

import io.github.techtastic.valkyries_and_fire.block.ValkyriesAndFireBlocks;
import io.github.techtastic.valkyries_and_fire.item.ValkyriesAndFireItems;

public class ValkyriesAndFireMod {
    public static final String MOD_ID = "valkyries_and_fire";

    public static void init() {
        ValkyriesAndFireBlocks.register();
        ValkyriesAndFireItems.register();
    }
}
