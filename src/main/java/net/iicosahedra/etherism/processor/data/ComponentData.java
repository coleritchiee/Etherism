package net.iicosahedra.etherism.processor.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import javax.annotation.Nullable;
import java.util.Optional;

public record ComponentData(
        ComponentType type,
        @Nullable CompoundTag config
) {
    public enum ComponentType {
        TRANSISTOR,
        WIRE,
        CAU,
        CUSTOM
    }

    public static final Codec<ComponentData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.xmap(
                    ComponentType::valueOf,
                    ComponentType::name
            ).fieldOf("type").forGetter(ComponentData::type),
            CompoundTag.CODEC.optionalFieldOf("config").forGetter(data -> Optional.ofNullable(data.config))
    ).apply(instance, (type, config) -> new ComponentData(type, config.orElse(null))));

    public static final StreamCodec<RegistryFriendlyByteBuf, ComponentData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8.map(
                    ComponentType::valueOf,
                    ComponentType::name
            ),
            ComponentData::type,
            ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG),
            data -> Optional.ofNullable(data.config),
            (type, config) -> new ComponentData(type, config.orElse(null))
    );

    public static ComponentData of(ComponentType type) {
        return new ComponentData(type, null);
    }

    public static ComponentData of(ComponentType type, CompoundTag config) {
        return new ComponentData(type, config);
    }

    public boolean isTransistor() {
        return type == ComponentType.TRANSISTOR;
    }

    public boolean isCau() {
        return type == ComponentType.CAU;
    }

    public Optional<String> getGateType() {
        if (config != null && isTransistor()) {
            return Optional.ofNullable(config.getString("gate"));
        }
        return Optional.empty();
    }

    public Optional<Integer> getBoundOpcode() {
        if (config != null && isCau()) {
            return config.contains("opcode") ?
                    Optional.of(config.getInt("opcode")) :
                    Optional.empty();
        }
        return Optional.empty();
    }
}