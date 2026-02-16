package piotro15.biomeblends.datagen;

import biomesoplenty.core.BiomesOPlenty;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import piotro15.biomeblends.BiomeBlends;
import piotro15.biomeblends.registry.BiomeBlendsRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = BiomeBlends.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        event.createDatapackRegistryObjects(new RegistrySetBuilder()
                .add(BiomeBlendsRegistries.BLEND_TYPE, BlendTypeProvider::registerBlendTypes),
                Set.of(BiomeBlends.MOD_ID, "minecraft", BiomesOPlenty.MOD_ID)
        );

        gen.addProvider(true, new RecipeDatagen(packOutput, lookupProvider));
        gen.addProvider(true, new LanguageDatagen(packOutput, BiomeBlends.MOD_ID, "en_us"));
        gen.addProvider(true, new ItemModelDatagen(packOutput, BiomeBlends.MOD_ID, event.getExistingFileHelper()));
    }
}
