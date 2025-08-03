package piotro15.biomeblends;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {
    public static final CommonConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    public final ForgeConfigSpec.DoubleValue verticalScale;
    public final ForgeConfigSpec.DoubleValue horizontalScale;

    public final ForgeConfigSpec.BooleanValue ignoreVerticalRadius;

    private CommonConfig(ForgeConfigSpec.Builder builder) {
        horizontalScale = builder.comment("When any blend is applied, the horizontal radius is multiplied by this number.").defineInRange("horizontal_scale", 1.0D, 0, 10);
        verticalScale = builder.comment("When any blend is applied, the vertical radius is multiplied by this number.").defineInRange("vertical_scale", 1.0D, 0, 10);
        ignoreVerticalRadius = builder.comment("Causes all blends to be applied from the bottom to the top of the world.").define("ignore_vertical_radius", false);
    }

    static {
        Pair<CommonConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        INSTANCE = pair.getLeft();
        SPEC = pair.getRight();
    }
}
