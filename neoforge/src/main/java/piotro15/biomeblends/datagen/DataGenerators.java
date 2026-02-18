package piotro15.biomeblends.datagen;

import biomesoplenty.core.BiomesOPlenty;
import net.minecraft.DetectedVersion;
import net.minecraft.client.resources.LegacyStuffWrapper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
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
import org.jetbrains.annotations.NotNull;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

@EventBusSubscriber(modid = BiomeBlends.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
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

        gen.addProvider(true, new RecipeDatagen(packOutput, lookupProvider));
        gen.addProvider(true, new LanguageDatagen(packOutput, BiomeBlends.MOD_ID, "en_us"));
        gen.addProvider(true, new ItemModelDatagen(packOutput, BiomeBlends.MOD_ID, event.getExistingFileHelper()));

        addExtraDataPackProvider(BiomesOPlenty.MOD_ID, new RegistrySetBuilder().add(BiomeBlendsRegistries.BLEND_TYPE, BlendTypeProvider::registerBOP), RecipeDatagen.BOPRecipeDatagen::new, event);
    }

    private static void addExtraDataPackProvider(String modId, RegistrySetBuilder registrySetBuilder, BiFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, RecipeProvider> recipeProvider, GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput dataOutput = generator.getPackOutput("datapacks/" + modId);

        generator.addProvider(event.includeServer(), new PackMetadataGenerator(dataOutput)
                .add(PackMetadataSection.TYPE, new PackMetadataSection(Component.translatable("biomeblends.datapacks." + modId), DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA))));

        generator.addProvider(event.includeServer(),
                new DatapackBuiltinEntriesProvider(dataOutput, event.getLookupProvider(), registrySetBuilder, Set.of(modId)) {
                    @Override
                    public @NotNull String getName() {
                        return "Registries " + modId;
                    }
                });

        RecipeProvider inner = recipeProvider.apply(dataOutput, event.getLookupProvider());
        generator.addProvider(event.includeServer(), RecipeDatagen.namedRecipeProvider("Recipes " + modId, inner));
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
}
