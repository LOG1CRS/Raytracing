package Objects;

import java.awt.*;

public class Sphere extends Object3D{

    private Vector3D origin;
    private float radius;
    private Color color;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Sphere(Vector3D origin, float radius, Color color) {
        super(origin, color);
        setRadius(radius);
    }
}
