package piotro15.biomeblends.fabric;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.fml.config.ModConfig;
import piotro15.biomeblends.BiomeBlends;
import net.fabricmc.api.ModInitializer;
import piotro15.biomeblends.CommonConfig;
import piotro15.biomeblends.command.GenerateBlendsCommand;
import piotro15.biomeblends.item.BlendItem;
import piotro15.biomeblends.registry.BiomeBlendsItems;
import piotro15.biomeblends.util.Platform;

import java.util.function.Function;

public final class BiomeBlendsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Platform.setup(new FabricPlatform());

        BiomeBlends.init();

        ConfigRegistry.INSTANCE.register(BiomeBlends.MOD_ID, ModConfig.Type.COMMON, CommonConfig.SPEC);

        FabricLoader.getInstance().getModContainer(BiomeBlends.MOD_ID).ifPresent(container -> {
            FabricPlatform.compatibilityDatapacks.forEach((s, booleanValue) -> {
                if (FabricLoader.getInstance().isModLoaded(s) && booleanValue.get()) {
                    ResourceManagerHelper.registerBuiltinResourcePack(BiomeBlends.id(s), "datapacks/" + s, container, true);
                }
            });
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, selection) -> GenerateBlendsCommand.register(dispatcher));
    }

    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(BiomeBlends.MOD_ID, name));

        // Create the item instance.
        T item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }
}
