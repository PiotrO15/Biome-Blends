package piotro15.biomeblends.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.FileUtil;
import net.minecraft.ResourceLocationException;
import net.minecraft.client.gui.screens.inventory.StructureBlockEditScreen;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.StringRepresentableArgument;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.FillCommand;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.storage.LevelResource;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class GenerateBlendsCommand {
    private static Path generatedDir = null;

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher, CommandBuildContext commandBuildContext) {
        commandDispatcher.register(
                Commands.literal("exportblends")
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                        .then(Commands.argument("matcher", StringArgumentType.string())
                        .executes(context -> export(context.getSource(), StringArgumentType.getString(context, "matcher")))));
    }

    public static int export(CommandSourceStack commandSourceStack, String matcher) throws CommandSyntaxException {
        System.out.println("Exporting blends matching: " + matcher);
        var reg = commandSourceStack.getLevel().registryAccess().registryOrThrow(Registries.BIOME);
        generatedDir = commandSourceStack.getServer().getWorldPath(LevelResource.GENERATED_DIR).normalize();
        List<ResourceLocation> blends = reg.keySet().stream().filter(resourceLocation -> resourceLocation.toString().matches(matcher)).toList();

        System.out.println("Found " + blends.size() + " blends to export.");

        try {
            blends.forEach(resourceLocation -> {
                save(resourceLocation);
            });
        } catch (ResourceLocationException e) {
            return 0;
        }
        return blends.size();
    }

    public static boolean save(ResourceLocation resourceLocation) {
        Path path = createAndValidatePath(resourceLocation, ".json");

        CompoundTag compoundTag = new CompoundTag();

        try {
            System.out.println(path);
            Path parent = path.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            String data = compoundTag.toString();
            Files.writeString(path, data);
            System.out.println("Saved blend to " + path);

            return true;
        } catch (Throwable var12) {
            return false;
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
}
