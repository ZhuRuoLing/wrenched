package icu.takeneko.wrenched;

import icu.takeneko.wrenched.networking.WrenchedNetworking;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Wrenched.MODID)
public class Wrenched {
    public static final String MODID = "wrenched";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Wrenched(IEventBus modEventBus, ModContainer modContainer) {
        WrenchedItems.register(modEventBus);
        modEventBus.addListener(Wrenched::registerPayload);
        LOGGER.info("HELLO WORLD!");
    }

    public static void registerPayload(@NotNull RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        WrenchedNetworking.register(registrar);
    }

    public static ResourceLocation of(String location) {
        return ResourceLocation.tryBuild(MODID, location);
    }
}
