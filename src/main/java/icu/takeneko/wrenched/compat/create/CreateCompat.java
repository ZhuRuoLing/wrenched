package icu.takeneko.wrenched.compat.create;

import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;

public class CreateCompat {
    public static Level wrapLevelIfPossible(Level original) {
        if (ModList.get().isLoaded("create")){
            return wrapLevelInternal(original);
        }
        return original;
    }

    public static Level wrapLevelInternal(Level original) {
        return new VisualizationUnsupported(original);
    }
}
