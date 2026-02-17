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

    public final ModConfigSpec.BooleanValue bopCompat;

    private CommonConfig(ModConfigSpec.Builder builder) {
        horizontalScale = builder.defineInRange("horizontal_scale", 1.0D, 0, 10);
        verticalScale = builder.defineInRange("vertical_scale", 1.0D, 0, 10);

        ignoreVerticalRadius = builder.define("ignore_vertical_radius", false);

        blendUseCooldown = builder.defineInRange("blend_cooldown", 10, 0, 1000);

        bopCompat = builder.define("compatibility.biomesoplenty", true);
    }

    static {
        Pair<CommonConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(CommonConfig::new);
        INSTANCE = pair.getLeft();
        SPEC = pair.getRight();
    }
}
