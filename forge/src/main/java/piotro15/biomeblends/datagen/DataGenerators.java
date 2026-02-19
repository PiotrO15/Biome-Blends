package piotro15.biomeblends.datagen;

import biomesoplenty.core.BiomesOPlenty;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.world.level.FoliageColor;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = BiomeBlends.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        initColors();

        gen.addProvider(
                true,
                (DataProvider.Factory<DatapackBuiltinEntriesProvider>) output -> new DatapackBuiltinEntriesProvider(
                        output,
                        lookupProvider,
                        new RegistrySetBuilder()
                                .add(BiomeBlendsRegistries.BLEND_TYPE, BlendTypeProvider::registerBlendTypes),
                        Set.of(BiomeBlends.MOD_ID, "minecraft")
                )
        );

        gen.addProvider(true, new RecipeDatagen(packOutput));
        gen.addProvider(true, new LanguageDatagen(packOutput, BiomeBlends.MOD_ID, "en_us"));
        gen.addProvider(true, new ItemModelDatagen(packOutput, BiomeBlends.MOD_ID, event.getExistingFileHelper()));

        addExtraDataPackProvider(BiomesOPlenty.MOD_ID, new RegistrySetBuilder().add(BiomeBlendsRegistries.BLEND_TYPE, BlendTypeProvider::registerBOP), RecipeDatagen.BOPRecipeDatagen::new, event);
    }

    private static void addExtraDataPackProvider(String modId, RegistrySetBuilder registrySetBuilder, Function<PackOutput, RecipeProvider> recipeProvider, GatherDataEvent event) {
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

        RecipeProvider inner = recipeProvider.apply(dataOutput);
        generator.addProvider(event.includeServer(), RecipeDatagen.namedRecipeProvider("Recipes " + modId, inner));
    }

    private static void initColors() {
        ResourceLocation location = ResourceLocation.withDefaultNamespace("textures/colormap/foliage.png");
        String path = "/assets/" + location.getNamespace() + "/" + location.getPath();
        try (InputStream inputStream = FoliageColor.class.getResourceAsStream(path)) {
            NativeImage nativeImage = NativeImage.read(inputStream);

            FoliageColor.init(nativeImage.makePixelArray());
        } catch (IOException iOException) {
            throw new IllegalStateException("Failed to load foliage color texture", iOException);
        }
    }
}
