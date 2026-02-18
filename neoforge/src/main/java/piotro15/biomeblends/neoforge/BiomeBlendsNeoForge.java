package piotro15.biomeblends.neoforge;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import piotro15.biomeblends.BiomeBlends;
import net.neoforged.fml.common.Mod;
import piotro15.biomeblends.CommonConfig;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.command.GenerateBlendsCommand;
import piotro15.biomeblends.registry.BiomeBlendsCreativeModeTabs;
import piotro15.biomeblends.registry.BiomeBlendsDataComponents;
import piotro15.biomeblends.registry.BiomeBlendsItems;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;
import piotro15.biomeblends.util.Platform;

@Mod(BiomeBlends.MOD_ID)
public final class BiomeBlendsNeoForge {
    public BiomeBlendsNeoForge(IEventBus modEventBus, ModContainer container) {
        Platform.setup(new NeoForgePlatform());
        BiomeBlends.init();

        modEventBus.addListener(this::registerDatapackRegistries);
        modEventBus.addListener(this::registerBlendsInCreativeTab);
        modEventBus.addListener(this::addDataPacks);
        NeoForge.EVENT_BUS.addListener(this::registerCommands);

        container.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
    }

    @SubscribeEvent
    public void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        for (NeoForgePlatform.DataRegistryRegisterable<?> registerable : NeoForgePlatform.dataRegistryRegisterables) {
            registerable.register(event);
        }
    }

    @SubscribeEvent
    public void registerBlendsInCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (!event.getTabKey().equals(BiomeBlendsCreativeModeTabs.BLENDS_TAB.getKey())) return;

        if (Minecraft.getInstance().level != null) {
            Registry<BlendType> blendTypeRegistry = Minecraft.getInstance().level.registryAccess().registryOrThrow(BiomeBlendsRegistries.BLEND_TYPE);

            for (ResourceKey<BlendType> blendKey : blendTypeRegistry.registryKeySet()) {
                ItemStack stack = new ItemStack(BiomeBlendsItems.BIOME_BLEND.get());
                stack.set(BiomeBlendsDataComponents.BLEND_TYPE.get(), blendKey.location());
                event.accept(stack);
            }
        }
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        GenerateBlendsCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void addDataPacks(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            if (ModList.get().isLoaded("biomesoplenty") && CommonConfig.INSTANCE.bopCompat.get()) {
                event.addPackFinders(ResourceLocation.fromNamespaceAndPath(BiomeBlends.MOD_ID, "datapacks/biomesoplenty"), PackType.SERVER_DATA, Component.literal("Mod blends"), PackSource.BUILT_IN, true, Pack.Position.BOTTOM);
            }
        }
    }
}
