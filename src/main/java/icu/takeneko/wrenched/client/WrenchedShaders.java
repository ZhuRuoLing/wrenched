package icu.takeneko.wrenched.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import icu.takeneko.wrenched.Wrenched;
import net.minecraft.client.renderer.ShaderInstance;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

public class WrenchedShaders {
    static ShaderInstance renderTypeColoredOverlayShader;
    static ShaderInstance ringShader;
    static ShaderInstance selectionShader;


    public static void register(RegisterShadersEvent event) {
        try {
            event.registerShader(
                new ShaderInstance(
                    event.getResourceProvider(),
                    Wrenched.of("rendertype_translucent_colored_overlay"),
                    DefaultVertexFormat.BLOCK
                ),
                it -> renderTypeColoredOverlayShader = it
            );
            event.registerShader(
                new ShaderInstance(
                    event.getResourceProvider(),
                    Wrenched.of("ring"),
                    DefaultVertexFormat.POSITION_COLOR
                ),
                it -> ringShader = it
            );
            event.registerShader(
                new ShaderInstance(
                    event.getResourceProvider(),
                    Wrenched.of("selection"),
                    DefaultVertexFormat.POSITION_COLOR
                ),
                it -> selectionShader = it
            );
        } catch (Exception e) {
            Wrenched.LOGGER.error("Shader loading has failed.", e);
        }
    }

    public static ShaderInstance getSelectionShader() {
        return selectionShader;
    }

    public static ShaderInstance getRingShader() {
        return ringShader;
    }

    public static ShaderInstance getRenderTypeColoredOverlayShader() {
        return renderTypeColoredOverlayShader;
    }
}
