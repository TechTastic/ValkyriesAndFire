package io.github.techtastic.valkyries_and_fire.block;

import com.github.alexthe666.iceandfire.entity.DragonType;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.techtastic.valkyries_and_fire.ValkyriesAndFireMod;
import io.github.techtastic.valkyries_and_fire.block.custom.DragonWingBlock;
import io.github.techtastic.valkyries_and_fire.item.ValkyriesAndFireItems;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.valkyrienskies.mod.common.ValkyrienSkiesMod;

public class ValkyriesAndFireBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ValkyriesAndFireMod.MOD_ID, Registry.BLOCK_REGISTRY);
    private static final DeferredRegister<Block> BLOCKS_WITHOUT_ITEMS = DeferredRegister.create(ValkyriesAndFireMod.MOD_ID, Registry.BLOCK_REGISTRY);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ValkyriesAndFireMod.MOD_ID, Registry.ITEM_REGISTRY);

    public final RegistrySupplier<Block> FIRE_DRAGON_WING = BLOCKS.register("fire_dragon_wing", () ->
            new DragonWingBlock(BlockBehaviour.Properties.copy(ValkyrienSkiesMod.TEST_WING), DragonType.FIRE));
    public final RegistrySupplier<Block> ICE_DRAGON_WING = BLOCKS.register("ice_dragon_wing", () ->
            new DragonWingBlock(BlockBehaviour.Properties.copy(ValkyrienSkiesMod.TEST_WING), DragonType.ICE));
    public final RegistrySupplier<Block> LIGHTNING_DRAGON_WING = BLOCKS.register("lightning_dragon_wing", () ->
            new DragonWingBlock(BlockBehaviour.Properties.copy(ValkyrienSkiesMod.TEST_WING), DragonType.LIGHTNING));

    public static void register() {
        BLOCKS.register();
        BLOCKS_WITHOUT_ITEMS.register();

        for (RegistrySupplier<Block> block : BLOCKS) {
            ITEMS.register(block.get().getDescriptionId(), () ->
                    new BlockItem(block.get(), new Item.Properties().tab(ValkyriesAndFireItems.TAB)));
        }

        ITEMS.register();
    }
}
