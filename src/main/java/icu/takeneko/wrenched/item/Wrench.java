package icu.takeneko.wrenched.item;

import icu.takeneko.wrenched.util.WrenchedBlockExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

public class Wrench extends Item {
    public static final Property<?>[] SUPPORTED_PROPERTIES = {
        BlockStateProperties.FACING,
        BlockStateProperties.FACING_HOPPER,
        BlockStateProperties.HORIZONTAL_FACING,
        BlockStateProperties.ORIENTATION,
        BlockStateProperties.AXIS,
        BlockStateProperties.HORIZONTAL_AXIS
    };

    public Wrench(Properties properties) {
        super(properties);
    }

    public static boolean ableToUseWrench(Level level, BlockPos pos, Player entity) {
        BlockState state = level.getBlockState(pos);

        if (state.getBlock() instanceof WrenchedBlockExtension extension) {
            return extension.wrenched$checkBlockState(state);
        }
        return true;
    }

    public static Property<?> findModifyableProperty(BlockState state) {
        Property<?> result = null;
        if (state.getBlock() instanceof WrenchedBlockExtension changeable) {
            result = changeable.wrenched$getProperty(state);
        }
        if (result != null) {
            return result;
        }
        for (Property<?> supportedProperty : SUPPORTED_PROPERTIES) {
            if (state.hasProperty(supportedProperty)) {
                return supportedProperty;
            }
        }
        return null;
    }
}
