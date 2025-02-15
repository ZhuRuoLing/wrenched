package icu.takeneko.wrenched.client;

import net.irisshaders.iris.api.v0.IrisApi;
import net.neoforged.fml.ModList;

public class IrisHelper {
    public static boolean isShaderEnabled() {
        if (ModList.get().isLoaded("iris")) {
            return isShaderEnabledInternal();
        }
        return false;
    }

    private static boolean isShaderEnabledInternal() {
        return IrisApi.getInstance().isShaderPackInUse();
    }
}
