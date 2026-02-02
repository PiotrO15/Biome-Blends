package piotro15.biomeblends.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.JsonOps;
import net.minecraft.FileUtil;
import net.minecraft.ResourceLocationException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.LevelResource;
import piotro15.biomeblends.blend.BlendType;
import piotro15.biomeblends.blend.blend_action.SetBiomeAction;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ExportBlendsCommand {
    private static final DynamicCommandExceptionType ERROR_INVALID_PATTERN = new DynamicCommandExceptionType(object -> Component.translatable("commands.exportblends.invalid_pattern", object));
    private static final SimpleCommandExceptionType ERROR_NO_BLENDS_FOUND = new SimpleCommandExceptionType(Component.translatable("commands.exportblends.no_blends_found"));
    private static Path generatedDir = null;

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                Commands.literal("exportblends")
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                        .then(Commands.argument("pattern", StringArgumentType.string())
                        .executes(context -> export(context.getSource(), StringArgumentType.getString(context, "pattern")))));
    }

    public static int export(CommandSourceStack commandSourceStack, String pattern) throws CommandSyntaxException {
        if (!isValidRegex(pattern)) {
            throw ERROR_INVALID_PATTERN.create(pattern);
        }

        Registry<Biome> registry = commandSourceStack.getLevel().registryAccess().registryOrThrow(Registries.BIOME);
        generatedDir = commandSourceStack.getServer().getWorldPath(LevelResource.GENERATED_DIR).normalize();
        List<ResourceLocation> blends = registry.keySet().stream().filter(resourceLocation -> resourceLocation.toString().matches(pattern)).toList();

        if (blends.isEmpty()) {
            throw ERROR_NO_BLENDS_FOUND.create();
        }

        try {
            blends.forEach(ExportBlendsCommand::save);
        } catch (ResourceLocationException e) {
            return 0;
        }

        commandSourceStack.sendSuccess(() -> Component.translatable("commands.exportblends.success", blends.size()), true);

        return blends.size();
    }

    public static void save(ResourceLocation resourceLocation) {
        Path path = createAndValidatePath(resourceLocation, ".json");

        BlendType.BlendTypeBuilder builder = new BlendType.BlendTypeBuilder().action(new SetBiomeAction(resourceLocation));

        try {
            Path parent = path.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            JsonElement e = BlendType.CODEC.encodeStart(JsonOps.INSTANCE, builder.build()).getOrThrow(error -> {
                throw new ResourceLocationException("Failed to encode blend data: " + error);
            });

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(e);

            Files.writeString(path, jsonString);
        } catch (Throwable exception) {
            throw new ResourceLocationException("Failed to save blend to " + path, exception);
        }
    }

    public static Path createAndValidatePath(ResourceLocation resourceLocation, String string) {
        if (resourceLocation.getPath().contains("//")) {
            throw new ResourceLocationException("Invalid resource path: " + resourceLocation);
        } else {
            try {
                Path path = generatedDir.resolve(resourceLocation.getNamespace());
                Path path2 = path.resolve("blend_type");
                Path path3 = FileUtil.createPathToResource(path2, resourceLocation.getPath(), string);
                if (path3.startsWith(generatedDir) && FileUtil.isPathNormalized(path3) && FileUtil.isPathPortable(path3)) {
                    return path3;
                } else {
                    throw new ResourceLocationException("Invalid resource path: " + path3);
                }
            } catch (InvalidPathException invalidPathException) {
                throw new ResourceLocationException("Invalid resource path: " + resourceLocation, invalidPathException);
            }
        }
    }

    private static boolean isValidRegex(String pattern) {
        try {
            Pattern.compile(pattern);
            return true;
        } catch (PatternSyntaxException e) {
            return false;
        }
    }
}
