package piotro15.biomeblends.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataPackRegistryEvent;
import piotro15.biomeblends.BiomeBlends;
import net.minecraftforge.fml.common.Mod;
import piotro15.biomeblends.CommonConfig;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.command.GenerateBlendsCommand;
import piotro15.biomeblends.registry.BiomeBlendsCreativeModeTabs;
import piotro15.biomeblends.registry.BiomeBlendsItems;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;
import piotro15.biomeblends.util.Platform;

import java.nio.file.Path;
import java.util.function.Function;

@Mod(BiomeBlends.MOD_ID)
public final class BiomeBlendsForge {
    public BiomeBlendsForge() {
        EventBuses.registerModEventBus(BiomeBlends.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        Platform.setup(new ForgePlatform());
        BiomeBlends.init();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::registerDatapackRegistries);
        modEventBus.addListener(this::registerBlendsInCreativeTab);
        modEventBus.addListener(this::addDataPacks);

        FMLJavaModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
    }

    @SubscribeEvent
    public void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        for (ForgePlatform.DataRegistryRegisterable<?> registerable : ForgePlatform.dataRegistryRegisterables) {
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
                BlendType.save(stack, blendKey.location());
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
                Path resourcePath = ModList.get().getModFileById(BiomeBlends.MOD_ID).getFile().findResource("datapacks", "biomesoplenty");
                Function<String, PackResources> onName = path -> new PathPackResources(path, resourcePath, true);
                Pack.ResourcesSupplier resources = string -> onName.apply("builtin/biomesoplenty");

                Pack pack = Pack.readMetaAndCreate(ResourceLocation.fromNamespaceAndPath(BiomeBlends.MOD_ID, "datapacks/biomesoplenty").toString(), Component.literal("Mod blends"), true, resources, PackType.SERVER_DATA, Pack.Position.BOTTOM, PackSource.BUILT_IN);
                event.addRepositorySource(packConsumer -> packConsumer.accept(pack));
            }
        }
    }
}
