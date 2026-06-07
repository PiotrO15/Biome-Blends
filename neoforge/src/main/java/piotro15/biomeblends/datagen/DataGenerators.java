package piotro15.biomeblends.datagen;

//import biomesoplenty.core.BiomesOPlenty;
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
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackFormat;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.InclusiveRange;
import net.minecraft.world.level.FoliageColor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
//import net.potionstudios.biomeswevegone.BiomesWeveGone;
import org.jetbrains.annotations.NotNull;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = BiomeBlends.MOD_ID)
public class DataGenerators {
    private static LanguageDatagen languageProvider;

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        System.out.println("[BiomeBlends] Gathering Data...");
        PackOutput packOutput = event.getGenerator().getPackOutput();
        initColors(event.getResourceManager(PackType.CLIENT_RESOURCES));

        event.createDatapackRegistryObjects(new RegistrySetBuilder()
                        .add(BiomeBlendsRegistries.BLEND_TYPE, BlendTypeProvider::registerBlendTypes),
                Set.of(BiomeBlends.MOD_ID, "minecraft")
        );

        languageProvider = new LanguageDatagen(packOutput, BiomeBlends.MOD_ID, "en_us");

        event.createProvider(RecipeDatagen.Runner::new);
//        event.addProvider(namedProvider("Recipes " + BiomeBlends.MOD_ID, new RecipeDatagen.BlendRecipeProvider.Runner(packOutput, event.getLookupProvider())));


//        addExtraDataPackProvider(BiomesOPlenty.MOD_ID, event, BlendData.biomesOPlentyBlends);
//        addExtraDataPackProvider(BiomesWeveGone.MOD_ID, event, BlendData.biomesWeveGoneBlends);

        event.addProvider(languageProvider);
        event.addProvider(new ItemModelDatagen(packOutput));

        System.out.println("[BiomeBlends] Done!");
    }

    private static void addExtraDataPackProvider(String modId, GatherDataEvent event, List<BlendData> blends) {
        DataGenerator generator = event.getGenerator();
        PackOutput dataOutput = generator.getPackOutput("datapacks/" + modId);

        event.addProvider(namedProvider("Pack Metadata " + modId, new PackMetadataGenerator(dataOutput)
                .add(PackMetadataSection.SERVER_TYPE, new PackMetadataSection(Component.translatable("biomeblends.datapacks." + modId), new InclusiveRange<>(DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA))))));

        event.addProvider(
                new DatapackBuiltinEntriesProvider(dataOutput, event.getLookupProvider(), new RegistrySetBuilder().add(BiomeBlendsRegistries.BLEND_TYPE, (context) -> BlendTypeProvider.registerBlendTypes(context, blends)), Set.of(modId)) {
                    @Override
                    public @NotNull String getName() {
                        return "Registries " + modId;
                    }
                });

//        RecipeProvider inner = new RecipeDatagen.BlendRecipeProvider(dataOutput, event.getLookupProvider(), blends);
        event.addProvider(namedProvider("Recipes " + modId, new RecipeDatagen.BlendRecipeProvider.Runner(dataOutput, event.getLookupProvider())));
        languageProvider.addBlendTranslations(blends);
    }

    private static void initColors(ResourceManager arg) {
        Identifier LOCATION = Identifier.withDefaultNamespace("textures/colormap/foliage.png");
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
