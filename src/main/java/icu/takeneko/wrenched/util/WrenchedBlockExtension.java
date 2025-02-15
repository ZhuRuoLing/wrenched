package icu.takeneko.wrenched.util;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public interface WrenchedBlockExtension {
    default boolean wrenched$checkBlockState(BlockState blockState) {
        return true;
    }

    @Nullable
    Property<?> wrenched$getProperty(BlockState blockState);
}
