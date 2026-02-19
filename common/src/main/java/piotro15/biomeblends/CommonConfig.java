package piotro15.biomeblends;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {
    public static final CommonConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    public final ForgeConfigSpec.DoubleValue verticalScale;
    public final ForgeConfigSpec.DoubleValue horizontalScale;

    public final ForgeConfigSpec.BooleanValue ignoreVerticalRadius;

    public final ForgeConfigSpec.IntValue blendUseCooldown;

    public final ForgeConfigSpec.BooleanValue bopCompat;

    private CommonConfig(ForgeConfigSpec.Builder builder) {
        horizontalScale = builder.defineInRange("horizontal_scale", 1.0D, 0, 10);
        verticalScale = builder.defineInRange("vertical_scale", 1.0D, 0, 10);

        ignoreVerticalRadius = builder.define("ignore_vertical_radius", false);

        blendUseCooldown = builder.defineInRange("blend_cooldown", 10, 0, 1000);

        bopCompat = builder.define("compatibility.biomesoplenty", true);
    }

    static {
        Pair<CommonConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        INSTANCE = pair.getLeft();
        SPEC = pair.getRight();
    }
}
