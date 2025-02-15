package icu.takeneko.wrenched.mixin;

import icu.takeneko.wrenched.util.WrenchedBlockExtension;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PistonBaseBlock.class)
abstract class PistonBaseBlockMixin implements WrenchedBlockExtension {
    @Override
    public boolean wrenched$checkBlockState(BlockState blockState) {
        return !blockState.getValue(PistonBaseBlock.EXTENDED);
    }

    @Override
    public @Nullable Property<?> wrenched$getProperty(BlockState blockState) {
        return PistonBaseBlock.FACING;
    }
}
