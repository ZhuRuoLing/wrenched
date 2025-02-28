package icu.takeneko.wrenched.compat.appeng;

import appeng.api.orientation.IOrientationStrategy;
import appeng.api.orientation.OrientationStrategies;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;

public class AppEngCompat {

    public static boolean checkIsAppEngFullOrientationBlock(BlockState blockState){
        if (ModList.get().isLoaded("ae2")) {
            return __checkIsAppEngFullOrientationBlock(blockState);
        }
        return false;
    }

    private static boolean __checkIsAppEngFullOrientationBlock(BlockState block) {
        return IOrientationStrategy.get(block) == OrientationStrategies.full();
    }
}
