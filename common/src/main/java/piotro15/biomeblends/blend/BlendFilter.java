package piotro15.biomeblends.blend;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;

import java.util.List;

public record BlendFilter<T>(List<T> values, boolean negate) {
    public static final Codec<BlendFilter<Identifier>> RESOURCE_LOCATION_CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.listOf().fieldOf("values").forGetter(BlendFilter::values),
                    Codec.BOOL.optionalFieldOf("negate", false).forGetter(BlendFilter::negate)
            ).apply(instance, BlendFilter::new)
    );

    public static final Codec<BlendFilter<String>> STRING_CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.STRING.listOf().fieldOf("values").forGetter(BlendFilter::values),
                    Codec.BOOL.optionalFieldOf("negate", false).forGetter(BlendFilter::negate)
            ).apply(instance, BlendFilter::new)
    );
}
