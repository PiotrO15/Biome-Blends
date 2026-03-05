package piotro15.biomeblends.datagen;

import biomesoplenty.core.BiomesOPlenty;
import net.minecraft.DetectedVersion;
import net.minecraft.client.resources.LegacyStuffWrapper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.FoliageColor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import org.jetbrains.annotations.NotNull;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = BiomeBlends.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    private static LanguageDatagen languageProvider;
    private static ItemModelDatagen modelProvider;

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        initColors(event.getResourceManager(PackType.CLIENT_RESOURCES));

        event.createDatapackRegistryObjects(new RegistrySetBuilder()
                        .add(BiomeBlendsRegistries.BLEND_TYPE, BlendTypeProvider::registerBlendTypes),
                Set.of(BiomeBlends.MOD_ID, "minecraft")
        );

        languageProvider = new LanguageDatagen(packOutput, BiomeBlends.MOD_ID, "en_us");
        modelProvider = new ItemModelDatagen(packOutput, BiomeBlends.MOD_ID, event.getExistingFileHelper());

        gen.addProvider(true, new RecipeDatagen(packOutput, lookupProvider));

        addExtraDataPackProvider(BiomesOPlenty.MOD_ID, event, BlendData.biomesOPlentyBlends);
        addExtraDataPackProvider(BiomesWeveGone.MOD_ID, event, BlendData.biomesWeveGoneBlends);

        gen.addProvider(event.includeClient(), languageProvider);
        gen.addProvider(event.includeClient(), modelProvider);
    }

    private static void addExtraDataPackProvider(String modId, GatherDataEvent event, List<BlendData> blends) {
        DataGenerator generator = event.getGenerator();
        PackOutput dataOutput = generator.getPackOutput("datapacks/" + modId);

        generator.addProvider(event.includeServer(), namedProvider("Pack Metadata " + modId, new PackMetadataGenerator(dataOutput)
                .add(PackMetadataSection.TYPE, new PackMetadataSection(Component.translatable("biomeblends.datapacks." + modId), DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA)))));

        generator.addProvider(event.includeServer(),
                new DatapackBuiltinEntriesProvider(dataOutput, event.getLookupProvider(), new RegistrySetBuilder().add(BiomeBlendsRegistries.BLEND_TYPE, (context) -> BlendTypeProvider.registerBlendTypes(context, blends)), Set.of(modId)) {
                    @Override
                    public @NotNull String getName() {
                        return "Registries " + modId;
                    }
                });

        RecipeProvider inner = new RecipeDatagen.BlendRecipeProvider(dataOutput, event.getLookupProvider(), blends);
        generator.addProvider(event.includeServer(), namedProvider("Recipes " + modId, inner));
        languageProvider.addBlendTranslations(blends);
        modelProvider.registerBlendModels(blends);
    }

    private static void initColors(ResourceManager arg) {
        ResourceLocation LOCATION = ResourceLocation.withDefaultNamespace("textures/colormap/foliage.png");
        try {
            int[] colors = LegacyStuffWrapper.getPixels(arg, LOCATION);

            FoliageColor.init(colors);
        } catch (IOException iOException) {
            throw new IllegalStateException("Failed to load foliage color texture", iOException);
        }
    }

    public static DataProvider namedProvider(String name, DataProvider provider) {
        return new DataProvider() {
            @Override
            public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
                return provider.run(output);
            }

            @Override
            public @NotNull String getName() {
                return name;
            }
        };
    }
}
