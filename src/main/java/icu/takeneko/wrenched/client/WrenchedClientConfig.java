package icu.takeneko.wrenched.client;

import icu.takeneko.wrenched.Wrenched;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Wrenched.MODID, bus = EventBusSubscriber.Bus.MOD)
public class WrenchedClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue WRENCH_AE2_BLOCKS = BUILDER
        .comment("Whether to use Wrenched wrench logic on ae2 full orientation blocks.")
        .define("shouldWrenchAE2Blocks", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean shouldWrenchAE2Blocks;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        shouldWrenchAE2Blocks = WRENCH_AE2_BLOCKS.get();
    }
}
