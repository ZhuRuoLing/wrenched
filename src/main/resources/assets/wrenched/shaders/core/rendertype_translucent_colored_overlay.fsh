#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 OverlayColor;
uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    color = mix(linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor), OverlayColor, 0.3);
    fragColor = color;
}
