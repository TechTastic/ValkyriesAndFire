package io.github.techtastic.valkyries_and_fire.item;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.techtastic.valkyries_and_fire.ValkyriesAndFireMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ValkyriesAndFireItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ValkyriesAndFireMod.MOD_ID, Registry.ITEM_REGISTRY);

    public static final CreativeModeTab TAB =
            CreativeTabRegistry.create(
                    new ResourceLocation(ValkyriesAndFireMod.MOD_ID),
                    () -> new ItemStack(ValkyriesAndFireItems.LOGO.get()));

    public static final RegistrySupplier<Item> LOGO = ITEMS.register("logo", () ->
            new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> DRAGON_LEATHER = ITEMS.register("dragon_leather", () ->
            new Item(new Item.Properties().tab(TAB)));

    public static void register() {
        ITEMS.register();
    }
}
