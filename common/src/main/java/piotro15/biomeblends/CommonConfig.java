package piotro15.biomeblends;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {
    public static final CommonConfig INSTANCE;
    public static final ModConfigSpec SPEC;

    public final ModConfigSpec.DoubleValue verticalScale;
    public final ModConfigSpec.DoubleValue horizontalScale;

    public final ModConfigSpec.BooleanValue ignoreVerticalRadius;

    public final ModConfigSpec.IntValue blendUseCooldown;

    private CommonConfig(ModConfigSpec.Builder builder) {
        horizontalScale = builder.comment("When any blend is applied, the horizontal radius is multiplied by this number.").defineInRange("horizontal_scale", 1.0D, 0, 10);
        verticalScale = builder.comment("When any blend is applied, the vertical radius is multiplied by this number.").defineInRange("vertical_scale", 1.0D, 0, 10);

        ignoreVerticalRadius = builder.comment("Causes all blends to be applied from the bottom to the top of the world.").define("ignore_vertical_radius", false);

        blendUseCooldown = builder.comment("Cooldown between blend uses in ticks (20 ticks = 1 second).").defineInRange("blend_cooldown", 10, 0, 1000);
    }

    static {
        Pair<CommonConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(CommonConfig::new);
        INSTANCE = pair.getLeft();
        SPEC = pair.getRight();
    }
}
