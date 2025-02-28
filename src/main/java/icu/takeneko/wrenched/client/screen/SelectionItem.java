package icu.takeneko.wrenched.client.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector2f;

import java.util.Objects;

class SelectionItem {
    final Vector2f center;
    final float angle;
    final float detectionAngleStart;
    final float detectionAngleEnd;
    final BlockState state;
    final Component description;

    SelectionItem(
        Vector2f center,
        float angle,
        float detectionAngleStart,
        float detectionAngleEnd,
        BlockState state,
        Component description
    ) {
        this.center = center;
        this.angle = angle;
        this.detectionAngleStart = detectionAngleStart;
        this.detectionAngleEnd = detectionAngleEnd;
        this.state = state;
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SelectionItem) obj;
        return Objects.equals(this.center, that.center) &&
            Float.floatToIntBits(this.angle) == Float.floatToIntBits(that.angle) &&
            Float.floatToIntBits(this.detectionAngleStart) == Float.floatToIntBits(that.detectionAngleStart) &&
            Float.floatToIntBits(this.detectionAngleEnd) == Float.floatToIntBits(that.detectionAngleEnd) &&
            Objects.equals(this.state, that.state) &&
            Objects.equals(this.description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, angle, detectionAngleStart, detectionAngleEnd, state, description);
    }
}