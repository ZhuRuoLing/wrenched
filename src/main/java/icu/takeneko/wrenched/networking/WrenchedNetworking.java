package icu.takeneko.wrenched.networking;

import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class WrenchedNetworking {
    public static void register(PayloadRegistrar registrar) {
        registrar.playToServer(
            ServerboundModifyBlockPayload.TYPE,
            ServerboundModifyBlockPayload.STREAM_CODEC,
            ServerboundModifyBlockPayload::handle
        );
    }

}
