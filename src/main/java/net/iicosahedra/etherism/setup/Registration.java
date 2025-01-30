package net.iicosahedra.etherism.setup;

import net.iicosahedra.etherism.processor.data.ComponentData;
import net.iicosahedra.etherism.util.ResourceLoc;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.neoforged.neoforge.internal.versions.neoforge.NeoForgeVersion.MOD_ID;

public class Registration {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.createDataComponents(ResourceKey.createRegistryKey(ResourceLoc.create("data_components")), MOD_ID);


    public static void init(IEventBus modEventBus) {

    }
    // Data Components
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ComponentData>> COMPONENT_DATA = DATA_COMPONENTS.register(
            "component_data",
            () -> DataComponentType.<ComponentData>builder()
                    .persistent(ComponentData.CODEC)
                    .networkSynchronized(ComponentData.STREAM_CODEC)
                    .build()
    );
}
