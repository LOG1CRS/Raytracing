package Objects;

import java.awt.*;

public abstract class Object3D {
    private Vector3D origin;
    private Color color;

    public Vector3D getOrigin() {
        return origin;
    }

    public void setOrigin(Vector3D origin) {
        this.origin = origin;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Object3D(Vector3D origin, Color color) {
        setOrigin(origin);
        setColor(color);
    }
}
